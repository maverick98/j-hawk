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
 * 
 *
 * 
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.commons.exception;

import java.util.Collection;

/**
 *
 * @author manosahu
 */
public class CommonUtil {
  public static Boolean areAllTrue(Collection<Boolean> bools){
        if(bools == null || bools.isEmpty()){
            return false;
        }
        for(Boolean bool : bools){
            if(!bool){
                return false;
            }
        }
        return true;
    }
  
   public static boolean getBooleanData(double element) throws Exception {
        if (!(element == 1.0 || element == 0.0)) {
            throw new Exception("Can not convert element{" + element + "} to boolean.");
        }

        boolean result;

        result = (element == 1.0);

        return result;
    }
    public static boolean greaterThan(Comparable c1, Comparable c2){
        return c1.compareTo(c2) > 0;
    }
    public static boolean lessThan(Comparable c1, Comparable c2){
        return c1.compareTo(c2) < 0;
    }
    public static boolean isEqual(Comparable c1, Comparable c2){
        return c1.compareTo(c2) == 0;
    }
}
