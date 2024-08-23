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
package org.hawk.lang.object;

import java.util.HashMap;
import java.util.Map;

import org.hawk.util.HawkUtil;

/**
 *
 * @author manoranjan
 */
public class AbstractStructRequestFinder implements IStructRequestFinder {

    @Override
    public String find(Object mapObj, String key) throws Exception {


        Map map = findMap(mapObj);

        return HawkUtil.convertHawkStringToJavaString((String) map.get(key));


    }

    @Override
    public Map<String, String> findMap(Object mapObj) throws Exception {
        if(mapObj == null){
            return new HashMap<>();
        }
        if ( !(mapObj instanceof Map)) {

            throw new Exception("Could not find map in the structure");
        }
        Map map = (Map<String, String>) mapObj;

        return map;


    }
}
