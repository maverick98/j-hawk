/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.hawk.logger.HawkLogger;
import org.commons.string.StringUtil;

/**
 *
 * @author manoranjan
 */
public class HttpConnectionFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(HttpConnectionFactory.class.getName());

    public static HttpURLConnection createConnection(String targetURL) {
        if (StringUtil.isNullOrEmpty(targetURL)) {
            throw new IllegalArgumentException("invalid url passed");
        }
        HttpURLConnection connection = null;
        try {
            connection = createConnection(new URL(targetURL));
        } catch (MalformedURLException ex) {
            logger.error(ex);
        }
        return connection;
    }

    public static HttpURLConnection createConnection(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("invalid url passed");
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        }catch (IOException ex) {
            logger.error(ex);
        }
        return connection;
    }
}
