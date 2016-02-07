/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hawk.http;

import java.util.Map;
import org.hawk.util.HttpUtil;
import org.hawk.util.StringUtil;

/**
 * Default implementation of AbstractHttpExecutor
 * @author msahu
 */
public class DefaultHttpSessionExecutor extends AbstractHttpExecutor{

    @Override
    public String executeGetRequest(String targetURL, boolean invalidateSession) throws Exception {
        targetURL = addRequestTokenToGET(targetURL);
        String responseHTML = super.executeGetRequest(targetURL, invalidateSession);
        this.setHttpSessionInfo(responseHTML);
        return responseHTML;
    }

    @Override
    public String executePost(String targetURL, String postData, boolean invalidateSession) throws Exception {
        targetURL = addRequestTokenToGET(targetURL);
        postData = addRequestTokenToPOST(postData);
        System.out.println("target url:" + targetURL);
        String responseHTML = super.executePost(targetURL, postData, invalidateSession);
        this.setHttpSessionInfo(responseHTML);
        return responseHTML;

    }

    @Override
    public String executeMultiPartPOST(String targetURL, Map<String, String> map) {
        targetURL = addRequestTokenToGET(targetURL);
        System.out.println("target url:" + targetURL);
        String responseHTML = super.executeMultiPartPOST(targetURL, map);
        this.setHttpSessionInfo(responseHTML);
        return responseHTML;
    }

    @Override
    public String uploadFile(String targetURL, String exsistingFileName)
            throws Exception {
        targetURL = addRequestTokenToGET(targetURL);

        String responseHTML = super.uploadFile(targetURL, exsistingFileName);
        this.setHttpSessionInfo(responseHTML);
        return responseHTML;

    }

    private String addRequestTokenToGET(String targetURL) {
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo != null) {
            String requestToken = HttpUtil.getRequestToken();
            if(!StringUtil.isNullOrEmpty(requestToken)){
                String requestTokenValue = (String) httpSessionInfo.getOtherData();

                if (!StringUtil.isNullOrEmpty(requestTokenValue)) {
                    String postDataSuffix = requestToken+"=" + requestTokenValue;
                    if (targetURL.contains("?")) {
                        targetURL = targetURL + "&"+postDataSuffix;
                    } else {
                        targetURL = targetURL + "?"+postDataSuffix;
                    }

                }
            }
        }
        return targetURL;
    }
    private String addRequestTokenToPOST(String postData) {
        HttpSessionInfo httpSessionInfo = httpSessionLocal.get();
        if (httpSessionInfo != null) {
            String requestToken = HttpUtil.getRequestToken();
            if(!StringUtil.isNullOrEmpty(requestToken)){
                String requestTokenValue = (String) httpSessionInfo.getOtherData();
                if (!StringUtil.isNullOrEmpty(requestTokenValue)) {
                    String postDataSuffix = requestToken+"=" + requestTokenValue;
                    if(StringUtil.isNullOrEmpty(postData)){
                        postData = postDataSuffix;
                    }else{
                        postData = postData + "&"+postDataSuffix;
                    }
                    
                }
            }
        }
        return postData;
    }
}
