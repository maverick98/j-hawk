/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.module.script;
import org.hawk.exception.HawkException;
import org.hawk.ds.Stack;
import java.util.Map;
import java.util.Map.Entry;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.exception.HawkCompilationException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.script.type.BooleanDataType;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.Variable;
import org.hawk.pattern.PatternMatcher;
import org.hawk.util.HawkUtil;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @author msahu
 */

public class DoWhileLoopScript extends MultiLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(DoWhileLoopScript.class.getName());
    private Stack<String> boundaryCheckExpStack = new Stack<String>();
    private String boundaryCheckExp = null;
    private String boundaryCheckExpOrig = null;

    public String getBoundaryCheckExpOrig() {
        return boundaryCheckExpOrig;
    }

    public void setBoundaryCheckExpOrig(String boundaryCheckExpOrig) {
        this.boundaryCheckExpOrig = boundaryCheckExpOrig;
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

    public void pushForLoopData(){
            this.pushBoundaryCheckExp();
    }
    public void popForLoopData(){
            this.popBoundaryCheckExp();
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
    public String toString(){
        return "Do-While::"+this.getBoundaryCheckExp();
    }
    @Override
    public IScript execute() throws HawkException {

        this.pushLocalVars();
        this.pushForLoopData();
        boolean breakApplied = false;
        IScript rtnVal =LocalVarDeclScript.createDummyScript();
        boolean returned = false;

        boolean boundaryCheck = true;
       

        do{

            String boundaryCheckExpTmp = this.getBoundaryCheckExp();
            if(boundaryCheckExpTmp != null){

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
                    IScript status = script.execute();
                    if(status!= null && (script instanceof BreakScript)){
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


            }else{
                this.popForLoopData();
            }


        }while(boundaryCheck);

        this.popLocalVars();
        if(returned){
            return rtnVal;
        }
        
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


        Map<Integer, String> scriptMap = ScriptInterpreter.getInstance().getScriptMap();
        Map<Integer, String> lineDoWhileDoMatcherMap = PatternMatcher.match(DO_WHILE_LOOP_PATTERN, scriptMap.get(i));
        if(lineDoWhileDoMatcherMap == null){
            return -1;
        }

        MultiLineContainer mlc = extractMultiLineContainer(DoWhileLoopScript.class,scriptMap, i + 1);
        int j = mlc.getEnd() + 1;
        Map<Integer, String> lineDoWhileWhileMatcherMap = null;


        do {
            if (scriptMap.get(j) != null && !scriptMap.get(j).isEmpty()) {
                lineDoWhileWhileMatcherMap = PatternMatcher.match(WHILE_LOOP_PATTERN, scriptMap.get(j));
                if (lineDoWhileWhileMatcherMap == null) {
                    throw new HawkCompilationException("Could not find matching while statement for do at {" + i + "}");
                } else {
                    break;
                }
            }
            j = j + 1;
        } while (true);


        DoWhileLoopScript innerDoWhileLoopScript = new DoWhileLoopScript();


        String whileLoopData = lineDoWhileWhileMatcherMap.get(1);


        whileLoopData = HawkUtil.parseBracketData(whileLoopData);
        whileLoopData = whileLoopData.trim();
        Map<Integer, String> lineWhileLoopDataMatcherMap = PatternMatcher.match(
                WHILE_LOOP_DATA_PATTERN,
                whileLoopData);
        if (lineWhileLoopDataMatcherMap != null) {


            String exp = lineWhileLoopDataMatcherMap.get(1);



            if (!exp.trim().isEmpty()) {

                innerDoWhileLoopScript.setBoundaryCheckExpOrig(exp);

            } else {
                innerDoWhileLoopScript.setBoundaryCheckExpOrig(null);
            }


        }

        innerDoWhileLoopScript.setLineNumber(i + 1);
        multiLineScript.addScript(i, innerDoWhileLoopScript);
        innerDoWhileLoopScript.setOuterMultiLineScript(multiLineScript);
        parseMultiLines(scriptMap, mlc.getStart() + 1, mlc.getEnd(), innerDoWhileLoopScript);
        i = j + 1;


        return i;
    }
    private static final Pattern WHILE_LOOP_PATTERN = Pattern.compile("\\s*" + whileLoop + "\\s*(.*)\\s*");
    private static final Pattern WHILE_LOOP_DATA_PATTERN = Pattern.compile("\\s*(.*)\\s*");
    private static final Pattern DO_WHILE_LOOP_PATTERN = Pattern.compile("\\s*" + doWhileLoop + "\\s*");


}
