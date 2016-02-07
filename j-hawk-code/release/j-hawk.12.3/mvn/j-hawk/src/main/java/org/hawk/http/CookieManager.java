/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.http;

/**
 *
 * @author msahu
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.logging.Level;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.hawk.logger.HawkLogger;
import org.hawk.util.HttpUtil;

/**
 * CookieManager is a simple utilty for handling cookies when working
 * with java.net.URL and java.net.URLConnection
 * objects.
 *
 *
 *     Cookiemanager cm = new CookieManager();
 *     URL url = new URL("http://www.hccp.org/test/cookieTest.jsp");
 *
 *      . . .
 *
 *     // getting cookies:
 *     URLConnection conn = url.openConnection();
 *     conn.connect();
 *
 *     // setting cookies
 *     cm.storeCookies(conn);
 *     cm.setCookies(url.openConnection());
 *     original author Ian Brown... modified by Manoranjan Sahu
 *     @author Ian Brown
 *     @author msahu
 **/
public class CookieManager {

    private static final String SET_COOKIE = "Set-Cookie";
    private static final String COOKIE_VALUE_DELIMITER = ";";
    private static final String PATH = "path";
    private static final String EXPIRES = "expires";
    private static final String DATE_FORMAT = "EEE, dd-MMM-yyyy hh:mm:ss z";
    private static final String SET_COOKIE_SEPARATOR = "; ";
    private static final String COOKIE = "Cookie";
    private static final char NAME_VALUE_SEPARATOR = '=';
    private static final char DOT = '.';
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Retrieves and stores cookies returned by the host on the other side
     * of the the open java.net.URLConnection.
     *
     * The connection MUST have been opened using the connect()
     * method or a IOException will be thrown.
     *
     * @param conn a java.net.URLConnection - must be open, or IOException will be thrown
     * @throws java.io.IOException Thrown if conn is not open.
     */
    public static Map<String, Map<String, String>> getCookie(URLConnection conn) throws IOException {

        Map<String, Map<String, String>> domainStore = new HashMap<String, Map<String, String>>();
        // OK, now we are ready to get the cookies out of the URLConnection

        String headerName = null;
        for (int i = 1; (headerName = conn.getHeaderFieldKey(i)) != null; i++) {
            if (headerName.equalsIgnoreCase(SET_COOKIE)) {
                Map<String, String> cookie = new HashMap<String, String>();
                StringTokenizer st = new StringTokenizer(conn.getHeaderField(i), COOKIE_VALUE_DELIMITER);

                // the specification dictates that the first name/value pair
                // in the string is the cookie name and value, so let's handle
                // them as a special case:

                if (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    String name = token.substring(0, token.indexOf(NAME_VALUE_SEPARATOR));
                    String value = token.substring(token.indexOf(NAME_VALUE_SEPARATOR) + 1, token.length());
                    domainStore.put(name, cookie);
                    cookie.put(name, value);
                }

                while (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    if (token.indexOf(NAME_VALUE_SEPARATOR) > 0) {
                        cookie.put(token.substring(0, token.indexOf(NAME_VALUE_SEPARATOR)).toLowerCase(),
                                token.substring(token.indexOf(NAME_VALUE_SEPARATOR) + 1, token.length()));
                    } else {
                        break;
                    }
                }
            }
        }

        return domainStore;
    }
    //private static final String JSESSIONID = "JSESSIONID_ecommerce";

    public static String fetchJSessionID(URLConnection conn) {

        String jsessionIdValue = null;
        try {
            Map<String, Map<String, String>> map = getCookie(conn);
            String jsessionId = HttpUtil.getJSesssionID();
            if (map != null && !map.isEmpty() && map.containsKey(jsessionId)) {
                jsessionIdValue = map.get(jsessionId).get(jsessionId);
            }
        } catch (IOException ex) {
            logger.severe( ex);
        }
        return jsessionIdValue;
    }

    public static boolean loadCookies(HttpClient httpClient, HttpSessionInfo httpSessionInfo) {

        boolean loaded = false;
        httpSessionInfo.getCookies().clear();
        try {
            Cookie[] cookies = httpClient.getState().getCookies();
            String jsessionId = HttpUtil.getJSesssionID();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(jsessionId)) {
                    httpSessionInfo.setJsessionId(cookie.getValue());
                }
                httpSessionInfo.getCookies().add(cookie);
            }
            loaded = true;
        } catch (Exception ex) {
            logger.severe(ex);
        }
        return loaded;
    }

    /**
     * Prior to opening a URLConnection, calling this method will set all
     * unexpired cookies that match the path or subpaths for thi underlying URL
     *
     * The connection MUST NOT have been opened
     * method or an IOException will be thrown.
     *
     * @param conn a java.net.URLConnection - must NOT be open, or IOException will be thrown
     * @throws java.io.IOException Thrown if conn has already been opened.
     */
    /*
    public void setCookies(URLConnection conn) throws IOException {

    // let's determine the domain and path to retrieve the appropriate cookies
    URL url = conn.getURL();
    String domain = getDomainFromHost(url.getHost());
    String path = url.getPath();


    if (domainStore == null) return;
    StringBuilder cookieStringBuffer = new StringBuilder();

    Iterator cookieNames = domainStore.keySet().iterator();
    while(cookieNames.hasNext()) {
    String cookieName = (String)cookieNames.next();
    Map<String,String> cookie = domainStore.get(cookieName);
    // check cookie to ensure path matches  and cookie is not expired
    // if all is cool, add cookie to header string
    if (comparePaths((String)cookie.get(PATH), path) && isNotExpired((String)cookie.get(EXPIRES))) {
    cookieStringBuffer.append(cookieName);
    cookieStringBuffer.append("=");
    cookieStringBuffer.append((String)cookie.get(cookieName));
    if (cookieNames.hasNext()) cookieStringBuffer.append(SET_COOKIE_SEPARATOR);
    }
    }
    try {
    conn.setRequestProperty(COOKIE, cookieStringBuffer.toString());
    } catch (java.lang.IllegalStateException ise) {
    IOException ioe = new IOException("Illegal State! Cookies cannot be set on a URLConnection that is already connected. "
    + "Only call setCookies(java.net.URLConnection) AFTER calling java.net.URLConnection.connect().");
    throw ioe;
    }
    }


    private String getDomainFromHost(String host) {
    if (host.indexOf(DOT) != host.lastIndexOf(DOT)) {
    return host.substring(host.indexOf(DOT) + 1);
    } else {
    return host;
    }
    }

    private boolean isNotExpired(String cookieExpires) {
    if (cookieExpires == null) return true;
    Date now = new Date();
    try {
    return (now.compareTo(dateFormat.parse(cookieExpires))) <= 0;
    } catch (java.text.ParseException pe) {
    pe.printStackTrace();
    return false;
    }
    }

    private boolean comparePaths(String cookiePath, String targetPath) {
    if (cookiePath == null) {
    return true;
    } else if (cookiePath.equals("/")) {
    return true;
    } else if (targetPath.regionMatches(0, cookiePath, 0, cookiePath.length())) {
    return true;
    } else {
    return false;
    }

    }
     * 
     */
    public static void main(String[] args) {
        CookieManager cm = new CookieManager();
        try {
            URL url = new URL("http://localhost:8080/xweb");
            URLConnection conn = url.openConnection();
            conn.connect();

            System.out.println(CookieManager.fetchJSessionID(conn));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private static final HawkLogger logger = HawkLogger.getLogger(CookieManager.class.getName());
}
