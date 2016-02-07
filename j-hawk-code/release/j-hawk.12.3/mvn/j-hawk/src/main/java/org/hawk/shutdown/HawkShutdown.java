
package org.hawk.shutdown;

import org.hawk.logger.HawkLogger;
import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;

/**
 *
 * @author msahu
 */
public class HawkShutdown implements Runnable{
    private static final HawkLogger logger = HawkLogger.getLogger(HawkShutdown.class.getName());

    @Override
    public void run() {
        logger.info("Shutting down Hawk");
        //One last time ... dump me !!!
        BuildModeEnum buildModeEnum = ScriptUsage.getInstance().getBuildMode();
        if(buildModeEnum == buildModeEnum.PERF){
            HawkPerfDataCollector.dump(true);
            //HawkPerfDataCollector.processPerfData();
        }
        logger.info("Hawk Shutdown complete");
    }

}
