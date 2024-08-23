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

import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.module.cache.AllModuleCache;
//import org.hawk.module.ModuleCache;
/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class TrainingExecutor implements IHawkCommandExecutor{

   

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
         AppContainer.getInstance().getBean(AllModuleCache.class).showTasks();
         return true;
    }

}
