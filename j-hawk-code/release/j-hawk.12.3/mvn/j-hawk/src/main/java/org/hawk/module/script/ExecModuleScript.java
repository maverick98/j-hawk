

package org.hawk.module.script;

import org.hawk.ds.Stack;
import org.hawk.module.lib.SystemModule;
import org.hawk.module.script.FunctionScript.FunctionInvocationInfo;
import java.util.Map.Entry;
import org.hawk.exception.HawkException;
import org.hawk.module.IModule;
import org.hawk.module.ModuleFactory;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.task.SubTaskContainer;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;


/**
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
public class ExecModuleScript extends SingleLineScript{

    private  static final Pattern EXEC_MODULE_PATTERN=
    Pattern.compile("\\s*"+exec+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)->([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

    public ExecModuleScript(){

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

    
    public Map<Integer,IScript> getParamMap(){
        return this.getExecParameterScript().getParamMap();
    }

    private  boolean parseModuleParameters() throws HawkException{

        this.subTaskContainer.clearParams();

        for(Entry<Integer,IScript> entry:this.getParamMap().entrySet()){

            Object paramObj = entry.getValue().toJava();

            this.subTaskContainer.addParam(paramObj);

        }

        return true;
    }
   
    @ScriptPattern
    public static Pattern getPattern() {
        return EXEC_MODULE_PATTERN;
    }

    @CreateScript
    public static ExecModuleScript createScript(Map<Integer,String> lineModuleMatcherMap) throws HawkException{

        if(lineModuleMatcherMap == null){

            return null;

        }

        ExecModuleScript execScript = new ExecModuleScript();

        String moduleName = lineModuleMatcherMap.get(1);

        String moduleSubTask = lineModuleMatcherMap.get(2);

        String moduleParams = lineModuleMatcherMap.get(3);

        ExecParameterScript execParameterScript = (ExecParameterScript)ExecParameterScript.createScript(moduleParams);

        execScript.setExecParameterScript(execParameterScript);

        //Map<IModule,Map<String,SubTaskContainer>> cachedSubTasks = ModuleFactory.getInstance().getSubTasks();

        Map<String,IModule> moduleMap = ModuleFactory.getInstance().getModules();

        IModule moduleInstance = moduleMap.get(moduleName);

        if (moduleInstance == null){

            //Look for alias

            AliasScript aliasScript= AliasScript.getInstance();

            moduleName = aliasScript.get(moduleName);

            moduleInstance = moduleMap.get(moduleName);
        
        }

        if(moduleInstance == null){

            throw new HawkException("Could not find the module {"+moduleName+"}");

        }

        SubTaskContainer subTaskContainer = moduleInstance.getSubTask(moduleSubTask);

        if(subTaskContainer == null){

            throw new HawkException("Invalid task{"+moduleName+"->"+moduleSubTask+"}");

        }

        subTaskContainer.setTaskName(moduleSubTask);

        subTaskContainer.setTaskIteration(1);

        subTaskContainer.setModule(moduleName);

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
    public IScript execute() throws HawkException {

        this.getExecParameterScript().execute();

        this.parseModuleParameters();

        IScript status = null;

        FunctionInvocationInfo functionInvocationInfo =new FunctionInvocationInfo
                                                        (

                                                            this.findEncapsulatingFunctionScript()
                                                                .getFunctionName(),

                                                                this.getLineNumber()
                                                        );


        Stack<FunctionScript.FunctionInvocationInfo> functionStack = SystemModule.getFunctionStack().get();

        if(functionStack == null){

            functionStack = new Stack<FunctionScript.FunctionInvocationInfo>();

        }

        functionStack.push(functionInvocationInfo);

        SystemModule.getFunctionStack().set(functionStack);

        status = this.subTaskContainer.execute();

        functionStack.pop();
        
        return status;
    }

    @Override
    public String toString(){
        return "";
    }
    @Override
    public boolean isVariable() {
        return false;
    }

    private static final HawkLogger logger = HawkLogger.getLogger(ExecModuleScript.class.getName());
}




