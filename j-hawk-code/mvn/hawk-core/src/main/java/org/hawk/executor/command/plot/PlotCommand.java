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
