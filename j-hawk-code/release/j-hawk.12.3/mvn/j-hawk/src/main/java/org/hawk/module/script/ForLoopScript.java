


package org.hawk.module.script;

import org.hawk.exception.HawkException;
import org.hawk.ds.Stack;
import java.util.Map;
import java.util.Map.Entry;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.module.script.type.DoubleDataType;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.Variable;
import org.hawk.pattern.PatternMatcher;
import org.hawk.util.HawkUtil;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */

public class ForLoopScript extends MultiLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ForLoopScript.class.getName());


    private boolean defaultForLoop = false;
    
    

    private Stack<LocalVarDeclScript> variableInitScriptStack = new Stack<LocalVarDeclScript>();

    private Stack<String> boundaryCheckExpStack = new Stack<String>();

    private Stack<AssignmentScript> variableIterationScriptStack = new Stack<AssignmentScript>();


    private LocalVarDeclScript variableInitScript = null;

    private String boundaryCheckExp = null;

    private AssignmentScript variableIterationScript = null;


    private LocalVarDeclScript variableInitScriptOrig = null;

    private String boundaryCheckExpOrig = null;

    private AssignmentScript variableIterationScriptOrig = null;

    public String getBoundaryCheckExpOrig() {
        return boundaryCheckExpOrig;
    }

    public void setBoundaryCheckExpOrig(String boundaryCheckExpOrig) {
        this.boundaryCheckExpOrig = boundaryCheckExpOrig;
    }

    public LocalVarDeclScript getVariableInitScriptOrig() {
        return variableInitScriptOrig;
    }

    public void setVariableInitScriptOrig(LocalVarDeclScript variableInitScriptOrig) {
        this.variableInitScriptOrig = variableInitScriptOrig;
    }

    public AssignmentScript getVariableIterationScriptOrig() {
        return variableIterationScriptOrig;
    }

    public void setVariableIterationScriptOrig(AssignmentScript variableIterationScriptOrig) {
        this.variableIterationScriptOrig = variableIterationScriptOrig;
    }



    

    public String getBoundaryCheckExp() {
        return boundaryCheckExpStack.top();
        
    }

    public void setBoundaryCheckExp(String boundaryCheckExp) {
        this.boundaryCheckExp = boundaryCheckExp;
    }

    public void pushBoundaryCheckExp() {
        this.boundaryCheckExp = this.boundaryCheckExpOrig;
        this.boundaryCheckExpStack.push(this.boundaryCheckExp);
    }
    public String popBoundaryCheckExp() {
        this.boundaryCheckExp = null;
        return this.boundaryCheckExpStack.pop();
    }

    public LocalVarDeclScript getVariableInitScript() {
        return this.variableInitScriptStack.top();
        
    }

    public void setVariableInitScript(LocalVarDeclScript variableInitScript) {
        this.variableInitScript = variableInitScript;
    }

    public void pushVariableInitScript() {
        this.variableInitScript = this.variableInitScriptOrig.copy();
        this.variableInitScriptStack.push(this.variableInitScript);
    }

    public LocalVarDeclScript popVariableInitScript() {
        this.variableInitScript = null;
        return this.variableInitScriptStack.pop();
    }

    public AssignmentScript getVariableIterationScript() {
        return this.variableIterationScriptStack.top();
        
    }

    public void setVariableIterationScript(AssignmentScript variableIterationScript) {
        this.variableIterationScript = variableIterationScript;
    }

    public void pushVariableIterationScript() {
        this.variableIterationScript = this.variableIterationScriptOrig.copy();
        this.variableIterationScriptStack.push(this.variableIterationScript);
    }

    public AssignmentScript popVariableIterationScript() {
        this.variableIterationScript = null;
        return this.variableIterationScriptStack.pop();
    }



    public void pushForLoopData(){
        if(!this.isDefaultForLoop()){
            this.pushVariableInitScript();
            this.pushBoundaryCheckExp();
            this.pushVariableIterationScript();
        }
    }


    public void popForLoopData(){
        if(!this.isDefaultForLoop()){
            this.popVariableInitScript();
            this.popBoundaryCheckExp();
            this.popVariableIterationScript();
        }
    }

    @Override
    public void pushLocalVars(){
        
        super.pushLocalVars();
    }

    @Override
    public Map<String,IDataType> popLocalVars(){

        super.popLocalVars();
        return null;
    }



    @Override
    public IScript getLocalValue(Variable localVar){
       LocalVarDeclScript tmp = this.getVariableInitScript();
       IScript rtn = null;
       if(tmp!=null && tmp.getLocalVar().equals(localVar)){
            if(tmp != null){
                tmp.getLocalVar().setVariableValue(tmp.getLocalVarValue().getVariableValue());
                rtn = tmp;
            }else{
                rtn = null;
            }
       }
       if(rtn == null){
           rtn = super.getLocalValue(localVar);
       }
       return rtn;
    }


    @Override
    public IScript getLocalValue(String localVar){

        Variable variable = new Variable(VarTypeEnum.VAR,null,localVar);
        return this.getLocalValue(variable);
    }

    @Override
    public void setLocalValue(Variable variable,IScript localValue){

        if(this.isLocalVarDeclared(variable)){

            LocalVarDeclScript tmp = this.getVariableInitScript();
            if(tmp!= null){
                tmp.setLocalVarValue(localValue.getVariableValue());
            }

        }else{
            super.setLocalValue(variable, localValue);
        }
    }

    @Override
    public void unsetLocalValue(Variable localVar){
       if(this.isLocalVarDeclared(localVar)){
            LocalVarDeclScript tmp = this.getVariableInitScript();
            if(tmp!= null){
                tmp.setLocalVar(null);
            }

       }else{
           super.unsetLocalValue(localVar);
       }
    }

   

    @Override
    public boolean isLocalVarDeclared(Variable localVar){

        return super.isLocalVarDeclared(localVar);
    }


    @Override
    public String toString(){
        return "For::"+this.getVariableInitScript().toString()+"::"+this.getBoundaryCheckExp()+"::"+this.getVariableIterationScript().toString();
    }
   
    public boolean isDefaultForLoop() {
        return defaultForLoop;
    }

    public void setDefaultForLoop(boolean defaultForLoop) {
        this.defaultForLoop = defaultForLoop;
    }

    

   

    @Override
    public IScript execute() throws HawkException {

        this.pushLocalVars();
        this.pushForLoopData();
        boolean breakApplied = false;
        IScript rtnVal = LocalVarDeclScript.createDummyScript();
        boolean returned = false;
        LocalVarDeclScript variableInitScriptTmp = this.getVariableInitScript();
        if(variableInitScriptTmp!= null && !this.isDefaultForLoop()){
            variableInitScriptTmp.execute();
        }
        boolean boundaryCheck = true;
        DoubleDataType zero = new DoubleDataType(0);
        
        do{
            
            String boundaryCheckExpTmp = this.getBoundaryCheckExp();
            if(boundaryCheckExpTmp != null && !this.isDefaultForLoop()){
                
                Variable rtn = this.evaluateLocalVariable
                                                    (
                                                        boundaryCheckExpTmp
                                                    ).getVariableValue();
                BooleanDataType dd = null;
                if(rtn.getVariableValue() instanceof BooleanDataType){
                    dd =(BooleanDataType)rtn.getVariableValue();
                }else{
                    throw new HawkException("Invalid expression");
                }
                    
                boundaryCheck = dd.isData();

            }

            if(boundaryCheck){
                for(Entry<Integer,IScript> entry:this.innerScripts.entrySet()){
                    IScript script = entry.getValue();
                    IScript status = null;
                    try{
                        status = script.execute();
                    }catch(Throwable th){
                        th.printStackTrace();
                        throw new HawkException("error while executing script at line no {"+(entry.getKey()+1)+"}");
                        
                    }
                    if(status!= null && (script instanceof BreakScript) && !this.isDefaultForLoop()){
                        breakApplied = true;
                        break;
                    }

                    if(status!= null && status.getVariableValue().isRtrn()){
                        returned = true;
                        rtnVal = status;
                        
                        break;
                    }


                }

                this.unsetAllLocalValue();
                if(breakApplied || returned){
                    break;
                }

                AssignmentScript variableIterationScriptTmp = this.getVariableIterationScript();
                if(variableIterationScriptTmp != null && !this.isDefaultForLoop()){
                        variableIterationScriptTmp.execute();
                }
            }else{
                this.popForLoopData();
            }
            if(this.isDefaultForLoop()){
                break;
            }
          
        }while(boundaryCheck);

        this.popLocalVars();
        if(returned){
            return rtnVal;
        }
        rtnVal.getVariableValue().setVariableValue(new BooleanDataType(true));
        return rtnVal;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    @CreateScript
    public static int createScript
            (
                MultiLineScript multiLineScript,
                int i
            ) throws HawkException{

        Map<Integer,String> scriptMap = ScriptInterpreter.getInstance().getScriptMap();
        Map<Integer,String> lineForMatcherMap = PatternMatcher.match(FOR_LOOP_PATTERN, scriptMap.get(i));
        if (lineForMatcherMap == null) {
            return -1;
        }
        ForLoopScript innerForLoopScript = null;


        if (lineForMatcherMap != null) {
            MultiLineContainer mlc = extractMultiLineContainer(ForLoopScript.class,scriptMap, i + 1);
            innerForLoopScript = new ForLoopScript();


            String forLoopData = lineForMatcherMap.get(1);


            forLoopData = HawkUtil.parseBracketData(forLoopData);
            forLoopData = forLoopData.trim();
            Map<Integer, String> lineForLoopDataMatcherMap = PatternMatcher.match(
                    FOR_LOOP_DATA_PATTERN,
                    forLoopData);
            if (lineForLoopDataMatcherMap != null) {

                String varInit = lineForLoopDataMatcherMap.get(1);
                String exp = lineForLoopDataMatcherMap.get(2);
                String varItr = lineForLoopDataMatcherMap.get(3);

                if (!varInit.trim().isEmpty()) {



                    Map<Integer, String> lineVarDeclMatcherMap = PatternMatcher.match(
                            LocalVarDeclScript.getPattern(),
                            varInit);

                    LocalVarDeclScript script = LocalVarDeclScript.createScript(lineVarDeclMatcherMap);

                    if (!LocalVarDeclScript.isValid(script.getLocalVar(), multiLineScript)) {
                        throw new HawkException("the variable " + script.getLocalVar() + " is already declared");
                    }
                    script.setLineNumber(i + 1);
                    innerForLoopScript.setVariableInitScriptOrig(script);
                    if (script != null) {
                        script.setOuterMultiLineScript(innerForLoopScript);
                    }

                } else {
                    innerForLoopScript.setVariableInitScriptOrig(null);
                }
                if (!exp.trim().isEmpty()) {

                    innerForLoopScript.setBoundaryCheckExpOrig(exp);

                } else {
                    innerForLoopScript.setBoundaryCheckExpOrig(null);
                }

                if (!varItr.trim().isEmpty()) {



                    Map<Integer, String> lineVarAsgnMatcherMap = PatternMatcher.match(
                            AssignmentScript.getPattern(),
                            varItr);

                    AssignmentScript script = AssignmentScript.createScript(lineVarAsgnMatcherMap);
                    script.setLineNumber(i + 1);
                    innerForLoopScript.setVariableIterationScriptOrig(script);
                    if (script != null) {
                        script.setOuterMultiLineScript(innerForLoopScript);
                    }

                } else {
                    innerForLoopScript.setVariableIterationScriptOrig(null);
                }
            }

            innerForLoopScript.setLineNumber(i + 1);
            multiLineScript.addScript(i, innerForLoopScript);
            innerForLoopScript.setOuterMultiLineScript(multiLineScript);
            parseMultiLines(scriptMap, mlc.getStart() + 1, mlc.getEnd(), innerForLoopScript);
            i = mlc.getEnd() + 1;


        }
        return i;
    }

    private static final Pattern FOR_LOOP_PATTERN=Pattern.compile("\\s*"+forLoop+"\\s*(.*)\\s*");

    private static final Pattern FOR_LOOP_DATA_PATTERN=

            Pattern.compile("\\s*(.*)\\s*;\\s*(.*)\\s*;\\s*(.*)\\s*");

    
}




