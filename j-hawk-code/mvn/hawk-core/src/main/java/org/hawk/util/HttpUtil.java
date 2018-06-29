
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
package org.hawk.util;

import org.commons.resource.ResourceUtil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;
import org.commons.file.FileUtil;
import static org.hawk.constant.HawkConstant.ENCODING;
import org.common.di.AppContainer;

import org.hawk.executor.command.HawkCommandParser;
import org.hawk.http.HttpResponse;
import org.hawk.http.HttpSessionInfo;
import org.hawk.logger.HawkLogger;
import org.hawk.module.core.HttpModule;

/**
 * An utility to communicate with the targetURL. Apart from sending post
 * requests , this can also also upload file too.
 *
 * @version 1.0 12 Jul, 2010
 * @author msahu
 * @see HttpModule
 */
public class HttpUtil {

    /**
     * This reads the HttpsURLConnection and saves the response in a html file.
     * This will be mainly used for debugging purpose.
     *
     * @param conn connection from which data is to be read.
     * @return returns the response after saving it in result.html
     */
    public static HttpResponse fetchAndSaveResult(HttpURLConnection conn, HttpSessionInfo httpSessionInfo) {
        HttpResponse httpResponse = null;
        if (conn != null && httpSessionInfo != null) {
            httpResponse = new HttpResponse();
            String response;
            DataInputStream inStream = null;
            BufferedReader bfr = null;

            try {
                inStream = new DataInputStream(conn.getInputStream());
                StringBuilder sb = new StringBuilder();
                bfr = new BufferedReader(new InputStreamReader(inStream, Charset.forName(ENCODING)));
                String line;

                while ((line = bfr.readLine()) != null) {
                    sb.append(line);
                    sb.append('\r');
                }


                FileUtil.writeFile("./result-" + httpSessionInfo.getActionName() + ".html", sb.toString());
                response = sb.toString();
                httpResponse.setResponse(response);
                httpResponse.setResponseCode(conn.getResponseCode());
                httpResponse.setResponseMessage(conn.getResponseMessage());


            } catch (IOException ioex) {
                ioex.printStackTrace();
                
                    if (AppContainer.getInstance().getBean(HawkCommandParser.class).shouldDumpModuleExecution()) {
                        FileUtil.writeFile("./result-" + httpSessionInfo.getActionName() + ".html", ioex.toString());
                    }
               


            } finally {

                ResourceUtil.close(bfr, inStream);
            }
        }

        return httpResponse;
    }
    
    public static boolean saveResult(HttpResponse httpResponse ,HttpSessionInfo httpSessionInfo) throws Exception{
        
        FileUtil.writeFile("./result-" + httpSessionInfo.getActionName() + ".html", httpResponse.getResponse());
        return true;
    }

    public static String getJSesssionID() {
        String defaultJSessionID = "JSESSIONID";
        String jsessionID = System.getProperty("JSESSIONID");
        return (jsessionID == null || jsessionID.isEmpty()) ? defaultJSessionID : jsessionID;
    }

    public static String getRequestToken() {

        return System.getProperty("REQUEST_TOKEN");

    }

    public static boolean isHTTPSURL(String targetURL) {
        return targetURL.startsWith("https");
    }
    private static final HawkLogger logger = HawkLogger.getLogger(HttpUtil.class.getName());

    public static String dump(URL sourceURL){
            
        String response =null;
        DataInputStream inStream = null;
        BufferedReader bfr = null;
        try {
            URLConnection conn = sourceURL.openConnection();
            if (conn != null) {
                inStream = new DataInputStream(conn.getInputStream());
                StringBuilder sb = new StringBuilder();
                bfr = new BufferedReader(new InputStreamReader(inStream, Charset.forName(ENCODING)));
                String line;
                while ((line = bfr.readLine()) != null) {
                    sb.append(line);
                    sb.append('\r');
                }
                response = sb.toString();
            }
        } catch (IOException ex) {
            logger.error(ex.toString());
        } finally {
            ResourceUtil.close(bfr, inStream);
        }
        return response;
    }
    public static Map<Integer, String> dumpURLToMap(URL sourceURL) {
        Map<Integer, String> result = new TreeMap<>();
        String response;
        DataInputStream inStream = null;
        BufferedReader bfr = null;
        try {
            URLConnection conn = sourceURL.openConnection();
            if (conn != null) {


               

                inStream = new DataInputStream(conn.getInputStream());
                StringBuilder sb = new StringBuilder();
                bfr = new BufferedReader(new InputStreamReader(inStream, Charset.forName(ENCODING)));
                String line;

                while ((line = bfr.readLine()) != null) {
                    result.put(result.size(), line);
                    sb.append(line);
                    sb.append('\r');
                }

                response = sb.toString();
                
            }
        } catch (IOException ex) {
            logger.error(ex.toString());
        } finally {
            ResourceUtil.close(bfr, inStream);
        }
        return result;
    }
    
    public static boolean download(URL downloadURL, File downloadLocalPath){
        String downloadedData = dump(downloadURL);
        boolean status;
        
            FileUtil.writeFile(downloadLocalPath, downloadedData);
            status = true;
       
        return status;
    }
}
