/*
 * Copyright (c) 2013-2014 Oracle and/or its affiliates. All rights reserved.
 */
package org.hawk.http;

import com.jayway.restassured.specification.RequestSpecification;
import org.hawk.lang.object.StructureScript;
import org.hawk.logger.HawkLogger;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import com.jayway.restassured.response.ResponseOptions;
import java.util.Map;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author manosahu
 */
public class HttpRestExecutor extends HttpExecutor {

    public HttpRestExecutor(String httpModuleName, StructureScript structureScript) throws Exception {
        super(httpModuleName, structureScript);

    }

    public HttpResponse executePostRequestWithinSession() throws Exception {
        return null;
    }

    public HttpResponse executeGetRequest() throws Exception {
        HttpResponse response = new HttpResponse();
        ResponseOptions r = mygiven().contentType(JSON).accept(JSON).get(this.getHttpRequest().getTargetURL());
       
        JSONObject json = new JSONObject(r.body().asString());
        System.out.println(r.getContentType());

        response.setContentType(r.contentType());
        response.setResponse(r.body().asString());
        response.setResponseCode(r.statusCode());
        //response.setHeaders(r.getHeaders().asList().toArray(ts));
        return response;
    }

    public RequestSpecification mygiven() {

        return this.setHeader(this.setAuth(given()));
    }

    private static abstract class AbstractHttpAuthVisitor {

        Map<String, String> authMap;

        public AbstractHttpAuthVisitor(HttpRequest httpRequest) {
            authMap = httpRequest.getAuth();
        }

        public AbstractHttpAuthVisitor(Map<String, String> headerMap) {
            this.authMap = headerMap;
        }

        public abstract void onVisit(String key, String value);

        public void visit() {

            if (authMap != null && !authMap.isEmpty()) {
                for (Map.Entry<String, String> entry : authMap.entrySet()) {
                    this.onVisit(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    static class RSHolder {

        RequestSpecification rs;

        public RequestSpecification getRs() {
            return rs;
        }

        public void setRs(RequestSpecification rs) {
            this.rs = rs;
        }

    }

    private RequestSpecification setHeader(final RequestSpecification rs) {
        final RSHolder rsHolder = new RSHolder();
        new AbstractHttpHeaderVisitor(this.getHttpRequest()) {

            @Override
            public void onVisit(String key, String value) {
                rsHolder.setRs(rs.header(key, value));
            }
        }.visit();

        return rsHolder.getRs();
    }

    private RequestSpecification setAuth(final RequestSpecification rs) {
        final RSHolder rsHolder = new RSHolder();
        new AbstractHttpAuthVisitor(this.getHttpRequest()) {

            @Override
            public void onVisit(String key, String value) {
                rsHolder.setRs(rs.auth().preemptive().basic(key, value));
            }
        }.visit();
        return rsHolder.getRs();
    }

    private static final HawkLogger logger = HawkLogger.getLogger(HttpRestExecutor.class.getName());

}
