/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
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
package org.hawk.lang.function;

import java.util.Map;
import java.util.Set;

import org.hawk.lang.IScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.common.di.ScanMe;
import org.hawk.lang.loop.ForLoopScript;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.object.LocalVarDeclScript;

/**
 *
 * @version 1.0 2 May, 2010
 * @author msahu
 */
@ScanMe(true)
public class ReturnScript extends SingleLineScript {

    public ReturnScript() {

    }

    private String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public static class Return extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Return" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getRetturn().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getRetturn().getLinePattern();
    }

    private boolean isDefaultForLoop( MultiLineScript outerMLS){
        boolean result = false;
        if(outerMLS instanceof ForLoopScript){
            ForLoopScript defaultForLoopScript =  (ForLoopScript)outerMLS;
            result = defaultForLoopScript.isDefaultForLoop();
        }
        return result;
    }
    @Override
    public IScript execute() throws Exception {

        IScript rtnValue = this.evaluate();

        boolean defaultForLoopNotFound = true;
        MultiLineScript outerMLS = this.getOuterMultiLineScript();
        do {

            if (outerMLS != null) {

                outerMLS.orderReturn();
                if (isDefaultForLoop(outerMLS)) {
                    defaultForLoopNotFound = false;
                }
                outerMLS = outerMLS.getOuterMultiLineScript();
            }
        } while (defaultForLoopNotFound);
        return rtnValue;
    }

    public IScript evaluate() throws Exception {

        if (this.expression != null && !this.expression.isEmpty()) {
            IObjectScript rtn = this.evaluateLocalVariable(this.expression);
            rtn.getVariableValue().setRtrn(true);

            return rtn;
        }

        return null;
    }

    public ReturnScript createScript(Map<Integer, String> lineEchoMatcherMap) {

        if (lineEchoMatcherMap == null) {
            return null;
        }
        ReturnScript returnScript = new ReturnScript();
        returnScript.setExpression(lineEchoMatcherMap.get(1));
        return returnScript;
    }
    /*
     @Override
     public boolean isVariable() {
     return false;
     }
     */

}
