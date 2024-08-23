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
package org.hawk.executor.command.internaltest.functional;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
import org.hawk.executor.command.internaltest.InternalTestCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;


/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class InternalFunctionalTestCommand extends InternalTestCommand {

    private static final String REGEX = "\\s*-inttest func\\s*";
    private static final String USAGE = "-inttest func";

    public InternalFunctionalTestCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        AppContainer.getInstance().getBean(InternalFunctionalTestExecutor.class).execute(this);
        return true;
    }
    
    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean( InternalFunctionalTestCommand.class);
    }
}
