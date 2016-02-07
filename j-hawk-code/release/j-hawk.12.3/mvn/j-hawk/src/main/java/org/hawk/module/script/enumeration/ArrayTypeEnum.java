/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.module.script.enumeration;

/**
 *
 * @author msahu
 */
public enum ArrayTypeEnum {

    STRUCT(VarTypeEnum.STRUCT),VAR(VarTypeEnum.VAR),UNDEFINED(null);

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
}
