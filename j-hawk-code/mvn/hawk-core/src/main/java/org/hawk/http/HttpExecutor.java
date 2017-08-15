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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.commons.file.FileUtil;
import static org.hawk.constant.HawkConstant.ENCODING;
import org.hawk.data.perf.PerfDataProcessor;
import org.common.di.AppContainer;


import org.hawk.lang.object.StructureScript;
import org.hawk.logger.HawkLogger;
//import org.hawk.module.core.HttpModule;
import org.hawk.util.HawkUtil;
import org.hawk.util.HttpUtil;
import org.commons.resource.ResourceUtil;
import org.commons.string.StringUtil;
/**
 * An utility to communicate with the targetURL.
 * Apart from sending post requests , this can also also upload file too.
 * @version 1.0 12 Jul, 2010
 * @author msahu
 * @see HttpModule
 */
public class HttpExecutor implements IHttpExecutor {

    private static final HawkLogger logger = HawkLogger.getLogger(HttpUtil.class.getName());
    protected static final HttpSessionLocal<HttpSessionInfo> httpSessionLocal = new HttpSessionLocal<>();
    private String httpModuleName;
    private StructureScript structureScript;
    private HttpRequest httpRequest;

    public String getHttpModuleName() {
        return httpModuleName;
    }

    public void setHttpModuleName(String httpModuleName) {
        this.httpModuleName = httpModuleName;
    }

    

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public StructureScript getStructureScript() {
        return structureScript;
    }

    public void setStructureScript(StructureScript structureScript) {
        this.structureScript = structureScript;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpExecutor(String httpModuleName, StructureScript structureScript) throws Exception {
        this.httpModuleName = httpModuleName;
        this.structureScript = structureScript;
        this.httpRequest = new HttpRequest(this.structureScript.toJavaMap());

    }

    
    private boolean setHttpSessionInfo(URLConnection conn, boolean invalidateSession) {

        String jsessionId = CookieManager.fetchJSessionID(conn);
        if (jsessionId != null && !invalidateSession) {
            HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
            httpSessionInfo.setJsessionId(jsessionId);
            httpSessionLocal.set(httpSessionInfo);
            return true;
        }
        return false;
    }

    private boolean setHttpSessionInfo(HttpResponse httpResponse) {
        boolean result;
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo != null) {
            //httpSessionInfo.setOtherData(HTMLUtil.findSessionToken(responseHTML));
            httpSessionInfo.setOtherData(httpResponse);
            httpSessionLocal.set(httpSessionInfo);
            result = true;
        } else {
            result = false;
        }
        return result;
    }



    


    protected HttpResponse executePostRequest() throws Exception {
        this.addRequestTokenToGET();
        this.addRequestTokenToPOST();
        HttpResponse httpResponse;
        String targetURL = this.getHttpRequest().findTargetURL();
        boolean invalidateSession = this.getStructureScript().getControlRequest().isInvalidateSession();
        boolean shouldDump = this.getStructureScript().getControlRequest().isDump();
        String postData = this.getHttpRequest().getPostParams();

        HttpURLConnection connection = null;

        String jsessionIdValue = null;
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo == null) {
            httpSessionInfo = new HttpSessionInfo(Thread.currentThread().getName());
            httpSessionLocal.set(httpSessionInfo);

        } else {
            if (!invalidateSession) {
                jsessionIdValue = httpSessionInfo.getJsessionId();
            } else {
                jsessionIdValue = null;
                httpSessionInfo.setJsessionId(null);
                httpSessionLocal.set(httpSessionInfo);
            }
        }
        DataOutputStream dos = null;
        try {
            connection = HttpConnectionFactory.createConnection(targetURL);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes(Charset.forName(ENCODING)).length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (jsessionIdValue != null && !jsessionIdValue.isEmpty()) {
                String jsessionId = HttpUtil.getJSesssionID();
                connection.setRequestProperty("Cookie", jsessionId + "=" + jsessionIdValue);
            }
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            setHeader(connection);
            dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(postData);
            dos.flush();
           
            httpResponse = HttpUtil.fetchAndSaveResult(connection, httpSessionLocal.get());
            setHttpSessionInfo(connection, invalidateSession);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error while hitting {" + targetURL + "} with postdata {" + postData + "}");
            logger.error("error while hitting {" + targetURL + "} with postdata {" + postData + "}");
            if (shouldDump) {
                FileUtil.writeFile("./result-" + httpSessionLocal.get().getActionName() + ".html", e.toString());
            }
            return null;
        } finally {
            ResourceUtil.close(dos);
            if (connection != null) {
                connection.disconnect();
            }
        }

