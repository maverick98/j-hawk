/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.http;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import org.hawk.logger.HawkLogger;
import org.hawk.data.perf.HawkPerfDataCollector;
import org.hawk.module.lib.HttpModule;
import org.hawk.util.HawkUtil;
import org.hawk.util.HttpUtil;

/**
 *
 * @author msahu
 */
/**
 * This is the Hawk Thread which is used to hit the app server
 * simultaneously
 */
public class HawkHttpAgent implements Runnable {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkHttpAgent.class.getName());
    Object args = null;
    HttpModule httpModule = null;
    IHttpExecutor httpExecutor;

    public HawkHttpAgent(HttpModule httpModule, IHttpExecutor httpExecutor, Object args) {
        this.args = args;
        this.httpModule = httpModule;
        this.httpExecutor = httpExecutor;
    }

    public HawkHttpAgent(HttpModule httpModule, Object args) {
        this.args = args;
        this.httpModule = httpModule;
    }

    public IHttpExecutor getHttpExecutor() {
        return httpExecutor;
    }

    public void setHttpExecutor(IHttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    @Override
    public void run() {
        try {
            this.processInstruction();
        } catch (Exception ex) {
            logger.severe(ex);
        }
    }

    /**
     * This processes the instruction from hawk script.
     * This hits the targetURL with the the list of parameters in the argument.
     * @param args argument recevied from hawk script to execute
     * @return returns true on success
     * @throws Exception
     */
    public String processInstruction() throws Exception {
        if (args == null) {
            return null;
        }
        String response = null;
        Map<String, String> map = (Map<String, String>) args;
        logger.info("map received from Hawk....");
        logger.info(map.toString());
        String actionName = map.get("actionName");
        String targetURL = map.get("targetURL");
        String uploadFile = map.get("uploadFile");
        String multiPartPOST = map.get("multiPartPOST");
        String invalidateSession = map.get("invalidateSession");
        multiPartPOST = HawkUtil.convertHawkStringToJavaString(multiPartPOST);
        actionName = HawkUtil.convertHawkStringToJavaString(actionName);
        uploadFile = HawkUtil.convertHawkStringToJavaString(uploadFile);
        invalidateSession = HawkUtil.convertHawkStringToJavaString(invalidateSession);
        String fileName = null;
        targetURL = HawkUtil.convertHawkStringToJavaString(targetURL);


        if ("TRUE".equals(uploadFile)) {

            fileName = map.get("fileName");
            fileName = HawkUtil.convertHawkStringToJavaString(fileName);

            File importFile = new File(fileName);
            System.out.println(importFile.getName());
            try {
                HawkPerfDataCollector.start(this.httpModule, actionName);
                response = this.httpExecutor.uploadFile(targetURL, fileName);
                HawkPerfDataCollector.end(this.httpModule, actionName);
            } catch (Throwable th) {
                HawkPerfDataCollector.endWithFailure(httpModule, actionName);
                throw new Exception(th);
            }
        } else if ("TRUE".equals(multiPartPOST)) {
            try {
                HawkPerfDataCollector.start(this.httpModule, actionName);
                response = this.httpExecutor.executeMultiPartPOST(targetURL, map);

                HawkPerfDataCollector.end(this.httpModule, actionName);
            } catch (Throwable th) {
                HawkPerfDataCollector.endWithFailure(httpModule, actionName);
                throw new Exception(th);
            }

        } else {

            try {
                HawkPerfDataCollector.start(this.httpModule, actionName);
                String queryParams = HttpUtil.createPostData(map);
                //if (queryParams != null && !queryParams.isEmpty()) {
                //    queryParams = "?" + queryParams;
                //}
                if ("TRUE".equals(invalidateSession)) {
                    //response = this.httpExecutor.executeGetRequest(targetURL + queryParams, true);
                    response = this.httpExecutor.executePost(targetURL, queryParams, true);
                } else {
                    //response = this.httpExecutor.executeGetRequest(targetURL + queryParams, false);
                    response = this.httpExecutor.executePost(targetURL, queryParams, false);
                }

                HawkPerfDataCollector.end(this.httpModule, actionName);
            } catch (Throwable th) {
                HawkPerfDataCollector.endWithFailure(httpModule, actionName);
                throw new Exception(th);
            }
        }

        return response;
    }
}
