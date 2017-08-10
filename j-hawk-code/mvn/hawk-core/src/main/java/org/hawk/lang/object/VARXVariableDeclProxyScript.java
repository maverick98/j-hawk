/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.hawk.lang.object;

import java.util.Map;
import java.util.Set;
import org.common.di.ScanMe;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;
import static org.hawk.lang.constant.HawkLanguageKeyWord.varx;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.Variable;
import org.hawk.module.annotation.PostCreateScript;
import org.hawk.xml.XMLUtil;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
public class VARXVariableDeclProxyScript extends SingleLineScript implements IObjectScript {

    public static class VarxVariableDecl extends SingleLineGrammar {

        @Override
        public String toString() {
            return "VarxVariableDecl" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
    private String localVarx;
    private String localVarxExp;
    private Variable variable;
    IObjectScript result;
    private Variable variableValue = null;
    
    private  VARXVariableDeclProxyScript actualScript=null;;

    public VARXVariableDeclProxyScript getActualScript() {
        return actualScript;
    }

    public void setActualScript(VARXVariableDeclProxyScript actualScript) {
        this.actualScript = actualScript;
    }

    
    
    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getVarxVariableDecl().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getVarxVariableDecl().getLinePattern();
    }

    public String getLocalVarx() {
        return localVarx;
    }

    public void setLocalVarx(String localVarx) {
        this.localVarx = localVarx;
    }

    public String getLocalVarxExp() {
        return localVarxExp;
    }

    public void setLocalVarxExp(String localVarxExp) {
        this.localVarxExp = localVarxExp;
    }

    @Override
    public VARXVariableDeclProxyScript createScript(Map<Integer, String> lineVarxVariableMatcherMap) throws Exception {

        if (lineVarxVariableMatcherMap == null) {
            return null;
        }
        VARXVariableDeclProxyScript script = new VARXVariableDeclProxyScript();
        script.setLocalVarx(lineVarxVariableMatcherMap.get(1));
        script.setLocalVarxExp(lineVarxVariableMatcherMap.get(2));
        script.setVariable(new Variable(VarTypeEnum.VARX, null, script.getLocalVarx()));
        script.setVariableValue(script.getVariable());

        return script;
    }
    
     @Override
    public IObjectScript execute() throws Exception {
        IObjectScript result;
        result = this.evaluateLocalVariable(this.getLocalVarxExp());
        
        if(result.getVariableValue().getValue().toString().endsWith("xml")){
            actualScript = new XMLVariableDeclScript();
        }else if(result.getVariableValue().getValue().toString().endsWith("json")){
            actualScript = new JSONObjectScript();
        }
        actualScript.setResult(result);
        actualScript.setLocalVarx(this.getLocalVarx());
        actualScript.setLocalVarxExp(this.getLocalVarxExp());
        actualScript.setVariable(this.getVariable());
        actualScript.setVariableValue(this.getVariableValue());
        actualScript.execute();
        actualScript.setOuterMultiLineScript(this.getOuterMultiLineScript());
        actualScript.outerMultiLineScript.setLocalValue(actualScript.getVariable(), this);
        if(result.getVariableValue() != null && this.getVariable() != null){
            actualScript.getVariable().setValue(result.getVariableValue().getValue());
        }
        return this;
    }

    @PostCreateScript
    public boolean checkVariable() throws Exception {
        boolean status = false;
        this.getOuterMultiLineScript().setLocalValue(this.getVariable(), null);
        status = true;

        return status;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public IObjectScript getResult() {
        return result;
    }

    public void setResult(IObjectScript result) {
        this.result = result;
    }
    
    public Variable getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(Variable variableValue) {
        this.variableValue = variableValue;
    }

    @Override
    public String mangle() {

        return "_" + varx + "_";
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean passByReference() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObjectScript getActualObjectScript() {
         return this.getActualScript();
    }

    @Override
    public Object toJava() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject refer(IHawkObject otherScript) throws Exception {
       return this.getActualObjectScript().refer(otherScript);
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject add(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject subtract(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject multiply(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject divide(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject modulus(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject equalTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThan(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThanEqualTo(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject and(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject or(IObject otherObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isProxy() {
        return true;
    }
    
    
}
