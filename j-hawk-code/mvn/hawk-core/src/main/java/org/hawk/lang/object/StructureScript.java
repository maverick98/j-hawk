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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.Header;
import org.codehaus.jettison.json.JSONException;
import static org.hawk.constant.HawkConstant.ACTION_NAME;
import static org.hawk.constant.HawkConstant.HTTP_RESPONSE_CODE;
import static org.hawk.constant.HawkConstant.HTTP_RESPONSE_MESSAGE;
import static org.hawk.constant.HawkConstant.OUT;
import org.common.di.AppContainer;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;
import org.codehaus.jettison.json.JSONObject;
import org.hawk.executor.cache.multiline.structure.IStructureDefinitionScriptCache;
import org.hawk.executor.cache.multiline.structure.StructureDefinitionScriptCache;
import org.hawk.http.HttpResponse;
import org.hawk.lang.IScript;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.common.di.ScanMe;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
@ScanMe(true)
public class StructureScript extends SingleLineScript implements IObjectScript {

    private static final HawkLogger logger = HawkLogger.getLogger(StructureScript.class.getName());
    private String structName;
    private Variable structVarName;
    private Map<String, IObjectScript> members = new HashMap<String, IObjectScript>();
    private ControlRequest controlRequest;

    @Autowired(required = true)
    //@Qualifier("default")
    private IStructureDefinitionScriptCache structureDefnScriptCache;

    /**
     * Default CTOR
     */
    public StructureScript() {
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public Variable getStructVarName() {
        return structVarName;
    }

    public void setStructVarName(Variable structVarName) {
        this.structVarName = structVarName;
    }

    public Map<String, IObjectScript> getMembers() {
        return members;
    }

    public void setMembers(Map<String, IObjectScript> members) {
        this.members = members;
    }

    public boolean addMember(String member, IObjectScript valueScript) {
        this.getMembers().put(member, valueScript);
        return true;
    }

    public boolean containsMember(String member) {
        return this.getMember(member) != null;
    }

    public ControlRequest getControlRequest() {
        return controlRequest;
    }

    public void setControlRequest(ControlRequest controlRequest) {
        this.controlRequest = controlRequest;
    }

    public IObjectScript getMember(String member) {
        IObjectScript result = this.getMembers().get(member);

        return result;
    }

    public boolean setMember(String member, IObjectScript valueScript) {

        boolean status = false;

        for (Entry<String, IObjectScript> entry : this.getMembers().entrySet()) {

            IObjectScript script = entry.getValue();

            if (script.getVariable().getName().equals(member)) {

                this.getMembers().put(member, valueScript);

                status = true;

                break;

            }

        }

        return status;
    }

    public Map<String, IObjectScript> copyMembers() {

        Map<String, IObjectScript> copyMembers = new HashMap<String, IObjectScript>();

        for (Entry<String, IObjectScript> entry : this.getMembers().entrySet()) {

            copyMembers.put(entry.getKey(), (IObjectScript) (entry.getValue().copy()));

        }

        return copyMembers;
    }

    @Override
    public String toString() {
        String result = null;
        try {

            result = this.toJavaMap().toString();

        } catch (Exception ex) {

            logger.error(null, ex);

        }

        return result;
    }

    public static class Structure extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Structure" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getStructure().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getStructure().getLinePattern();
    }

    @Override
    public IScript execute() throws Exception {

        if (this.getOuterMultiLineScript().isLocalVarDeclared(this.getStructVarName())) {

            throw new Exception(this.getStructVarName() + " is already declared.");

        }

        //StructureDefnScript structureDefnScript = structureDefnScriptCache.findStructureDefn(this.getStructName());
        StructureDefnScript structureDefnScript = AppContainer.getInstance().getBean(StructureDefinitionScriptCache.class)
                .getStructureDefnMap()
                .get(this.getStructVarName().getStructName());

        IDataType value = new StringDataType(structureDefnScript.toString());

        Variable result = new Variable(VarTypeEnum.STRUCT, this.getStructName(), this.getStructVarName().getName());

        result.setValue(value);

        this.getOuterMultiLineScript().setLocalValue(this.getStructVarName(), this);

        return this;
    }

