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
package org.hawk.shutdown;

import org.hawk.data.perf.PerfDataProcessor;
import org.common.di.AppContainer;
import org.hawk.executor.command.HawkCommandParser;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author msahu
 */
public class HawkShutdown implements Runnable{
    private static final HawkLogger logger = HawkLogger.getLogger(HawkShutdown.class.getName());

    @Override
    public void run() {
       // logger.info("Shutting down Hawk");
        PerfDataProcessor hawkPerfDataCollector =AppContainer.getInstance().getBean( PerfDataProcessor.class);
        //One last time ... dump me !!!
       if(AppContainer.getInstance().getBean(HawkCommandParser.class).shouldCollectPerfData()){
            hawkPerfDataCollector.dump(true);
            
        }
      //  logger.info("Hawk Shutdown complete");
    }

}
