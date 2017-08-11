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
