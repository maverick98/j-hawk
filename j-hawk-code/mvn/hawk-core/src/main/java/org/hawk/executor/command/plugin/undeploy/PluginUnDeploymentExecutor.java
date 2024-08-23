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
package org.hawk.executor.command.plugin.undeploy;



import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.IHawkPluginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class PluginUnDeploymentExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(PluginUnDeploymentExecutor.class.getName());
    @Autowired(required = true)
       
    private IHawkPluginService hawkPluginService;

    public IHawkPluginService getHawkPluginService() {
        return hawkPluginService;
    }

    public void setHawkPluginService(IHawkPluginService hawkPluginService) {
        this.hawkPluginService = hawkPluginService;
    }

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        boolean status;
        PluginUnDeploymentCommand pluginUnDeploymentCommand = null;
        if (hawkCommand instanceof PluginUnDeploymentCommand) {
            pluginUnDeploymentCommand = (PluginUnDeploymentCommand) hawkCommand;
        } else {
         //   throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String pluginArchive = pluginUnDeploymentCommand.getPluginArchive();
        status = this.getHawkPluginService().unDeploy(pluginArchive);
        return status;
    }
}
