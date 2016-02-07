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
    private static Map<Class, SingleLineScript> cachedSingleLineScripts = new LinkedHashMap<Class, SingleLineScript>();

    public static Map<Class, SingleLineScript> getSingleLineScripts() throws Exception {

        if (cachedSingleLineScripts != null && !cachedSingleLineScripts.isEmpty()) {
            return cachedSingleLineScripts;

        }

        final Map<Integer, SingleLineScript> sortedSingleLineScripts = new TreeMap<Integer, SingleLineScript>();


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
