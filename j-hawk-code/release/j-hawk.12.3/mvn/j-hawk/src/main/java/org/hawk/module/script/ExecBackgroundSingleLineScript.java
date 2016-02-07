
package org.hawk.module.script;
import java.util.ArrayList;
import java.util.List;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @author msahu
 */

public class ExecBackgroundSingleLineScript extends ExecFunctionScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecBackgroundSingleLineScript.class.getName());
    private  static final Pattern FUNCTION_EXEC_BACKGROUND_PATTERN=
    Pattern.compile("\\s*"+execBackground+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

    private static List<Thread> threads = new ArrayList<Thread>();
    private IScript result = null;

    /**
     * ExecFunctionScript pattern.
     * @return returns ExecFunctionScript
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern(){
        return FUNCTION_EXEC_BACKGROUND_PATTERN;
    }




    @CreateScript
    public static ExecBackgroundSingleLineScript createScript(Map<Integer,String> lineFunctionMatcherMap) throws HawkException{

        if(lineFunctionMatcherMap == null){
            return null;
        }
        ExecBackgroundSingleLineScript functionScript = new ExecBackgroundSingleLineScript();
        functionScript.setFunctionName(lineFunctionMatcherMap.get(1));
        ExecParameterScript execParameterScript = (ExecParameterScript)ExecParameterScript.createScript(lineFunctionMatcherMap.get(2));
        functionScript.setExecParameterScript(execParameterScript);
        return functionScript;
    }

    @Override
    public IScript execute() throws HawkException {

        this.getExecParameterScript().execute();

        String mangledParams = this.getMangledParams();
        String mangledFunctionName = this.getFunctionName()+mangledParams;

        BackgroundTask backGroundTask = new BackgroundTask();
        backGroundTask.setFunctionName(this.getFunctionName());
        backGroundTask.setLineNumber(this.getLineNumber());
        backGroundTask.setMangledFunctionName(mangledFunctionName);
        backGroundTask.setClonedParamMap(this.cloneParamMap());
        Thread thread = new Thread(backGroundTask);
        thread.setDaemon(true);
        threads.add(thread);
        thread.start();
        
        return null;
    }




    private  class BackgroundTask implements Runnable{

        private String functionName;
        private int lineNumber;
        private String mangledFunctionName;

        Map<Integer,IScript> clonedParamMap;

        public String getMangledFunctionName() {
            return mangledFunctionName;
        }

        public void setMangledFunctionName(String mangledFunctionName) {
            this.mangledFunctionName = mangledFunctionName;
        }

        public Map<Integer, IScript> getClonedParamMap() {
            return clonedParamMap;
        }

        public void setClonedParamMap(Map<Integer, IScript> clonedParamMap) {
            this.clonedParamMap = clonedParamMap;
        }

        public String getFunctionName() {
            return functionName;
        }

        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }


        public BackgroundTask(){

        }
        public void run() {
            try {
                result = this.execute();
            } catch (HawkException ex) {
               logger.severe( null, ex);
            }
        }
        public IScript execute() throws HawkException {


            return ExecFunctionScript.execute
                                        (
                                            this.functionName,
                                            this.lineNumber,
                                            this.mangledFunctionName,
                                            this.clonedParamMap
                                        );

        }
    }
}
