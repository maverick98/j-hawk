


package org.hawk.module.script;

import org.hawk.exception.HawkException;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.ScriptPattern;
import org.hawk.util.HawkUtil;
import java.util.Map;
import org.hawk.logger.HawkLogger;
import java.util.regex.Pattern;
import static org.hawk.constant.HawkLanguageKeyWord.*;
/**
 *
 * @version 1.0 8 Apr, 2010
 * @author msahu
 */
public class ThinkScript extends SingleLineScript{

    private static final HawkLogger logger = HawkLogger.getLogger(ThinkScript.class.getName());
    
    private  static final Pattern THINK_PATTERN=Pattern.compile("\\s*"+think+"\\s*(\\d*)\\s*");

    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ThinkScript(){
    }

     @ScriptPattern
    public static Pattern getPattern() {
        return THINK_PATTERN;
    }

    @CreateScript
    public static ThinkScript createScript(Map<Integer,String> lineThinkMatcherMap){

        if(lineThinkMatcherMap== null){
            return null;
        }
        ThinkScript thinkScript = new ThinkScript();
        thinkScript.setTime(Integer.parseInt(lineThinkMatcherMap.get(1)));
        return thinkScript;
    }

    @Override
    public IScript execute() throws HawkException {

        logger.info("executing thinktime "+this.time);
        HawkUtil.sleep(this.time);
        
        return LocalVarDeclScript.createDummyScript();
    }
    @Override
    public boolean isVariable() {
        return false;
    }

}




