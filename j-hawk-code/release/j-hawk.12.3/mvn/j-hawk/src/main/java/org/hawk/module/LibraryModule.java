


package org.hawk.module;

import org.hawk.module.annotation.Implementor;
import org.hawk.module.annotation.Implementors;
import org.hawk.module.lib.HttpModule;
import org.hawk.module.lib.MathModule;
import org.hawk.module.lib.SystemModule;

/**
 * Placeholder for all the library modules that hawk supports.
 * @version 1.0 11 Jul, 2010
 * @author msahu
 * @see IModule
 * @see SystemModule
 * @see HttpModule
 * @see MathModule
 */

@Implementors
(
    {
            @Implementor(clazz="SystemModule"),
            @Implementor(clazz="HttpModule"),
            @Implementor(clazz="MathModule"),
            @Implementor(clazz="FileModule")

    }
)
public class LibraryModule {

    /**
     * This checks if a module is a library
     * @param moduleClassName input class name of the module
     * @return true if the module is library otherwise false
     */
    public static boolean isLibraryModule(String moduleClassName){
        Implementors implementors =(Implementors) LibraryModule.class.getAnnotation(Implementors.class);

        for(Implementor implementor :implementors.value()){
            try {
                if(moduleClassName.equals(implementor.clazz())){
                    return true;
                }
            } catch (Exception ex) {
                
            }

        }
        return false;
    }


}




