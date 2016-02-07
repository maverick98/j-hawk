

package org.hawk.module.script;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.StringDataType;
import org.hawk.module.script.type.Variable;
import java.util.HashMap;
import org.hawk.module.script.type.BooleanDataType;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class StructureScript extends SingleLineScript{


    private static final HawkLogger logger = HawkLogger.getLogger(StructureScript.class.getName());

    private static final Pattern STRUCTURE_PATTERN = 
            Pattern.compile("\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*");

    private String structName;

    private Variable structVarName;

    private Map<String,IScript> members = new HashMap<String,IScript>();

    /**
     * Default CTOR
     */
    public StructureScript(){

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

    public Map<String,IScript> getMembers() {
        return members;
    }

    public void setMembers(Map<String,IScript> members) {
        this.members = members;
    }

    public IScript getMember(String member){
        IScript result = this.getMembers().get(member);
        
        return result;
    }
    public boolean setMember(String member, IScript valueScript){

        boolean status = false;

        for(Entry<String,IScript> entry:this.getMembers().entrySet()){

            IScript script = entry.getValue();

            if(script.getVariable().getVariableName().equals(member)){

                this.getMembers().put(member,valueScript);

                status = true;

                break;

            }

        }
        
        
        return status;
    }

    public Map<String,IScript> copyMembers(){

        Map<String,IScript> copyMembers = new HashMap<String,IScript>();
        
        for(Entry<String,IScript> entry:this.getMembers().entrySet()){

            copyMembers.put(entry.getKey(),entry.getValue().copy());

        }

        return copyMembers;
    }


    @Override
    public String toString(){

        try {

            return this.toJavaMap().toString();

        } catch (HawkException ex) {

            logger.severe( null, ex);

        }

        return null;
    }
    

    /**
     * Echo script pattern.
     * @return returns echo script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return STRUCTURE_PATTERN;
    }
    @Override
    public IScript execute() throws HawkException {
       
        if(this.getOuterMultiLineScript().isLocalVarDeclared(this.getStructVarName())){

            throw new HawkException(this.getStructVarName() +" is already declared.");

        }

        StructureDefnScript structureDefnScript = ScriptInterpreter.getInstance()
                                                                   .getStructureDefnMap()
                                                                   .get(this.getStructName());
        

        IDataType value = new StringDataType(structureDefnScript.toString());

        Variable result =  new Variable(VarTypeEnum.STRUCT,this.getStructName(),this.getStructVarName().getVariableName());

        result.setVariableValue(value);

        this.getOuterMultiLineScript().setLocalValue(this.getStructVarName(),this );

        return this;
    }
     /**
     * This returns <tt>StructureScript</tt> from echo matcher map.
     * @param lineStructMatcherMap
     * @return
     */
    @CreateScript
    public static StructureScript createScript(Map<Integer,String> lineStructMatcherMap){

        if(lineStructMatcherMap == null){

            return null;

        }

        StructureScript structScript = new StructureScript();

        structScript.setStructName(lineStructMatcherMap.get(1).toString());

        try {

            StructureDefnScript structureDefnScript = ScriptInterpreter.getInstance()
                                                                       .getStructureDefnMap()
                                                                       .get(structScript.getStructName());

            structScript.setMembers(structureDefnScript.instantiate());

        } catch (HawkException ex) {

            logger.severe( null, ex);

            return null;
        }

        Variable structVar = new Variable
                                    (
                                        VarTypeEnum.STRUCT,
                                        structScript.getStructName(),
                                        lineStructMatcherMap.get(2).toString()
                                    );

        structScript.setStructVarName(structVar);


        return structScript;
    }

    public Map<String,IScript> instantiate() throws HawkException{

        Map<String,IScript> rtn = null;

        Map<String,StructureDefnScript> structDefnMap = ScriptInterpreter.getInstance()
                                                                         .getStructureDefnMap();

        if(structDefnMap.containsKey(this.getStructName())){

            rtn =  structDefnMap.get(this.getStructName()).instantiate();

        }else{

            throw new HawkException("Could not find struct definition for {"+this.getStructName()+"}");

        }

        return rtn;
    }

     /**
     * This converts the hawk structure into java map..
     * @return
     * @throws org.hawk.exception.HawkException
     */
    
    @Override
    public Map<Object,Object> toJavaMap() throws HawkException{

        Map<Object,Object> javaMap = new LinkedHashMap<Object,Object>();

        javaMap.put("actionName", this.getStructName());

        for(Entry<String,IScript> entry:this.getMembers().entrySet()){

            Map<Object,Object> innerMap = entry.getValue().toJavaMap();

            for(Entry<Object,Object> innerEntry:innerMap.entrySet()){

                javaMap.put(innerEntry.getKey(), innerEntry.getValue());

            }

        }

        return javaMap;
    }

    @Override
    public Object toJava() throws HawkException {
        return this.toJavaMap();
    }


    @Override
    public boolean isVariable() {
        return true;
    }

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
        Variable val = new Variable(VarTypeEnum.STRUCT,this.getStructName(),this.toString());
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
    public String mangle(){

        return "_"+this.getStructName()+"_";
    }

    @Override
    public IScript refer(IScript otherScript) throws HawkException {
        return this.getMember(otherScript.getVariable().getVariableName());
    }

    @Override
    public IScript assign(IScript otherScript) throws HawkException {

        if(otherScript == null){

            throw new HawkException("Can not assign null...");

        }

        if(!(otherScript instanceof StructureScript) ){

            throw new HawkException("Type mismatch");

        }

        StructureScript otherStructScript = (StructureScript)otherScript;

        this.setMembers(otherStructScript.getMembers());

        this.setStructName(otherStructScript.getStructName());

        this.setStructVarName(otherStructScript.getStructVarName());

        this.setVariable(otherStructScript.getVariable());

        this.setVariableValue(otherStructScript.getVariableValue());

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
        return this.toString();
    }

    @Override
    public IScript equalTo(IScript otherScript) throws HawkException {

        LocalVarDeclScript result = LocalVarDeclScript.createBooleanScript();
        BooleanDataType type = (BooleanDataType)result.getVariableValue().getVariableValue();
        if(otherScript == null){
           
            type.setData(false);
            return result;
        }
        if( ! (otherScript instanceof StructureScript)){
            throw new HawkException("incompatible type");
        }
        StructureScript otherStructScript = (StructureScript)otherScript;
        
        for(Entry<String,IScript> myEntry:this.getMembers().entrySet()){
           IScript cmp  =myEntry.getValue().equalTo(otherStructScript.getMember(myEntry.getKey()));
           if(cmp == null || !((BooleanDataType)cmp.getVariableValue().getVariableValue()).isData()){
               type.setData(false);
               break;
           }
          
        }
        type.setData(true);
        return result;
        
    }






}




