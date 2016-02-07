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

package org.commons.ds.clubber;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author manosahu
 */
public class ClubberFactory {


    private static final ClubberFactory theInstance = new ClubberFactory();

    private final Map<String, Boolean> clubberCache = new HashMap<String, Boolean>();

    public static ClubberFactory getInstance() {
        return theInstance;
    }

    /**
     * This checks if the element is operand
     *
     * @param element
     * @return returns true if the element is
     */

    public boolean isClubber(String element) {

        if (clubberCache.containsKey(element)) {
            return clubberCache.get(element);
        }
        
        boolean result = element.equals("(") || element.equals(")");
              

        clubberCache.put(element, result);
        return result;
    }
}

