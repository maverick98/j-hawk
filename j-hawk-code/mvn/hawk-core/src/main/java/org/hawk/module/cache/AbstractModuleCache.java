/**
 *  
 * left.
 *
 *    
 *  
 *
 */
package org.hawk.module.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import org.hawk.lang.util.AliasScript;
import org.hawk.module.IModule;
import org.hawk.module.task.ISubTaskCache;
import org.hawk.module.task.SubTaskContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author msahu98
 */
public abstract class AbstractModuleCache implements IModuleCache {

    private Map<String, IModule> modules = new LinkedHashMap<>();

    @Autowired
    private ISubTaskCache subTaskCache;

    public ISubTaskCache getSubTaskCache() {
        return subTaskCache;
    }

    public void setSubTaskCache(ISubTaskCache subTaskCache) {
        this.subTaskCache = subTaskCache;
    }

    @Override
    public Map<String, IModule> getModules() {
        return modules;
    }

    public void setModules(Map<String, IModule> modules) {
        this.modules = modules;
    }

    @Override
    public boolean resetModules() {
        this.setModules(new LinkedHashMap<String, IModule>());
        return true;
    }

    @Override
    public abstract boolean cacheModules(boolean shouldCacheSubTasks) throws Exception;

    @Override
    public boolean refreshModules(boolean shouldCacheSubTasks) throws Exception {
        return false;
    }

    @Override
    public IModule lookUpModule(String moduleName) throws Exception {
        Map<String, IModule> moduleMap = this.getModules();

        IModule moduleInstance = moduleMap.get(moduleName);

        if (moduleInstance == null) {

            //Look for alias
            AliasScript aliasScript = AliasScript.getInstance();

            moduleName = aliasScript.get(moduleName);

            moduleInstance = moduleMap.get(moduleName);

        }

        return moduleInstance;
    }

    @Override
    public SubTaskContainer lookUpSubTask(String moduleSubTask) throws Exception {
        SubTaskContainer subTaskContainer = null;
        Map<String, IModule> moduleMap = this.getModules();
        if (moduleMap != null) {
            for (Map.Entry<String, IModule> entry : moduleMap.entrySet()) {
                subTaskContainer = entry.getValue().getSubTask(moduleSubTask);
                if (subTaskContainer != null) {
                    break;
                }
            }
        }
        return subTaskContainer;
    }

    @Override
    public SubTaskContainer lookUpSubTask(String moduleName, String moduleSubTask) throws Exception {
        if (moduleSubTask == null || moduleSubTask.isEmpty()) {
            throw new Exception("invalid sub task");
        }
        IModule moduleInstance = this.lookUpModule(moduleName);
        SubTaskContainer subTaskContainer;
        if (moduleInstance != null) {
            subTaskContainer = moduleInstance.getSubTask(moduleSubTask);
        } else {
            subTaskContainer = this.lookUpSubTask(moduleSubTask);
        }

        if (subTaskContainer == null) {

            throw new Exception("Invalid task{" + moduleName + "->" + moduleSubTask + "}");

        }

        return subTaskContainer;
    }

    @Override
    public String showTasks() throws Exception {
        Map<String, IModule> moduleMap = this.getModules();
        return this.getSubTaskCache().showTasks(moduleMap);
    }

}
