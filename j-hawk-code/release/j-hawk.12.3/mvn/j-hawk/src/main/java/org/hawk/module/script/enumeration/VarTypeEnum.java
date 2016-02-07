

package org.hawk.module.script.enumeration;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 *
 * @author msahu
 */
public enum VarTypeEnum {

    VAR,STRUCT,UNDEFINED;
    public static VarTypeEnum parse(String varTypeEnumStr){
        if(varTypeEnumStr == null || varTypeEnumStr.isEmpty()){
            return UNDEFINED;
        }
        VarTypeEnum result = UNDEFINED;
        if(varTypeEnumStr.equals(var)){
            result = VAR;
        }else{
            result = STRUCT;
        }
        return result;
    }
}
