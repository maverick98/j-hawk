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
package org.hawk.executor.cache.multiline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.commons.implementor.InstanceVisitable;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
public class MultiLineScriptCacheFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineScriptCacheFactory.class.getName());
    private static Map<Integer, IMultiLineScriptCache> cachedMultiLineScriptCache = new TreeMap<>();

    public static boolean isInsideMultiLineScript(int i) {
        boolean status = false;
        for (Entry<Integer, IMultiLineScriptCache> entry : cachedMultiLineScriptCache.entrySet()) {
            if (entry.getValue().isInsideMultiLineScript(i)) {
                status = true;
                break;
            }
        }
        return status;
    }

    public static Map<Integer, IMultiLineScriptCache> getMultiLineScriptCache() throws Exception {

        if (cachedMultiLineScriptCache != null && !cachedMultiLineScriptCache.isEmpty()) {
            return cachedMultiLineScriptCache;

        }



        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(MultiLineScriptCache.class);

        try {
            new MultiLineScriptCacheVisitor() {
                @Override
                public void onVisit(IMultiLineScriptCache instance) {
                    try {
                        Method createMethod = instance.getClass().getDeclaredMethod("create", new Class[]{});
                        IMultiLineScriptCache multiLineScriptCache = (IMultiLineScriptCache) createMethod.invoke(instance, new Object[]{});
                        cachedMultiLineScriptCache.put(instance.getSequence(), multiLineScriptCache);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (IllegalAccessException ex) {
                        System.out.println("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (InvocationTargetException ex) {
                        System.out.println("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (NoSuchMethodException ex) {
                        System.out.println("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (SecurityException ex) {
                        System.out.println("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching MultiLineScriptCacheVisitor" + ex.getMessage());
                        
                    }
                }
            }.visit(instanceVisitable);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }
       
        return cachedMultiLineScriptCache;
    }
}
