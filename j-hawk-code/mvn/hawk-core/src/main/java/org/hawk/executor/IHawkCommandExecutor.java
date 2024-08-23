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

package org.hawk.executor;


import org.hawk.executor.command.IHawkExecutionCommand;

/**
 *
 * @author user
 */
public interface IHawkCommandExecutor {
    
    boolean execute(IHawkExecutionCommand hawkCommand) throws Exception;

}