    /**
     * This returns <tt>StructureScript</tt> from echo matcher map.
     *
     * @param lineStructMatcherMap
     * @return
     */
    @Override
    public StructureScript createScript(Map<Integer, String> lineStructMatcherMap) throws Exception {

        if (lineStructMatcherMap == null) {

            return null;

        }

        StructureScript structScript = new StructureScript();

        structScript.setStructName(lineStructMatcherMap.get(1));

        try {

            StructureDefnScript structureDefnScript = AppContainer.getInstance().getBean(StructureDefinitionScriptCache.class)
                    .getStructureDefnMap()
                    .get(structScript.getStructName());

            Map<String, IObjectScript> map = structureDefnScript.instantiate();
            if (!map.containsKey(ACTION_NAME)) {
                LocalVarDeclScript actionNameScript = LocalVarDeclScript.createDummyStringScript();
                Variable actionNameValue = actionNameScript.getVariableValue();
                actionNameValue.setName(ACTION_NAME);
                actionNameValue.setValue(new StringDataType(structScript.getStructName()));

                map.put(ACTION_NAME, actionNameScript);
            }
            if (map.containsKey(OUT)) {
                logger.error("WARNING::: out is a keyword....It will be overriden by j-hawk");
            }
            LocalVarDeclScript outScript = LocalVarDeclScript.createDummyStringScript();
            Variable outValue = outScript.getVariableValue();
            outValue.setName(OUT);
            outValue.setValue(new StringDataType(""));
            map.put(OUT, outScript);
            structScript.setMembers(map);

        } catch (Exception ex) {

            logger.error(null, ex);

            return null;
        }

        Variable structVar = new Variable(
                VarTypeEnum.STRUCT,
                structScript.getStructName(),
                lineStructMatcherMap.get(2));

        structScript.setStructVarName(structVar);

        return structScript;
    }

    public Map<String, IObjectScript> instantiate() throws Exception {

        Map<String, IObjectScript> rtn = null;

        Map<String, StructureDefnScript> structDefnMap = AppContainer.getInstance().getBean(StructureDefinitionScriptCache.class)
                .getStructureDefnMap();

        if (structDefnMap.containsKey(this.getStructName())) {

            rtn = structDefnMap.get(this.getStructName()).instantiate();

        } else {

            throw new Exception("Could not find struct definition for {" + this.getStructName() + "}");

        }

        return rtn;
    }

    /**
     * This converts the hawk structure into java map..
     *
     * @return
     * @throws org.hawk.exception.Exception
     */
    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

        Map<Object, Object> javaMap = new LinkedHashMap<Object, Object>();

        for (Entry<String, IObjectScript> entry : this.getMembers().entrySet()) {

            if (entry.getValue() instanceof StructureScript) {

                Map<Object, Object> innerMap = entry.getValue().toJavaMap();

                javaMap.put(entry.getKey(), innerMap);
            } else {

                javaMap.putAll(entry.getValue().toJavaMap());
            }

        }

