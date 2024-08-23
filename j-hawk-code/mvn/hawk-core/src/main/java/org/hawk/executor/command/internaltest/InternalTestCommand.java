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
package org.hawk.executor.command.internaltest;

import java.util.Map;
import org.hawk.executor.command.HawkExecutionCommand;

/**
 *
 * @author Manoranjan Sahu
 */
public class InternalTestCommand extends HawkExecutionCommand {

    public InternalTestCommand(String regEx) {
        super(regEx);
    }

    public InternalTestCommand() {
        super();
    }

    @Override
    public boolean onCommandFound(Map<Integer, String> map) {
        return true;
    }
}
