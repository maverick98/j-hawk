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
package org.hawk.ds.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.commons.ds.element.Element;
import org.commons.ds.element.IElement;
import org.commons.string.ParserException;
import org.commons.string.StringUtil;
import org.hawk.executor.DefaultScriptExecutor;
import org.hawk.lang.function.ExecFunctionScript;
import org.hawk.lang.function.ExecModuleScript;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;

/**
 *
 * @author user
 */
public class ExecElement extends Element {

    /**
     * RegEx to catch exec statements in the expression.
     */
    public static final Pattern EXEC_PATTERN = Pattern.compile("\\{\\s*e?x?e?c?\\s*.*");
    private static final HawkLogger logger = HawkLogger.getLogger(DefaultScriptExecutor.class.getName());

    public ExecElement() {
    }

    public ExecElement(final String element, final int pos) {
        super(element, pos);
    }

    /**
     * This parses the "`exec test ( `exec test("India")`)`"
     *
     * @param input
     * @return returns "exec test ( `exec test("India")`)"
     * @throws Exception
     */
    public static String parseExec(String input) throws Exception, ParserException {
        if (input == null || input.isEmpty()) {
            throw new Exception("invalid");
        }
        input = input.trim();

        return StringUtil.parseDelimeterData(input, '{', '}');
    }

    public static ExecElement create(String input, int pos) throws Exception, ParserException {
        String operand = ExecElement.parseExec(input);
        ExecElement execElement = null;
        if (PatternMatcher.match(new ExecFunctionScript().getPatterns(), operand) != null) {
            execElement = ExecFunctionElement.create(operand, pos);
        } else if (PatternMatcher.match(new ExecModuleScript().getPatterns(), operand) != null) {
            execElement = ExecModuleElement.create(operand, pos);
        }
        return execElement;
    }

    /**
     *
     * @param input
     * @param pos
     * @return
     */
    @Override
    public IElement parse(final String input, final int pos) {
        Matcher execMatcher = EXEC_PATTERN.matcher(input);
        Element execElement = null;
        if (execMatcher.matches()) {
            try {
                execElement = create(input, pos);
            } catch (Exception ex) {
                logger.error("error", ex);
            }
        }
        return execElement;

    }

    @Override
    public int getShiftLength() {
        return this.getElement().length() + 2;
    }

    @Override
    public String getHorizontalParserSequence() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldEvaluate() {
        return true;
    }
}
