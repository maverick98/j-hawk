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
package org.hawk.executor.cache.singleline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hawk.executor.cache.multiline.MultiLineScriptCacheFactory;
import org.commons.implementor.InstanceVisitable;
import org.hawk.logger.HawkLogger;

/**
 *
 * @author Manoranjan Sahu
 */
public class SingleLineScriptCacheFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineScriptCacheFactory.class.getName());
    private static final Map<Integer, ISingleLineScriptCache> cachedSingleLineScriptCache = new TreeMap<>();

    public static Map<Integer, ISingleLineScriptCache> getSingleLineScriptCache() throws Exception {

        if (cachedSingleLineScriptCache != null && !cachedSingleLineScriptCache.isEmpty()) {
            return cachedSingleLineScriptCache;

        }


        

        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(SingleLineScriptCache.class);

        try {
            new SingleLineScriptCacheVisitor() {
                @Override
                public void onVisit(ISingleLineScriptCache instance) {
                    try {
                        Method createMethod = instance.getClass().getDeclaredMethod("create", new Class[]{});
                        ISingleLineScriptCache singleLineScriptCache = (ISingleLineScriptCache) createMethod.invoke(instance, new Object[]{});
                        cachedSingleLineScriptCache.put(instance.getSequence(), singleLineScriptCache);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (IllegalAccessException ex) {
                        System.out.println("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (InvocationTargetException ex) {
                        System.out.println("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (NoSuchMethodException ex) {
                        System.out.println("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        
                    } catch (SecurityException ex) {
                        System.out.println("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        logger.error("error while caching SingleLineScriptCacheVisitor" + ex.getMessage());
                        
                    }
                }
            }.visit(instanceVisitable);
        } catch (Exception ex) {
            throw new Exception(ex);
        }

        
        return cachedSingleLineScriptCache;
    }
}
