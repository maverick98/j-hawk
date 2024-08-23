/*
 * This file is part of j-hawk
 *  
 * 
 */
package org.hawk.lang.object;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.common.di.ScanMe;
import org.common.json.JSONUtil;
import org.commons.ds.exp.IObject;
import org.commons.file.FileUtil;
import org.hawk.ds.exp.IHawkObject;
import org.hawk.lang.IScript;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.type.DataTypeFactory;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
public class JSONObjectScript extends VARXVariableDeclProxyScript {

    private Object json;

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }

    @Override
    public IObjectScript execute() throws Exception {
        Object json;
        if (this.isReadFromFile()) {
            json = FileUtil.readFile(this.getResult().getVariableValue().getValue().toString());
        } else {
            json = this.getResult().getVariableValue().getValue().toString();
        }
        this.setJson(json);
        return this;
    }

    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript) other;
        IHawkObject result = null;
        Object rtn;
        if (JSONUtil.isJsonObject(this.getJson().toString())) {
            try {
                JSONObject thisJson = new JSONObject(this.getJson().toString());
                rtn = thisJson.get(otherScript.getVariable().getName());
                JSONObjectScript script = new JSONObjectScript();
                script.setVariable(new Variable(VarTypeEnum.VAR, null, otherScript.getVariable().getName()));
                script.setVariableValue(script.getVariable());
                script.setJson(rtn);
                script.getVariableValue().setValue(DataTypeFactory.createDataType(script.getJson().toString()));

                result = script;

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        if (result == null) {
            result = LocalVarDeclScript.createDummyStringScript();
        }

        return result;
    }

    @Override
    public int length() {
        int length = -1;
        if (JSONUtil.isJsonArray(this.getJson().toString())) {
            JSONArray array = JSONUtil.createJsonArray(this.getJson().toString());

            length = array.length();

        } else if (JSONUtil.isJsonObject(this.getJson().toString())) {
            JSONObject object = JSONUtil.createJsonObject(this.getJson().toString());

            length = object.length();
        }
        return length;
    }

    @Override
    public boolean passByReference() {
        return true;
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
        return this;
    }

    @Override
    public IHawkObject assign(IHawkObject otherObject) throws Exception {
        if (otherObject == null || !(otherObject instanceof IObjectScript)) {
            throw new Exception("Can not assign null...");
        }
        IObjectScript otherScript = (IObjectScript) otherObject;

        if (!JSONUtil.isJson(otherScript.getVariableValue().getValue().toString())) {
            return null;
        }

        return this;
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript) other;
        if (otherScript == null || otherScript.getVariable() == null || otherScript.getVariable().getValue() == null) {
            throw new Exception("null array index found...");
        }

        IDataType indexDataType = otherScript.getVariable().getValue();

        if ((indexDataType instanceof IntDataType)) {
            IntDataType index = (IntDataType) indexDataType;
            if (!index.isPositiveInteger()) {
                throw new Exception("Non integer array index access");
            }
        }
        IObjectScript result = null;
        if (JSONUtil.isJsonArray(this.getJson().toString())) {
            JSONArray array = new JSONArray(this.getJson().toString());
            Object out = array.get(Integer.parseInt(indexDataType.value()));
            JSONObjectScript jsonObject = new JSONObjectScript();
            result = jsonObject;
            jsonObject.setJson(out.toString());
            jsonObject.setVariable(new Variable(VarTypeEnum.VAR, null, null));
            jsonObject.setVariableValue(jsonObject.getVariable());
            jsonObject.getVariableValue().setValue(DataTypeFactory.createDataType(jsonObject.getJson().toString()));
        } else {
            throw new Exception("-> supported on array elements only.");
        }

        return result;
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
        return this.toJson();
    }

    @Override
    public String toString() {
        return this.toUI();
    }

    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

        Map<Object, Object> javaMap = new LinkedHashMap<>();

        javaMap.put(this.getVariable().getName(), this.getJson());
        return javaMap;
    }

    @Override
    public String toJson() {

        return this.getJson().toString();

    }

}
