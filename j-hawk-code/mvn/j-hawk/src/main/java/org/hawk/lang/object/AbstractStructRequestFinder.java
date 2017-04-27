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
            return new HashMap<String,String>();
        }
        if ( !(mapObj instanceof Map)) {

            throw new Exception("Could not find map in the structure");
        }
        Map map = (Map<String, String>) mapObj;

        return map;


    }
}
