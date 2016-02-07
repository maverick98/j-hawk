


package org.hawk.module.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.http.DefaultHttpSessionExecutor;
import org.hawk.http.HawkHttpAgent;
import org.hawk.module.AbstractModule;
import org.hawk.module.annotation.CollectPerfData;
import org.hawk.module.annotation.Module;
import org.hawk.module.annotation.SubTask;
import org.hawk.util.HawkUtil;

/**
 * This is a hawk library module .
 * This hits the targetURL with the parameters.
 * This can upload files too.
 * @version 1.0 1 Aug, 2010
 * @author msahu
 * @see SystemModule
 */
@CollectPerfData(myself=true)
@Module(name="HttpModule")
public class HttpModule extends AbstractModule{


    private List<Thread> threads = new ArrayList<Thread>();

     @Override
    public String toString() {
        return this.getName();
    }
    public String getName() {
        return "HttpModule";
    }
    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
    @SubTask(name="execute",sequence = 1,ignoreException=false,hawkParam="var hawkStruct")
    public boolean execute(Object ... args) throws Exception{
        for(Object arg:args){
            Map<String,String> map = (Map<String,String>)arg;
            String useThread = HawkUtil.convertHawkStringToJavaString(map.get("concurrent"));
            HawkHttpAgent hawkHttpAgent = new HawkHttpAgent(this,new DefaultHttpSessionExecutor(),arg);
            if("TRUE".equals(useThread)){
                logger.info("creating hawk thread....");
                Thread hawkHttpAgentThread = new Thread(hawkHttpAgent);
                hawkHttpAgentThread.setName(map.get("actionName"));
                hawkHttpAgentThread.start();
                this.threads.add(hawkHttpAgentThread);
            }else{
                logger.info("serial execution");
                hawkHttpAgent.processInstruction();
            }
        }
        return true;
    }

    /**
     * This waits for all the hawk threads to finish their tasks.
     * @param allThreads list of all threads which are started.
     * @return true on success
     */
    @SubTask(name="wait",sequence = 1,ignoreException=false,hawkParam="")
    public boolean waitforThreadsToFinish(Object ... args) throws Exception{
        boolean status = false;
        if(this.threads == null || this.threads.isEmpty()){
            return false;
        }
        for(Thread thread:this.threads){
            try {
                thread.join();
            } catch (InterruptedException ex) {
                logger.severe( null, ex);
                return false;
            }
        }
        status = true;
        return status;
    }

    /**
     * This resets all the hawk threads.
     * @param allThreads list of all threads which are started.
     * @return true on success
     */
    @SubTask(name="reset",sequence = 1,ignoreException=false ,hawkParam="")
    public boolean resetThreads(Object ... args) throws Exception{
        boolean status = false;
        this.threads = new ArrayList<Thread>();
        HawkPerfDataCollector.dump(true);
        status = true;
        return status;
    }

    
    


    private static final HawkLogger logger = HawkLogger.getLogger(HttpModule.class.getName());
}




