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
   
   
public class ScriptInterpretationDebugCommand extends ScriptInterpretationCommand {

    private static final String REGEX = "\\s*-debug\\s*-f\\s*(.*)";
    private static final String USAGE = "-debug -f {hawk file path}";

    public ScriptInterpretationDebugCommand() {
        super(REGEX);
    }

    @Override
    public String getCommandUsage() {

        return USAGE;

    }
    @Override
     public boolean shouldDumpModuleExecution() {
         return true;
     }
    
    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean( this.getClass());
    }
    
}
