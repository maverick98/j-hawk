


package org.hawk.module.script;


import org.hawk.pattern.PatternMatcher;
import org.hawk.ds.Stack;
import org.hawk.exception.HawkException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.Variable;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 *
 * @version 1.0 10 Apr, 2010
 * @author msahu
 */
public class FunctionScript extends MultiLineScript{



    private  static final Pattern FUNCTION_DEFN_PATTERN=Pattern.compile("\\s*"+function+"\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*\\(\\s*(.*)\\s*\\)\\s*");

    private static final Pattern FUNCTION_DEFN_BRACKET_START_PATTERN=Pattern.compile("\\s*\\{\\s*");

    private static final Pattern FUNCTION_DEFN_BRACKET_END_PATTERN=Pattern.compile("\\s*\\}\\s*");

    public static final class FunctionInvocationInfo{
        private String name;
        private int lineNo;

        public FunctionInvocationInfo(String name, int lineNo) {
            this.name = name;
            this.lineNo = lineNo;
        }

        @Override
        public String toString() {
            return "at "+name+"("+lineNo+")";
        }

        
        public int getLineNo() {
            return lineNo;
        }

        public void setLineNo(int lineNo) {
            this.lineNo = lineNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static Map<String,FunctionScript>  parseFunctions(Map<Integer,String> scriptMap) throws HawkException{
        
        Map<String,FunctionScript> functionMap = new HashMap<String,FunctionScript>();

        for(int i=0;i<scriptMap.size();i++){

            Map<Integer,String> map = PatternMatcher.match(FUNCTION_DEFN_PATTERN, scriptMap.get(i));

            if(map!= null){
                String functionName = map.get(1);
                String parameters = map.get(2).trim();
                ParameterScript parameterScriptOrig = ParameterScript.parseFunctionParameters(parameters);
                

                FunctionScript functionScript = new FunctionScript();
                functionScript.setLineNumber(i+1);
                functionScript.setFunctionName(functionName);
                functionScript.setOriginalFunctionName(functionName);
                MultiLineContainer functionDefn = extractFunctionDefinition(scriptMap,i+1,functionName);
                functionScript.setMultiLineContainer(functionDefn);
                functionScript.setParameterScriptOrig(parameterScriptOrig);
                functionScript.pushParameterScript();
                functionMap.put(functionScript.mangle(), functionScript);

            }
        }

        return functionMap;
    }

    public FunctionScript createFunctionTemplate(){
        FunctionScript templateCopy = new FunctionScript();
        templateCopy.setFunctionName(this.getFunctionName());
        templateCopy.setMultiLineContainer(this.getMultiLineContainer());
        templateCopy.setParameterScriptOrig(this.getParameterScriptOrig());
        templateCopy.pushParameterScript();
        return templateCopy;
    }

   
    private static MultiLineContainer extractFunctionDefinition(Map<Integer,String> scriptMap,int x,String functionName) throws HawkException{
        MultiLineContainer mlc = new MultiLineContainer();
        boolean firstStartBracktFound = false;
        Stack stack = new Stack();
        int start = x;
        int end = -1;
        int i = x;
        mlc.setMultiLineStart(x-1);
        for(;i<scriptMap.size();i++){

            Map<Integer,String> startingBracketMap = PatternMatcher.match(FUNCTION_DEFN_BRACKET_START_PATTERN, scriptMap.get(i));

            if(startingBracketMap!=null){

                    if(!firstStartBracktFound){
                        firstStartBracktFound = true;
                        start = i;
                    }
                    stack.push("{");
        
            }

            Map<Integer,String> endingBracketMap = PatternMatcher.match(FUNCTION_DEFN_BRACKET_END_PATTERN, scriptMap.get(i));

            if(endingBracketMap!=null){

                    stack.pop();

            }

            if(stack.isEmpty()){
                break;
            }


        }

        if(!stack.isEmpty() || !firstStartBracktFound){
            throw new HawkException ("invalid function {"+functionName+"}");
        }

        end = i;

        mlc.setStart(start);
        mlc.setEnd(end);

        return mlc;
    }


    private ForLoopScript defaultForLoopScript = null;

    

    private Stack<ParameterScript> parameterScriptStack = new Stack<ParameterScript>();

    private ParameterScript parameterScript = null;

    private ParameterScript parameterScriptOrig = null;

    public ParameterScript getParameterScriptOrig() {
        return parameterScriptOrig;
    }

    public void setParameterScriptOrig(ParameterScript parameterScriptOrig) {
        this.parameterScriptOrig = parameterScriptOrig;
    }

    

    

    public ParameterScript getParameterScript() {
        return this.parameterScriptStack.top();
        
    }

    public void setParameterScript(ParameterScript parameterScript) {
        this.parameterScript = parameterScript;
    }

    public void pushParameterScript() {
        this.parameterScript = this.parameterScriptOrig.copy();
        this.parameterScriptStack.push(this.parameterScript);
    }

    public ParameterScript popParameterScript() {
        this.parameterScript = null;
        return this.parameterScriptStack.pop();
    }

  
    
    @Override
    public String mangle(){
        return this.functionName+this.mangleParameters();
    }
    private String mangleParameters(){
        return this.getParameterScript().mangle();
    }


    public boolean initializeParams(){
        return this.getParameterScript().initializeParams();
    }


    public boolean initializeParamsValue(Map<Integer, IScript> params) throws HawkException{
       return this.getParameterScript().initializeParamsValue(params);
    }

    public IScript getParamValue(Variable paramVar){
        return this.getParameterScript().getParamValue(paramVar);
    }

    
    public IScript getParamValue(String localVar){
        
        return this.getParameterScript().getParamValue(localVar);
    }

    

    public void setParamValue(Variable variable,IScript localValue){
        this.getParameterScript().setParamValue(variable, localValue);
    }

    public boolean isParamVarDeclared(Variable paramVar){

        return this.getParameterScript().isParamVarDeclared(paramVar);
    }

    public ForLoopScript getDefaultForLoopScript() {
        return defaultForLoopScript;
    }

    public void setDefaultForLoopScript(ForLoopScript defaultForLoopScript) {
        this.defaultForLoopScript = defaultForLoopScript;
        this.defaultForLoopScript.setDefaultMultiLineScript(true);
    }

    private String originalFunctionName;
    private String functionName;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getOriginalFunctionName() {
        return originalFunctionName;
    }

    public void setOriginalFunctionName(String originalFunctionName) {
        this.originalFunctionName = originalFunctionName;
    }

    public boolean isMyOverloadingMate(FunctionScript theOtherFunctionScript){
        return this.getOriginalFunctionName().equals(theOtherFunctionScript.getOriginalFunctionName());
    }
    
    public IScript executeDefaultForLoopScript(Map<Integer,IScript> params) throws HawkException{
        if(this.defaultForLoopScript == null){
            throw new HawkException("could not find defaultScript to execute");
        }
        this.pushParameterScript();
        this.initializeParamsValue(params);
        IScript result  = this.defaultForLoopScript.execute();
        this.popParameterScript();
        return result;

    }

    @Override
    public String toString(){
        return this.functionName;
    }

    public boolean isMainFunction(){
        return this.functionName.equals("main");
    }

    @Override
    public void pushLocalVars(){

        this.defaultForLoopScript.pushLocalVars();
        
    }

    @Override
    public Map<String,IDataType> popLocalVars(){

        return this.defaultForLoopScript.popLocalVars();
    }

    @Override
    public boolean isVariable() {
        return false;
    }
}




