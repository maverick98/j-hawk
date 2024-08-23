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

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class AllPluginUnDeploymentCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-undeploy\\s*plugin\\s*-all\\s*";
    private static final String USAGE = "-undeploy plugin -all";
    
    public AllPluginUnDeploymentCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(AllPluginUnDeploymentExecutor.class).execute(this);

    }

    

    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }

    @Override
    public boolean shouldCacheConfig() {
        return true;
    }
}
