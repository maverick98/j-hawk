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

package org.hawk.lang.enumeration;
import static org.hawk.lang.constant.HawkLanguageKeyWord.var;
import static org.hawk.lang.constant.HawkLanguageKeyWord.varx;
/**
 *
 * @author msahu
 */
public enum VarTypeEnum {

    VAR,STRUCT,VARX,UNDEFINED;
    public static VarTypeEnum parse(String varTypeEnumStr){
        if(varTypeEnumStr == null || varTypeEnumStr.isEmpty()){
            return UNDEFINED;
        }
        VarTypeEnum result = UNDEFINED;
        if(varTypeEnumStr.equals(var)){
            result = VAR;
        }else  if(varTypeEnumStr.equals(varx)){
            result = VARX;
        }else{
            result = STRUCT;
        }
        return result;
    }
}
