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

package org.hawk.executor.command.plugin.nyd;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ShowNYDPluginCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-show\\s*plugin\\s*-nyd\\s*";
    private static final String USAGE = "-show plugin -nyd";
   

    public ShowNYDPluginCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(ShowNYDPluginExecutor.class).execute(this);

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

