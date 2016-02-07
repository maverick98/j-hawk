

package org.hawk.module.script;


import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import java.util.regex.Pattern;
/**
 *
 * @version 1.0 1 May, 2010
 * @author msahu
 * @see IfScript
 */
public class SingleLineCommentScript extends SingleLineScript{

    /**
     * RegEx Pattern to parse break script
     */
    private  static final Pattern COMMENT_PATTERN=Pattern.compile("\\s*//.*\\s*");


    /**
     * Default CTOR
     */
    public SingleLineCommentScript(){

    }


    /**
     * Returns the comment script pattern
     * @return returns the comment script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return COMMENT_PATTERN;
    }
    /**
     * There is nothing to be done except
     * @return
     * @throws org.hawk.exception.HawkException
     */
    @Override
    public IScript execute() throws HawkException {
        
        return LocalVarDeclScript.createDummyScript();
    }

    /**
     * Nothing to be done here.
     * @param lineCommentMatcherMap
     * @return
     * @see CreateScript
     */
    @CreateScript
    public static SingleLineCommentScript createScript(Map<Integer,String> lineCommentMatcherMap){

       return  new SingleLineCommentScript();
    }

    @Override
    public boolean isVariable() {
        return false;
    }
}




