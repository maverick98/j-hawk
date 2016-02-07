
package org.hawk.module.script;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.hawk.exception.HawkException;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.CreateScript;
import org.hawk.module.annotation.MultiLinePattern;
import org.hawk.pattern.PatternMatcher;

/**
 *
 * @author msahu
 */
@MultiLinePattern(startDelim="/\\*",endDelim="\\*/",startPattern="\\s*/\\*\\s*.*",endPattern="\\s*.*\\*/\\s*")
public class MultiLineCommentScript extends MultiLineScript{

    private static final Pattern MULTILINE_COMMENT_START_PATTERN =Pattern.compile("\\s*/\\*\\s*.*");
    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineCommentScript.class.getName());

    @Override
    public boolean isVariable() {
        return false;
    }
    /**
     * This returns <tt>MultiLineCommentScript</tt> from MultiLineComment matcher map.
     * @param multiLineScript
     * @return
     */
    @CreateScript
    public static int createScript
            (
                MultiLineScript multiLineScript,
                int i
            ) throws HawkException{
            return findScriptEnd(i);
    }

    private static int findScriptEnd(int i) throws HawkException{
        Map<Integer,String> scriptMap = ScriptInterpreter.getInstance().getScriptMap();
        Map<Integer,String> lineMultiLineCommantMatcherMap = PatternMatcher.match(MULTILINE_COMMENT_START_PATTERN, scriptMap.get(i));
        if (lineMultiLineCommantMatcherMap == null) {
            return -1;
        }
        MultiLineContainer mlc = extractMultiLineContainer(MultiLineCommentScript.class,scriptMap, i);
        return mlc.getEnd()+1;
    }
    public static Map<Integer,Integer> cacheGlobalMultiLineScripts() throws HawkException{

        Map<Integer,Integer> globalMultiLineScriptMap = new TreeMap<Integer,Integer>();
        ScriptInterpreter si = ScriptInterpreter.getInstance();
        Map<Integer,String> scriptMap = si.getScriptMap();
        for(int i=0;i<scriptMap.size();i++){
            
            if(si.isInsideFunctionScript(i) || si.isInsideStructureScript(i)){
                continue;
            }
            int scriptEnd = findScriptEnd(i);
            if(scriptEnd!= -1){
                globalMultiLineScriptMap.put(i, scriptEnd);
            }
        }

        return globalMultiLineScriptMap;
    }
}
