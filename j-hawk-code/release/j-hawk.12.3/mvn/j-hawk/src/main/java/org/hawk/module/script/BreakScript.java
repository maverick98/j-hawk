

package org.hawk.module.script;


import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 * <p>This breaks out a for loop in hawk script.
 * e.g.<br>
 * 
 * 
 * for(var i = 1 ; i <= 100 ; i = i +1)<br>
 * {<br>
 *          if ( i == 50 )<br>
 *              {<br>
 *                  echo "Stop here"<br>
 *                  break<br>
 *              }<br>
 * 
 * }<br>
 * 
 * @version 1.0 1 May, 2010
 * @author msahu
 * @see IfScript
 */
public class BreakScript extends SingleLineScript{

    /**
     * RegEx Pattern to parse break script
     */
    private  static final Pattern BREAK_PATTERN=Pattern.compile("\\s*"+breakStatement+"\\s*");


    /**
     * Default CTOR
     */
    public BreakScript(){

    }
    

    /**
     * Returns the break script pattern
     * @return returns the break script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return BREAK_PATTERN;
    }
    /**
     * There is nothing to be done except
     * returning <tt>true</tt> as <tt>break</tt> does not have anything to execute.
     * @return
     * @throws org.hawk.exception.HawkException
     */
    @Override
    public IScript execute() throws HawkException {
        
        return LocalVarDeclScript.createDummyScript();
    }

    /**
     * This creates the BreakScript from the break matcher map.
     * @param lineBreakMatcherMap
     * @return
     * @see CreateScript
     */
    @CreateScript
    public static BreakScript createScript(Map<Integer,String> lineBreakMatcherMap){

        if(lineBreakMatcherMap == null){
            return null;
        }
        BreakScript breakScript = new BreakScript();
        return breakScript;
    }
     @Override
    public boolean isVariable() {
        return false;
    }
}




