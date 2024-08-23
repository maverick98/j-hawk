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
package org.hawk.executor.command.plugin.details;



import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.HawkPlugin;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ShowPluginDetailsExecutor extends DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowPluginDetailsExecutor.class.getName());

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {


        ShowPluginDetailsCommand showPluginDetailsCommand = null;
        if (hawkCommand instanceof ShowPluginDetailsCommand) {
            showPluginDetailsCommand = (ShowPluginDetailsCommand) hawkCommand;
        } else {
    //        throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String pluginArchive = showPluginDetailsCommand.getPluginArchive();

        HawkPlugin hawkPlugin = this.getHawkPluginService().findPlugin(pluginArchive);
        System.out.println("Plugin "+hawkPlugin);
        System.out.println("Version "+hawkPlugin.getPluginMetaData().getSoftware().getVersion().getVersion());
        return true;
    }
}