         this.setHttpSessionInfo(httpResponse);    
        return httpResponse;
    }

    
    private HttpResponse executeMultiPartPOSTRequestWithinSession() throws Exception {

        this.addRequestTokenToGET();
        HttpResponse httpResponse = this.executeMultiPartPOSTRequest();
        this.setHttpSessionInfo(httpResponse);
        return httpResponse;
    }

    private HttpResponse executeMultiPartPOSTRequest() throws Exception {

        String targetURL = this.getHttpRequest().findTargetURL();
        //Map<String, String> headerMap = this.getHttpRequest().getHeader();
        Map<String, String> postDataMap = this.getHttpRequest().getBody();
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String jsessionId = httpSessionLocal.get() != null ? httpSessionLocal.get().getJsessionId() : null;
        try {

            conn = HttpConnectionFactory.createConnection(targetURL);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            if (jsessionId != null && !jsessionId.isEmpty()) {
                conn.setRequestProperty("Cookie", "JSESSIONID=" + jsessionId);
            }
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            setHeader(conn);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (Entry<String, String> entry : postDataMap.entrySet()) {
                count = count + 1;
                sb.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\";" + "").append(lineEnd).append("");
                sb.append(lineEnd);
                sb.append(HawkUtil.convertHawkStringToJavaString(entry.getValue()));
                sb.append(lineEnd);
                if (count < postDataMap.size()) {
                    sb.append(twoHyphens).append(boundary).append(lineEnd);
                }
            }
            sb.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);

            dos.writeBytes(sb.toString());
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            logger.error(ex.toString());
        } catch (IOException ioe) {
            logger.error(ioe.toString());
        }finally{
            ResourceUtil.close(dos);
        }

        return HttpUtil.fetchAndSaveResult(conn, httpSessionLocal.get());
    }

    
    private HttpResponse uploadFileWithinSession() throws Exception {

        this.addRequestTokenToGET();

        HttpResponse httpResponse = this.uploadFile();
        this.setHttpSessionInfo(httpResponse);
        return httpResponse;

    }

    private HttpResponse uploadFile() throws Exception {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String jsessionId = httpSessionLocal.get().getJsessionId();
        String exsistingFileName = this.getHttpRequest().findFileName();
        FileInputStream fileInputStream = null;
        try {
            File file = new File(exsistingFileName);
            fileInputStream = new FileInputStream(file);
            conn = HttpConnectionFactory.createConnection(this.getHttpRequest().findTargetURL());
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            if (jsessionId != null && !jsessionId.isEmpty()) {
                conn.setRequestProperty("Cookie", "JSESSIONID=" + jsessionId);
            }
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            setHeader(conn);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=uploadFile;"
                    + " filename=" + file.getName() + "" + lineEnd + "  ");
            dos.writeBytes(lineEnd);
            dos.writeBytes("Content-Type: text/csv" + lineEnd);
            dos.writeBytes(lineEnd);
            String data = FileUtil.readFile(exsistingFileName);
            dos.writeBytes(data);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            
            dos.flush();
            
        } catch (MalformedURLException ex) {
            logger.error(ex.toString());
        } catch (IOException ioe) {
            logger.error(ioe.toString());
        }finally{
            ResourceUtil.close(dos,fileInputStream);
        }
        return HttpUtil.fetchAndSaveResult(conn, httpSessionLocal.get());
    }

   
    protected HttpResponse executeGetRequest() throws Exception {
        this.addRequestTokenToGET();
        HttpResponse httpResponse = this.executeGetRequestInternal();
        this.setHttpSessionInfo(httpResponse);
        return httpResponse;
    }
   
    private HttpResponse executeGetRequestInternal() throws Exception {

        HttpResponse httpResponse = new HttpResponse();

        String response;

        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo == null) {
            httpSessionInfo = new HttpSessionInfo(Thread.currentThread().getName());
            httpSessionLocal.set(httpSessionInfo);
        }
        String actionName = this.getHttpRequest().findActionName();
        httpSessionInfo.setActionName(actionName);

        String targetURL = this.getHttpRequest().findTargetURL();
        HttpClient client = new HttpClient();
        boolean shouldDump = this.getStructureScript().getControlRequest().isDump();
        GetMethod  method = new GetMethod(targetURL);

        for (Cookie cookie : httpSessionInfo.getCookies()) {
            client.getState().addCookie(cookie);
        }
       // Map<String,String> getMap = this.getHttpRequest().getGETParamsMap();
        method.setQueryString(this.getHttpRequest().getGetParams());
       // method.setRequestHeader("User-Agent", "MSIE 5.5");
        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");
        //Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0
        setHeader(method);
        method.setFollowRedirects(true);
        boolean invalidateSession = this.getStructureScript().getControlRequest().isInvalidateSession();
        try {
            ///client.executeMethod(method);
            int statusCode = client.executeMethod(method);
            System.out.println("status code:" + statusCode);

            byte[] responseBody = method.getResponseBody();
            response = new String(responseBody,Charset.forName(ENCODING));
            httpResponse.setResponse(response);
            httpResponse.setResponseCode(method.getStatusCode());
            httpResponse.setResponseMessage(method.getStatusText());
            Header []  headers  = method.getResponseHeaders();
            httpResponse.setHeaders(headers);
            if(shouldDump){
            HttpUtil.saveResult(httpResponse, httpSessionInfo);
            }
            if (invalidateSession) {
                httpSessionLocal.set(null);
            } else {
                loadSessionInfo(client, httpSessionInfo);
            }

        } catch (IOException e) {
            System.out.println("error while hitting {" + targetURL + "}");
            
            
        } finally {

            method.releaseConnection();
        }

        return httpResponse;
    }
    
    private boolean loadSessionInfo(HttpClient client, HttpSessionInfo httpSessionInfo) {
        return CookieManager.loadCookies(client, httpSessionInfo);
    }

    @Override
    public HttpResponse execute() throws Exception {

        HttpResponse httpResponse = null;
        PerfDataProcessor hawkPerfDataCollector =AppContainer.getInstance().getBean( PerfDataProcessor.class);

        String actionName = this.getHttpRequest().findActionName();
        boolean shouldUploadFile = this.getHttpRequest().shouldUploadFile();
        boolean shouldMultiPartPOST = this.getHttpRequest().shouldMultiPartPost();
        boolean isGet = this.getHttpRequest().isGet();
        boolean isPost = this.getHttpRequest().isPost();
       


        if (shouldUploadFile) {

            try {
                hawkPerfDataCollector.start(this.getHttpModuleName(), actionName);
                httpResponse = this.uploadFileWithinSession();
                hawkPerfDataCollector.end(this.getHttpModuleName(), actionName);
            } catch (Throwable th) {
                hawkPerfDataCollector.endWithFailure(this.getHttpModuleName(), actionName);
                throw new Exception(th);
            }
        } else if (shouldMultiPartPOST) {
            try {
                hawkPerfDataCollector.start(this.getHttpModuleName(), actionName);
                httpResponse = this.executeMultiPartPOSTRequestWithinSession();

                hawkPerfDataCollector.end(this.getHttpModuleName(), actionName);
            } catch (Throwable th) {
                hawkPerfDataCollector.endWithFailure(getHttpModuleName(), actionName);
                throw new Exception(th);
            }

        } else {

            try {
                hawkPerfDataCollector.start(this.getHttpModuleName(), actionName);

                if (isPost) {
                    httpResponse = this.executePostRequest();
                } else if (isGet) {
                    httpResponse = this.executeGetRequest();
                }
                httpResponse.setActionName(actionName);

                hawkPerfDataCollector.end(this.getHttpModuleName(), actionName);
            } catch (Throwable th) {
                hawkPerfDataCollector.endWithFailure(getHttpModuleName(), actionName);
                throw new Exception(th);
            }
        }
        this.getStructureScript().setOut(httpResponse);
        return httpResponse;
    }

    private boolean addRequestTokenToGET() {
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        String targetURL = this.getHttpRequest().getTargetURL();
        if (httpSessionInfo != null) {
            String requestToken = HttpUtil.getRequestToken();
            if (!StringUtil.isNullOrEmpty(requestToken)) {
                String requestTokenValue = httpSessionInfo.getRequestTokenValue();

                if (!StringUtil.isNullOrEmpty(requestTokenValue)) {
                    String postDataSuffix = requestToken + "=" + requestTokenValue;
                    if (targetURL.contains("?")) {
                        targetURL = targetURL + "&" + postDataSuffix;
                    } else {
                        targetURL = targetURL + "?" + postDataSuffix;
                    }

                }
            }
        }
        this.getHttpRequest().setTargetURL(targetURL);
        return true;
    }

    private boolean addRequestTokenToPOST() {
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo != null) {
            String requestToken = HttpUtil.getRequestToken();
            if (!StringUtil.isNullOrEmpty(requestToken)) {
                String requestTokenValue = httpSessionInfo.getRequestTokenValue();
                if (!StringUtil.isNullOrEmpty(requestTokenValue)) {


                    this.getHttpRequest().getBody().put(requestToken, requestTokenValue);


                }
            }
        }
        return true;
    }
    
    protected static abstract class AbstractHttpHeaderVisitor{
        Map<String, String> headerMap;
        public AbstractHttpHeaderVisitor(HttpRequest httpRequest){
            headerMap = httpRequest.getHeader();
        }
        public AbstractHttpHeaderVisitor(Map<String, String> headerMap){
            this.headerMap = headerMap;
        }
        public abstract void onVisit(String key , String value);
        public void visit(){
             
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Entry<String, String> entry : headerMap.entrySet()) {
                    this.onVisit(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    
    private void setHeader(final HttpMethod method) {
       
        new AbstractHttpHeaderVisitor(this.getHttpRequest()) {
            
            @Override
            public void onVisit(String key, String value) {
                 method.setRequestHeader(key, value);
            }
        }.visit();
        
    }
    
    private void setHeader(final HttpURLConnection connection) {
            
        new AbstractHttpHeaderVisitor(this.getHttpRequest()) {
            
            @Override
            public void onVisit(String key, String value) {
                 connection.setRequestProperty(key, value);
            }
        }.visit();
    }
    
    

    @Override
    public void run() {
        try {
            this.execute();
        } catch (Exception ex) {
            logger.error("error occurred", ex);
        }
    }
}
