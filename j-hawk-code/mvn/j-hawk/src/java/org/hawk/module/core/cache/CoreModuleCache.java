/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.hawk.module.core.cache;

import java.util.Map;
import org.hawk.module.cache.AbstractModuleCache;
import org.hawk.module.core.HawkCoreModuleFactory;
import org.hawk.module.core.ICoreModule;

/**
 *
 * @author msahu98
 */
public class CoreModuleCache extends AbstractModuleCache implements ICoreModuleCache{

    private boolean cacheCoreModulesInternal() throws Exception {
        this.resetModules();
        Map<Integer, ICoreModule> map = HawkCoreModuleFactory.getCachedHawkCoreModules();

        for (Map.Entry<Integer, ICoreModule> entry : map.entrySet()) {
            ICoreModule coreModule = entry.getValue();
            String moduleName = coreModule.getName();
            this.getModules().put(moduleName, coreModule);
        }
        return true;
    }

    @Override
    public boolean cacheModules(boolean shouldCacheSubTasks) throws Exception {
        boolean result;
        boolean moduleCached = this.cacheCoreModulesInternal();
        if (moduleCached) {
            result = this.getSubTaskCache().cacheSubTasks(this.getModules());
        } else {
            result = false;
        }
        return result;
    }

}
