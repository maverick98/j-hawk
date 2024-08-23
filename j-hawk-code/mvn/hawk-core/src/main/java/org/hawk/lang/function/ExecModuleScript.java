/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.lang.function;

import java.util.Map;
import java.util.Set;
import org.common.di.AppContainer;

import org.hawk.lang.IScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.hawk.module.task.SubTaskContainer;
import org.common.di.ScanMe;
import org.hawk.module.cache.AllModuleCache;

/**
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class ExecModuleScript extends SingleLineScript {

    public ExecModuleScript() {
    }
    private ExecParameterScript execParameterScript = null;

    public ExecParameterScript getExecParameterScript() {
        return execParameterScript;
    }

    public void setExecParameterScript(ExecParameterScript execParameterScript) {
        this.execParameterScript = execParameterScript;
    }

    public String getParams() {
        return this.getExecParameterScript().getParams();
    }

    public Map<Integer, IObjectScript> getParamMap() {
        return this.getExecParameterScript().getParamMap();
    }

    private boolean parseModuleParameters() throws Exception {
        return subTaskContainer.parseModuleParameters(this.getParamMap());
    }

    public static class ExecModule extends SingleLineGrammar {

        @Override
        public String toString() {
            return "ExecModule" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getExecModule().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getExecModule().getLinePattern();
    } 
   
    private static boolean isModuleDeclared(Map<Integer, String> lineModuleMatcherMap) {
        boolean result = false;
        if (lineModuleMatcherMap != null && !lineModuleMatcherMap.isEmpty()) {
            String moduleSubTask = lineModuleMatcherMap.get(1);

            StringBuilder sb = new StringBuilder();
            String moduleName = lineModuleMatcherMap.get(2);
            sb.append(moduleName);
            String subTask = lineModuleMatcherMap.get(3);
            sb.append(subTask);
            if (moduleSubTask != null) {
                result = !moduleSubTask.equalsIgnoreCase(sb.toString());
            }

        }

        return result;
    }

    
    @Override
    public  ExecModuleScript createScript(Map<Integer, String> lineModuleMatcherMap) throws Exception {

        if (lineModuleMatcherMap == null) {

            return null;

        }

        ExecModuleScript execScript = new ExecModuleScript();
        

        String moduleName;

        String subTask;
        if (isModuleDeclared(lineModuleMatcherMap)) {
            
            moduleName = lineModuleMatcherMap.get(2);

            subTask = lineModuleMatcherMap.get(3);
        }else{
            moduleName = null;
            subTask = lineModuleMatcherMap.get(1);
        }   


        String moduleParams = lineModuleMatcherMap.get(4);

        ExecParameterScript execParameterScript = (ExecParameterScript) new ExecParameterScript().createScript(moduleParams);

        execScript.setExecParameterScript(execParameterScript);


        SubTaskContainer subTaskContainer = AppContainer.getInstance().getBean(AllModuleCache.class).lookUpSubTask(moduleName, subTask);

        subTaskContainer.setTaskName(subTask);

        subTaskContainer.setTaskIteration(1);


        execScript.setSubTaskContainer(subTaskContainer);

        return execScript;
    }

    @Override
    public void setOuterMultiLineScript(MultiLineScript outerMultiLineScript) {

        super.setOuterMultiLineScript(outerMultiLineScript);

        this.getExecParameterScript().setOuterMultiLineScript(this.getOuterMultiLineScript());
    }
    private SubTaskContainer subTaskContainer;

    public SubTaskContainer getSubTaskContainer() {
        return subTaskContainer;
    }

    public void setSubTaskContainer(SubTaskContainer subTaskContainer) {
        this.subTaskContainer = subTaskContainer;
    }

    @Override
    public IScript execute() throws Exception {

        this.getExecParameterScript().execute();

        this.parseModuleParameters();

        IScript status;

        FunctionInvocationInfo functionInvocationInfo = new FunctionInvocationInfo
                                                            (
                                                                this.findEncapsulatingFunctionScript().getFunctionName(),
                                                                this.getLineNumber()
                                                            );


        
        status = new AbstractFunctionStackUtility() {

                        @Override
                        public IScript execute() throws Exception {
                            return subTaskContainer.execute();
                        }
                    }.pushExecutePop(functionInvocationInfo);

        return status;
    }

    @Override
    public String toString() {
        return "";
    }
    /*
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 
    
    private static final HawkLogger logger = HawkLogger.getLogger(ExecModuleScript.class.getName());
}
