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

package org.hawk.executor.command.plugin.downloaded;

import java.util.Set;


import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.HawkPlugin;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ShowDownloadedPluginExecutor extends  DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowDownloadedPluginExecutor.class.getName());
    
    
    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        
        
        if (!(hawkCommand instanceof ShowDownloadedPluginCommand)) {
        //    throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        
        Set<HawkPlugin> hawkPlugins = this.getHawkPluginService().findDownloadedPlugins();
        for(HawkPlugin hawkPlugin : hawkPlugins){
            logger.info(hawkPlugin.getName());
        }
        return true;
    }
}

