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
package org.hawk.executor.command;

import java.util.List;


/**
 *
 * @author user
 */
public interface IHawkExecutionCommandFactory {

    public List<IHawkExecutionCommand> getHawkExecutionCommands() throws Exception;

    public IHawkExecutionCommand create(String args[]) throws Exception;

    public String getCommandUsage();
}
