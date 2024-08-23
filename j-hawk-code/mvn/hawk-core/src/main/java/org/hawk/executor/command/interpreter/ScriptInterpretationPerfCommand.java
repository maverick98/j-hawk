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
package org.hawk.executor.command.interpreter;

import org.common.di.AppContainer;
import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ScriptInterpretationPerfCommand extends ScriptInterpretationCommand {

    private static final String REGEX = "\\s*-perf\\s*-f\\s*(.*)";
    private static final String USAGE = "-perf -f {hawk file path}";

    public ScriptInterpretationPerfCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }
     @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
