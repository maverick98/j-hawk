/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.http;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import static org.hawk.constant.HawkConstant.ACTION_NAME;
import static org.hawk.constant.HawkConstant.FILE_NAME;
import static org.hawk.constant.HawkConstant.HTTP_BODY;
import static org.hawk.constant.HawkConstant.HTTP_GET;
import static org.hawk.constant.HawkConstant.HTTP_HEADER;
import static org.hawk.constant.HawkConstant.HTTP_METHOD;
import static org.hawk.constant.HawkConstant.HTTP_POST;
import static org.hawk.constant.HawkConstant.HTTP_REQUEST;
import static org.hawk.constant.HawkConstant.MULTIPART_POST;
import static org.hawk.constant.HawkConstant.TARGET_URL;
import static org.hawk.constant.HawkConstant.UPLOAD_FILE;
import static org.hawk.constant.HawkConstant.HTTP_AUTH;

import org.hawk.lang.object.AbstractStructRequestFinder;

/**
 *
 * @author Manoranjan Sahu
 */
public class HttpRequestFinder extends AbstractStructRequestFinder {

    private static final Map<Integer, String> priorityMap = new TreeMap<Integer, String>();

    static {

        priorityMap.put(1, HTTP_REQUEST);
        priorityMap.put(2, HTTP_BODY);

    }
    /*
     private String findWithPriority(Map mainMap, String key) throws Exception {

     String data = null;

     for (Map.Entry<Integer, String> entry : priorityMap.entrySet()) {

     data = find(mainMap.get(entry.getValue()), key);
     if (data != null && !data.isEmpty()) {
     break;
     }

     }
     if (data == null) {
     data = (String) mainMap.get(key);
     }
     return HawkUtil.convertHawkStringToJavaString(data);
     }
     */

    public String findTargetURL(Map mainMap) throws Exception {
        String targetURL = find(mainMap.get(HTTP_REQUEST), TARGET_URL);
        if (targetURL == null) {
            targetURL = "";
        }
        return targetURL;
        //return findWithPriority(mainMap, TARGET_URL);
    }
    
    public String findActionName(Map mainMap) throws Exception {
        String actionName = (String) mainMap.get(ACTION_NAME);
        if (actionName == null) {
            actionName = "";
        }
        return actionName;
        // return findWithPriority(mainMap, ACTION_NAME);
    }

    public boolean shouldUploadFile(Map mainMap) throws Exception {

        return "1".equals(find(mainMap.get(HTTP_BODY), UPLOAD_FILE));
    }

    public boolean shouldMultiPartPost(Map mainMap) throws Exception {

        return "1".equals(find(mainMap.get(HTTP_BODY), MULTIPART_POST));
    }

    public String findFileName(Map mainMap) throws Exception {

        return find(mainMap, FILE_NAME);
    }

    public Map<String, String> findHttpBody(Map mainMap) throws Exception {

        return findMap(mainMap.get(HTTP_BODY));
    }

    public Map<String, String> findHttpRequest(Map mainMap) throws Exception {

        return findMap(mainMap.get(HTTP_REQUEST));
    }
    public Map<String, String> findHttpAuth(Map mainMap) throws Exception {
        Map<String, String> authMap = new HashMap<>();
        Map map = (Map) mainMap.get(HTTP_AUTH);

        for (Object t : map.keySet()) {

            if (map.get(t) instanceof Map) {
                Map tmpMap = (Map) (map.get(t));
                String h1Key = null;
                String h1Value = null;
                for (Object key : tmpMap.keySet()) {
                    Object value = tmpMap.get(key);

                    if (value instanceof Map) {
                        Map tmpMap1 = (Map) value;
                        for (Object key1 : tmpMap1.keySet()) {
                            Object value1 = tmpMap1.get(key1);
                            if (h1Key == null) {
                                h1Key = value1.toString();
                            } else {
                                h1Value = value1.toString();
                            }

                        }
                    }
                }
                authMap.put(h1Key, h1Value);
            }
        }
        return authMap;
    }

    public Map<String, String> findHttpHeader(Map mainMap) throws Exception {
        Map<String, String> headerMap = new HashMap<>();
        Map map = (Map) mainMap.get(HTTP_HEADER);

        for (Object t : map.keySet()) {

            if (map.get(t) instanceof Map) {
                Map tmpMap = (Map) (map.get(t));
            String h1Key = null;
            String h1Value = null;
            for (Object key : tmpMap.keySet()) {
                Object value = tmpMap.get(key);
                
                if (value instanceof Map) {
                    Map tmpMap1 = (Map) value;
                    for (Object key1 : tmpMap1.keySet()) {
                        Object value1 = tmpMap1.get(key1);
                        if (h1Key == null) {
                            h1Key = value1.toString();
                        } else {
                            h1Value = value1.toString();
                        }
                        
                    }
                }
            }
            headerMap.put(h1Key, h1Value);
            }
        }
        return headerMap;
    }

    public String findMethod(Map mainMap) throws Exception {
        return find(mainMap.get(HTTP_REQUEST), HTTP_METHOD);
    }

    public boolean isGet(Map mainMap) throws Exception {

        String method = this.findMethod(mainMap);

        return method == null ? false : HTTP_GET.equals(method);
    }

    public boolean isPost(Map mainMap) throws Exception {

        String method = this.findMethod(mainMap);

        return method == null ? true : HTTP_POST.equals(method);
    }
}
