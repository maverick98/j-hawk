/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 *  
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