/*
 * This file is part of hawkeye
 *  
 *
 * 
 *
 *  
 *  
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.lang.runtime.java.object;


import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;

/**
 *
 * @author manoranjan
 */
public class JavaObjectHawkScriptConverter {

    public static IObjectScript createHawkScript(Object javaObject) throws Exception {

        IObjectScript result;
        if(javaObject == null){
            return null;
        }
        Class javaObjectClazz = javaObject.getClass();
        if(javaObjectClazz.equals(int.class) || javaObjectClazz.equals(Integer.class)){

            result = LocalVarDeclScript.createDummyIntScript((Integer)javaObject);

        }else if(javaObjectClazz.equals(boolean.class) || javaObjectClazz.equals(Boolean.class)){

            result = LocalVarDeclScript.createDummyBooleanScript((Boolean)javaObject);

        }else if(javaObjectClazz.equals(String.class) ||javaObjectClazz.equals(char.class) || javaObjectClazz.equals(Character.class)){

            result = LocalVarDeclScript.createDummyStringScript(javaObject.toString());

        }else if(javaObjectClazz.equals(float.class) || javaObjectClazz.equals(Float.class)){

            result = LocalVarDeclScript.createDummyDoubleScript((Double)javaObject);

        }else if(javaObjectClazz.equals(double.class) || javaObjectClazz.equals(Double.class)){

            result = LocalVarDeclScript.createDummyDoubleScript((Double)javaObject);

        }else if(javaObjectClazz.equals(ArrayDeclScript.class)){

            result = (ArrayDeclScript)javaObject;

        }else{
        
            result = JavaArrayScript.isArray(javaObject)
                        ?
                                JavaArrayScript.createScript(javaObject)
                            :
                                JavaObjectScript.createScript(javaObject);
        }
        return result;
    }

   
}
