

package org.hawk.module.script;


import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 * <p>This echoes message in the console 
 * e.g.<br>
 *
 *
 * for(var i = 1 ; i <= 100 ; i = i +1)<br>
 * {<br>
 *          if ( i == 50 )<br>
 *              {<br>
 *                  <h3>echo "Stop here"<br></h3>
 *                  break<br>
 *              }<br>
 *
 * }<br>
 *
 * @version 1.0 1 May, 2010
 * @author msahu
 * 
 */
public class EchoScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(EchoScript.class.getName());

    /**
     * Default CTOR
     */
    public EchoScript(){
        
    }




    /**
     * list of messages of  <tt>echo</tt> script.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

    /**
     * Echo script pattern.
     * @return returns echo script pattern
     * @see ScriptPattern
     */
    @ScriptPattern
    public static Pattern getPattern() {
        return ECHO_PATTERN;
    }
    @Override
    public IScript execute() throws HawkException {
        System.out.println
                    (
                        this.evaluateLocalVariable
                                                (
                                                    this.message
                                                ).toUI()
                    );
        
        return  LocalVarDeclScript.createDummyScript();
    }

   

    /**
     * This returns <tt>EchoScript</tt> from echo matcher map.
     * @param lineEchoMatcherMap
     * @return
     */
    @CreateScript
    public static EchoScript createScript(Map<Integer,String> lineEchoMatcherMap){

        if(lineEchoMatcherMap == null){
            return null;
        }
        EchoScript echoScript = new EchoScript();
        echoScript.setMessage(lineEchoMatcherMap.get(1).toString());

        
        return echoScript;
    }


   
    
    @Override
    public boolean isVariable() {
        return false;
    }
    
    /**
     * The primary echo RegEx pattern to parse echo script
     */
    private  static final Pattern ECHO_PATTERN=Pattern.compile("\\s*"+echo+"\\s*(.*)\\s*");

   


}




