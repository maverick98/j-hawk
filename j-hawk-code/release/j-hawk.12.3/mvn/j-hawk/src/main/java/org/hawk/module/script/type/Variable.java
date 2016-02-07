

package org.hawk.module.script.type;

import org.hawk.logger.HawkLogger;
import org.hawk.exception.HawkException;
import org.hawk.module.script.enumeration.ArrayTypeEnum;
import org.hawk.module.script.enumeration.VarTypeEnum;

/**
 *
 * @version 1.0 25 Jul, 2010
 * @author msahu
 */
public final class Variable {

    private static final HawkLogger logger = HawkLogger.getLogger(Variable.class.getName());

    private VarTypeEnum varTypeEnum;

    private String structName;

    private String variableName;

    private IDataType variableValue;

    private Integer index;

    private boolean rtrn;

    private ArrayTypeEnum arrayType;

    /**
     * 
     * @param varTypeEnum
     * @param structName is used if VarTypeEnum is STRUCT otherwise ignored.
     * @param variableName
     */
    public Variable(VarTypeEnum varTypeEnum, String structName, String variableName) {
        this(varTypeEnum,structName,variableName,ArrayTypeEnum.UNDEFINED);
    }
    public Variable(VarTypeEnum varTypeEnum, String structName, String variableName,ArrayTypeEnum arrayType) {
        this.varTypeEnum = varTypeEnum;
        this.structName = structName;
        this.variableName = variableName;
        this.arrayType = arrayType;
        this.index = -1;
    }

    public Variable(){
        
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    
    public ArrayTypeEnum getArrayType() {
        return arrayType;
    }

    public void setArrayType(ArrayTypeEnum arrayType) {
        this.arrayType = arrayType;
    }

    
    public Variable copy(){

        Variable clonedCopy = new Variable();
        clonedCopy.setVarTypeEnum(this.getVarTypeEnum());
        clonedCopy.setStructName(this.getStructName());
        clonedCopy.setVariableName(this.getVariableName());
        clonedCopy.setArrayType(this.getArrayType());
        try {
            clonedCopy.setVariableValue
                        (
                            this.getVariableValue()!= null
                                                    ?
                                                            this.getVariableValue().copy()
                                                        :
                                                            null
                        );
        } catch (HawkException ex) {
            logger.severe( null, ex);
            return null;
        }
        return clonedCopy;
    }

    public IDataType getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(IDataType variableValue) {
        this.variableValue = variableValue;
    }

    public boolean isRtrn() {
        return rtrn;
    }

    public void setRtrn(boolean rtrn) {
        this.rtrn = rtrn;
    }


    

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public VarTypeEnum getVarTypeEnum() {
        return varTypeEnum;
    }

    public void setVarTypeEnum(VarTypeEnum varTypeEnum) {
        this.varTypeEnum = varTypeEnum;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        if(this.varTypeEnum == null){
            sb.append("VAR");
        }else{
            sb.append(this.varTypeEnum.name());
        }
        sb.append("-");
        if(this.structName != null){
            sb.append(this.structName);
        }
        sb.append("-");
        if(this.variableName != null){
            sb.append(this.variableName);
        }
        sb.append("-");
        if(this.variableValue != null){
            sb.append(this.variableValue.toString());
        }
        sb.append(">");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Variable other = (Variable) obj;
        if (this.varTypeEnum != other.varTypeEnum) {
            return false;
        }
        if ((this.structName == null) ? (other.structName != null) : !this.structName.equals(other.structName)) {
            return false;
        }
        if ((this.variableName == null) ? (other.variableName != null) : !this.variableName.equals(other.variableName)) {
            return false;
        }
        if (this.arrayType != other.getArrayType()){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.varTypeEnum != null ? this.varTypeEnum.hashCode() : 0);
        hash = 97 * hash + (this.structName != null ? this.structName.hashCode() : 0);
        hash = 97 * hash + (this.variableName != null ? this.variableName.hashCode() : 0);
        hash = 97 * hash + (this.arrayType!=null?this.arrayType.hashCode():0);
        return hash;
    }

    public Variable add(Variable otherVariable) throws HawkException{


        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        
        IDataType resultData = thisData.add(thatData);

        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable subtract(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.subtract(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable divide(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.divide(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable modulus(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.modulus(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable multiply(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.multiply(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }

    public Variable greaterThan(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.greaterThan(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable lessThan(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.lessThan(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable greaterThanEqualTo(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.greaterThanEqualTo(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable lessThanEqualTo(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.lessThanEqualTo(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }

    public Variable equalTo(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.equalTo(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }

    public Variable and(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.and(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }
    public Variable or(Variable otherVariable) throws HawkException{

        IDataType thisData = this.getVariableValue().copy();
        IDataType thatData = otherVariable.getVariableValue().copy();
        IDataType resultData = thisData.or(thatData);
        Variable rtn = new Variable(VarTypeEnum.VAR,null,null);
        rtn.setVariableValue(resultData);
        return rtn;
    }

}




