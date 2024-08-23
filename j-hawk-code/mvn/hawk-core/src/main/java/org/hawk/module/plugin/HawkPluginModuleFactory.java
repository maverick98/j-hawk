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
   
     public static void refreshHawkPluginModules(HawkPluginModule hawkPluginModule) throws HawkPluginException{
         cachedHawkPluginModuleMap.put(cachedHawkPluginModuleMap.size(),hawkPluginModule);
     }
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
