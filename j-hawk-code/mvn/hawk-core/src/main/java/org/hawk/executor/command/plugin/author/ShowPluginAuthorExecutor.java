/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
