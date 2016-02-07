/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import javax.net.ssl.HttpsURLConnection;
import org.hawk.logger.HawkLogger;
import org.hawk.util.HttpUtil;

/**
 *
 * @author manoranjan
 */
public class HttpConnectionFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(HttpConnectionFactory.class.getName());

    public static HttpURLConnection createConnection(String targetURL) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetURL);

            if (HttpUtil.isHTTPSURL(targetURL)) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

        } catch (MalformedURLException ex) {
           logger.severe(ex);
        } catch (IOException ex) {
           logger.severe(ex);
        }
        return connection;
    }
}
