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
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;

import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.type.Variable;

/**
 *
 * @author Manoranjan Sahu
 */
public class ArrayMemberProxyScript extends SingleLineScript implements IObjectScript{

    private IObjectScript actualMemberScript;

    public IObjectScript getActualMemberScript() {
        return actualMemberScript;
    }

    public void setActualMemberScript(IObjectScript actualMemberScript) {
        this.actualMemberScript = actualMemberScript;
    }
    
    
    
    @Override
    public Variable getVariable() {
        return this.getActualMemberScript().getVariable();
    }

    @Override
    public void setVariable(Variable value) {
        this.getActualMemberScript().setVariable(value);
    }

    @Override
    public void setVariableValue(Variable value) {
        this.getActualMemberScript().setVariableValue(value);
    }

    @Override
    public Variable getVariableValue() {
        return this.getActualMemberScript().getVariableValue();
    }

    @Override
    public String mangle() {
        return this.getActualMemberScript().mangle();
    }

    @Override
    public int length() {
        return this.getActualMemberScript().length();
    }

    @Override
    public boolean passByReference() {
        return this.getActualMemberScript().passByReference();
    }

    @Override
    public String toUI() {
        return this.getActualMemberScript().toUI();
    }

    @Override
    public IObject add(IObject other) throws Exception {
        
        return this.getActualMemberScript().add(other);
    }

    @Override
    public IObject subtract(IObject other) throws Exception {
        
        return this.getActualMemberScript().subtract(other);
    }

    @Override
    public IObject multiply(IObject other) throws Exception {
        
        return this.getActualMemberScript().multiply(other);
    }

    @Override
    public IObject divide(IObject other) throws Exception {
        
        return this.getActualMemberScript().divide(other);
    }

    @Override
    public IObject modulus(IObject other) throws Exception {
        
        return this.getActualMemberScript().modulus(other);
    }

    @Override
    public IObject equalTo(IObject other) throws Exception {
        
        return this.getActualMemberScript().equalTo(other);
    }

    @Override
    public IObject greaterThan(IObject other) throws Exception {
        
        return this.getActualMemberScript().greaterThan(other);
    }

    @Override
    public IObject lessThan(IObject other) throws Exception {
        
        return this.getActualMemberScript().lessThan(other);
    }

    @Override
    public IObject greaterThanEqualTo(IObject other) throws Exception {
        
        return this.getActualMemberScript().greaterThanEqualTo(other);
    }

    @Override
    public IObject lessThanEqualTo(IObject other) throws Exception {
        
        return this.getActualMemberScript().lessThanEqualTo(other);
    }

    @Override
    public IObject and(IObject other) throws Exception {
        
        return this.getActualMemberScript().and(other);
    }

    @Override
    public IObject or(IObject other) throws Exception {
        
        return this.getActualMemberScript().or(other);
    }

    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
        
        return this.getActualMemberScript().refer(other);
    }
    private Variable var; 

    public Variable getVar() {
        return var;
    }

    public void setVar(Variable var) {
        this.var = var;
    }
    
    

    @Override
    public IHawkObject assign(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        IHawkObject result = null;
        IObjectScript memberScript = null;
        if(otherScript instanceof VariableDeclScript){
            LocalVarDeclScript lvds = new LocalVarDeclScript();
            lvds.setLocalVar(this.getVar());
            lvds.setLocalVarValue(lvds.getLocalVar());
            memberScript = lvds;
            result = lvds.assign(other);
        }else if(otherScript instanceof ArrayDeclScript){
            ArrayDeclScript arrayDeclScript = ArrayDeclScript.createDefaultArrayScript();
            arrayDeclScript.setArrayVariable(this.getVar());
            memberScript = arrayDeclScript;
            result = arrayDeclScript.assign(otherScript);
        }
        this.setActualMemberScript(memberScript);
        return result;
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject other) throws Exception {
        return this.getActualMemberScript().arrayBracket(other);
    }

    @Override
    public Object toJava() throws Exception {
        return this.getActualMemberScript().toJava();
    }

    @Override
    public boolean isProxy() {
        return true;
    }

    @Override
    public IObjectScript getActualObjectScript() {
        return this.getActualMemberScript();
    }

    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

         return this.actualMemberScript.toJavaMap();
    }
}
