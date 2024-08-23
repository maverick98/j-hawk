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
package org.hawk.executor.command.plugin.nyd;

import java.util.Set;


import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.HawkPlugin;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ShowNYDPluginExecutor extends  DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowNYDPluginExecutor.class.getName());
   

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {


        if (!(hawkCommand instanceof ShowNYDPluginCommand)) {
       //     throw new HawkError("You cant be here.. have you really implemented any code ???");
        }

        Set<HawkPlugin> hawkPlugins = this.getHawkPluginService().findNYDPlugins();
        for (HawkPlugin hawkPlugin : hawkPlugins) {
            logger.info(hawkPlugin.getName());
        }
        return true;
    }
}
