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
