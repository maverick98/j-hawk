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
package org.hawk.lang.object;

import java.util.Map;
import java.util.regex.Pattern;
import org.common.di.AppContainer;
import org.commons.ds.exp.IObject;

import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.DoubleDataType;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
import org.hawk.module.annotation.PostCreateScript;
import org.common.di.ScanMe;
import org.hawk.ds.exp.IHawkObject;
import org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache;
/**
 *
 * @version 1.0 29 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class LocalVarDeclScript extends VariableDeclScript {

    public LocalVarDeclScript() {
        super();
    }

    public String getLocalVarValExp() {
        String result = this.getOrigVariableValExp();
        if (this.vaiableValExp != null && !this.vaiableValExp.isEmpty()) {
            result = this.vaiableValExp;
        }
        return result;
    }

    public void setLocalVarValExp(String localVarValExp) {
        if (localVarValExp != null && !localVarValExp.isEmpty()) {
            this.vaiableValExp = localVarValExp;
        } else {
            this.vaiableValExp = this.getOrigVariableValExp();
        }
    }

    public void setLocalVar(Variable localVar) {
        this.variable = localVar;
    }

    public void setLocalVarValue(Variable localVarValue) {
        this.variableValue = localVarValue;
    }

    public Variable getLocalVar() {
        return variable;
    }

    public Variable getLocalVarValue() {
        return this.variableValue;
    }

    @Override
    public LocalVarDeclScript copy() {
        LocalVarDeclScript copy = new LocalVarDeclScript();
        copy.setLocalVar(this.getLocalVar());
        copy.setLocalVarValExp(this.getLocalVarValExp());
        copy.setLocalVarValue(this.getLocalVarValue());
        copy.setOrigVariableValExp(this.getOrigVariableValExp());
        copy.setOuterMultiLineScript(this.getOuterMultiLineScript());
        return copy;
    }

    
    public  Pattern getPattern() {
        return VARIABLE_PATTERN;
    }

    public static LocalVarDeclScript createDummyBooleanScript() {
        BooleanDataType booleanDataType = new BooleanDataType(true);
        return createDummyScript(booleanDataType);
    }

    public static LocalVarDeclScript createDummyStringScript() {
        StringDataType stringDataType = new StringDataType("");
        return createDummyScript(stringDataType);
    }

    public static LocalVarDeclScript createDummyStringScript(String data) {
        StringDataType stringDataType = new StringDataType("");
        LocalVarDeclScript localVarDeclScript =  createDummyScript(stringDataType);

        Variable variableValue = localVarDeclScript.getVariableValue();
        variableValue.setName(data);
        variableValue.setValue(new StringDataType(data));

        return localVarDeclScript;
    }
    public static LocalVarDeclScript createDummyBooleanScript(boolean data) {
        BooleanDataType stringDataType = new BooleanDataType(data);
        LocalVarDeclScript localVarDeclScript =  createDummyScript(stringDataType);

        Variable variableValue = localVarDeclScript.getVariableValue();
        variableValue.setName("");
        variableValue.setValue(new BooleanDataType(data));

        return localVarDeclScript;
    }
    public static LocalVarDeclScript createDummyDoubleScript(double data) {
        DoubleDataType doubleDataType = new DoubleDataType(0.0);
        LocalVarDeclScript localVarDeclScript =  createDummyScript(doubleDataType);

        Variable variableValue = localVarDeclScript.getVariableValue();
        variableValue.setName("");
        variableValue.setValue(new DoubleDataType(data));

        return localVarDeclScript;
    }
    public static LocalVarDeclScript createDummyIntScript(int data) {
        IntDataType intDataType = new IntDataType(0);
        LocalVarDeclScript localVarDeclScript =  createDummyScript(intDataType);

        Variable variableValue = localVarDeclScript.getVariableValue();
        variableValue.setName("");
        variableValue.setValue(new IntDataType(data));

        return localVarDeclScript;
    }
    public static LocalVarDeclScript createDummyDoubleScript() {
        
        DoubleDataType doubleDataType = new DoubleDataType(0.0);
        return createDummyScript(doubleDataType);
    }

    public static LocalVarDeclScript createDummyIntScript() {

        IntDataType intDataType = new IntDataType(0);
        return createDummyScript(intDataType);
    }
    private static LocalVarDeclScript createDummyScript(IDataType variableValue) {
        Variable variable = Variable.createDummyVariable(variableValue);
        
        LocalVarDeclScript result = new LocalVarDeclScript();
        result.setLocalVarValue(variable);
        result.setLocalVar(variable);
        return result;
    }
    @Override
    public IObjectScript execute() throws Exception {

        IObjectScript result;
        result = this.evaluateLocalVariable(this.getLocalVarValExp());
        IObjectScript clonedResult = (IObjectScript)result.copy();
        this.variableValue = clonedResult.getVariableValue();

        this.outerMultiLineScript.setLocalValue(this.variable, clonedResult);
        if(this.getVariableValue() != null && this.variable!= null){
            this.variable.setValue(this.getVariableValue().getValue());
        }
        return this;
    }
    
    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
        super.assign(otherScript);
    //    VariableDeclScript otherVariableDeclScript = (VariableDeclScript)otherScript;
     //   System.out.println("cur value of  "+this.outerMultiLineScript.getLocalValue(variable));
      //  System.out.println("setting value "+otherVariableDeclScript.getVariableValue().getValue() + " in "+this.outerMultiLineScript);
    //    this.outerMultiLineScript.setLocalValue(variable, otherVariableDeclScript);
        return this;
    }
    
    @Override
    public  LocalVarDeclScript createScript(Map<Integer, String> lineLocalVarDeclMatcherMap) throws Exception{

        if (lineLocalVarDeclMatcherMap == null) {
            return null;
        }
        LocalVarDeclScript localVarDeclScript = new LocalVarDeclScript();
        Variable localVar = new Variable(VarTypeEnum.VAR, null, lineLocalVarDeclMatcherMap.get(1));
        localVarDeclScript.setLocalVar(localVar);
        localVarDeclScript.setLocalVarValExp(lineLocalVarDeclMatcherMap.get(2));
        localVarDeclScript.setOrigVariableValExp(localVarDeclScript.getLocalVarValExp());
        return localVarDeclScript;
    }

    @PostCreateScript
    public boolean checkVariable() throws Exception {
        boolean status = false;
        if (LocalVarDeclScript.isValid(this.getLocalVar(), this.getOuterMultiLineScript())) {

            this.getOuterMultiLineScript().setLocalValue(this.getLocalVar(), null);
            status = true;
        } else {
            throw new Exception(this.getLocalVar() + " is already declared");
        }
        return status;
    }

    public static boolean isValid(Variable variable, MultiLineScript multiLineScript) throws Exception {

        if (variable == null || variable.getName() == null || variable.getName().isEmpty() || multiLineScript == null) {
            throw new Exception("invalid parameter");
        }

        boolean operandDeclared = false;
        do {
            operandDeclared = multiLineScript.isLocalVarDeclared(variable);
            if (operandDeclared) {
                return false;
            } else {

                if (multiLineScript.getFunctionScript() != null) {
                    operandDeclared = multiLineScript.getFunctionScript().isParamVarDeclared(variable);
                    return !operandDeclared
                            ? !AppContainer.getInstance().getBean(GlobalVariableScriptCache.class).isGlobalVarDeclared(variable)
                            : false;
                }

                multiLineScript = multiLineScript.getOuterMultiLineScript();
            }
        } while (true);
    }
    /*
    @Override
    public boolean isVariable() {
        return true;
    }
    */ 

    @Override
    public Variable getVariable() {
        return super.getVariable();
    }

    @Override
    public void setVariableValue(Variable variableValue) {
        super.setVariableValue(variableValue);
    }

    public static  LocalVarDeclScript createBooleanScript() {

        Variable tmp = new Variable(VarTypeEnum.VAR, null, null);

        BooleanDataType rtn = new BooleanDataType(true);

        tmp.setValue(rtn);

        LocalVarDeclScript result = new LocalVarDeclScript();

        result.setVariable(tmp);

        result.setVariableValue(tmp);

        return result;
    }

    @Override
    public IObject equalTo(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        LocalVarDeclScript result = LocalVarDeclScript.createBooleanScript();
        BooleanDataType type = (BooleanDataType)result.getVariableValue().getValue();
        if(otherScript == null){

            type.setData(false);
            return result;
        }
        if( ! (otherScript instanceof LocalVarDeclScript  ||  otherScript instanceof VariableDeclScript)){
            throw new Exception("incompatible type");
        }
        VariableDeclScript otherLocalVarDeclScript = (VariableDeclScript)otherScript;
        
        if(this.getLocalVarValue().getValue().equalTo(otherLocalVarDeclScript.getVariableValue().getValue()).isData()){
            type.setData(true);
        }else{
            type.setData(false);
        }
       
        return result;
    }
    
}
