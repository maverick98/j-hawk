/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
