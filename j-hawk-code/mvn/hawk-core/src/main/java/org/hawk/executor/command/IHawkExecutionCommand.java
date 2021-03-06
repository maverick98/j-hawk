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
