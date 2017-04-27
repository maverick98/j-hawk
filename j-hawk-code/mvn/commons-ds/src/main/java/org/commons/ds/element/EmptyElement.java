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
