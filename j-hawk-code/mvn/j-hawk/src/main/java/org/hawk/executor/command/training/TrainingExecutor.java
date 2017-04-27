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
//@Component(TRAININGEXECUTOR)
//@Qualifier(DEFAULTQUALIFIER)
public class TrainingExecutor implements IHawkCommandExecutor{

   

    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
         AppContainer.getInstance().getBean(AllModuleCache.class).showTasks();
         return true;
    }

}
