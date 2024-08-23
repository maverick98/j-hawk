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
package org.hawk.executor.command.plugin.deploy;



import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class PluginDeploymentExecutor extends  DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(PluginDeploymentExecutor.class.getName());
    
    
    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        boolean status;
        PluginDeploymentCommand pluginDeploymentCommand = null;
        if (hawkCommand instanceof PluginDeploymentCommand) {
            pluginDeploymentCommand = (PluginDeploymentCommand) hawkCommand;
        } else {
         //   throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String pluginArchive = pluginDeploymentCommand.getPluginArchive();
        status = this.getHawkPluginService().deploy(pluginArchive);
        return status;
    }
}
