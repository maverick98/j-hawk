/*
 * This file is part of hawkeye
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
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
