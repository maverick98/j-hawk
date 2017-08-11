/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.lang.loop;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.commons.string.StringUtil;
import org.common.di.AppContainer;
import org.commons.ds.stack.Stack;

import org.hawk.input.HawkInput;
import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.whileLoop;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.type.BooleanDataType;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.common.di.ScanMe;

/**
 *
 * @author msahu
 */
@ScanMe(true)
public class WhileLoopScript extends MultiLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(WhileLoopScript.class.getName());
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
        return "While::"+this.getBoundaryCheckExp();
    }
    @Override
    public IScript execute() throws Exception {

        this.pushLocalVars();
        this.pushForLoopData();
        boolean breakApplied = false;
        IScript rtnVal = LocalVarDeclScript.createDummyBooleanScript();
        boolean returned = false;

        boolean boundaryCheck = true;
        

        do{

            String boundaryCheckExpTmp = this.getBoundaryCheckExp();
            if(boundaryCheckExpTmp != null){

                Variable rtn = this.evaluateLocalVariable(boundaryCheckExpTmp).getVariableValue();
                BooleanDataType dd = null;
                if(rtn.getValue() instanceof BooleanDataType){
                    dd =(BooleanDataType)rtn.getValue();
                }else{
                    throw new Exception("Invalid expression");
                }

                boundaryCheck = dd.isData();

            }

            if(boundaryCheck){
                for(Entry<Integer,IScript> entry:this.innerScripts.entrySet()){
                    IScript script = entry.getValue();
                    IObjectScript status = (IObjectScript)script.execute();
                    if(this.breakApplied()){
                        breakApplied = true;
                        this.releaseBreak();
                        break;
                    }
                    /*
                    if(status!= null && (script instanceof BreakScript)){
                        breakApplied = true;
                        break;
                    }
                    */
                     if(this.returnOrdered()){
                        returned = true;
                        this.acceptReturn();
                        rtnVal = status;
                        break;
                    }
                   /* if(status!= null && status.getVariableValue().isRtrn()){
                        returned = true;
                        rtnVal = status;
                        break;
                    }
                    */ 


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
    /*
    @Override
    public boolean isVariable() {
        return false;
    }
    */ 

     /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    
    public  int createScript
            (
                MultiLineScript multiLineScript,
                int i
            ) throws Exception{


        Map<Integer, String> scriptMap =AppContainer.getInstance().getBean(HawkInput.class).getScriptMap();
        Map<Integer, String> lineWhileMatcherMap = PatternMatcher.match(WHILE_LOOP_PATTERN, scriptMap.get(i));
        if(lineWhileMatcherMap == null){
            return -1;
        }

        MultiLineContainer mlc = multiLineScript.extractMultiLineContainer(scriptMap, i + 1);
        WhileLoopScript innerWhileLoopScript = new WhileLoopScript();


        String whileLoopData = lineWhileMatcherMap.get(1);


        whileLoopData = StringUtil.parseBracketData(whileLoopData);
        whileLoopData = whileLoopData.trim();
        Map<Integer, String> lineWhileLoopDataMatcherMap = PatternMatcher.match(
                WHILE_LOOP_DATA_PATTERN,
                whileLoopData);
        if (lineWhileLoopDataMatcherMap != null) {


            String exp = lineWhileLoopDataMatcherMap.get(1);



            if (!exp.trim().isEmpty()) {

                innerWhileLoopScript.setBoundaryCheckExpOrig(exp);

            } else {
                innerWhileLoopScript.setBoundaryCheckExpOrig(null);
            }


        }

        innerWhileLoopScript.setLineNumber(i + 1);
        multiLineScript.addScript(i, innerWhileLoopScript);
        innerWhileLoopScript.setOuterMultiLineScript(multiLineScript);
        innerWhileLoopScript.parseMultiLines(scriptMap, mlc.getStart() + 1, mlc.getEnd() );
        i = mlc.getEnd() + 1;

        return i;
    }
            
    @Override
    public boolean isLoop() {
        return true;
    }        
   
    private static final Pattern WHILE_LOOP_PATTERN = Pattern.compile("\\s*" + whileLoop + "\\s*(.*)\\s*");
    private static final Pattern WHILE_LOOP_DATA_PATTERN = Pattern.compile("\\s*(.*)\\s*");


}
