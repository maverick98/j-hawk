/**
 *  
 * left.
 *
 *    
 *  
 *
 */
package org.hawk.module.cache;

import org.hawk.module.container.cache.ContainerModuleCache;
import org.hawk.module.core.cache.CoreModuleCache;
import org.hawk.module.plugin.cache.PluginModuleCache;
import org.hawk.sequence.DefaultSequenceProvider;

/**
 *
 * @author msahu98
 */
public class ModuleCacheSequenceProvider extends DefaultSequenceProvider {

    public ModuleCacheSequenceProvider() {
        this.getSequenceMap().put(ContainerModuleCache.class, 1000);
        this.getSequenceMap().put(CoreModuleCache.class, 2000);
        this.getSequenceMap().put(PluginModuleCache.class, 3000);
    }
}
