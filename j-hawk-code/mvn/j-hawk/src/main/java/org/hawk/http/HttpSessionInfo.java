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
package org.hawk.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.Cookie;
import org.hawk.html.HTMLUtil;
import org.hawk.lang.object.StructureDefnScript;
import org.hawk.lang.object.StructureScript;

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

    public String getRequestTokenValue(){

        HttpResponse httpResponse = (HttpResponse)this.getOtherData();
        String response = httpResponse.getResponse();
        return HTMLUtil.findSessionToken(response);
    }
    @Override
    public String toString() {
        return "HttpSessionInfo{" + "actionName=" + actionName + " ,jsessionId=" + jsessionId + " ,otherData=" + otherData + "}";
    }
}
