/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.cache;

import java.util.HashMap;

/**
 * HawkCache to contain key value pair
 *
 * @version 1.0 1 Apr, 2010
 * @author msahu
 */
public class HawkCache extends HashMap {

    private static final long serialVersionUID = 398820763181265L;

    /**
     *
     * @param <T>
     * @param key
     * @param clazz
     * @return
     */
    public <T> T get(Object key, Class<T> clazz) {

        Object valueObj = this.get(key);

        return (T) valueObj;

    }
}
