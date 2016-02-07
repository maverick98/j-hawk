


package org.hawk.module.script;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.Implementor;
import org.hawk.module.annotation.Implementors;
import java.util.Map;
import static org.hawk.constant.HawkConstant.*;

/**
 *
 * @VERSION 1.0 10 Apr, 2010
 * @author msahu
 */
public class MultiLineScriptFactory {



    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineScriptFactory.class.getName());
    private static Map<Class,MultiLineScript> cachedMultiLineScripts = new LinkedHashMap<Class,MultiLineScript>();


    public static Map<Class,MultiLineScript> getMultiLineScripts(){

        if(cachedMultiLineScripts != null && !cachedMultiLineScripts.isEmpty()){
            return cachedMultiLineScripts;

        }

        Map<Integer,Class> sortedMultiLineScripts = new TreeMap<Integer,Class>();
        Class multiLineClazz = MultiLineScript.class;

        if(multiLineClazz.isAnnotationPresent(Implementors.class)){
            Implementors implementors =(Implementors) multiLineClazz.getAnnotation(Implementors.class);

            for(Implementor implementor :implementors.value()){
                try {
                        String clazzStr  = SCRIPT_PACKAGE+"."+implementor.clazz();

                        Class clazz = Class.forName(clazzStr);


                        sortedMultiLineScripts.put(implementor.parsingSequence(), clazz);
                        

                } catch (Exception ex) {
                    System.out.println("error while parsing multiline scripts"+ex.getMessage());
                    logger.severe("error while parsing multiline scripts"+ex.getMessage());

                }

            }
            try{
                for(Entry<Integer,Class> entry:sortedMultiLineScripts.entrySet()){
                    MultiLineScript instance = (MultiLineScript) entry.getValue().newInstance();
                    cachedMultiLineScripts.put(entry.getValue(),instance );
                }
            }catch(Exception ex){
                 System.out.println("error while caching multiline scripts"+ex.getMessage());
                 logger.severe("error while caching multiline scripts"+ex.getMessage());
            }

        }

        return cachedMultiLineScripts;
    }
}




