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
        logger.info("Shutting down Hawk");
        PerfDataProcessor hawkPerfDataCollector =AppContainer.getInstance().getBean( PerfDataProcessor.class);
        //One last time ... dump me !!!
       if(AppContainer.getInstance().getBean(HawkCommandParser.class).shouldCollectPerfData()){
            hawkPerfDataCollector.dump(true);
            
        }
        logger.info("Hawk Shutdown complete");
    }

}
