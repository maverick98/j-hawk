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
 */
package org.hawk.lang.object;

import java.util.Map;
import java.util.Set;

import org.hawk.lang.IScript;
import org.hawk.lang.console.EchoScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
@ScanMe(true)
public class AssignmentScript extends SingleLineScript {

    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public AssignmentScript() {
    }
    public static class Assignment extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Assignment" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getAssignment().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getAssignment().getLinePattern();
    }
   

    @Override
    public AssignmentScript createScript(Map<Integer, String> lineAssignmentMatcherMap) throws Exception {

        if (lineAssignmentMatcherMap == null) {
            return null;
        }
        AssignmentScript assignmentScript = new AssignmentScript();
        String exp = lineAssignmentMatcherMap.get(0);
        assignmentScript.setExpression(exp);
        return assignmentScript;

    }

    @Override
    public String toString() {
        return "{" + this.expression + "}";
    }

    @Override
    public IScript execute() throws Exception {
        IScript result;
        try {
            if(this.expression.equals("rtnValue = `exec b()`")){
                System.out.println("I am here");
            }
            result = this.evaluateLocalVariable(this.expression);
        } catch (Exception ex) {
          //  ex.printStackTrace();
            EchoScript echoScript = new EchoScript();
            echoScript.setMessage(this.expression);
            echoScript.setCandidateForEvaluation(false);
            result = echoScript.execute();
        }
       // this.outerMultiLineScript.setLocalValue(null, result);
        return result;
    }


    @Override
    public AssignmentScript copy() {
        AssignmentScript copy = new AssignmentScript();
        copy.setExpression(this.getExpression());
        copy.setOuterMultiLineScript(this.getOuterMultiLineScript());
        return copy;
    }
}
