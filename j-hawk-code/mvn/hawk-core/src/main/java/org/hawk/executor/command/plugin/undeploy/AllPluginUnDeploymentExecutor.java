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
import org.hawk.plugin.HawkPluginServiceImpl;
import org.hawk.plugin.IHawkPluginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class AllPluginUnDeploymentExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(AllPluginUnDeploymentExecutor.class.getName());
    @Autowired(required = true)
       
    private HawkPluginServiceImpl hawkPluginServiceImpl;

    public IHawkPluginService getHawkPluginService() {
        return hawkPluginServiceImpl;
    }

    public void setHawkPluginService(HawkPluginServiceImpl hawkPluginService) {
        this.hawkPluginServiceImpl = hawkPluginService;
    }

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        boolean status = false;
        AllPluginUnDeploymentCommand allPluginUnDeploymentCommand = null;
        if (hawkCommand instanceof AllPluginUnDeploymentCommand) {
            //allPluginUnDeploymentCommand = (AllPluginUnDeploymentCommand) hawkCommand;
            
        } else {
           //hrow new HawkError("You cant be here.. have you really implemented any code ???");
        }
        
        return status;
    }
}
