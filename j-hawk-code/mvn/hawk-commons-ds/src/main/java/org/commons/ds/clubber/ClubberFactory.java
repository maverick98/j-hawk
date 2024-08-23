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

    private final Map<String, Boolean> clubberCache = new HashMap<>();

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

