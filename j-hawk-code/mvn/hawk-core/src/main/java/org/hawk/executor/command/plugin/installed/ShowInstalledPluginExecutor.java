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
package org.hawk.executor.command.plugin.installed;

import java.util.Set;


import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.plugin.DefaultPluginExecutor;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.HawkPlugin;

/**
 *
 * @author Manoranjan Sahu
 */
//@Component(SHOWINSTALLEDPLUGINEXECUTOR)
//@Qualifier(DEFAULTQUALIFIER)
public class ShowInstalledPluginExecutor extends  DefaultPluginExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowInstalledPluginExecutor.class.getName());
    

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {


        if (!(hawkCommand instanceof ShowInstalledPluginCommand)) {
     //       throw new HawkError("You cant be here.. have you really implemented any code ???");
        }

        Set<HawkPlugin> hawkPlugins = this.getHawkPluginService().findInstalledPlugins();
        for (HawkPlugin hawkPlugin : hawkPlugins) {
            logger.info(hawkPlugin.getName());
        }
        return true;
    }
}
