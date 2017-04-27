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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manosahu
 */
public class ElementUtil {

    /**
     * This returns the input list of elements . This does not modify the input
     * list.
     *
     * @param list list of elements to be reversed.
     * @return returns the reverse of the list.
     */
    public static List<IElement> reverse(final List<IElement> list) {
        List<IElement> result = new ArrayList<IElement>();

        for (int i = list.size() - 1, j = 0; i >= 0; i--, j++) {
            Element element = (Element) list.get(i);

            /*
            if (element.isClubber()) {

                ((ClubberElement) element).flip();
                result.add(j, element);
            } else {
                result.add(j, list.get(i));
            }
                    */
            element.onReverseVisit(result, j);
        }

        return result;
    }
}
