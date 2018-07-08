/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.hawk.module.cache;

import java.util.Map;
import org.hawk.module.IModule;
import org.hawk.module.task.SubTaskContainer;

/**
 *
 * @author msahu98
 */
public interface IModuleCache {

    public boolean cacheModules(boolean shouldCacheSubTasks) throws Exception;
    
     
    public boolean refreshModules(boolean shouldCacheSubTasks) throws Exception;

    public Map<String, ? extends  IModule> getModules();

    public boolean resetModules();

    public  IModule  lookUpModule(String moduleName) throws Exception;

    public SubTaskContainer lookUpSubTask(String moduleSubTask) throws Exception;

    public SubTaskContainer lookUpSubTask(String moduleName, String moduleSubTask) throws Exception;

    public String showTasks() throws Exception;

}
