/**
  * This file is part of impensa.
  *  
  *
  * 1) Modify it if you can understand.
  * 2) If you distribute a modified version, you must do it at your own risk.
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
