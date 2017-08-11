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
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import static org.hawk.constant.HawkConstant.ACTION_NAME;
import static org.hawk.constant.HawkConstant.HTTP_METHOD;
import static org.hawk.constant.HawkConstant.OUT;
import static org.hawk.constant.HawkConstant.TARGET_URL;

import org.hawk.lang.object.StructureScript;
import org.hawk.util.HawkUtil;
import org.commons.string.StringUtil;
/**
 *
 * @author manoranjan
 */
public class HttpRequest {

    private String targetURL;
    private RequestTypeEnum requestTypeEnum;
    private Map<String, String> request = new HashMap<>();
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> auth = new HashMap<>();
    private Map<String, String> body = new HashMap<>();
    private HttpRequestFinder httpRequestFinder = new HttpRequestFinder();
    private Map hawkMap = new HashMap();

    public Map getHawkMap() {
        return hawkMap;
    }

    public void setHawkMap(Map hawkMap) {
        this.hawkMap = hawkMap;
    }

    public Map<String, String> getRequest() {
        return request;
    }

    public void setRequest(Map<String, String> request) {
        this.request = request;
    }

    public Map<String, String> getAuth() {
        return auth;
    }

    public void setAuth(Map<String, String> auth) {
        this.auth = auth;
    }
    
    public static enum RequestTypeEnum {

        GET, POST;
    }

    public RequestTypeEnum getRequestTypeEnum() {
        return requestTypeEnum;
    }

    public void setRequestTypeEnum(RequestTypeEnum requestTypeEnum) {
        this.requestTypeEnum = requestTypeEnum;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }
    public HttpRequest(StructureScript structureScript) throws Exception{
        
    }
    public HttpRequest(Map hawkMap) throws Exception {
        this.hawkMap = hawkMap;
        this.request = this.httpRequestFinder.findHttpRequest(hawkMap);
        this.body = this.httpRequestFinder.findHttpBody(hawkMap);
        this.header = this.httpRequestFinder.findHttpHeader(hawkMap);
        this.auth = this.httpRequestFinder.findHttpAuth(hawkMap);
        this.targetURL = this.httpRequestFinder.findTargetURL(hawkMap);
    }

    public String findTargetURL() throws Exception {

        if (StringUtil.isNullOrEmpty(this.getTargetURL())) {
            this.setTargetURL(this.httpRequestFinder.findTargetURL(this.getHawkMap()));
        }
        return this.getTargetURL();
    }

    public String findActionName() throws Exception {

        return this.httpRequestFinder.findActionName(this.getHawkMap());
    }

    public boolean shouldUploadFile() throws Exception {

        return this.httpRequestFinder.shouldUploadFile(this.getHawkMap());
    }

    public boolean shouldMultiPartPost() throws Exception {

        return this.httpRequestFinder.shouldMultiPartPost(this.getHawkMap());
    }

    public String findFileName() throws Exception {

        return this.httpRequestFinder.findFileName(this.getHawkMap());
    }

    public HttpRequestFinder getHttpRequestFinder() {
        return httpRequestFinder;
    }

    public void setHttpRequestFinder(HttpRequestFinder httpRequestFinder) {
        this.httpRequestFinder = httpRequestFinder;
    }
    
   

    public boolean isGet() throws Exception {

        return this.getHttpRequestFinder().isGet(this.getHawkMap());
    }

    public boolean isPost() throws Exception {

        return this.getHttpRequestFinder().isPost(this.getHawkMap());
    }

    public String getPostParams() {
        
        return this.getParamsInternal(this.getBody());
    }
    private static final Set<String> KEYWORDS = new TreeSet<>();
    
    static {
        KEYWORDS.add(TARGET_URL);
        KEYWORDS.add(HTTP_METHOD);
        KEYWORDS.add(ACTION_NAME);
        KEYWORDS.add(OUT);
    }
    private Map<String,String> filterKeywords(Map<String,String> map){
        Map<String,String> result = new HashMap<>();
        
        map.entrySet().stream().filter((entry) -> !(KEYWORDS.contains(entry.getKey()) ));
        
        return result;
    }
    public Map<String,String> getGETParamsMap(){
        return this.filterKeywords(this.getRequest());
    }
    public String getGetParams() {
       
       Map<String,String> map = this.getGETParamsMap();
       return this.getParamsInternal(map);
    }

    private String getParamsInternal(Map<String, String> map) {
        String result = "";
        if (map != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (Entry<String, String> entry : map.entrySet()) {

                sb.append(entry.getKey()).append("=")
                        .append(HawkUtil.convertHawkStringToJavaString(entry.getValue()))
                        .append((i != this.getBody().size() ? "&" : ""));

                i++;
            }
            result = sb.toString();
        }
        return result;
    }

    
}
