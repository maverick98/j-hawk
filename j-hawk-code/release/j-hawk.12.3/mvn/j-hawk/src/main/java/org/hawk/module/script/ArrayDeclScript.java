

package org.hawk.module.script;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import org.hawk.module.script.type.IDataType;
import org.hawk.ds.OperatorEnum;
import org.hawk.module.script.enumeration.ArrayTypeEnum;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.exception.HawkInterpretationError;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.module.script.type.DoubleDataType;
import org.hawk.module.script.type.Variable;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @author msahu
 */
public class ArrayDeclScript  extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ArrayDeclScript.class.getName());

    /**
     * Default CTOR
     */
    public ArrayDeclScript(){

    }


    private Map<Integer,IScript> members = new TreeMap<Integer,IScript>();

    private ArrayTypeEnum arrayType;

    private Variable arrayVariable;

    private Variable arrayVariableValue;

    private ArrayDeclScript rhsArrayDeclScript;

    private String execScriptExpression;

    private ArrayDeclTypeEnum arrayDeclTypeEnum = ArrayDeclTypeEnum.UNDEFINED;



    public boolean isInitialized() {
        return this.arrayDeclTypeEnum == ArrayDeclTypeEnum.NEWARRAY;
    }

    public String getExecScriptExpression() {
        return execScriptExpression;
    }

    public void setExecScriptExpression(String execScriptExpression) {
        this.execScriptExpression = execScriptExpression;
    }

    public ArrayDeclTypeEnum getArrayDeclTypeEnum() {
        return arrayDeclTypeEnum;
    }

    public void setArrayDeclTypeEnum(ArrayDeclTypeEnum arrayDeclTypeEnum) {
        this.arrayDeclTypeEnum = arrayDeclTypeEnum;
    }
    
    public ArrayDeclScript getRhsArrayDeclScript() {
        return rhsArrayDeclScript;
    }

    public void setRhsArrayDeclScript(ArrayDeclScript rhsArrayDeclScript) {
        this.rhsArrayDeclScript = rhsArrayDeclScript;
    }

    public ArrayTypeEnum getArrayType() {
        return arrayType;
    }

    public void setArrayType(ArrayTypeEnum arrayType) {
        this.arrayType = arrayType;
    }

    public Variable getArrayVariable() {
        return arrayVariable;
    }

    public void setArrayVariable(Variable arrayVariable) {
        this.arrayVariable = arrayVariable;
    }

    public Map<Integer, IScript> getMembers() {
        Map<Integer, IScript> result = null;
        if(this.isInitialized()){
            result=  this.members;
        }else{
            result = this.getRhsArrayDeclScript().getMembers();
        }
        return result;
    }
    public IScript getMember(Integer index){
        IScript result = null;
        if(this.isInitialized()){
            if(this.getMembers().containsKey(index)){
                result = this.getMembers().get(index);
            }else{
                result = this.createDefaultScript();
                this.setMember(index, result);
            }
        }else{
            result = this.getRhsArrayDeclScript().getMember(index);
        }
        return result;
        
    }

    private  IScript createDefaultScript(){

        IScript result = null;
        if(this.isStructType()){
            String arrayTypeStr = this.getArrayVariable().getStructName();
            try {
                StructureDefnScript structureDefnScript = ScriptInterpreter.getInstance().getStructureDefnMap().get(arrayTypeStr);
                StructureScript structureScript = new StructureScript();
                structureScript.setMembers(structureDefnScript.instantiate());
                structureScript.setStructName(arrayTypeStr);
                structureScript.setVariable(this.getArrayVariable().copy());
                structureScript.setVariableValue(structureScript.getVariable());
                result = structureScript;
            } catch (HawkException ex) {
                logger.severe( null, ex);
            }
        }else{
            LocalVarDeclScript lvds = new LocalVarDeclScript();
            lvds.setLocalVar(this.getArrayVariable().copy());
            lvds.setLocalVarValue(lvds.getLocalVar());
            result = lvds;
        }

        return result;
    }

    public void setMembers(Map<Integer, IScript> members) {
        if(this.isInitialized()){
            this.members = members;
        }else{
            this.getRhsArrayDeclScript().setMembers(members);
        }
    }

    @Override
    public int length(){
        int result = -1;
        if(this.isInitialized()){
            result = this.getMembers().size();
        }else{
            result = this.getRhsArrayDeclScript().length();
        }

        return result;
    }
    public boolean setMember(Integer i,IScript data){
        if(!this.isInitialized()){
            return this.getRhsArrayDeclScript().setMember(i, data);
        }
        if(i == null || i < 1){
            throw new HawkInterpretationError("Invalid index access of array {"+this.getArrayVariable().getVariableName()+"}");
        }
        if(data == null){
            throw new HawkInterpretationError("Can't set null at index {"+i+"of array {"+this.getArrayVariable().getVariableName()+"}");
        }
        this.getMembers().put(i, data);
        return true;
    }

    public Map<Integer,IScript>  copyMembers(){
        if(!this.isInitialized()){
            return this.getRhsArrayDeclScript().copyMembers();
        }
        Map<Integer,IScript>  copyMembers = new TreeMap<Integer,IScript>();

        for(Entry<Integer,IScript> entry:this.getMembers().entrySet()){
           copyMembers.put(entry.getKey(), entry.getValue().copy());
        }

        return copyMembers;
    }
    
    public boolean isVarType(){
        return this.arrayType == ArrayTypeEnum.VAR;
    }
    public boolean isStructType(){
        return this.arrayType == ArrayTypeEnum.STRUCT;
    }

    /**
     * Array script pattern.
     * @return returns Array script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return ARRAY_EMPTY_INIT_PATTERN;
    }
    @Override
    public IScript execute() throws HawkException {
        IScript rtn = LocalVarDeclScript.createDummyScript();
        Variable variable = rtn.getVariableValue();
        if(this.isInitialized()){
            this.getOuterMultiLineScript().setLocalValue(this.getArrayVariable(), this);
            BooleanDataType bool = new BooleanDataType(true);
            variable.setVariableValue(bool);
  
        }else{
            ArrayDeclScript result = null;

            if(this.getArrayDeclTypeEnum() == arrayDeclTypeEnum.OLDARRAY){
                result = (ArrayDeclScript)this.evaluateLocalVariable
                                                    (
                                                        this.getRhsArrayDeclScript()
                                                            .getArrayVariable()
                                                            .getVariableName()
                                                    );
            }else if(this.getArrayDeclTypeEnum() == arrayDeclTypeEnum.EXEC){
                result = (ArrayDeclScript)this.evaluateLocalVariable
                                                    (
                                                        this.getExecScriptExpression()
                                                    );
            }
            if(result != null){
                this.getOuterMultiLineScript().setLocalValue(this.getArrayVariable(), this);
                BooleanDataType bool = new BooleanDataType(true);
                variable.setVariableValue(bool);
            }else{
                throw new HawkInterpretationError("Could not find array {"+this.getRhsArrayDeclScript().getArrayVariable().getVariableName()+"}");
            }
            this.setRhsArrayDeclScript(result);
        }

        return rtn;
    }

    /**
     * This returns <tt>ArrayDeclScript</tt> from echo matcher map.
     * @param lineArrayMatcherMap
     * @return
     */
    @CreateScript
    public static ArrayDeclScript createScript(Map<Integer,String> lineArrayMatcherMap) throws HawkException{

        if(lineArrayMatcherMap == null){
            return null;
        }
        
        String arrayTypeLeft = lineArrayMatcherMap.get(1);
        String arrayVariableStr = lineArrayMatcherMap.get(2);
        String leftSideArrayBracket = lineArrayMatcherMap.get(3);
        String execStartingStr = lineArrayMatcherMap.get(4);
        String newKeyWordStr = lineArrayMatcherMap.get(5);
        String arrayTypeRight = lineArrayMatcherMap.get(6);
        String rightSideArrayBracket = lineArrayMatcherMap.get(7);
        String execParameters = lineArrayMatcherMap.get(8);
        
        boolean rhsArrayUsed = false;
        boolean execUsed = false;
        boolean newArrayInitialization = false;
        if(execStartingStr.isEmpty() && execParameters.isEmpty() ){
            if(!newKeyWordStr.isEmpty()&&!arrayTypeLeft.equals(arrayTypeRight)){
                throw new Error("invalid array declaration");
            }
            if(!leftSideArrayBracket.isEmpty() && !rightSideArrayBracket.isEmpty() && !newKeyWordStr.equals(newKeyWord)){
                throw new Error("requires \"new\" keyword while creating a hawk array");
            }
            execUsed =  false;
            if(rightSideArrayBracket.isEmpty() && newKeyWordStr.isEmpty()){
                rhsArrayUsed = true;
                newArrayInitialization = false;
            }else{
                rhsArrayUsed = false;
                newArrayInitialization = true;
            }
            
        }else if(!execStartingStr.isEmpty() && !execParameters.isEmpty() ){
            if(!(newKeyWordStr.isEmpty()  && rightSideArrayBracket.isEmpty())){
                throw new HawkException("invalid array declaration");
            }
            if(!execParameters.endsWith("`")){
                throw new HawkException("invalid array declaration");
            }
            execUsed = true;
            rhsArrayUsed =false;
            newArrayInitialization = false;
        }else{
            throw new HawkException("invalid array declaration");
        }
        ArrayDeclScript arrayDeclScript = new ArrayDeclScript();
        ArrayTypeEnum arrayType = ArrayTypeEnum.valueOf(VarTypeEnum.parse(arrayTypeLeft));
        arrayDeclScript.setArrayType(arrayType);
        if(arrayDeclScript.isStructType()){
            try {
                StructureDefnScript structureDefnScript = ScriptInterpreter.getInstance()
                                                                           .getStructureDefnMap()
                                                                           .get(arrayTypeLeft);
                if(structureDefnScript == null){
                    throw new Error("Could not find struct {"+arrayTypeLeft+"}");
                }

            } catch (HawkException ex) {
               throw new Error("Unexpected error occurred while finding struct {"+arrayVariableStr+"}");

            }
        }

        Variable arrayVariable = new Variable
                                        (
                                            arrayDeclScript.isVarType()?VarTypeEnum.VAR:VarTypeEnum.STRUCT,
                                            arrayDeclScript.isVarType()?null:arrayTypeLeft,
                                            arrayVariableStr,
                                            arrayDeclScript.isVarType()?ArrayTypeEnum.VAR:ArrayTypeEnum.STRUCT
                                        );
        arrayDeclScript.setArrayVariable(arrayVariable);
        if(rhsArrayUsed || execUsed){
            
            if(rhsArrayUsed){
                arrayDeclScript.setArrayDeclTypeEnum(ArrayDeclTypeEnum.OLDARRAY);
                ArrayDeclScript rhsArrayDeclScript = new ArrayDeclScript();
                rhsArrayDeclScript.setArrayType(arrayDeclScript.getArrayType());
                Variable rhsArrayVariable = new Variable
                                            (
                                                arrayDeclScript.isVarType()?VarTypeEnum.VAR:VarTypeEnum.STRUCT,
                                                arrayDeclScript.isVarType()?null:arrayVariableStr,
                                                arrayTypeRight,
                                                arrayDeclScript.isVarType()?ArrayTypeEnum.VAR:ArrayTypeEnum.STRUCT
                                            );
                rhsArrayDeclScript.setArrayVariable(rhsArrayVariable);
                arrayDeclScript.setRhsArrayDeclScript(rhsArrayDeclScript);
            }
            if(execUsed){
                arrayDeclScript.setArrayDeclTypeEnum(ArrayDeclTypeEnum.EXEC);
                String execExpStr = execStartingStr +" "+ arrayTypeRight +" " + execParameters;
                arrayDeclScript.setExecScriptExpression(execExpStr);
            }
        }else{
            arrayDeclScript.setArrayDeclTypeEnum(ArrayDeclTypeEnum.NEWARRAY);
        }
        return arrayDeclScript;
    }


    private  static enum ArrayDeclTypeEnum{

        NEWARRAY,OLDARRAY,EXEC,UNDEFINED;
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    

    @Override
    public Variable getVariable() {
        return this.arrayVariable;
    }

    @Override
    public void setVariable(Variable value) {
        this.arrayVariable = value;
    }


    @Override
    public void setVariableValue(Variable value) {
        return;
    }

    @Override
    public Variable getVariableValue() {
        if(this.arrayVariableValue == null){
        this.arrayVariableValue = new Variable
                                        (
                                            this.isVarType()?VarTypeEnum.VAR:VarTypeEnum.STRUCT,
                                            this.getArrayVariable().getStructName(),
                                            this.toString(),
                                            this.isVarType()?ArrayTypeEnum.VAR:ArrayTypeEnum.STRUCT
                                        );
        }
        return arrayVariableValue;
    }

    @Override
    public ArrayDeclScript copy() {

        ArrayDeclScript clonedArrayScript = new ArrayDeclScript();
        clonedArrayScript.setArrayType(this.getArrayType());
        clonedArrayScript.setArrayVariable(this.getArrayVariable().copy());
        clonedArrayScript.setArrayDeclTypeEnum(this.getArrayDeclTypeEnum());
        if(this.isInitialized()){
            clonedArrayScript.setMembers(this.copyMembers());
        }else{
            clonedArrayScript.members = new TreeMap<Integer,IScript>();
        }

        return clonedArrayScript;
    }

    @Override
    public String mangle(){
        return "_"+var+OperatorEnum.ARRAYBRACKET.getOperator()+"_";
    }

    @Override
    public IScript arrayBracket(IScript otherScript) throws HawkException {
        if(otherScript == null || otherScript.getVariable() == null || otherScript.getVariable().getVariableValue() == null){
            throw new HawkException("null array index found...");
        }
        IDataType indexDataType = otherScript.getVariable().getVariableValue();

        if(!(indexDataType instanceof  DoubleDataType)){
            throw new HawkException("Invalid array index");
        }
        DoubleDataType index = (DoubleDataType)indexDataType;
        if( !index.isPositiveInteger()){
            throw new HawkException("Non integer array index access");
        }
        IScript result = this.getMember(Integer.parseInt(index.toString()));
        return result;
    }

    @Override
    public IScript assign(IScript otherScript) throws HawkException {
         if(otherScript == null){
            throw new HawkException("Can not assign null...");
        }
        if(!(otherScript instanceof ArrayDeclScript) ){
            throw new HawkException("Type mismatch");
        }
        ArrayDeclScript otherArrayDeclScript = (ArrayDeclScript)otherScript;
        this.setMembers(otherArrayDeclScript.getMembers());
        this.setVariable(otherArrayDeclScript.getVariable());
        this.setVariableValue(otherArrayDeclScript.getVariableValue());

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
        List<String> result = new ArrayList<String>();
        Map<Integer,IScript> map = this.isInitialized()
                                            ?
                                                    this.getMembers()
                                                :
                                                    this.getRhsArrayDeclScript().getMembers();
        for(Entry<Integer,IScript> entry:map.entrySet()){
            result.add(entry.getValue().toUI());
        }
        return result.toString();
    }


    @Override
    public Map<Object, Object> toJavaMap() throws HawkException {

          Map<Object,Object> javaMap = new LinkedHashMap<Object,Object>();
          Map<Object,Object> memberMap = new LinkedHashMap<Object,Object>();

          Map<Integer,IScript> map = this.isInitialized()
                                            ?
                                                    this.getMembers()
                                                :
                                                    this.getRhsArrayDeclScript().getMembers();

          for(int i=1;i<=map.size();i++){
              Map<Object,Object> innerJavaMap = map.get(i).toJavaMap();
              memberMap.put(i, innerJavaMap);
          }
          javaMap.put(this.getVariable().getVariableName(), memberMap);
          return javaMap;
    }

    @Override
    public Object toJava() throws HawkException {
        return this;
    }


    //Fixme ... implement it properly
    @Override
    public IScript equalTo(IScript otherScript) throws HawkException {

        LocalVarDeclScript result = LocalVarDeclScript.createBooleanScript();
        BooleanDataType type = (BooleanDataType)result.getVariableValue().getVariableValue();
        if(otherScript == null){

            type.setData(false);
            return result;
        }
        if( ! (otherScript instanceof ArrayDeclScript)){
            throw new HawkException("incompatible type");
        }
        ArrayDeclScript otherArrayDeclScript = (ArrayDeclScript)otherScript;
        if(this.getArrayVariable().equals(otherArrayDeclScript.getArrayVariable())){

            if(this.getVariable().equals(otherArrayDeclScript.getVariable())){

                if(this.getVariableValue().equals(otherArrayDeclScript.getVariableValue())){

                    type.setData(true);
                }else{
                    type.setData(false);
                }
            }else{
                type.setData(false);
            }
        }else{
            type.setData(false);
        }
        return result;
    }

    /**
     * The primary echo RegEx pattern to parse array script eg var a[] = new var[]
     */
     private static final Pattern ARRAY_EMPTY_INIT_PATTERN=
        Pattern.compile
                    (
                              //1 var or any struct            //2 var or struct name            //3 array [] 
                        "\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*(\\[\\s*\\])\\s*"

                        +"="
                               //4   `exec         //5 new      //6 (var or any struct) same as $1 or exec function name
                        + "\\s*(`?\\s*e?x?e?c?)\\s*(n?e?w?)\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*"

                            //7 array[]       //8               
                        + "(\\[?\\s*\\]?)\\s*(\\(?.*\\)?)\\s*"
                    );

     

}


