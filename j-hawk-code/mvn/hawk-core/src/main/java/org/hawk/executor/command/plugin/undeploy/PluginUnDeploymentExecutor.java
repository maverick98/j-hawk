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
