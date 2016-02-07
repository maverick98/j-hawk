


package org.hawk.module.script;


import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 *
 * @version 1.0 2 May, 2010
 * @author msahu
 */
public class ReturnScript extends SingleLineScript{

    private  static final Pattern RETURN_PATTERN=Pattern.compile("\\s*"+returnStatement+"\\s*(.*)\\s*");


    public ReturnScript(){

    }
    

    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }


    @ScriptPattern
    public static Pattern getPattern() {
        return RETURN_PATTERN;
    }
    @Override
    public IScript execute() throws HawkException {
        
        return evaluate();
    }

    public IScript evaluate() throws HawkException{

        if(this.expression!= null && !this.expression.isEmpty()){
            IScript rtn = this.evaluateLocalVariable(this.expression);
            rtn.getVariableValue().setRtrn(true);
            
            return rtn;
        }

        return null;
    }

    @CreateScript
    public static ReturnScript createScript(Map<Integer,String> lineEchoMatcherMap){

        if(lineEchoMatcherMap == null){
            return null;
        }
        ReturnScript returnScript = new ReturnScript();
        returnScript.setExpression(lineEchoMatcherMap.get(1));
        return returnScript;
    }
    @Override
    public boolean isVariable() {
        return false;
    }
}




