/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.common.di.ScanMe;

/**
 *
 * @author user
 */
@ScanMe(true)
public class NumberElement extends Element {

    
    /**
     * RegEx for parsing numbers
     */
    private static final Pattern CONSTANT_NUMBER_PATTERN = Pattern.compile("(\\d+\\.{0,1}\\d*).*");

    public NumberElement(){
        
    }
    public NumberElement(final String element, final int pos) {
        super(element, pos);
    }

    public static NumberElement create(final String data, final int pos) {
        NumberElement numberElement = new NumberElement(data, pos);
       // numberElement.setIsVariable(false);
        return numberElement;
    }

   
    /**
     *
     * @param input
     * @param pos
     * @return
     */
    @Override
    public  IElement parse(final String input, final int pos) {
        Matcher numberMatcher = CONSTANT_NUMBER_PATTERN.matcher(input);
        Element numberElement = null;
        if (numberMatcher.matches()) {
            String number = numberMatcher.group(1);
            numberElement = create(number, pos);
        }
        return numberElement;
    }
     @Override
    public int getShiftLength() {
        return this.getElement().length();
    }

    @Override
    public String getHorizontalParserSequence() {
        return "4.2.2.3";
    }
}