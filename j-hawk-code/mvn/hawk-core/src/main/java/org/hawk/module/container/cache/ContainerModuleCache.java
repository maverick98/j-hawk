/**
 *  
 * left.
 *
 *    
 *  
 *
 */
package org.hawk.module.container.cache;

import java.util.Map;
import org.hawk.module.cache.AbstractModuleCache;
import org.hawk.module.container.HawkContainerModuleFactory;
import org.hawk.module.container.IContainerModule;

/**
 *
 * @author msahu98
 */
public class ContainerModuleCache extends AbstractModuleCache implements IContainerModuleCache {

    private boolean cacheContainerModulesInternal() throws Exception {
        this.resetModules();
        Map<Integer, IContainerModule> map = HawkContainerModuleFactory.getCachedHawkContainerModules();

        for (Map.Entry<Integer, IContainerModule> entry : map.entrySet()) {
            IContainerModule containerModule = entry.getValue();
            String moduleName = containerModule.getName();
            this.getModules().put(moduleName, containerModule);
        }
        return true;
    }

    @Override
    public boolean cacheModules(boolean shouldCacheSubTasks) throws Exception {
        boolean result;
        boolean moduleCached = this.cacheContainerModulesInternal();
        if (moduleCached) {
            result = this.getSubTaskCache().cacheSubTasks(this.getModules());
        } else {
            result = false;
        }
        return result;
    }

}
