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

import java.util.Map;


/**
 *
 * @author Manoranjan Sahu
 */
public class HawkExecutionCommand implements IHawkExecutionCommand{
    
    private String regEx;
    
    public HawkExecutionCommand(){
        
    }
    public HawkExecutionCommand(String regEx){
        this.regEx = regEx;
    }
   
    
    @Override
    public boolean execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String getRegEx() {
        return this.regEx;
    }

   

    @Override
    public String getCommandUsage() {
        
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onCommandFound(Map<Integer, String> map) {
        return true;
    }

    @Override
    public boolean shouldCollectPerfData() {
         return false;
    }

    @Override
    public boolean shouldCacheHawkCoreModulesOnly() {
         return false;
    }

    @Override
    public boolean shouldCacheConfig() {
         return false;
    }

    @Override
    public boolean shouldDumpModuleExecution() {
         return false;
    }

    @Override
    public boolean shouldUseLogger() {
        return false;
    }

}
