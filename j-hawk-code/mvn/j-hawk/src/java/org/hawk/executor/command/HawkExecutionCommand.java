/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
