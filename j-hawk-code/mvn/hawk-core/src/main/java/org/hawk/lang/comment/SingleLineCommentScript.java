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
package org.hawk.lang.comment;

import java.util.Map;
import java.util.Set;

import org.hawk.lang.IScript;
import org.hawk.lang.condition.IfScript;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 1 May, 2010
 * @author msahu
 * @see IfScript
 */
@ScanMe(true)
public class SingleLineCommentScript extends SingleLineScript {

   
    /**
     * Default CTOR
     */
    public SingleLineCommentScript() {

    }

    public static class SingleLineComment extends SingleLineGrammar {

        @Override
        public String toString() {
            return "SingleLineComment" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getSingleLineComment().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {

        return this.getGrammar().getSingleLine().getSingleLineComment().getLinePattern();
    }

     /**
     * There is nothing to be done except
     *
     * @return
     * @throws org.hawk.exception.Exception
     */
    @Override
    public IScript execute() throws Exception {

        return LocalVarDeclScript.createDummyBooleanScript();
    }

    /**
     * Nothing to be done here.
     *
     * @param lineCommentMatcherMap
     * @return
     * @throws org.hawk.exception.Exception
     * @see CreateScript
     */
    public SingleLineCommentScript createScript(Map<Integer, String> lineCommentMatcherMap) throws Exception {

        return new SingleLineCommentScript();
    }
    /*
     @Override
     public boolean isVariable() {
     return false;
     }
     */

}
