package org.hawk.lang.object;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.common.di.ScanMe;
import org.commons.ds.exp.IObject;
import org.commons.file.FileUtil;
import org.hawk.ds.exp.IHawkObject;
import org.hawk.lang.IScript;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.type.Variable;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
public class JSONObjectScript extends VARXVariableDeclProxyScript {
    
    private JSONObject json;
    
    public JSONObject getJson() {
        return json;
    }
    
    public void setJson(JSONObject json) {
        this.json = json;
    }
    
    @Override
    public IObjectScript execute() throws Exception {
        this.setJson(new JSONObject(FileUtil.readFile(this.getResult().getVariableValue().getValue().toString())));
        return this;
    }
    
    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript) other;
        
        Object rtn = this.getJson().get(otherScript.getVariable().getName());
        JSONObjectScript script = new JSONObjectScript();
        script.setVariable(new Variable(VarTypeEnum.VAR, null, otherScript.getVariable().getName()));
        script.setVariableValue(script.getVariable());
     //   script.setJson(new JSONObject(rtn));
        return script;
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
    public boolean isProxy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public IObjectScript getActualObjectScript() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object toJava() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public IScript copy() {
        JSONObjectScript copy = new JSONObjectScript();
        copy.setVariable(this.getVariable());
        copy.setVariableValue(this.getVariableValue());
        copy.setLocalVarx(this.getLocalVarx());
        copy.setLocalVarxExp(this.getLocalVarxExp());
        copy.setOuterMultiLineScript(this.getOuterMultiLineScript());
        copy.setJson(json);
        return copy;
    }
    
    @Override
    public String toUI() {
        String ui = "";
        try {
            ui = this.getJson().toString(2);
        } catch (JSONException ex) {
            Logger.getLogger(JSONObjectScript.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ui;
    }
     @Override
    public String toString() {
        return this.toUI();
    }
    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

          Map<Object,Object> javaMap = new LinkedHashMap<Object,Object>();
         
          javaMap.put(this.getVariable().getName(), this.getJson());
          return javaMap;
    }
    
}
