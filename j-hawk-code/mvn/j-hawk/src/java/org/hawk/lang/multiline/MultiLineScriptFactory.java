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
    private static Map<Class, MultiLineScript> cachedMultiLineScripts = new LinkedHashMap<>();

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
