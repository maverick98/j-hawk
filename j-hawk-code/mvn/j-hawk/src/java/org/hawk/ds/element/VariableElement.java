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
package org.hawk.ds.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.commons.ds.element.Element;
import org.commons.ds.element.IElement;
import org.common.di.ScanMe;

/**
 *
 * @author user
 */
@ScanMe(true)
public class VariableElement extends Element {

    /**
     * RegEx to parse variable in the expression.
     */
    public static final Pattern VARIABLE_EXISTENCE_PATTERN = Pattern.compile("([a-z|A-Z]{1,}[a-z|A-Z|\\.|\\d]*)\\s*.*");

    public VariableElement() {
    }

    public VariableElement(final String element, final int pos) {
        super(element, pos);
    }

    public static VariableElement create(final String operand, final int pos) {
        VariableElement operandElement = new VariableElement(operand, pos);
      //  operandElement.setIsVariable(true);
        return operandElement;
    }

    @Override
    public  IElement parse(final String input, final int pos) {
        Matcher operandMatcher = VARIABLE_EXISTENCE_PATTERN.matcher(input);
        Element operandElement = null;
        if (operandMatcher.matches()) {
            String operand = operandMatcher.group(1);
            operandElement = create(operand, pos);
        }
        return operandElement;
    }

    @Override
    public int getShiftLength() {
        return this.getElement().length();
    }

    @Override
    public String getHorizontalParserSequence() {
        return "4.2.2.1";
    }
    
     @Override
    public boolean shouldEvaluate(){
        return true;
    }
}
