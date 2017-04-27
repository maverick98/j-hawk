/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.lang.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.common.di.AppContainer;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.execBackground;
import org.hawk.lang.function.ExecFunctionScript;
import org.hawk.lang.function.ExecParameterScript;
import org.hawk.lang.function.FunctionInvocationInfo;
import org.hawk.lang.function.IFunctionExecutor;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.common.di.ScanMe;
import org.hawk.lang.function.FunctionExecutor;

/**
 *
 * @author msahu
 */
@ScanMe(true)
public class ExecBackgroundSingleLineScript extends ExecFunctionScript {

    private static final HawkLogger logger = HawkLogger.getLogger(ExecBackgroundSingleLineScript.class.getName());
    private static final Pattern FUNCTION_EXEC_BACKGROUND_PATTERN
            = Pattern.compile("\\s*" + execBackground + "\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

    private static List<Thread> threads = new ArrayList<Thread>();
    private IScript result = null;
    
    
     public static class ExecBackground extends SingleLineGrammar {

        @Override
        public String toString() {
            return "ExecBackground" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getExecBackground().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getExecBackground().getLinePattern();
    } 
    /**
     * ExecFunctionScript pattern.
     *
     * @return returns ExecFunctionScript
     * @see ScriptPattern
     */
    /*
    public Pattern getPattern() {
        return FUNCTION_EXEC_BACKGROUND_PATTERN;
    }
    */

    @Override
    public ExecBackgroundSingleLineScript createScript(Map<Integer, String> lineFunctionMatcherMap) throws Exception {

        if (lineFunctionMatcherMap == null) {
            return null;
        }
        ExecBackgroundSingleLineScript functionScript = new ExecBackgroundSingleLineScript();
        functionScript.setFunctionName(lineFunctionMatcherMap.get(1));
        ExecParameterScript execParameterScript = (ExecParameterScript) new ExecParameterScript().createScript(lineFunctionMatcherMap.get(2));
        functionScript.setExecParameterScript(execParameterScript);
        return functionScript;
    }

    @Override
    public IScript execute() throws Exception {

        this.getExecParameterScript().execute();

        FunctionInvocationInfo functionInvocationInfo = new FunctionInvocationInfo();
        functionInvocationInfo.setEncapsulatingFunctionName(this.getFunctionName());
        functionInvocationInfo.setLineNo(this.getLineNumber());
        functionInvocationInfo.setFunctionName(this.getFunctionName());
        functionInvocationInfo.setParamMap(this.cloneParamMap());
        BackgroundTask backGroundTask = new BackgroundTask(functionInvocationInfo);
        Thread thread = new Thread(backGroundTask);
        thread.setDaemon(true);
        threads.add(thread);
        thread.start();

        return null;
    }

    private class BackgroundTask implements Runnable {

        private FunctionInvocationInfo functionInvocationInfo;

        public FunctionInvocationInfo getFunctionInvocationInfo() {
            return functionInvocationInfo;
        }

        public void setFunctionInvocationInfo(FunctionInvocationInfo functionInvocationInfo) {
            this.functionInvocationInfo = functionInvocationInfo;
        }

        public BackgroundTask(FunctionInvocationInfo functionInvocationInfo) {
            this.functionInvocationInfo = functionInvocationInfo;
        }

        public BackgroundTask() {

        }

        @Override
        public void run() {
            try {
                result = this.execute();
            } catch (Exception ex) {
                logger.error(null, ex);
            }
        }

        public IScript execute() throws Exception {

            IFunctionExecutor functionExecutor = AppContainer.getInstance().getBean(FunctionExecutor.class);
            IObjectScript rtnVar = functionExecutor.execute(functionInvocationInfo);
            return rtnVar;

        }
    }

}
