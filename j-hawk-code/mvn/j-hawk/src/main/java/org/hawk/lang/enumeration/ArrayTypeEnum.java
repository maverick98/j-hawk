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

import static org.hawk.lang.enumeration.VarTypeEnum.VAR;

/**
 *
 * @author msahu
 */
public enum ArrayTypeEnum {

    STRUCT(VarTypeEnum.STRUCT),VAR(VarTypeEnum.VAR),VARX(VarTypeEnum.VARX),UNDEFINED(null);

    public static ArrayTypeEnum valueOf(VarTypeEnum varTypeEnum) {
        if(varTypeEnum == null){
            return ArrayTypeEnum.UNDEFINED;
        }
        ArrayTypeEnum arrayTypeEnum = null;
        switch(varTypeEnum){
            case STRUCT:
                arrayTypeEnum =  ArrayTypeEnum.STRUCT;
                break;
            case VAR:
                arrayTypeEnum =  ArrayTypeEnum.VAR;
                break;
            case VARX:
                arrayTypeEnum =  ArrayTypeEnum.VARX;
                break;
                
            default:
                arrayTypeEnum = UNDEFINED;
        }
        return arrayTypeEnum;
    }
    private VarTypeEnum varType;

    ArrayTypeEnum(VarTypeEnum varTypeEnum){
        this.varType = varTypeEnum;
    }
    public boolean isStructArray(){
        return varType== VarTypeEnum.STRUCT;
    }
    public boolean isVarArray(){
        return varType== VarTypeEnum.VAR;
    }
    public boolean isVarxArray(){
        return varType== VarTypeEnum.VARX;
    }
//   
}