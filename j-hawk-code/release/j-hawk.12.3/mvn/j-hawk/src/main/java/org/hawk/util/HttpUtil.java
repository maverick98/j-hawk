package org.hawk.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import org.hawk.logger.HawkLogger;
import java.net.HttpURLConnection;
import org.hawk.exception.HawkException;
import org.hawk.http.HttpSessionInfo;
import org.hawk.module.script.ScriptUsage;
import org.hawk.module.script.enumeration.BuildModeEnum;

/**
 * An utility to communicate with the targetURL.
 * Apart from sending post requests , this can also also upload file too.
 * @version 1.0 12 Jul, 2010
 * @author msahu
 * @see HttpModule
 */
public class HttpUtil {

    
    /**
     * This creates POST data from the input map passed in from hawk script.
     * This checks for key parameters like targetURL, uploadFile , fileName and concurrent.
     * The above inbuilt key words will be moved a inbuilt hawk structure in the next release
     * after inheritance is implemented.
     * @param hawkMap input map from the hawk script
     * @return POST data
     * @throws HawkException
     */
    public static String createPostData(Map<String, String> hawkMap) throws HawkException {
        if (hawkMap == null) {
            throw new HawkException("hawk map is null");
        }
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Entry<String, String> entry : hawkMap.entrySet()) {
            if (!(entry.getKey().equals("targetURL") || entry.getKey().equals("uploadFile")
                    || entry.getKey().equals("fileName") || entry.getKey().equals("multiPartPOST") || entry.getKey().equals("concurrent") || entry.getKey().equals("actionName"))) {
                sb.append(entry.getKey()).append("=").append(HawkUtil.convertHawkStringToJavaString(entry.getValue())).append((i != hawkMap.size() ? "&" : ""));
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * This reads the HttpsURLConnection and saves the response in a html file.
     * This will be mainly used for debugging purpose.
     * @param conn connection from which data is to be read.
     * @return returns the response after saving it in result.html
     */
    public static String fetchAndSaveResult(HttpURLConnection conn,HttpSessionInfo httpSessionInfo) {
        String response = null;
        DataInputStream inStream = null;
        BufferedReader bfr = null;
        boolean status = false;
        try {
            inStream = new DataInputStream(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            bfr = new BufferedReader(new InputStreamReader(inStream));
            String line = null;

            while ((line = bfr.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
            }

            //if (ScriptUsage.getInstance().getBuildMode() == BuildModeEnum.DEBUG) {
                HawkUtil.writeFile("./result-" + httpSessionInfo.getActionName() + ".html", sb.toString());
            //}
            response = sb.toString();
            status = true;
        } catch (Exception ioex) {
            try {
                if (ScriptUsage.getInstance().getBuildMode() == BuildModeEnum.DEBUG) {
                    HawkUtil.writeFile("./result-" + httpSessionInfo.getActionName() + ".html", ioex.toString());
                }
            } catch (HawkException hawkEx) {
                logger.severe( hawkEx.toString());
            }
            response = null;
            status = false;
        } finally {
            try {
                bfr.close();
                inStream.close();
            } catch (IOException ioex) {
                logger.severe( ioex.toString());
            }
        }

        return response;
    }
    public static String getJSesssionID(){
        String defaultJSessionID = "JSESSIONID";
        String jsessionID = System.getProperty("JSESSIONID");
        return (jsessionID == null || jsessionID.isEmpty()) ? defaultJSessionID:jsessionID;
    }
    public static String getRequestToken(){
        
        return System.getProperty("REQUEST_TOKEN");
        
    }
    public static boolean isHTTPSURL(String targetURL){
        return targetURL.startsWith("https");
    }
    private static final HawkLogger logger = HawkLogger.getLogger(HttpUtil.class.getName());
}
