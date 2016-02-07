

package org.hawk.module.script;


import org.hawk.util.HawkUtil;
import java.util.Map;
import org.hawk.exception.HawkException;
import java.util.Map.Entry;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import org.hawk.module.annotation.CreateScript;
import org.hawk.pattern.PatternMatcher;
import static org.hawk.constant.HawkLanguageKeyWord.*;



/**
 *
 * @version 1.0 23 Apr, 2010
 * @author msahu
 */
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

    public boolean evaluate() throws HawkException{
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
    public IScript execute() throws HawkException {

        IScript rtnVal = LocalVarDeclScript.createDummyScript();
       
        if(this.evaluate()){
             for(Entry<Integer,IScript> entry:this.innerScripts.entrySet()){
                IScript script = entry.getValue();
                IScript status = script.execute();

                if(status!= null && (script instanceof BreakScript)){
                    break;
                }
                
                if(status!= null && (script instanceof ReturnScript) ){
                    rtnVal = status;
                    return rtnVal;
                }
            }
        }else if(this.hasElseCondition()){

            return this.elseIfScript.execute();
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
            MultiLineContainer mlc = extractMultiLineContainer(IfScript.class,scriptMap, i + 1);
            parseMultiLines(scriptMap, mlc.getStart() + 1, mlc.getEnd(), ifScript);
            i = mlc.getEnd() + 1;
            lineIfMatcherMap = PatternMatcher.match(ELSEIF_PATTERN, scriptMap.get(i));
            if (lineIfMatcherMap != null) {

                ElseIfScript elseIfScript = new ElseIfScript();
                elseIfScript.setLineNumber(i + 1);
                String exp = HawkUtil.parseBracketData(lineIfMatcherMap.get(1));
                elseIfScript.setExpression(exp);
                elseIfScript.setOuterMultiLineScript(multiLineScript);
                if (!elseScriptAdded) {
                    ifScript.setElseIfScript(elseIfScript);

                }
                if (lineIfMatcherMap.get(1) == null || lineIfMatcherMap.get(1).isEmpty()) {
                    if (!elseScriptAdded) {
                        elseScriptAdded = true;
                    } else {
                        throw new HawkException("invalid else (if) script");
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




