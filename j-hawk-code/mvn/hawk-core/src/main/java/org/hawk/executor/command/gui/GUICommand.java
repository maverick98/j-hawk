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
package org.hawk.executor.command.gui;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class GUICommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-gui\\s*";
    private static final String USAGE = "-gui";

    public GUICommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        AppContainer.getInstance().getBean(GUIExecutor.class).execute(this);
        return true;
    }
     @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(GUICommand.class);
    }
}
