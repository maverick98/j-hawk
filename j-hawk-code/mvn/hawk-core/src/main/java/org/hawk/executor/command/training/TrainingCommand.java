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

package org.hawk.executor.command.training;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;


/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)

   
   
public class TrainingCommand extends HawkExecutionCommand{

    private static final String REGEX = "\\s*-training\\s*";
    private static final String USAGE = "-training";

    public TrainingCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }
    
    @Override
    public boolean execute() throws Exception {
        AppContainer.getInstance().getBean(TrainingExecutor.class).execute(this);
        return true;
    }
    @Override
    public boolean shouldCacheConfig() {
        return true;
    }
    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
   

}
