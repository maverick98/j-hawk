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
package org.hawk.lang.multiline;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.commons.implementor.InstanceVisitable;
import org.hawk.logger.HawkLogger;

/**
 *
 * @VERSION 1.0 10 Apr, 2010
 * @author msahu
 */
public class MultiLineScriptFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineScriptFactory.class.getName());
    private static final Map<Class, MultiLineScript> cachedMultiLineScripts = new LinkedHashMap<>();

    public static Map<Class, MultiLineScript> getMultiLineScripts() throws Exception {

        if (cachedMultiLineScripts != null && !cachedMultiLineScripts.isEmpty()) {
            return cachedMultiLineScripts;

        }

       // final Map<Integer, Class> sortedMultiLineScripts = new TreeMap<Integer, Class>();


        final Map<Integer, MultiLineScript> sortedMultiLineScripts = new TreeMap<>();


        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(MultiLineScript.class);

        new MultiLineScriptVisitor() {
            @Override
            public void onVisit(MultiLineScript instance) {
                sortedMultiLineScripts.put(instance.getVerticalParserSequence(), instance);
                
            }
        }.visit(instanceVisitable);

        for(Entry<Integer,MultiLineScript> entry: sortedMultiLineScripts.entrySet()){
            cachedMultiLineScripts.put(entry.getValue().getClass(), entry.getValue());
        }

        return cachedMultiLineScripts;
    }
}
