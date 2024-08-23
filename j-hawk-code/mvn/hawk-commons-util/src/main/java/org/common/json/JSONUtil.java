/**
 *  
 * left.
 *
 *    
 *  
 *
 *
 */
package org.common.json;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.commons.string.StringUtil;

/**
 *
 * @author reemeeka
 */
public class JSONUtil {

    public static boolean isJson(String json){
        return isJsonObject(json)|| isJsonArray(json);
    }
    public static boolean isJsonObject(String json) {
        boolean result;
        if (StringUtil.isNullOrEmpty(json)) {
            return false;
        }
        try {
            JSONObject rtnJSON = new JSONObject(json.trim());
            result = true;
        } catch (JSONException ex) {
            result = false;

        }
        return result;
    }

    public static JSONObject createJsonObject(String json) {
        JSONObject result;
        if (StringUtil.isNullOrEmpty(json)) {
            return null;
        }
        try {
            result = new JSONObject(json.trim());

        } catch (JSONException ex) {
            result = null;

        }
        return result;
    }

    public static boolean isJsonArray(String json) {
        boolean result;
        if (StringUtil.isNullOrEmpty(json)) {
            return false;
        }
        try {
            JSONArray rtnJSON = new JSONArray(json.trim());
            result = true;
        } catch (JSONException ex) {
            result = false;
        }
        return result;
    }

    public static JSONArray createJsonArray(String json) {
        JSONArray result;
        if (StringUtil.isNullOrEmpty(json)) {
            return null;
        }
        try {
            result = new JSONArray(json.trim());

        } catch (JSONException ex) {
            result = null;
        }
        return result;

    }

}
