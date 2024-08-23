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

import javafx.application.Application;
import org.hawk.executor.IHawkCommandExecutor;
import org.hawk.executor.command.IHawkExecutionCommand;
import org.hawk.executor.command.IHawkExecutionCommandFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class GUIExecutor implements IHawkCommandExecutor{

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
        
         Application.launch(GUIHawkMain.class,new String[]{});
         return true;
    }
    
   

}
