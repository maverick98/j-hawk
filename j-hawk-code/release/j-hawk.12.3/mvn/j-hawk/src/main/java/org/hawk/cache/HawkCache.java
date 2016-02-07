
package org.hawk.cache;

import java.util.HashMap;

/**
 * HawkCache to contain key value pair
 * @version 1.0 1 Apr, 2010
 * @author msahu
 */
public class HawkCache extends HashMap{

    public  <T> T get(Object key , Class<T> clazz) {

        Object valueObj = this.get(key);

        return (T)valueObj;

    }

}




