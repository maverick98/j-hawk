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
