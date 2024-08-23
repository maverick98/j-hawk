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
package org.hawk.lang.singleline;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.commons.implementor.InstanceVisitable;
import org.hawk.logger.HawkLogger;

/**
 *
 * @VERSION 1.0 10 Apr, 2010
 * @author msahu
 */
public class SingleLineScriptFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(SingleLineScriptFactory.class.getName());
    private static Map<Class, SingleLineScript> cachedSingleLineScripts = new LinkedHashMap<>();

    public static Map<Class, SingleLineScript> getSingleLineScripts() throws Exception {

        if (cachedSingleLineScripts != null && !cachedSingleLineScripts.isEmpty()) {
            return cachedSingleLineScripts;

        }

        final Map<Integer, SingleLineScript> sortedSingleLineScripts = new TreeMap<>();


        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(SingleLineScript.class);

        new SingleLineScriptVisitor() {
            @Override
            public void onVisit(SingleLineScript instance) {
                sortedSingleLineScripts.put(instance.getVerticalParserSequence(), instance);
                
            }
        }.visit(instanceVisitable);
        for (Map.Entry<Integer, SingleLineScript> entry : sortedSingleLineScripts.entrySet()) {
            cachedSingleLineScripts.put(entry.getValue().getClass(), entry.getValue());
        }

        return cachedSingleLineScripts;
    }
}
