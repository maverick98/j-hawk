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
package org.hawk.executor.command.version;

import org.common.di.AppContainer;

import org.hawk.executor.command.HawkExecutionCommand;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;


/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)

   
   
public class VersionCommand extends HawkExecutionCommand {

    private static final String REGEX = "\\s*-version\\s*";
    
    private static final String USAGE = "-version";
    
    public VersionCommand(){
        super(REGEX);
    } 

    @Override
    public boolean execute() throws Exception {
         return AppContainer.getInstance().getBean(VersionExecutor.class).execute(this);
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
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
