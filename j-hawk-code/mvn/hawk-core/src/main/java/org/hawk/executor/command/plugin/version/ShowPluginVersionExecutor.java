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
package org.hawk.executor.command.plugin.version;



import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.HawkPlugin;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class ShowPluginVersionExecutor extends DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowPluginVersionExecutor.class.getName());

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {


        ShowPluginVersionCommand showPluginDetailsCommand = null;
        if (hawkCommand instanceof ShowPluginVersionCommand) {
            showPluginDetailsCommand = (ShowPluginVersionCommand) hawkCommand;
        } else {
       //     throw new HawkError("You cant be here.. have you really implemented any code ???");
        }
        String pluginArchive = showPluginDetailsCommand.getPluginArchive();

        HawkPlugin hawkPlugin = this.getHawkPluginService().findPlugin(pluginArchive);
        System.out.println("Plugin "+hawkPlugin);
        System.out.println("Version "+hawkPlugin.getPluginMetaData().getSoftware().getVersion().getVersion());
        return true;
    }
}
