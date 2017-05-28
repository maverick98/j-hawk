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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.common.di.AppContainer;
import org.commons.ds.operator.OperatorEnum;
import org.commons.ds.exp.IObject;
import org.hawk.ds.exp.IHawkObject;
import org.hawk.executor.cache.multiline.structure.IStructureDefinitionScriptCache;
import org.hawk.executor.cache.multiline.structure.StructureDefinitionScriptCache;
import static org.hawk.lang.constant.HawkLanguageKeyWord.newKeyWord;
import static org.hawk.lang.constant.HawkLanguageKeyWord.var;
import org.hawk.lang.enumeration.ArrayTypeEnum;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.IntDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.common.di.ScanMe;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author msahu
 */
@ScanMe(true)
public class ArrayDeclScript  extends SingleLineScript implements IObjectScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ArrayDeclScript.class.getName());

    /**
     * Default CTOR
     */
    public ArrayDeclScript(){

    }
    @Autowired(required = true)
       
    private IStructureDefinitionScriptCache  structureDefinitionScriptCache;

    protected Map<Integer,IObjectScript> members = new TreeMap<>();

    private ArrayTypeEnum arrayType;

    protected Variable arrayVariable;

    protected Variable arrayVariableValue;

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

    public Map<Integer, IObjectScript> getMembers() {
        Map<Integer, IObjectScript> result = null;
        if(this.isInitialized()){
            result=  this.members;
        }else{
            result = this.getRhsArrayDeclScript().getMembers();
        }
        return result;
    }
    public IObjectScript getMember(Integer index) throws Exception{
        IObjectScript result = null;
        if(this.isInitialized()){
            if(this.getMembers().containsKey(index)){
                result = this.getMembers().get(index);
                if(result != null && result.isProxy()){
                    result = result.getActualObjectScript();
                }
            }else{
                result = this.createDefaultScript();
                this.setMember(index, result);
            }
        }else{
            result = this.getRhsArrayDeclScript().getMember(index);
        }
        return result;
        
    }

    private  IObjectScript createDefaultScript(){

        IObjectScript result = null;
        if(this.isStructType()){
            String arrayTypeStr = this.getArrayVariable().getStructName();
            try {
                StructureDefnScript structureDefnScript = structureDefinitionScriptCache.findStructureDefn(arrayTypeStr);
                StructureScript structureScript = new StructureScript();
                structureScript.setMembers(structureDefnScript.instantiate());
                structureScript.setStructName(arrayTypeStr);
                structureScript.setVariable(this.getArrayVariable().copy());
                structureScript.setVariableValue(structureScript.getVariable());
                result = structureScript;
            } catch (Exception ex) {
                logger.error( null, ex);
            }
        }else{
            //LocalVarDeclScript lvds = new LocalVarDeclScript();
            //lvds.setLocalVar(this.getArrayVariable().copy());
            //lvds.setLocalVarValue(lvds.getLocalVar());
            ArrayMemberProxyScript arrayMemberProxyScript = new ArrayMemberProxyScript();
            arrayMemberProxyScript.setVar(this.getArrayVariable().copy());
            result = arrayMemberProxyScript;
            //result = lvds;
        }
         
        return result;
    }

    public void setMembers(Map<Integer, IObjectScript> members) {
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
    public boolean setMember(Integer i,IObjectScript data) throws Exception{
        if(!this.isInitialized()){
            return this.getRhsArrayDeclScript().setMember(i, data);
        }
        if(i == null || i < 0){
            System.out.println("value of i is "+i); 
            throw new Exception("Invalid index access of array {"+this.getArrayVariable().getName()+"}");
        }
        if(data == null){
            throw new Exception("Can't set null at index {"+i+"of array {"+this.getArrayVariable().getName()+"}");
        }
        this.getMembers().put(i, data);
        return true;
    }

    public Map<Integer,IObjectScript>  copyMembers(){
        if(!this.isInitialized()){
            return this.getRhsArrayDeclScript().copyMembers();
        }
        Map<Integer,IObjectScript>  copyMembers = new TreeMap<>();

        this.getMembers().entrySet().stream().forEach((entry) -> {
            copyMembers.put(entry.getKey(), (IObjectScript)(entry.getValue().copy()));
        });

        return copyMembers;
    }
    
   
    public boolean isStructType(){
        return this.arrayType == ArrayTypeEnum.STRUCT;
    }

    public static class ArrayDecl extends SingleLineGrammar {

        @Override
        public String toString() {
            return "ExecModule" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }
        

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getArrayDecl().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
       
        return this.getGrammar().getSingleLine().getArrayDecl().getLinePattern();
    } 
    
   
    private void reset(){
        this.members = new  TreeMap<>();
    }
    @Override
    public IObjectScript execute() throws Exception {
        this.reset();
        IObjectScript rtn = LocalVarDeclScript.createDummyBooleanScript();
        Variable variable = rtn.getVariableValue();
        if(this.isInitialized()){
            this.getOuterMultiLineScript().setLocalValue(this.getArrayVariable(), this);
            BooleanDataType bool = new BooleanDataType(true);
            variable.setValue(bool);
  
        }else{
            ArrayDeclScript result = null;

            if(this.getArrayDeclTypeEnum() == arrayDeclTypeEnum.OLDARRAY){
                result = (ArrayDeclScript)this.evaluateLocalVariable
                                                    (
                                                        this.getRhsArrayDeclScript()
                                                            .getArrayVariable()
                                                            .getName()
                                                    );
            }else if(this.getArrayDeclTypeEnum() == arrayDeclTypeEnum.EXEC){
                result = (ArrayDeclScript)this.evaluateLocalVariable
                                                    (
                                                        this.getExecScriptExpression()
                                                    );
                this.setRhsArrayDeclScript(result);
            }
            if(result != null){
                this.getOuterMultiLineScript().setLocalValue(this.getArrayVariable(), this);
                BooleanDataType bool = new BooleanDataType(true);
                variable.setValue(bool);
            }else{
                throw new Exception("Could not find array {"+this.getRhsArrayDeclScript().getArrayVariable().getName()+"}");
            }
            this.setRhsArrayDeclScript(result);
        }

        return rtn;
    }

    public static ArrayDeclScript createDefaultArrayScript(){
        ArrayDeclScript arrayDeclScript = new ArrayDeclScript();
        arrayDeclScript.setArrayDeclTypeEnum(ArrayDeclTypeEnum.NEWARRAY);
        Variable arrayVar = new Variable(VarTypeEnum.VAR,null,"");
        arrayDeclScript.setArrayVariable(arrayVar);
        arrayDeclScript.setArrayType(ArrayTypeEnum.VAR);
        return arrayDeclScript;
    }
    /**
     * This returns <tt>ArrayDeclScript</tt> from echo matcher map.
     * @param lineArrayMatcherMap
     * @return
     */
    @Override
    public  ArrayDeclScript createScript(Map<Integer,String> lineArrayMatcherMap) throws Exception{

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
                throw new Exception("invalid array declaration");
            }
            if(!execParameters.endsWith("`")){
                throw new Exception("invalid array declaration");
            }
            execUsed = true;
            rhsArrayUsed =false;
            newArrayInitialization = false;
        }else{
            throw new Exception("invalid array declaration");
        }
        ArrayDeclScript arrayDeclScript = new ArrayDeclScript();
        ArrayTypeEnum arrayType = ArrayTypeEnum.valueOf(VarTypeEnum.parse(arrayTypeLeft));
        arrayDeclScript.setArrayType(arrayType);
        if(arrayDeclScript.isStructType()){
            
                StructureDefnScript structureDefnScript = AppContainer.getInstance().getBean( StructureDefinitionScriptCache.class)
                                                                           .getStructureDefnMap()
                                                                           .get(arrayTypeLeft);
                if(structureDefnScript == null){
                    throw new Error("Could not find struct {"+arrayTypeLeft+"}");
                }

           
        }
        boolean isVarType = arrayDeclScript.getArrayType().isVarArray();    
        Variable arrayVariable = new Variable
                                        (
                                            isVarType?VarTypeEnum.VAR:VarTypeEnum.STRUCT,
                                            isVarType?null:arrayTypeLeft,
                                            arrayVariableStr,
                                            isVarType?ArrayTypeEnum.VAR:ArrayTypeEnum.STRUCT
                                        );
        arrayDeclScript.setArrayVariable(arrayVariable);
        if(rhsArrayUsed || execUsed){
            
            if(rhsArrayUsed){
                arrayDeclScript.setArrayDeclTypeEnum(ArrayDeclTypeEnum.OLDARRAY);
                ArrayDeclScript rhsArrayDeclScript = new ArrayDeclScript();
                rhsArrayDeclScript.setArrayType(arrayDeclScript.getArrayType());
                Variable rhsArrayVariable = new Variable
                                            (
                                                isVarType?VarTypeEnum.VAR:VarTypeEnum.STRUCT,
                                                isVarType?null:arrayVariableStr,
                                                arrayTypeRight,
                                                isVarType?ArrayTypeEnum.VAR:ArrayTypeEnum.STRUCT
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
    public IHawkObject refer(IHawkObject otherScript) throws Exception {
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


    private  static enum ArrayDeclTypeEnum{

        NEWARRAY,OLDARRAY,EXEC,UNDEFINED;
    }
    /*
    @Override
    public boolean isVariable() {
        return true;
    }
    */ 

    

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
        this.arrayVariableValue = value;
    }

    @Override
    public Variable getVariableValue() {
        if(this.arrayVariableValue == null){
            boolean isVarType = this.getArrayType().isVarArray();
            this.arrayVariableValue = new Variable
                                            (
                                                isVarType?VarTypeEnum.VAR:VarTypeEnum.STRUCT,
                                                this.getArrayVariable().getStructName(),
                                                this.toString(),
                                                isVarType?ArrayTypeEnum.VAR:ArrayTypeEnum.STRUCT
                                            );
        }
        return arrayVariableValue;
    }

    @Override
    public ArrayDeclScript copy() {
        return this;
    }
   
    //@Override
    public ArrayDeclScript copy1() {

        ArrayDeclScript clonedArrayScript = new ArrayDeclScript();
        clonedArrayScript.setArrayType(this.getArrayType());
        clonedArrayScript.setArrayVariable(this.getArrayVariable().copy());
        clonedArrayScript.setArrayDeclTypeEnum(this.getArrayDeclTypeEnum());
        if(this.isInitialized()){
            clonedArrayScript.setMembers(this.copyMembers());
        }else{
            clonedArrayScript.members = new TreeMap<>();
        }

        return clonedArrayScript;
    }

    @Override
    public String mangle(){
        return "_"+var+OperatorEnum.ARRAYBRACKET.getOperator()+"_";
    }

    @Override
    public IHawkObject arrayBracket(IHawkObject other) throws Exception {
        IObjectScript otherScript = (IObjectScript)other;
        if(otherScript == null || otherScript.getVariable() == null || otherScript.getVariable().getValue() == null){
            throw new Exception("null array index found...");
        }
        
        IDataType indexDataType = otherScript.getVariable().getValue();

        
        if((indexDataType instanceof  IntDataType)){
            IntDataType index = (IntDataType)indexDataType;
            if( !index.isPositiveInteger()){
                throw new Exception("Non integer array index access");
            }
        }
       
        IObjectScript result = this.getMember(Integer.parseInt(indexDataType.value()));
        return result;
    }

    @Override
    public IHawkObject assign(IHawkObject otherScript) throws Exception {
         if(otherScript == null || !(otherScript instanceof ArrayDeclScript)){
            throw new Exception("Can not assign null...");
        }
        ArrayDeclScript otherArrayDeclScript = (ArrayDeclScript)otherScript;
        this.setMembers(otherArrayDeclScript.getMembers());
        this.setVariable(otherArrayDeclScript.getVariable());
        this.setVariableValue(otherArrayDeclScript.getVariableValue());

        
        return this;
    }

    @Override
    public String toUI() {
        List<String> result = new ArrayList<>();
        Map<Integer,IObjectScript> map = this.isInitialized()
                                            ?
                                                    this.getMembers()
                                                :
                                                    this.getRhsArrayDeclScript().getMembers();
        for(Entry<Integer,IObjectScript> entry:map.entrySet()){
            result.add(entry.getValue().toUI());
        }
        return result.toString();
    }


    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

          Map<Object,Object> javaMap = new LinkedHashMap<>();
          Map<Object,Object> memberMap = new LinkedHashMap<>();

          Map<Integer,IObjectScript> map = this.isInitialized()
                                            ?
                                                    this.getMembers()
                                                :
                                                    this.getRhsArrayDeclScript().getMembers();

          for(int i=1;i<=map.size();i++){
              Map<Object,Object> innerJavaMap = map.get(i).toJavaMap();
              memberMap.put(i, innerJavaMap);
          }
          javaMap.put(this.getVariable().getName(), memberMap);
          return javaMap;
    }

    @Override
    public Object toJava() throws Exception {
        return this;
    }

    @Override
    public String toString() {
        return this.toUI();
    }
    

    //Fixme ... implement it properly
    @Override
    public IObject equalTo(IObject otherScript) throws Exception {

        LocalVarDeclScript result = LocalVarDeclScript.createBooleanScript();
        BooleanDataType type = (BooleanDataType)result.getVariableValue().getValue();
        if(otherScript == null){

            type.setData(false);
            return result;
        }
        if( ! (otherScript instanceof ArrayDeclScript)){
            throw new Exception("incompatible type");
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

    @Override
    public boolean passByReference() {
        return true;
    }
    
   
  

     

}


