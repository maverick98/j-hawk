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