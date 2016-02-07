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

import org.apache.log4j.Logger;
import org.commons.string.StringUtil;

import org.common.di.ScanMe;

/**
 *
 * @author user
 */
@ScanMe(true)
public class StringElement extends Element{
     private static final Logger logger = Logger.getLogger(StringElement.class.getName());
     public StringElement(){
         
     }
     public StringElement(final String element, final int pos) {
        super(element, pos);
    }

    public static StringElement create(final String data, final int pos) {
        StringElement stringElement = new StringElement(data, pos);
      //  stringElement.setIsVariable(false);
        return stringElement;
    }
    
     @Override
     public  IElement parse(final String input, final int pos) {
        StringElement result = null;
        String operand = null;
        operand = StringUtil.parseDelimeterData(input, '\"', '\"');
        
        if (operand != null) {
            result =  create(operand, pos);
        }
        return result;

    }
      @Override
    public int getShiftLength() {
        return this.getElement().length() +2;
    }

    @Override
    public String getHorizontalParserSequence() {
        return "4.2.1";
    }
}
