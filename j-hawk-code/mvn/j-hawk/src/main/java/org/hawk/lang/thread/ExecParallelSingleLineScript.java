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
import org.common.di.AppContainer;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.currentThreadName;
import static org.hawk.lang.constant.HawkLanguageKeyWord.reset;
import static org.hawk.lang.constant.HawkLanguageKeyWord.wait;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.function.ExecFunctionScript;
import org.hawk.lang.function.ExecParameterScript;
import org.hawk.lang.function.FunctionInvocationInfo;
import org.hawk.lang.function.IFunctionExecutor;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.common.di.ScanMe;
import org.hawk.lang.function.FunctionExecutor;

/**
 *
 * @author msahu
 */

@ScanMe(true)
public class ExecParallelSingleLineScript extends ExecFunctionScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecParallelSingleLineScript.class.getName());
  
    private static ThreadLocal<List<Thread>> threadLocals = new ThreadLocal<List<Thread>>();

    private IScript result = null;

    

    public List<Thread> getThreads() {
        List<Thread> threads = threadLocals.get();
        if(threads == null){
            threads = new ArrayList<Thread>();
            this.setThreads(threads);
        }
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        threadLocals.set(threads);
    }

    

    public static class ExecParallel extends SingleLineGrammar {

        @Override
        public String toString() {
            return "ExecParallel" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getExecParallel().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getExecParallel().getLinePattern();
    } 

   
    

    
    @Override
    public  ExecParallelSingleLineScript createScript(Map<Integer,String> lineFunctionMatcherMap) throws Exception{

        if(lineFunctionMatcherMap == null){
            return null;
        }
        ExecParallelSingleLineScript functionScript = new ExecParallelSingleLineScript();
        functionScript.setFunctionName(lineFunctionMatcherMap.get(1));
        ExecParameterScript execParameterScript = (ExecParameterScript) new ExecParameterScript().createScript(lineFunctionMatcherMap.get(2));
        functionScript.setExecParameterScript(execParameterScript);


        return functionScript;
    }

    @Override
    public IScript execute() throws Exception {

        String functionName = this.getFunctionName();
        
        if(wait.equals(functionName)){
            this.waitforThreadsToFinish();
        }else if(reset.equals(functionName)){
            this.reset();
        }else if(currentThreadName.equals(functionName)){
            Variable curThreadVar = new Variable(VarTypeEnum.VAR, null,currentThreadName);
            IDataType curThreadName= new StringDataType(Thread.currentThread().getName());
            curThreadVar.setValue(curThreadName);
            IObjectScript rtn = LocalVarDeclScript.createDummyBooleanScript();
            rtn.setVariable(curThreadVar);
            rtn.setVariableValue(curThreadVar);
            System.out.println(curThreadName.toString());
            return rtn;
        } else {

            this.getParamMap().clear();
            FunctionInvocationInfo functionInvocationInfo = new FunctionInvocationInfo();
            functionInvocationInfo.setEncapsulatingFunctionName(this.getFunctionName());
            functionInvocationInfo.setLineNo(this.getLineNumber());
            functionInvocationInfo.setFunctionName(this.getFunctionName());
           
            functionInvocationInfo.setParamMap(this.cloneParamMap());
            ParallelTask parallelTask = new ParallelTask(functionInvocationInfo);
            Thread thread = new Thread(parallelTask);
            this.getThreads().add(thread);
            thread.start();
        }
        return null;
    }


   /**
     * This waits for all the hawk threads to finish their tasks.
     * @param allThreads list of all threads which are started.
     * @return true on success
     */
    
    public boolean waitforThreadsToFinish(){
        
        if(this.getThreads() == null || this.getThreads().isEmpty()){
            return false;
        }
        for(Thread thread:this.getThreads()){
            try {
                thread.join();
            } catch (InterruptedException ex) {
                logger.error( null, ex);
                return false;
            }
        }
        
        return true;

    }
    public boolean reset(){
        boolean status = true;
        if(this.getThreads()==null || this.getThreads().isEmpty()){
            status = false;
        }else{
            this.setThreads(new ArrayList<Thread>());
            status = true;
        }
        return status;
    }

    private  class ParallelTask implements Runnable{

        private FunctionInvocationInfo  functionInvocationInfo;

        public FunctionInvocationInfo getFunctionInvocationInfo() {
            return functionInvocationInfo;
        }

        public void setFunctionInvocationInfo(FunctionInvocationInfo functionInvocationInfo) {
            this.functionInvocationInfo = functionInvocationInfo;
        }
       
        
        public ParallelTask(){
            
        }
        public ParallelTask(FunctionInvocationInfo functionInvocationInfo){
            this.functionInvocationInfo = functionInvocationInfo;
        }
      
        @Override
        public void run() {
            try {
                result = this.execute();
            } catch (Exception ex) {
               logger.error( null, ex);
            }
        }
        public IScript execute() throws Exception {

            IFunctionExecutor functionExecutor  = AppContainer.getInstance().getBean(FunctionExecutor.class);
            IObjectScript rtnVar = functionExecutor.execute(functionInvocationInfo);
            return rtnVar;
            
        }
    }
   
}
