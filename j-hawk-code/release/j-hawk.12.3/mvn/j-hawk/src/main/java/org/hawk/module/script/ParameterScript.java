


package org.hawk.module.script;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.module.script.enumeration.ArrayTypeEnum;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.Variable;
import org.hawk.pattern.PatternMatcher;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @version 1.0 25 Jul, 2010
 * @author msahu
 */
public class ParameterScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ParameterScript.class.getName());


    private  static final Pattern FUNCTION_PARAMETER_PATTERN=
            Pattern.compile("\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*(\\[?\\s*\\]?)\\s*");

    protected Map<Variable,IScript> paramScripts = new LinkedHashMap<Variable,IScript>();

    

    private Set<String> localStructs = new TreeSet<String>();

    public Map<Variable, IScript> getParamScripts() {
        return paramScripts;
    }

    public void setParamScripts(Map<Variable, IScript> paramScripts) {
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


    public boolean initializeParams(){
        
        
        for (Entry<Variable, IScript> entry : this.paramScripts.entrySet()) {
            entry.getValue().setVariableValue(null);
        }
        return true;
    }

     public boolean initializeParamsValue(Map<Integer, IScript> params) throws HawkException{
        if (params == null || params.isEmpty()) {
            return false;
        }
        int i = 1;
        for (Entry<Variable, IScript> entry : this.paramScripts.entrySet()) {
         // Revisit me
         //   if(ScriptInterpreter.getInstance().isGlobalVarDeclared(entry.getKey())){
         //       throw new HawkException (entry.getKey() + "is already declared as global var");
         //   }
            this.setParamValue(entry.getKey(), params.get(i).copy());
            i++;
        }
        return true;
    }

    public IScript getParamValue(Variable paramVar){
        return this.getParamValue(paramVar.getVariableName());
    }


    public IScript getParamValue(String localVar){
        if( (this.paramScripts == null || this.paramScripts.isEmpty())){
            return null;
        }
        IScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR,null,localVar);
        result = this.paramScripts.get(possibleKey);
        if(result == null){
            possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);
            result = this.paramScripts.get(possibleKey);
        }
        if(result == null){
            result = this.findArrayType(localVar, this.paramScripts);
        }
        return result;
    }

     public  IScript findArrayType(String localVar,Map<Variable,IScript> localVarTable){
        IScript result = null;
        Variable possibleKey = new Variable(VarTypeEnum.VAR, null, localVar);

        possibleKey.setArrayType(ArrayTypeEnum.VAR);
        possibleKey.setVarTypeEnum(VarTypeEnum.VAR);

        result = localVarTable.get(possibleKey);
        if(result == null){
            
            possibleKey.setArrayType(ArrayTypeEnum.STRUCT);
            possibleKey.setVarTypeEnum(VarTypeEnum.STRUCT);
            result = localVarTable.get(possibleKey);
            
            for(String struct:localStructs){
                possibleKey.setStructName(struct);
                result = localVarTable.get(possibleKey);
                if(result != null){
                    break;
                }
            }

        }
        return result;
    }


    public void setParamValue(Variable variable,IScript localValue){
        if(this.paramScripts != null){
            localValue.setVariable(variable);
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
        Map<Variable,IScript> copyParamScript =  new LinkedHashMap<Variable,IScript>();
        for (Entry<Variable, IScript> entry : this.paramScripts.entrySet()) {
            copyParamScript.put
                            (
                                entry.getKey().copy(),
                                entry.getValue()!= null
                                                ?
                                                        entry.getValue().copy()
                                                    :   null
                            );
        }
        copyParameterScript.setParamScripts(copyParamScript);
        return copyParameterScript;
    }

    @Override
    public String toString(){
        return "{"+this.paramScripts+"}";
    }

    @Override
    public String mangle(){

        StringBuilder sb = new StringBuilder();

        for(Entry<Variable,IScript> entry:this.paramScripts.entrySet()){

            sb.append(entry.getValue().mangle());
            
        }
        return sb.toString();
    }

    /**
     * Echo script pattern.
     * @return returns echo script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return FUNCTION_PARAMETER_PATTERN;
    }
    @Override
    public IScript execute() throws HawkException {

        
        return LocalVarDeclScript.createDummyScript();
    }

    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    @CreateScript
    public static IScript createScript(Map<Integer,String> lineParamMatcherMap){

        if(lineParamMatcherMap == null){
            return null;
        }
        IScript result = null;
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

     public static ParameterScript parseFunctionParameters(String parameters) throws HawkException{

            ParameterScript parameterScript = new ParameterScript();
            parameters = parameters.trim();
            if(parameters.endsWith(",")){
                throw new HawkException("Invalid parameters");
            }

            if(!parameters.isEmpty())
            {
                String params[] = parameters.split(",");

                if(params == null || params.length == 0 ){
                    throw new HawkException("Invalid parameters");

                }else{
                    int i = 1;
                    for(String param :params){

                        IScript script = createScript
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
                            throw new HawkException("Invalid parameters");
                        }
                    }
                }

            }

            return parameterScript;
    }

    @Override
    public boolean isVariable() {
        return false;
    }
}




