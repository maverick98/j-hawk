package org.hawk.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import org.hawk.logger.HawkLogger;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hawk.html.HTMLUtil;
import org.hawk.module.lib.HttpModule;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;
import org.hawk.util.HawkUtil;
import org.hawk.util.HttpUtil;

/**
 * An utility to communicate with the targetURL.
 * Apart from sending post requests , this can also also upload file too.
 * @version 1.0 12 Jul, 2010
 * @author msahu
 * @see HttpModule
 */
public class AbstractHttpExecutor implements IHttpExecutor {

    protected static HttpSessionLocal<HttpSessionInfo> httpSessionLocal = new HttpSessionLocal<HttpSessionInfo>();

    /**
     * This stores http session information on the http session local
     * @see HttpSessionInfo
     * @see HttpSessionLocal
     * @param conn
     * @param invalidateSession
     * @return
     */
    @Override
    public boolean setHttpSessionInfo(URLConnection conn, boolean invalidateSession) {

        String jsessionId = CookieManager.fetchJSessionID(conn);
        if (jsessionId != null && !invalidateSession) {
            HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
            httpSessionInfo.setJsessionId(jsessionId);
            httpSessionLocal.set(httpSessionInfo);
            return true;
        }
        return false;
    }

    public boolean setHttpSessionInfo(String responseHTML){
        boolean result = false;
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo != null) {
            httpSessionInfo.setOtherData(HTMLUtil.findSessionToken(responseHTML));
            httpSessionLocal.set(httpSessionInfo);
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    /**
     * Implementors should use this method to set their custom data.
     * @param otherData input data to be stored on http session
     * @return returns true on success or false
     */
    public static boolean setOtherData(Object otherData) {

        boolean status = false;
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();

        if (httpSessionInfo != null) {
            httpSessionInfo.setOtherData(otherData);
            status = true;
        } else {
            status = false;
        }


        return status;
    }

    /**
     * This hits the target URL with the post data using the JSESSIONID.
     * @param targetURL target url to hit
     * @param data post data
     * @param jsessionid used for standard j2ee session management.
     * @return JSESSIONID , essentially during login page of a website.otherwise returns the same.
     * @throws Exception
     */
    @Override
    public String executePost(String targetURL, String postData, boolean invalidateSession) throws Exception {

        if (targetURL == null || targetURL.isEmpty() || postData == null ) {
            throw new Exception("invalid arguments...");
        }
        URL url;
        HttpURLConnection connection = null;
        String response = null;
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

        try {
            connection = HttpConnectionFactory.createConnection(targetURL);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if (jsessionIdValue != null && !jsessionIdValue.isEmpty()) {
                String jsessionId = HttpUtil.getJSesssionID();
                connection.setRequestProperty("Cookie", jsessionId+"=" + jsessionIdValue);
            }
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(postData);
            dos.flush();
            dos.close();
            response = HttpUtil.fetchAndSaveResult(connection,httpSessionLocal.get());
            setHttpSessionInfo(connection, invalidateSession);
        } catch (Exception e) {
            System.out.println("error while hitting {"+targetURL+"} with postdata {"+postData+"}");
            logger.severe("error while hitting {"+targetURL+"} with postdata {"+postData+"}");
            if (ScriptUsage.getInstance().getBuildMode() == BuildModeEnum.DEBUG) {
                HawkUtil.writeFile("./result-" + httpSessionLocal.get().getActionName() + ".html", e.toString());
            }
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


        return response;
    }

    
    /**
     * This executes multipart post request.
     * @param targetURL input url to which data is to be sent.
     * @param map http post data
     * @return returns the response after hitting urlString with input http post data.
     */
    @Override
    public String executeMultiPartPOST(String targetURL, Map<String, String> map) {
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
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (Entry<String, String> entry : map.entrySet()) {
                count = count + 1;
                sb.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\";" + "").append(lineEnd).append("");
                sb.append(lineEnd);
                sb.append(HawkUtil.convertHawkStringToJavaString(entry.getValue()));
                sb.append(lineEnd);
                if (count < map.size()) {
                    sb.append(twoHyphens).append(boundary).append(lineEnd);
                }
            }
            sb.append(twoHyphens).append(boundary).append(twoHyphens).append(lineEnd);

            dos.writeBytes(sb.toString());
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            logger.severe(ex.toString());
        } catch (IOException ioe) {
            logger.severe(ioe.toString());
        }

        return HttpUtil.fetchAndSaveResult(conn,httpSessionLocal.get());
    }

    /**
     * This uploads a file to the target URL.
     * @param targetURL target URL to hit
     * @param exsistingFileName name of the file to upload
     * @param jsessionid JSESSIONID to use
     * @return returns true on success otherwise false
     * @throws Exception
     */
    @Override
    public String uploadFile(String targetURL, String exsistingFileName)
            throws Exception {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String jsessionId = httpSessionLocal.get().getJsessionId();
        try {
            File file = new File(exsistingFileName);
            FileInputStream fileInputStream = new FileInputStream(file);
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
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=uploadFile;"
                    + " filename=" + file.getName() + "" + lineEnd + "  ");
            dos.writeBytes(lineEnd);
            dos.writeBytes("Content-Type: text/csv" + lineEnd);
            dos.writeBytes(lineEnd);
            String data = HawkUtil.readFile(exsistingFileName);
            dos.writeBytes(data);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            logger.severe(ex.toString());
        } catch (IOException ioe) {
            logger.severe(ioe.toString());
        }
        return HttpUtil.fetchAndSaveResult(conn,httpSessionLocal.get());
    }
    private static final HawkLogger logger = HawkLogger.getLogger(HttpUtil.class.getName());

    @Override
    public String executeGetRequest(String targetURL, boolean invalidateSession) throws Exception {

        if (targetURL == null || targetURL.isEmpty()) {
            throw new Exception("invalid arguments...");
        }
        String response = null;

        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo == null) {
            httpSessionInfo = new HttpSessionInfo(Thread.currentThread().getName());
            httpSessionLocal.set(httpSessionInfo);
        }

        HttpClient client = new HttpClient();
        // Create a method instance.
        HttpMethod method = new GetMethod(targetURL);

        for (Cookie cookie : httpSessionInfo.getCookies()) {
            client.getState().addCookie(cookie);
        }


        method.setRequestHeader("User-Agent", "MSIE 5.5");
        method.setFollowRedirects(true);
         try {
            client.executeMethod(method);
            int statusCode = client.executeMethod(method);
            System.out.println("status code:" + statusCode);

            byte[] responseBody = method.getResponseBody();
            response = new String(responseBody);

            if (invalidateSession) {
                httpSessionLocal.set(null);
            } else {
                loadSessionInfo(client, httpSessionInfo);
            }

        } catch (Exception e) {
            System.out.println("error while hitting {"+targetURL+"}");
            logger.severe("error while hitting {"+targetURL+"}" );
            if (ScriptUsage.getInstance().getBuildMode() == BuildModeEnum.DEBUG) {
                HawkUtil.writeFile("./result-" + httpSessionLocal.get().getActionName() + ".html", e.toString());
            }
        } finally {
            // Release the connection.
            method.releaseConnection();
        }

        return response;
    }

    public boolean loadSessionInfo(HttpClient client, HttpSessionInfo httpSessionInfo) {
        return CookieManager.loadCookies(client, httpSessionInfo);
    }
}
