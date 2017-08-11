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
package org.hawk.lang.comment;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.function.IFunctionScriptCache;
import org.hawk.executor.cache.multiline.structure.IStructureDefinitionScriptCache;
import org.hawk.input.HawkInput;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.common.di.ScanMe;
import org.hawk.executor.cache.multiline.function.FunctionScriptCache;
import org.hawk.executor.cache.multiline.structure.StructureDefinitionScriptCache;

/**
 *
 * @author msahu
 */
@ScanMe(true)

public class MultiLineCommentScript extends MultiLineScript {

    private static final Pattern MULTILINE_COMMENT_START_PATTERN = Pattern.compile("\\s*/\\*\\s*.*");
    private static final HawkLogger logger = HawkLogger.getLogger(MultiLineCommentScript.class.getName());
    /*
     @Override
     public boolean isVariable() {
     return false;
     }
     */

    /**
     * This returns <tt>MultiLineCommentScript</tt> from MultiLineComment
     * matcher map.
     *
     * @param multiLineScript
     * @return
     */
    public int createScript(
            MultiLineScript multiLineScript,
            int i
    ) throws Exception {
        return findScriptEnd(i);
    }

    private static int findScriptEnd(int i) throws Exception {
        Map<Integer, String> scriptMap = AppContainer.getInstance().getBean( HawkInput.class).getScriptMap();
        Map<Integer, String> lineMultiLineCommantMatcherMap = PatternMatcher.match(MULTILINE_COMMENT_START_PATTERN, scriptMap.get(i));
        if (lineMultiLineCommantMatcherMap == null) {
            return -1;
        }
        MultiLineContainer mlc = new MultiLineCommentScript().extractMultiLineContainer(scriptMap, i);
        return mlc.getEnd() + 1;
    }

    public static Map<Integer, Integer> cacheGlobalMultiLineScripts() throws Exception {

        Map<Integer, Integer> globalMultiLineScriptMap = new TreeMap<>();
        HawkInput hawkInput = AppContainer.getInstance().getBean( HawkInput.class);
        IFunctionScriptCache functionScriptCache = AppContainer.getInstance().getBean(FunctionScriptCache.class);
        IStructureDefinitionScriptCache structureDefinitionCache = AppContainer.getInstance().getBean(StructureDefinitionScriptCache.class);
        Map<Integer, String> scriptMap = hawkInput.getScriptMap();
        for (int i = 0; i < scriptMap.size(); i++) {

            if (functionScriptCache.isInsideMultiLineScript(i) || structureDefinitionCache.isInsideMultiLineScript(i)) {
                continue;
            }
            int scriptEnd = findScriptEnd(i);
            if (scriptEnd != -1) {
                globalMultiLineScriptMap.put(i, scriptEnd);
            }
        }

        return globalMultiLineScriptMap;
    }

    @Override
    public String getStartDelim() {

        return "/\\*";
    }

    @Override
    public String getEndDelim() {

        return "\\*/";
    }

    @Override
    public LinePattern getStartPattern() {
        StringBuilder sb = new StringBuilder();
        sb.append("\\s*");
        sb.append(this.getStartDelim());
        sb.append("\\s*.*");
        LinePattern linePattern = new LinePattern();
        linePattern.setSequence(1);
        linePattern.setPattern(Pattern.compile("\\s*/\\*\\s*.*"));
        return linePattern;
    }

    @Override
    public LinePattern getEndPattern() {
        StringBuilder sb = new StringBuilder();
        sb.append("\\s*.*");
        sb.append(this.getStartDelim());
        sb.append("\\s*");
        LinePattern linePattern = new LinePattern();
        linePattern.setSequence(1);
        linePattern.setPattern(Pattern.compile("\\s*.*\\*/\\s*"));
        return linePattern;
    }

}
