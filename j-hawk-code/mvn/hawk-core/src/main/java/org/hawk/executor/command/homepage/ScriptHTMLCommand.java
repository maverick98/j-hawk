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

package org.hawk.executor.command.homepage;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

@ScanMe(true)

   
   
public class ScriptHTMLCommand extends HawkExecutionCommand {
 private static final String REGEX = "\\s*-homepage format-scripts\\s*";
    
    private static final String USAGE = "-homepage format-scripts";
    
    public ScriptHTMLCommand(){
        super(REGEX);
    } 

    @Override
    public boolean execute() throws Exception {
         return AppContainer.getInstance().getBean(ScriptHTMLExecutor.class).execute(this);
    }
   
    
    @Override
    public boolean shouldCacheHawkCoreModulesOnly() {
         return true;
    }
    @Override
    public String getCommandUsage() {
        
        return USAGE;
       
    }
    @Create
    public HawkExecutionCommand create() {
        return AppContainer.getInstance().getBean(ScriptHTMLCommand.class);
    }
}
