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
 * @author user
 */
public interface IHawkExecutionCommand {

    boolean execute() throws Exception;
    
    String getRegEx();
    
    
    
    String getCommandUsage();
    
    boolean onCommandFound(Map<Integer,String> map);
    
    public boolean shouldCollectPerfData();
    
    public boolean shouldCacheHawkCoreModulesOnly();
    
    public boolean shouldCacheConfig();
    
    public boolean shouldDumpModuleExecution();
    
    public boolean shouldUseLogger();
    
}
