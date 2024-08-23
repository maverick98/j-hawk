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


import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.IHawkExecutionCommandFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class HelpExecutor implements IHawkCommandExecutor{

    @Autowired(required = true)
       
    private IHawkExecutionCommandFactory hawkExecutionCommandFactory;

    public IHawkExecutionCommandFactory getHawkExecutionCommandFactory() {
        return hawkExecutionCommandFactory;
    }

    public void setHawkExecutionCommandFactory(IHawkExecutionCommandFactory hawkExecutionCommandFactory) {
        this.hawkExecutionCommandFactory = hawkExecutionCommandFactory;
    }
    
    @Override
    public boolean execute(IHawkExecutionCommand hawkCommand) throws Exception {
        
         System.out.println(this.getHawkExecutionCommandFactory().getCommandUsage());
         return true;
    }
    
   

}
