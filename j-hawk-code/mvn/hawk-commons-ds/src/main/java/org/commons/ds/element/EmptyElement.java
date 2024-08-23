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

import org.common.di.ScanMe;



/**
 *
 * @author user
 */
@ScanMe(true)
public class EmptyElement extends Element {

    @Override
    public int getShiftLength() {
        return 1;
    }

    @Override
    public IElement parse(String eleStr, int pos) {
        IElement result = null;
        if (eleStr.trim().equals("")) {
            result = new EmptyElement();
        }
        return result;
    }
     @Override
    public boolean shouldAdd() {
        return false;
    }

    @Override
    public String getHorizontalParserSequence() {
        return "3";
    }
}
