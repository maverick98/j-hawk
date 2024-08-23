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

package org.hawk.lang.condition;


import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.commons.string.StringUtil;
import org.common.di.AppContainer;

import org.hawk.input.HawkInput;
import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.elseCondition;
import static org.hawk.lang.constant.HawkLanguageKeyWord.ifCondition;
import org.hawk.lang.function.ReturnScript;
import org.hawk.lang.loop.BreakScript;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.common.di.ScanMe;



/**
 *
 * @version 1.0 23 Apr, 2010
 * @author msahu
 */
@ScanMe(true)
public class IfScript extends MultiLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(IfScript.class.getName());

    protected String expression;

    protected ElseIfScript elseIfScript;

    public ElseIfScript getElseIfScript() {
        return elseIfScript;
    }

    public void setElseIfScript(ElseIfScript elseIfScript) {
        this.elseIfScript = elseIfScript;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean evaluate() throws Exception{
        boolean status = true;
        if(this.expression != null && !this.expression.isEmpty()){
            status = this.evaluateLocalBoolean(this.expression);
        }else{
            status = true;
        }

        return status;
    }

    public boolean hasElseCondition(){
        return this.elseIfScript != null;
    }

    @Override
    public IScript execute() throws Exception {

        IScript rtnVal = LocalVarDeclScript.createDummyBooleanScript();
       
        if(this.evaluate()){
             for(Entry<Integer,IScript> entry:this.innerScripts.entrySet()){
                IScript script = entry.getValue();
                IScript status = script.execute();
                rtnVal = status;
            //    if(status!= null && (script instanceof BreakScript)){
            //        break;
            //    }
                  if(this.breakApplied()){
                       // breakApplied = true;
                        this.releaseBreak();
                        break;
                    }   
                
                 if(this.returnOrdered()){
                        //returned = true;
                        this.acceptReturn();
                        rtnVal = status;
                        return rtnVal;
                    }
              /*  if(status!= null && (script instanceof ReturnScript) ){
                    rtnVal = status;
                    return rtnVal;
                }
                 */
            }
        }else if(this.hasElseCondition()){

            return this.elseIfScript.execute();
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
    
    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param multiLineScript
     * @param i
     * @param lineEchoMatcherMap
     * @return
     * @throws org.hawk.exception.Exception
     */
    @Override
    public  int createScript
            (
                MultiLineScript multiLineScript,
                int i
            ) throws Exception{

        Map<Integer, String> scriptMap = AppContainer.getInstance().getBean( HawkInput.class).getScriptMap();
        Map<Integer, String> lineIfMatcherMap = PatternMatcher.match(IF_PATTERN, scriptMap.get(i));
        if (lineIfMatcherMap == null) {
            return -1;
        }
        IfScript firstIfScript = new IfScript();
        IfScript ifScript = firstIfScript;
        ifScript.setOuterMultiLineScript(multiLineScript);
        ifScript.setExpression(lineIfMatcherMap.get(1));
        firstIfScript.setLineNumber(i + 1);
        multiLineScript.addScript(i, firstIfScript);
        boolean elseScriptAdded = false;
        do {
            MultiLineContainer mlc = multiLineScript.extractMultiLineContainer(scriptMap, i + 1);
            ifScript.parseMultiLines(scriptMap, mlc.getStart() + 1, mlc.getEnd() );
            i = mlc.getEnd() + 1;
            lineIfMatcherMap = PatternMatcher.match(ELSEIF_PATTERN, scriptMap.get(i));
            if (lineIfMatcherMap != null) {

                ElseIfScript elseIfScript = new ElseIfScript();
                elseIfScript.setLineNumber(i + 1);
                String exp = StringUtil.parseBracketData(lineIfMatcherMap.get(1));
                elseIfScript.setExpression(exp);
                elseIfScript.setOuterMultiLineScript(multiLineScript);
                if (!elseScriptAdded) {
                    ifScript.setElseIfScript(elseIfScript);

                }
                if (lineIfMatcherMap.get(1) == null || lineIfMatcherMap.get(1).isEmpty()) {
                    if (!elseScriptAdded) {
                        elseScriptAdded = true;
                    } else {
                        throw new Exception("invalid else (if) script");
                    }
                }
                ifScript = elseIfScript;
            }

        } while (lineIfMatcherMap != null);

        return i;

    }
    
    private static final Pattern IF_PATTERN = Pattern.compile("\\s*" + ifCondition + "\\s*\\(\\s*(.*)\\s*\\)\\s*");
    private static final Pattern ELSEIF_PATTERN = Pattern.compile("\\s*" + elseCondition + "+\\s*i?f?\\s*(\\(?\\s*.*\\s*\\)?)\\s*");


}




