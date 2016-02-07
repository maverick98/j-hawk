
package org.hawk.module.script;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.StringDataType;
import org.hawk.module.script.type.Variable;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @author msahu
 */

public class ExecParallelSingleLineScript extends ExecFunctionScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ExecParallelSingleLineScript.class.getName());
    private  static final Pattern FUNCTION_EXEC_PARALLEL_PATTERN=
    Pattern.compile("\\s*"+execParallel+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

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

    



    /**
     * ExecFunctionScript pattern.
     * @return returns ExecFunctionScript
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern(){
        return FUNCTION_EXEC_PARALLEL_PATTERN;
    }


    

    @CreateScript
    public static ExecParallelSingleLineScript createScript(Map<Integer,String> lineFunctionMatcherMap) throws HawkException{

        if(lineFunctionMatcherMap == null){
            return null;
        }
        ExecParallelSingleLineScript functionScript = new ExecParallelSingleLineScript();
        functionScript.setFunctionName(lineFunctionMatcherMap.get(1));
        ExecParameterScript execParameterScript = (ExecParameterScript)ExecParameterScript.createScript(lineFunctionMatcherMap.get(2));
        functionScript.setExecParameterScript(execParameterScript);


        return functionScript;
    }

    @Override
    public IScript execute() throws HawkException {

        String functionName = this.getFunctionName();
        
        if(wait.equals(functionName)){
            this.waitforThreadsToFinish();
        }else if(reset.equals(functionName)){
            this.reset();
        }else if(currentThreadName.equals(functionName)){
            Variable curThreadVar = new Variable(VarTypeEnum.VAR, null,currentThreadName);
            IDataType curThreadName= new StringDataType(Thread.currentThread().getName());
            curThreadVar.setVariableValue(curThreadName);
            IScript rtn = LocalVarDeclScript.createDummyScript();
            rtn.setVariable(curThreadVar);
            rtn.setVariableValue(curThreadVar);
            System.out.println(curThreadName.toString());
            return rtn;
        } else {

            this.getParamMap().clear();
            String mangledParams = this.getMangledParams();
            String mangledFunctionName = this.getFunctionName()+mangledParams;

            ParallelTask parallelTask = new ParallelTask();
            parallelTask.setFunctionName(functionName);
            parallelTask.setLineNumber(this.getLineNumber());
            parallelTask.setMangledFunctionName(mangledFunctionName);
            parallelTask.setClonedParamMap(this.cloneParamMap());
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
                logger.severe( null, ex);
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

        private String functionName;
        private int lineNumber;
        private String mangledFunctionName;

        private Map<Integer,IScript> clonedParamMap;

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
        

        public ParallelTask(){
            
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
