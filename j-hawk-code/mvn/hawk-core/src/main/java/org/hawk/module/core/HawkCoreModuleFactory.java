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
package org.hawk.module.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.commons.implementor.ClazzLiteralVisitable;
import org.commons.implementor.InstanceVisitable;

/**
 *
 * @author Manoranjan Sahu
 */
public class HawkCoreModuleFactory {

    private static final Map<Integer, ICoreModule> cachedHawkCoreModuleMap =  new TreeMap<>();
    /**
     * This checks if a module is a core
     *
     * @param moduleClassName input class name of the module
     * @return true if the module is core otherwise false
     */
    public static boolean isHawkCoreModule(final String moduleClassName) {

        ClazzLiteralVisitable clazzLiteralVisitable = new ClazzLiteralVisitable();
        clazzLiteralVisitable.setClazz(HawkCoreModule.class);
        final List<Boolean> isHawkCoreModule = new ArrayList<>();
        new HawkCoreModuleCheckVisitor() {
            @Override
            public void onVisit(String clazzStr) {
                if (moduleClassName.equals(clazzStr)) {
                    isHawkCoreModule.set(0, Boolean.TRUE);
                }
            }
        }.visit(clazzLiteralVisitable);
        return isHawkCoreModule.get(0);
    }
    public static Map<Integer, ICoreModule> getCachedHawkCoreModules() throws Exception{
        if(cachedHawkCoreModuleMap != null && !cachedHawkCoreModuleMap.isEmpty()){
            return cachedHawkCoreModuleMap;
        }
        
        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(HawkCoreModule.class);
        try {
            new HawkCoreModuleImplVisitor() {
                
                @Override
                public void onVisit(HawkCoreModule hawkCoreModule) {
                    cachedHawkCoreModuleMap.put(cachedHawkCoreModuleMap.size(),hawkCoreModule);
                }
                
                
            }.visit(instanceVisitable);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return cachedHawkCoreModuleMap;
    }
}
