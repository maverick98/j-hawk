/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.http;

import java.net.URLConnection;
import java.util.Map;

/**
 *
 * @author msahu
 */
public interface IHttpExecutor {

    /**
     * This hits the target URL with the post data using the JSESSIONID.
     * @param targetURL target url to hit
     * @param data post data
     * @param jsessionid used for standard j2ee session management.
     * @return JSESSIONID , essentially during login page of a website.otherwise returns the same.
     * @throws Exception
     */
    String executePost(String targetURL, String postData, boolean invalidateSession) throws Exception;

    /***
     * requests for a post on the url specified by target url with the parameters specified in the requestparams 
     * @param targetURL
     * @param invalidateSession if true discard the jsessionid parameter
     * @return response body of the the requested url specified by target url
     * @throws Exception
     */
    String executeGetRequest(String targetURL, boolean invalidateSession) throws Exception;

    /**
     * This hits the target URL with the post data using the JSESSIONID.
     * @param targetURL target url to hit
     * @param data post data
     * @param jsessionid used for standard j2ee session management.
     * @return JSESSIONID , essentially during login page of a website.otherwise returns the same.
     * @throws Exception
     */
    String executeMultiPartPOST(String urlString, Map<String, String> map) throws Exception;

    /**
     * This uploads a file to the target URL.
     * @param urlString target URL to hit
     * @param exsistingFileName name of the file to upload
     * @param jsessionid JSESSIONID to use
     * @return returns true on success otherwise false
     * @throws Exception
     */
    String uploadFile(String urlString, String exsistingFileName) throws Exception;

    /**
     * This saves http session information for subsequence invocation.
     * @param conn httpconnection
     * @param invalidateSession should logout
     * @return returns true on success else false.
     */
    boolean setHttpSessionInfo(URLConnection conn, boolean invalidateSession);
}
