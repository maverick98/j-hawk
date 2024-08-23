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
package org.hawk.executor.command.help;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class HelpCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-help\\s*";
    private static final String USAGE = "-help";

    public HelpCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        AppContainer.getInstance().getBean(HelpExecutor.class).execute(this);
        return true;
    }
     @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(HelpCommand.class);
    }
}
