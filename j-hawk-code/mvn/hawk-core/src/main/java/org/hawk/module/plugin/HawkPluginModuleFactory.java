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
package org.hawk.module.plugin;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.commons.implementor.InstanceVisitable;
import org.hawk.logger.HawkLogger;
import org.hawk.plugin.exception.HawkPluginException;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkPluginModuleFactory {
     private static final HawkLogger logger = HawkLogger.getLogger(HawkPluginModuleFactory.class.getName());
    private static final Map<Integer, IPluginModule> cachedHawkPluginModuleMap =  new TreeMap<>();
   
    public static Map<Integer, IPluginModule> getCachedHawkPluginModules() throws HawkPluginException{
        if(cachedHawkPluginModuleMap != null && !cachedHawkPluginModuleMap.isEmpty()){
            return cachedHawkPluginModuleMap;
        }
        
        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(HawkPluginModule.class);
        try {
            new HawkPluginModuleImplVisitor() {

                @Override
                public void onVisit(HawkPluginModule hawkPluginModule) {
                    cachedHawkPluginModuleMap.put(cachedHawkPluginModuleMap.size(),hawkPluginModule);
                }

                
            }.visit(instanceVisitable);
        } catch (Exception ex) {
           logger.error(ex);
           throw new HawkPluginException(ex);
        }
        return cachedHawkPluginModuleMap;
    }
}
