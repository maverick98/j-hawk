package org.hawk.module.script;

import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import java.util.regex.Pattern;
import org.hawk.module.annotation.PostCreateScript;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.module.script.type.Variable;

/**
 *
 * @version 1.0 29 Apr, 2010
 * @author msahu
 */
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

    @ScriptPattern
    public static Pattern getPattern() {
        return VARIABLE_PATTERN;
    }

    public static LocalVarDeclScript createDummyScript() {
        Variable variable = new Variable(VarTypeEnum.VAR, null, null);
        BooleanDataType rtn = new BooleanDataType(true);
        variable.setVariableValue(rtn);
        LocalVarDeclScript result = new LocalVarDeclScript();
        result.setLocalVarValue(variable);
        result.setLocalVar(variable);
        return result;
    }

    @Override
    public IScript execute() throws HawkException {


        Variable result = null;


        result = this.evaluateLocalVariable(this.getLocalVarValExp()).getVariableValue();
        this.variableValue = result;

        this.outerMultiLineScript.setLocalValue(this.variable, this);
        this.variable.setVariableValue(result.getVariableValue());
        return this;
    }

    @CreateScript
    public static LocalVarDeclScript createScript(Map<Integer, String> lineLocalVarDeclMatcherMap) {

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
    public boolean checkVariable() throws HawkException {
        boolean status = false;
        if (LocalVarDeclScript.isValid(this.getLocalVar(), this.getOuterMultiLineScript())) {

            this.getOuterMultiLineScript().setLocalValue(this.getLocalVar(), null);
            status = true;
        } else {
            throw new HawkException(this.getLocalVar() + " is already declared");
        }
        return status;
    }

    public static boolean isValid(Variable variable, MultiLineScript multiLineScript) throws HawkException {

        if (variable == null || variable.getVariableName() == null || variable.getVariableName().isEmpty() || multiLineScript == null) {
            throw new HawkException("invalid parameter");
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
                            ? !ScriptInterpreter.getInstance().isGlobalVarDeclared(variable)
                            : false;
                }

                multiLineScript = multiLineScript.getOuterMultiLineScript();
            }
        } while (true);
    }

    @Override
    public boolean isVariable() {
        return true;
    }

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

        tmp.setVariableValue(rtn);

        LocalVarDeclScript result = new LocalVarDeclScript();

        result.setVariable(tmp);

        result.setVariableValue(tmp);

        return result;
    }

    @Override
    public IScript equalTo(IScript otherScript) throws HawkException {

        LocalVarDeclScript result = LocalVarDeclScript.createBooleanScript();
        BooleanDataType type = (BooleanDataType)result.getVariableValue().getVariableValue();
        if(otherScript == null){

            type.setData(false);
            return result;
        }
        if( ! (otherScript instanceof LocalVarDeclScript)){
            throw new HawkException("incompatible type");
        }
        LocalVarDeclScript otherLocalVarDeclScript = (LocalVarDeclScript)otherScript;
        if(this.getLocalVar().equals(otherLocalVarDeclScript.getLocalVar())){
            if(this.getLocalVarValue().equals(otherLocalVarDeclScript.getLocalVarValue())){
                type.setData(true);
            }else{
                type.setData(false);
            }
        }else{
            type.setData(false);
        }
        return result;
    }
}
