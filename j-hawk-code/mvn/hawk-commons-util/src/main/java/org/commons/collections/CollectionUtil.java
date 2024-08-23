/**
  * This file is part of impensa.
  *  
  *
  *  
  *    
  *
  */
package org.commons.collections;

import java.util.Collection;

/**
 *
 * @author msahu98
 */
public class CollectionUtil {

    public static boolean isNullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }
}
