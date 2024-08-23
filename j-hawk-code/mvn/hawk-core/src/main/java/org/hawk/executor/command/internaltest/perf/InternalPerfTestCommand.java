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
package org.hawk.executor.command.internaltest.perf;

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
   
   
public class InternalPerfTestCommand extends InternalTestCommand {

    private static final String REGEX = "\\s*-inttest perf\\s*";
    private static final String USAGE = "-inttest perf";

    public InternalPerfTestCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        AppContainer.getInstance().getBean(InternalPerfTestExecutor.class).execute(this);
        return true;
    }
    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean( InternalPerfTestCommand.class);
    }
}
