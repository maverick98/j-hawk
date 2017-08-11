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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.common.di.AppContainer;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.executor.cache.singleline.globalvar.GlobalVariableScriptCache;
import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.var;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.DataTypeFactory;
import org.hawk.lang.type.DoubleDataType;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
/**
 *
 * @version 1.0 1 May, 2010
 * @author msahu
 */
public class VariableDeclScript extends SingleLineScript implements IObjectScript{

   protected  static final Pattern VARIABLE_PATTERN=
   Pattern.compile("\\s*"+var+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|_|\\.|\\d]*)\\s*=\\s*(.*)\\s*");


    public VariableDeclScript(){

    }
    protected Variable variable;


    protected String vaiableValExp;

    protected String origVariableValExp;

    protected Variable variableValue = null;

    public String getOrigVariableValExp() {
        return origVariableValExp;
    }

    public void setOrigVariableValExp(String OrigVariableValExp) {
        this.origVariableValExp = OrigVariableValExp;
    }

    

    public String getVariableValExp() {
        return vaiableValExp;
    }

    public void setVariableValExp(String variableValExp) {
        this.vaiableValExp = variableValExp;
    }

    @Override
    public int length(){
        return this.getVariableValue().getValue().toString().length();
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
    
    public static class VariableDecl extends SingleLineGrammar {

        @Override
        public String toString() {
            return "VariableDecl" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getVariableDecl().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getVariableDecl().getLinePattern();
    }
  
    @Override
    public IScript execute() throws Exception {

        if(AppContainer.getInstance().getBean(GlobalVariableScriptCache.class).isGlobalVarDeclared(this.variable)){
            throw new Exception(this.variable +" is already declared.");
        }
        
        
        this.variableValue = this.evaluateGlobalVariable(this.vaiableValExp).getVariableValue();
        
        AppContainer.getInstance().getBean(GlobalVariableScriptCache.class)
                         .setGlobalValue
                            (
                                this.variable,
                                this
                            );

        
        return this;
    }

   
   @Override
    public  VariableDeclScript createScript(Map<Integer,String> lineVariableDeclMatcherMap) throws Exception{

        if(lineVariableDeclMatcherMap == null){
            return null;
        }
        VariableDeclScript variableDeclScript = new VariableDeclScript();
        Variable var = new Variable(VarTypeEnum.VAR,null,lineVariableDeclMatcherMap.get(1));
        variableDeclScript.setVariable(var);
        variableDeclScript.setVariableValExp(lineVariableDeclMatcherMap.get(2));
        return variableDeclScript;
    }
/*
    @Override
    public boolean isVariable() {
        return true;
    }
*/
    @Override
    public IObject add(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().add(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject divide(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().divide(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject equalTo(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().equalTo(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject greaterThan(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().greaterThan(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject greaterThanEqualTo(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().greaterThanEqualTo(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject lessThan(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().lessThan(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject lessThanEqualTo(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().lessThanEqualTo(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject modulus(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().modulus(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject multiply(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().multiply(otherScript.getVariableValue());
        
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject subtract(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        Variable resultVar  = this.getVariableValue().subtract(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {

        if(otherScript == null){
            throw new Exception("Can not assign null...");
        }
        if(!(otherScript instanceof VariableDeclScript) ){
            throw new Exception("Type mismatch");
        }
        VariableDeclScript otherVariableDeclScript = (VariableDeclScript)otherScript;
        
        this.setVariableValExp(otherVariableDeclScript.getVariableValExp());
        //this.setVariable(otherVariableDeclScript.getVariable());
        this.setVariableValue(otherVariableDeclScript.getVariableValue());
        this.getVariable().setValue(otherVariableDeclScript.getVariableValue().getValue());
        
        return this;
    }

    @Override
    public String toUI() {
        IDataType value = this.variableValue.getValue();
        return value == null ?"null":value.toString();
    }
    @Override
    public String toJson(){

       return this.getVariableValue().getValue().toJson();
    }

    
    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

          Map<Object,Object> javaMap = new LinkedHashMap<>();
          javaMap.put(this.getVariable().getName(), this.getVariableValue().getValue()+"");
          return javaMap;
    }

    @Override
    public Object toJava() throws Exception {
        Object result = null;

        result = DataTypeFactory.createJavaObject(this.getVariableValue().getValue());

        return result;
    }

    @Override
    public String toString() {
        
        return this.getVariableValue().getValue().toString();
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

    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
       IObjectScript otherScript = (IObjectScript)other;
        IObjectScript resultScript = LocalVarDeclScript.createDummyBooleanScript(false);
       IDataType dataType = this.getVariableValue().getValue();
       String isCheckStr = otherScript.getVariable().getName();
       if("isBool".equalsIgnoreCase(isCheckStr)){
           if(dataType instanceof BooleanDataType){
                resultScript = LocalVarDeclScript.createDummyBooleanScript(true);
           }
       }else if("isInt".equalsIgnoreCase(isCheckStr)){
           if(dataType instanceof IntDataType){
                resultScript = LocalVarDeclScript.createDummyBooleanScript(true);
           }
       }else if("isDouble".equalsIgnoreCase(isCheckStr)){
           if(dataType instanceof DoubleDataType){
                resultScript = LocalVarDeclScript.createDummyBooleanScript(true);
           }
       }else if("isStr".equalsIgnoreCase(isCheckStr)){
           if(dataType instanceof StringDataType){
                resultScript = LocalVarDeclScript.createDummyBooleanScript(true);
           }
       }
       return resultScript;
    }

    @Override
    public boolean passByReference() {
        return false;
    }

    @Override
    public IObject and(IObject otherObject) throws Exception {
         IObjectScript otherScript = (IObjectScript)otherObject;
        Variable resultVar  = this.getVariableValue().and(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IObject or(IObject otherObject) throws Exception {
        IObjectScript otherScript = (IObjectScript)otherObject;
        Variable resultVar  = this.getVariableValue().or(otherScript.getVariableValue());
        VariableDeclScript result = new VariableDeclScript();
        result.setVariable(resultVar);
        result.setVariableValue(resultVar);
        return result;
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableDeclScript other = (VariableDeclScript) obj;
        if (this.variable != other.variable && (this.variable == null || !this.variable.equals(other.variable))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    @Override
    public IObjectScript getActualObjectScript() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}



