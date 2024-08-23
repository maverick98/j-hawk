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
