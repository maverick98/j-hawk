

package org.hawk.module.script;

import java.util.Map;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class AssignmentScript extends SingleLineScript{

    /**
     *RegEx to find assignment operation present in the expression
     */
    private static final Pattern ASSIGNMENT_PATTERN=Pattern.compile("(.*)=(.*)");


   
    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public AssignmentScript(){

    }

    @ScriptPattern
    public static Pattern getPattern() {
        return ASSIGNMENT_PATTERN;
    }

    @CreateScript
    public static AssignmentScript createScript(Map<Integer,String> lineStructMatcherMap) throws HawkException{

        if(lineStructMatcherMap == null){
            return null;
        }
        AssignmentScript assignmentScript = new AssignmentScript();
        String exp = lineStructMatcherMap.get(0);
        assignmentScript.setExpression(exp);
        return assignmentScript;

    }

    @Override
    public String toString(){
        return "{"+this.expression+"}";
    }
    @Override
    public IScript execute() throws HawkException {
        
        IScript result = this.evaluateLocalVariable(this.expression);
        
        return result;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public AssignmentScript copy(){
        AssignmentScript copy = new AssignmentScript();
        copy.setExpression(this.getExpression());
        copy.setOuterMultiLineScript(this.getOuterMultiLineScript());
        return copy;
    }
}




