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
package org.hawk.executor.command.plot;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
public class PlotCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-plot\\s*";
    private static final String USAGE = "-plot";

    public PlotCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }

    @Override
    public boolean execute() throws Exception {
        return AppContainer.getInstance().getBean(PlottingExecutor.class).execute(this);

    }
}
