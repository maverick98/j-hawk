


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
public class SingleLineScriptFactory {

        
    
    private static final HawkLogger logger = HawkLogger.getLogger(SingleLineScriptFactory.class.getName());
    private static Map<Class,SingleLineScript> cachedSingleLineScripts = new LinkedHashMap<Class,SingleLineScript>();


    public static Map<Class,SingleLineScript> getSingleLineScripts(){

        if(cachedSingleLineScripts != null && !cachedSingleLineScripts.isEmpty()){
            return cachedSingleLineScripts;

        }

        Map<Integer,Class> sortedSingleLineScripts = new TreeMap<Integer,Class>();
        Class singleLineClazz = SingleLineScript.class;
        
        if(singleLineClazz.isAnnotationPresent(Implementors.class)){
            Implementors implementors =(Implementors) singleLineClazz.getAnnotation(Implementors.class);

            for(Implementor implementor :implementors.value()){
                try {
                        String clazzStr  = SCRIPT_PACKAGE+"."+implementor.clazz();
                       
                        Class clazz = Class.forName(clazzStr);
                        

                        sortedSingleLineScripts.put(implementor.parsingSequence(), clazz);
                        //cachedSingleLineScripts.put(clazz, instance);

                } catch (Exception ex) {
                   logger.severe("error while caching single line scripts ..."+ex.getMessage());
                   System.out.println("error while caching single line scripts ..."+ex.getMessage());
                }

            }
            try{
                for(Entry<Integer,Class> entry:sortedSingleLineScripts.entrySet()){
                    SingleLineScript instance = (SingleLineScript) entry.getValue().newInstance();
                    cachedSingleLineScripts.put(entry.getValue(),instance );
                }
            }catch(Exception ex){
                 logger.severe("error while creating single line scripts ..."+ex.getMessage());
                 System.out.println("error while creating single line scripts ..."+ex.getMessage());
            }

        }

        return cachedSingleLineScripts;
    }
}




