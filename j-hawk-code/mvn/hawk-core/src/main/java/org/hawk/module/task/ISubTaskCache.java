/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.hawk.module.task;

import java.util.Map;
import org.hawk.module.IModule;
import org.hawk.module.core.ICoreModule;
import org.hawk.module.plugin.IPluginModule;

/**
 *
 * @author msahu98
 */
public interface ISubTaskCache {

    public boolean cacheSubTasks(IModule module) throws Exception;

    //public boolean cacheAllSubTasks(Map<String, IModule> moduleMap) throws Exception;

    public boolean cacheSubTasks(Map<String, IModule> moduleMap) throws Exception;

   // public boolean cachePluginSubTasks(Map<String, IPluginModule> moduleMap) throws Exception;

    //public SubTaskContainer lookUpSubTask(Map<String, IModule> moduleMap,String moduleSubTask) throws Exception;
    //public SubTaskContainer lookUpSubTask(String moduleName, String moduleSubTask) throws Exception;
    public String showTasks(Map<String, IModule> moduleMap) throws Exception;

}
