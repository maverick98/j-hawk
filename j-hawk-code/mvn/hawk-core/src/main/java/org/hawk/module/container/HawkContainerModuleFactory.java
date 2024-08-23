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
 */
package org.hawk.module.container;

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
public class HawkContainerModuleFactory {

    private static final Map<Integer, IContainerModule> cachedHawkContainerModuleMap =  new TreeMap<>();
    /**
     * This checks if a module is a container
     *
     * @param moduleClassName input class name of the module
     * @return true if the module is core otherwise false
     */
    public static boolean isHawkContainerModule(final String moduleClassName) {

        ClazzLiteralVisitable clazzLiteralVisitable = new ClazzLiteralVisitable();
        clazzLiteralVisitable.setClazz(HawkContainerModule.class);
        final List<Boolean> isHawkContainerModule = new ArrayList<>();
        new HawkContainerModuleCheckVisitor() {
            @Override
            public void onVisit(String clazzStr) {
                if (moduleClassName.equals(clazzStr)) {
                    isHawkContainerModule.set(0, Boolean.TRUE);
                }
            }
        }.visit(clazzLiteralVisitable);
        return isHawkContainerModule.get(0);
    }
    public static Map<Integer, IContainerModule> getCachedHawkContainerModules() throws Exception{
        if(cachedHawkContainerModuleMap != null && !cachedHawkContainerModuleMap.isEmpty()){
            return cachedHawkContainerModuleMap;
        }
        
        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(HawkContainerModule.class);
        try {
            new HawkContainerModuleImplVisitor() {
                
                @Override
                public void onVisit(HawkContainerModule hawkContainerModule) {
                    cachedHawkContainerModuleMap.put(cachedHawkContainerModuleMap.size(),hawkContainerModule);
                }
                
                
            }.visit(instanceVisitable);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return cachedHawkContainerModuleMap;
    }
}
