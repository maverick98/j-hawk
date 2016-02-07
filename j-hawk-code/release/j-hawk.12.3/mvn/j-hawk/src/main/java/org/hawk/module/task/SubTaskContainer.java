


package org.hawk.module.task;

import org.hawk.exception.HawkException;
import org.hawk.module.IModule;
import org.hawk.module.ModuleFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.logger.HawkLogger;
import org.hawk.module.ModuleExecutor;
import org.hawk.module.annotation.CollectPerfData;
import org.hawk.module.script.IScript;
import org.hawk.module.script.LocalVarDeclScript;
import org.hawk.module.script.ScriptInterpreter;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.module.script.type.StringDataType;
import org.hawk.util.StringUtil;
import static org.hawk.constant.HawkConstant.*;

/**
 * This contains tasks under a particular module.
 * 
 * @version 1.0 7 Apr, 2010
 * @author msahu
 * @see ModuleExecutor
 * @see ScriptInterpreter
 */
public class SubTaskContainer implements Comparable<SubTaskContainer>{

    private static final HawkLogger logger = HawkLogger.getLogger(SubTaskContainer.class.getName());
    /**
     * Module of the subtask
     */
    IModule module;

    /**
     * Sub task name
     */
    String taskName;

    /**
     * Sub task's sequence
     */
    int sequence;

    /**
     * Sub task's <tt>Method</tt> object
     */
    Method taskMethod;

    /**
     * Sub task's iteration
     */
    int taskIteration;

    /**
     * If a exception occurs during sub task's execution,
     * whether to ignore this or not.
     */
    boolean ignoreException=false;


    /**
     * This is mainly used for display purpose... training mode.
     */
    String hawkParam = "";

    /**
     * This is used as method  args when run in prop mode.
     */
    String hawkExecutionParams = "";


    /**
     * params for subtasks
     * 
     */
    List<Object> params = new ArrayList<Object>();

    public Object[] getParams() {
        
        Object[] args = new Object[this.params.size()];
        int i=0;
        for(Object param:this.params){
            args[i] = param;
            i++;
        }
        Object[] rtn = new Object[1];
        rtn[0]=args;
        return rtn;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public boolean addParam(Object param){
        return this.params.add(param);
    }

    public boolean clearParams(){
        this.params.clear();
        return true;
    }

    

    public boolean isIgnoreException() {
        return ignoreException;
    }

    public void setIgnoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;
    }


    

    public IModule getModule() {
        return module;
    }

    public void setModule(IModule module) {
        this.module = module;
    }

    public void setModule(String moduleName){
        Map<String,IModule> moduleMap = ModuleFactory.getInstance().getModules();
        this.module = moduleMap.get(moduleName);
    }



    /**
     * Besides from executing subtask it collects it's execution time
     * which in turn helps plotting the graph.
     * @return
     * @throws org.hawk.exception.HawkException
     * @see HawkPerfDataCollector
     */
    public IScript execute()throws HawkException{
        IScript status =  LocalVarDeclScript.createDummyScript();
        
        boolean shouldCollectPerfData = this.shouldCollectPerfData();
        Object result = null;
        try {

                if(shouldCollectPerfData){
                    HawkPerfDataCollector.start(this.getModule(),this.getTaskName());
                }
            
                result = this.taskMethod.invoke(this.module,this.getParams());
                if(shouldCollectPerfData){
                    HawkPerfDataCollector.end(this.getModule(),this.getTaskName());
                }
                status.getVariableValue().setVariableValue(new StringDataType(result.toString()));
        } catch (Throwable th) {
            if(shouldCollectPerfData){
                HawkPerfDataCollector.endWithFailure(this.getModule(),this.getTaskName());

                if(!this.isIgnoreException()){
                    th.printStackTrace();
                    logger.severe("error while executing the task {"+this.getTaskSignature()+"} ..."+th.getMessage());
                    System.out.println("error while executing the task {"+this.getTaskSignature()+"} ..."+th.getMessage());
                    throw new HawkException(th);
                }
            }else{
                throw new HawkException(th);
            }

        }
        return status;
    }

    private boolean shouldCollectPerfData(){
        boolean shouldCollect = true;
        BuildModeEnum buildMode = ScriptUsage.getInstance().getBuildMode();
        if(buildMode == null || buildMode == BuildModeEnum.SCRIPTING){
            return false;
        }
        Class moduleClazz = this.getModule().getClass();
        if(moduleClazz.isAnnotationPresent(CollectPerfData.class)){

            CollectPerfData collectPerfData = (CollectPerfData) moduleClazz.getAnnotation(CollectPerfData.class);
            shouldCollect = !collectPerfData.myself();
        }else{
            shouldCollect= true;
        }
        return shouldCollect;
    }

    
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTaskName() {
        return taskName;
    }
    public String getTaskSignature(){
        return this.getTaskName() + "("+this.getHawkParam()+")";
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getHawkParam() {
        return hawkParam;
    }

    public void setHawkParam(String hawkParam) {
        this.hawkParam = hawkParam;
    }

    public String getHawkExecutionParams() {
        return hawkExecutionParams;
    }

    public void setHawkExecutionParams(String hawkExecutionParams) {
        this.hawkExecutionParams = hawkExecutionParams;
        if(!StringUtil.isNullOrEmpty(this.hawkExecutionParams)){
            List<Object> hawkExecutionParamsList = new ArrayList<Object>();
            StringTokenizer strTok = new StringTokenizer(this.hawkExecutionParams, HAWK_EXECUTION_PARAMS_DELIMETER);
            while(strTok.hasMoreElements()){
                hawkExecutionParamsList.add(strTok.nextElement());
            }
            this.setParams(hawkExecutionParamsList);
        }
    }
    
    
    public Method getTaskMethod() {
        return taskMethod;
    }

    public void setTaskMethod(Method taskMethod) {
        this.taskMethod = taskMethod;
    }

    public int getTaskIteration() {
        return taskIteration;
    }

    public void setTaskIteration(int taskIteration) {
        this.taskIteration = taskIteration;
    }

    

    public int compareTo(SubTaskContainer otherTaskContainer) {
        if(otherTaskContainer == null ){
            return -1;
        }
        return this.getSequence()-otherTaskContainer.getSequence();
    }

    @Override
    public String toString() {
        return "SubTaskContainer{" + "module=" + module + "taskName=" + taskName + "params=" + params + '}';
    }

    
    
}




