/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */


package org.hawk.lang.function;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.var;
import static org.hawk.lang.constant.HawkLanguageKeyWord.varx;
import org.hawk.lang.enumeration.ArrayTypeEnum;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.object.StructureScript;
import org.hawk.lang.object.XMLVariableDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;

/**
 *
 * @version 1.0 25 Jul, 2010
 * @author msahu
 */
public class ParameterScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ParameterScript.class.getName());


    private  static final Pattern FUNCTION_PARAMETER_PATTERN=
            Pattern.compile("\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*(\\[?\\s*\\]?)\\s*");

    protected Map<Variable,IObjectScript> paramScripts = new LinkedHashMap<>();

    

    private Set<String> localStructs = new TreeSet<>();

    public Map<Variable, IObjectScript> getParamScripts() {
        return paramScripts;
    }

    public void setParamScripts(Map<Variable, IObjectScript> paramScripts) {
        this.paramScripts = paramScripts;
    }

    public Set<String> getLocalStructs() {
        return localStructs;
    }

    public void setLocalStructs(Set<String> localStructs) {
        this.localStructs = localStructs;
    }

   
    
    
    public ParameterScript(){
        
    }

    public boolean isEmpty(){
        return this.paramScripts.isEmpty();
    }

    public boolean initializeParams(){
        
        
        for (Entry<Variable, IObjectScript> entry : this.paramScripts.entrySet()) {
            entry.getValue().setVariableValue(null);
        }
        return true;
    }

     public boolean initializeParamsValue(Map<Integer, IObjectScript> params) throws Exception{
        if (params == null || params.isEmpty()) {
            return false;
        }
        int i = 1;
        for (Entry<Variable, IObjectScript> entry : this.paramScripts.entrySet()) {
         // Revisit me
         //   if(ScriptInterpreter.getInstance().isGlobalVarDeclared(entry.getKey())){
         //       throw new Exception (entry.getKey() + "is already declared as global var");
         //   }
            this.setParamValue(entry.getKey(), (IObjectScript)params.get(i).copy());
            i++;
        }
        return true;
    }

    public IObjectScript getParamValue(Variable paramVar){
        return this.getParamValue(paramVar.getName());
    }


    public IObjectScript getParamValue(String localVar){
        if( (this.paramScripts == null || this.paramScripts.isEmpty())){
            return null;
        }
        IObjectScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR,null,localVar);
        result = this.paramScripts.get(possibleKey);
        if(result == null){
            possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);
            result = this.paramScripts.get(possibleKey);
        }
        if(result == null){
            result = this.findArrayType(localVar);
        }
        return result;
    }

     private  IObjectScript findArrayType(String localVar){
        IObjectScript result;
        Variable possibleKey = new Variable(VarTypeEnum.VAR, null, localVar);

        possibleKey.setArrayType(ArrayTypeEnum.VAR);
        possibleKey.setVarTypeEnum(VarTypeEnum.VAR);

        result = this.paramScripts.get(possibleKey);
        if(result == null){
            
            possibleKey.setArrayType(ArrayTypeEnum.STRUCT);
            possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);
            result = this.paramScripts.get(possibleKey);
            
            for(String struct:localStructs){
                possibleKey.setStructName(struct);
                result = this.paramScripts.get(possibleKey);
                if(result != null){
                    break;
                }
            }

        }
        return result;
    }


    public void setParamValue(Variable variable,IObjectScript localValue){
        if(this.paramScripts != null){
            if(!localValue.passByReference()){
                localValue.setVariable(variable);
            }
            this.paramScripts.put(variable,localValue);
            if(
                variable.getVarTypeEnum()!= null
                    &&
                variable.getVarTypeEnum() == VarTypeEnum.STRUCT
                    &&
                variable.getStructName()!= null
              )
            this.localStructs.add(variable.getStructName());
        }
    }

    public boolean isParamVarDeclared(Variable paramVar){

        return this.paramScripts.containsKey(paramVar);
    }

    @Override
    public ParameterScript copy(){
        ParameterScript copyParameterScript = new ParameterScript();
        copyParameterScript.setLocalStructs(this.localStructs);
        Map<Variable,IObjectScript> copyParamScript =  new LinkedHashMap<>();
        this.paramScripts.entrySet().stream().forEach((entry) -> {
            copyParamScript.put
                                    (
                                            entry.getKey().copy(),
                                            entry.getValue()!= null
                                                    ?
                                                    (IObjectScript)entry.getValue().copy()
                                                    :   null
                                    );
        });
        copyParameterScript.setParamScripts(copyParamScript);
        return copyParameterScript;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(this.paramScripts);
        sb.append("}");
        return sb.toString();
    }

    //@Override
    public String mangle(){

        StringBuilder sb = new StringBuilder();

        for(Entry<Variable,IObjectScript> entry:this.paramScripts.entrySet()){

            sb.append(entry.getValue().mangle());
            
        }
        return sb.toString();
    }

    /**
     * Echo script pattern.
     * @return returns echo script pattern
     * @see ScriptPattern
     */
    
    public  Pattern getPattern() {
        return FUNCTION_PARAMETER_PATTERN;
    }
    @Override
    public IScript execute() throws Exception {

        
        return LocalVarDeclScript.createDummyBooleanScript();
    }

    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    
    public  IObjectScript createScript(Map<Integer,String> lineParamMatcherMap){

        if(lineParamMatcherMap == null){
            return null;
        }
        IObjectScript result = null;
        Variable variable = new Variable(VarTypeEnum.VAR,null,lineParamMatcherMap.get(2));
        String arrayBracket = lineParamMatcherMap.get(3);
        
        if(lineParamMatcherMap.get(1).equals(var)){
            if(arrayBracket != null && !arrayBracket.isEmpty()){
                variable.setArrayType(ArrayTypeEnum.VAR);
                ArrayDeclScript arrayDeclScript = new ArrayDeclScript();
                arrayDeclScript.setArrayType(ArrayTypeEnum.VAR);
                arrayDeclScript.setArrayVariable(variable);
                result = arrayDeclScript;

                
            }else{
                LocalVarDeclScript lvds = new LocalVarDeclScript();
                lvds.setLocalVar(variable);
                lvds.setLocalVarValExp("");
                lvds.setLocalVarValue(null);
                result = lvds;
            }
        }else if(lineParamMatcherMap.get(1).equals(varx)){
            if(arrayBracket != null && !arrayBracket.isEmpty()){
                variable.setArrayType(ArrayTypeEnum.VARX);
                ArrayDeclScript arrayDeclScript = new ArrayDeclScript();
                arrayDeclScript.setArrayType(ArrayTypeEnum.VARX);
                arrayDeclScript.setArrayVariable(variable);
                result = arrayDeclScript;

                
            }else{
                XMLVariableDeclScript xMLVariableDeclScript = new XMLVariableDeclScript();
                xMLVariableDeclScript.setVariable(variable);
                xMLVariableDeclScript.setLocalVarxExp("");
                xMLVariableDeclScript.setLocalVarx(variable.getName());
                xMLVariableDeclScript.setVariableValue(null);
                result = xMLVariableDeclScript;
            }
        }else{
            if(arrayBracket !=null&& !arrayBracket.isEmpty()){
                variable.setArrayType(ArrayTypeEnum.STRUCT);
                variable.setStructName(lineParamMatcherMap.get(1));
                ArrayDeclScript arrayDeclScript = new ArrayDeclScript();
                arrayDeclScript.setArrayType(ArrayTypeEnum.STRUCT);
                variable.setVarTypeEnum(VarTypeEnum.STRUCT);
                arrayDeclScript.setArrayVariable(variable);
                result = arrayDeclScript;

                
            }else{
                StructureScript structureScript = new StructureScript();
                structureScript.setStructName(lineParamMatcherMap.get(1));
                variable.setVarTypeEnum(VarTypeEnum.STRUCT);
                structureScript.setStructVarName(variable);
                result = structureScript;
            }
        }
        
        return result;
    }

     public  ParameterScript parseFunctionParameters(String parameters) throws Exception{

            ParameterScript parameterScript = new ParameterScript();
            parameters = parameters.trim();
            if(parameters.endsWith(",")){
                throw new Exception("Invalid parameters");
            }

            if(!parameters.isEmpty())
            {
                String params[] = parameters.split(",");

                if(params == null || params.length == 0 ){
                    throw new Exception("Invalid parameters");

                }else{
                    int i = 1;
                    for(String param :params){

                        IObjectScript script = createScript
                                                    (
                                                        PatternMatcher.match
                                                                        (
                                                                            FUNCTION_PARAMETER_PATTERN,
                                                                            param
                                                                        )
                                                    );
                        
                        if(script!= null){

                            parameterScript.getParamScripts().put(script.getVariable(), script);
                            i++;
                        }else{
                            throw new Exception("Invalid parameters");
                        }
                    }
                }

            }

            return parameterScript;
    }
    /* 
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 
            }
        
    
    

