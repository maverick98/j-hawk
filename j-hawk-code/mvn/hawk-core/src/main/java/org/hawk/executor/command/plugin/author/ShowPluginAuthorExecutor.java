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
package org.hawk.executor.command.plugin.author;



import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.HawkPlugin;
import org.hawk.plugin.exception.HawkPluginException;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ShowPluginAuthorExecutor extends DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowPluginAuthorExecutor.class.getName());

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {


        ShowPluginAuthorCommand showPluginAuthorCommand = null;
        if (hawkCommand instanceof ShowPluginAuthorCommand) {
            showPluginAuthorCommand = (ShowPluginAuthorCommand) hawkCommand;
        } else {
          //  throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String pluginArchive = showPluginAuthorCommand.getPluginArchive();

        HawkPlugin hawkPlugin = this.getHawkPluginService().findPlugin(pluginArchive);
        System.out.println("Plugin "+hawkPlugin);
        System.out.println("Version "+hawkPlugin.getPluginMetaData().getSoftware().getContributor());
        return true;
    }
}
