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
