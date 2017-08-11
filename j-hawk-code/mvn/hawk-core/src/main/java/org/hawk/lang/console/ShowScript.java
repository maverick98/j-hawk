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
package org.hawk.lang.console;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.common.di.AppContainer;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.show;
import org.hawk.lang.grammar.SingleLineGrammar;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.singleline.pattern.LinePattern;
import org.hawk.logger.HawkLogger;
import org.hawk.output.HawkOutput;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
public class ShowScript extends EchoScript {

    private static final HawkLogger logger = HawkLogger.getLogger(ShowScript.class.getName());

    public static class Show extends SingleLineGrammar {

        @Override
        public String toString() {
            return "Show{" + "parsingSequence=" + this.getParsingSequence() + ", linePattern=" + this.getLinePattern() + '}';
        }

    }

    /**
     * Default CTOR
     */
    public ShowScript() {

    }

    @Override
    public Integer getVerticalParserSequence() {

        return this.getGrammar().getSingleLine().getShow().getParsingSequence();
    }

    @Override
    public Set<LinePattern> getPatterns() {
        /*
         Set<LinePattern> patterns = new TreeSet<LinePattern>();
         LinePattern linePattern = new LinePattern();
         linePattern.setSequence(1);
         linePattern.setPattern(SHOW_PATTERN);
         patterns.add(linePattern);
         return patterns;
         */
        return this.getGrammar().getSingleLine().getShow().getLinePattern();
    }

    @Override
    public IScript execute() throws Exception {
        String showOutput;

        showOutput = this.getMessage();

        HawkOutput hawkoutput = AppContainer.getInstance().getBean(HawkOutput.class);
        hawkoutput.writeEchoOutput(showOutput,false);
        //hawkoutput.writeEchoOutput("\n");
        return LocalVarDeclScript.createDummyBooleanScript();
    }

    /**
     * This returns <tt>ShowScript</tt> from echo matcher map.
     *
     * @param lineShowMatcherMap
     * @return
     */
    @Override
    public ShowScript createScript(Map<Integer, String> lineShowMatcherMap) {

        if (lineShowMatcherMap == null) {
            return null;
        }
        ShowScript showScript = new ShowScript();
        showScript.setMessage(lineShowMatcherMap.get(1));
        return showScript;
    }

    /**
     * The primary echo RegEx pattern to parse show script
     */
    private static final Pattern SHOW_PATTERN = Pattern.compile("\\s*" + show + "\\s*(.*)\\s*");

}