        return javaMap;
    }

    @Override
    public Object toJava() throws Exception {
        return this;
    }

    /*
     @Override
     public boolean isVariable() {
     return true;
     }
     */
    @Override
    public Variable getVariable() {
        return this.structVarName;
    }

    @Override
    public void setVariable(Variable value) {
        this.structVarName = value;
    }

    @Override
    public void setVariableValue(Variable value) {
        return;
    }

    @Override
    public Variable getVariableValue() {
        Variable val = new Variable(VarTypeEnum.STRUCT, this.getStructName(), this.toString());
        return val;
    }

    @Override
    public StructureScript copy() {

        StructureScript copyStructureScript = new StructureScript();

        copyStructureScript.setStructName(this.getStructName());

        copyStructureScript.setStructVarName(this.getStructVarName().copy());

        copyStructureScript.setMembers(this.copyMembers());

        return copyStructureScript;
    }

    @Override
    public String mangle() {

        return "_" + this.getStructName() + "_";
    }

    @Override
    public IHawkObject refer(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript) other;
        return this.getMember(otherScript.getVariable().getName());
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {

        if (otherScript == null) {

            throw new Exception("Can not assign null...");

        }

        if (!(otherScript instanceof StructureScript)) {

            throw new Exception("Type mismatch");

        }

        StructureScript otherStructScript = (StructureScript) otherScript;

        this.setMembers(otherStructScript.getMembers());

        this.setStructName(otherStructScript.getStructName());

        this.setStructVarName(otherStructScript.getStructVarName());

        this.setVariable(otherStructScript.getVariable());

        this.setVariableValue(otherStructScript.getVariableValue());

        return this;
    }

    @Override
    public String toUI() {
        return this.toString();
    }

    @Override
    public IObject equalTo(IObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript) other;
        LocalVarDeclScript result = LocalVarDeclScript.createBooleanScript();
        BooleanDataType type = (BooleanDataType) result.getVariableValue().getValue();
        if (otherScript == null) {

            type.setData(false);
            return result;
        }
        if (!(otherScript instanceof StructureScript)) {
            throw new Exception("incompatible type");
        }
        StructureScript otherStructScript = (StructureScript) otherScript;

        for (Entry<String, IObjectScript> myEntry : this.getMembers().entrySet()) {
            IObjectScript cmp = (IObjectScript) (myEntry.getValue().equalTo(otherStructScript.getMember(myEntry.getKey())));
            if (cmp == null || !((BooleanDataType) cmp.getVariableValue().getValue()).isData()) {
                type.setData(false);
                break;
            }

        }
        type.setData(true);
        return result;

    }

    public void setOut(HttpResponse httpResponse) {

        if (httpResponse != null) {
            String response = httpResponse.getResponse();
            IObjectScript outScript = null;//XMLResponseScript.createScript(response);
            if(httpResponse.getContentType().contains("json")){
                
                JSONObjectScript jsonObject = new JSONObjectScript();
                try {
                    jsonObject.setJson(new JSONObject (response));
                } catch (JSONException ex) {
                    Logger.getLogger(StructureScript.class.getName()).log(Level.SEVERE, null, ex);
                }
                jsonObject.setVariable(new Variable(VarTypeEnum.VAR, null,httpResponse.getActionName() ));
                outScript =jsonObject;
            }else{
                outScript = LocalVarDeclScript.createDummyStringScript(response);
            }
            outScript.getVariable().setName(OUT);
            LocalVarDeclScript responseCodeScript = LocalVarDeclScript.createDummyStringScript(String.valueOf(httpResponse.getResponseCode()));
            responseCodeScript.getVariable().setName(HTTP_RESPONSE_CODE);
            LocalVarDeclScript responseMsgScript = LocalVarDeclScript.createDummyStringScript(httpResponse.getResponseMessage());
            responseMsgScript.getVariable().setName(HTTP_RESPONSE_MESSAGE);
            this.addMember(OUT, outScript);
            this.addMember(HTTP_RESPONSE_CODE, responseCodeScript);
            this.addMember(HTTP_RESPONSE_MESSAGE, responseMsgScript);
            if (httpResponse.getHeaders() != null) {
                for (Header header : httpResponse.getHeaders()) {
                    LocalVarDeclScript headerScript = LocalVarDeclScript.createDummyStringScript(header.getValue());
                    headerScript.getVariable().setName(header.getName());
                    this.addMember(replaceHypen(header.getName()), headerScript);
                    System.out.println("adding header " + replaceHypen(header.getName()) + " with value " + headerScript);

                }
            }
        }
    }

    private String replaceHypen(String data) {
        return data.replaceAll("-", "");
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
    public IObject add(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject subtract(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject multiply(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject divide(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject modulus(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThan(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThan(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject greaterThanEqualTo(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject lessThanEqualTo(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject and(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IObject or(IObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject otherScript) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
