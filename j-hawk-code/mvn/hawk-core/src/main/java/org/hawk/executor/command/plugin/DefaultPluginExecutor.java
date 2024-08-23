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
