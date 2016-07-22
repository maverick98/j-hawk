package org.hawk.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.Cookie;
import org.hawk.module.script.StructureDefnScript;
import org.hawk.module.script.StructureScript;

/**
 * This is the placeholder for storing http session information.
 * @see CookieManager
 * @author msahu
 */
public class HttpSessionInfo {

    /**
     * Hawk action name
     * @see StructureScript
     * @see StructureDefnScript
     */
    private String actionName;
    /**
     * jsessionid of J2EE server
     * @see CookieManager
     */
    private String jsessionId;
    private Object otherData;
    private List<Cookie> cookies = null;

    public List<Cookie> getCookies() {
        if (cookies == null) {
            cookies = new ArrayList<Cookie>();
        }
        return cookies;
    }

    public HttpSessionInfo(String actionName) {
        this.actionName = actionName;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Object getOtherData() {
        return otherData;
    }

    public void setOtherData(Object otherData) {
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "HttpSessionInfo{" + "actionName=" + actionName + " ,jsessionId=" + jsessionId + " ,otherData=" + otherData + "}";
    }
}