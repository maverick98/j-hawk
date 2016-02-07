

package org.hawk.module.script;

import org.hawk.module.script.type.IDataType;
import java.util.LinkedHashMap;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.exception.HawkException;
import java.util.Map;
import java.util.regex.Pattern;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.Variable;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 *
 * @version 1.0 1 May, 2010
 * @author msahu
 */
public class VariableDeclScript extends SingleLineScript{

   protected  static final Pattern VARIABLE_PATTERN=
   Pattern.compile("\\s*"+var+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\.|\\d]*)\\s*=\\s*(.*)\\s*");


    public VariableDeclScript(){

    }
    protected Variable variable;


    protected String vaiableValExp;

    protected String OrigVariableValExp;

    protected Variable variableValue = null;

    public String getOrigVariableValExp() {
        return OrigVariableValExp;
    }

    public void setOrigVariableValExp(String OrigVariableValExp) {
        this.OrigVariableValExp = OrigVariableValExp;
    }

    

    public String getVariableValExp() {
        return vaiableValExp;
    }

    public void setVariableValExp(String variableValExp) {
        this.vaiableValExp = variableValExp;
    }

    @Override
    public int length(){
        return this.getVariableValue().getVariableValue().toString().length();
    }

    @Override
    public void setVariable(Variable var) {
        this.variable = var;
    }

    @Override
    public void setVariableValue(Variable variableValue) {
        this.variableValue = variableValue;
    }

    @Override
    public Variable getVariable() {
        return variable;
    }

    @Override
    public Variable getVariableValue() {
        return this.variableValue;
    }

    
    public static Pattern getPattern() {
        return VARIABLE_PATTERN;
    }
    @Override
    public IScript execute() throws HawkException {

        if(ScriptInterpreter.getInstance().isGlobalVarDeclared(this.variable)){
            throw new HawkException(this.variable +" is already declared.");
        }
        
        
        this.variableValue = this.evaluateGlobalVariable(this.vaiableValExp).getVariableValue();
        
        ScriptInterpreter.getInstance()
                         .setGlobalValue
                            (
                                this.variable,
                                this
                            );

        
        return this;
    }


    public static VariableDeclScript createScript(Map<Integer,String> lineVariableDeclMatcherMap){

        if(lineVariableDeclMatcherMap == null){
            return null;
        }
        VariableDeclScript variableDeclScript = new VariableDeclScript();
        Variable var = new Variable(VarTypeEnum.VAR,null,lineVariableDeclMatcherMap.get(1));
        variableDeclScript.setVariable(var);
        variableDeclScript.setVariableValExp(lineVariableDeclMatcherMap.get(2));
        return variableDeclScript;
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public IScript add(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().add(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript divide(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().divide(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript equalTo(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().equalTo(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript greaterThan(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().greaterThan(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript greaterThanEqualTo(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().greaterThanEqualTo(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript lessThan(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().lessThan(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript lessThanEqualTo(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().lessThanEqualTo(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript modulus(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().modulus(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript multiply(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().multiply(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript subtract(IScript otherScript) throws HawkException {
        Variable resultVar  = this.getVariableValue().subtract(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IScript assign(IScript otherScript) throws HawkException {

        if(otherScript == null){
            throw new HawkException("Can not assign null...");
        }
        if(!(otherScript instanceof VariableDeclScript) ){
            throw new HawkException("Type mismatch");
        }
        VariableDeclScript otherVariableDeclScript = (VariableDeclScript)otherScript;
        this.setVariableValExp(otherVariableDeclScript.getVariableValExp());
        //this.setVariable(otherVariableDeclScript.getVariable());
        this.setVariableValue(otherVariableDeclScript.getVariableValue());

        Variable variable = new Variable(VarTypeEnum.VAR,null,null);
        BooleanDataType rtn = new BooleanDataType(true);
        variable.setVariableValue(rtn);
        IScript result = new LocalVarDeclScript();
        result.setVariable(variable);
        result.setVariableValue(variable);
        return result;
    }

    @Override
    public String toUI() {
        IDataType value = this.variableValue.getVariableValue();
        return value == null ?"null":value.toString();
    }

    
    @Override
    public Map<Object, Object> toJavaMap() throws HawkException {

          Map<Object,Object> javaMap = new LinkedHashMap<Object,Object>();
          javaMap.put(this.getVariable().getVariableName(), this.getVariableValue().getVariableValue()+"");
          return javaMap;
    }

    @Override
    public Object toJava() throws HawkException {
        return this;
    }

    @Override
    public String toString() {
        return this.getVariableValue().getVariableValue().toString();
    }


    @Override
    public IScript copy() {
        VariableDeclScript copy = new VariableDeclScript();
        copy.setVariable(this.getVariable().copy());
        copy.setVariableValExp(this.getVariableValExp());
        copy.setVariableValue(this.getVariableValue().copy());
        copy.setOrigVariableValExp(this.getOrigVariableValExp());
        copy.setOuterMultiLineScript(this.getOuterMultiLineScript());
        return copy;
    }


    @Override
    public String mangle(){

        return "_"+var+"_";
    }
    
}




