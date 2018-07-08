/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.hawk.module.cache;

import org.hawk.module.core.cache.ICoreModuleCache;
import org.hawk.module.plugin.cache.IPluginModuleCache;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.hawk.module.container.cache.IContainerModuleCache;
import org.hawk.sequence.ISequenceProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author msahu98
 */
public class AllModuleCache extends AbstractModuleCache implements IAllModuleCache {

    private Map<Integer, IModuleCache> priorityModuleCache = new TreeMap<>();

    @Autowired
    private ISequenceProvider moduleCacheSequenceProvider;

    @Autowired
    private IContainerModuleCache containerModuleCache;

    @Autowired
    private ICoreModuleCache coreModuleCache;

    @Autowired
    private IPluginModuleCache pluginModuleCache;

    public ISequenceProvider getModuleCacheSequenceProvider() {
        return moduleCacheSequenceProvider;
    }

    public void setModuleCacheSequenceProvider(ISequenceProvider moduleCacheSequenceProvider) {
        this.moduleCacheSequenceProvider = moduleCacheSequenceProvider;
    }

    public IContainerModuleCache getContainerModuleCache() {
        return containerModuleCache;
    }

    public void setContainerModuleCache(IContainerModuleCache containerModuleCache) {
        this.containerModuleCache = containerModuleCache;
    }

    public ICoreModuleCache getCoreModuleCache() {
        return coreModuleCache;
    }

    public void setCoreModuleCache(ICoreModuleCache coreModuleCache) {
        this.coreModuleCache = coreModuleCache;
    }

    public IPluginModuleCache getPluginModuleCache() {
        return pluginModuleCache;
    }

    public void setPluginModuleCache(IPluginModuleCache pluginModuleCache) {
        this.pluginModuleCache = pluginModuleCache;
    }

    public Map<Integer, IModuleCache> getPriorityModuleCache() {
        return priorityModuleCache;
    }

    public void setPriorityModuleCache(Map<Integer, IModuleCache> priorityModuleCache) {
        this.priorityModuleCache = priorityModuleCache;
    }

    private boolean populatePriorityModuleCache(IModuleCache moduleCache) {
        Map<Class, Integer> sequenceMap = this.getModuleCacheSequenceProvider().getSequenceMap();
        Integer index = sequenceMap.get(moduleCache.getClass());
        this.getPriorityModuleCache().put(index, moduleCache);
        return true;
    }

    @Override
    public boolean refreshModules(boolean shouldCacheSubTasks) throws Exception {
        this.resetModules();
        this.populatePriorityModuleCache(this.getContainerModuleCache());
        this.populatePriorityModuleCache(this.getCoreModuleCache());
        this.populatePriorityModuleCache(this.getPluginModuleCache());

        for (Entry<Integer, IModuleCache> entry : this.getPriorityModuleCache().entrySet()) {
            IModuleCache moduleCache = entry.getValue();
            moduleCache.refreshModules(shouldCacheSubTasks);
            this.getModules().putAll(moduleCache.getModules());
        }

        return true;
    }

    @Override
    public boolean cacheModules(boolean shouldCacheSubTasks) throws Exception {

        this.populatePriorityModuleCache(this.getContainerModuleCache());
        this.populatePriorityModuleCache(this.getCoreModuleCache());
        this.populatePriorityModuleCache(this.getPluginModuleCache());

        for (Entry<Integer, IModuleCache> entry : this.getPriorityModuleCache().entrySet()) {
            IModuleCache moduleCache = entry.getValue();
            moduleCache.cacheModules(shouldCacheSubTasks);
            this.getModules().putAll(moduleCache.getModules());
        }

        return true;
    }

}
