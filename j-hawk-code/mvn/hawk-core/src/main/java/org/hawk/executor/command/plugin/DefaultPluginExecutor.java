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
package org.hawk.executor.command.plugin;


import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.IHawkPluginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class DefaultPluginExecutor implements IHawkCommandExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(DefaultPluginExecutor.class.getName());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
