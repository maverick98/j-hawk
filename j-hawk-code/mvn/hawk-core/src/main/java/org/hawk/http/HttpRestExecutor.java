/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.hawk.http;

import com.jayway.restassured.specification.RequestSpecification;
import org.hawk.lang.object.StructureScript;
import org.hawk.logger.HawkLogger;
import static com.jayway.restassured.RestAssured.given;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author manosahu
 */
public class HttpRestExecutor extends HttpExecutor {

    public HttpRestExecutor(String httpModuleName, StructureScript structureScript) throws Exception {
        super(httpModuleName, structureScript);

    }

    @Override
    public HttpResponse executeGetRequest() throws Exception {

        HttpResponse response = new HttpResponse();

        final Client client = Client.create(configureClient());
        client.setFollowRedirects(true);
        final WebResource resource = client.resource(this.getHttpRequest().getTargetURL());

        String get = resource.type("application/json").get(String.class);
        System.out.println(get);
        response.setContentType("application/json");
        response.setResponse(get);
        response.setResponseCode(200);
        //response.setHeaders(r.getHeaders().asList().toArray(ts));
        return response;
    }
    
     public static ClientConfig configureClient()
    {
        System.setProperty("jsse.enableSNIExtension", "false");
         Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        TrustManager[] certs = new TrustManager[]
        {
            new X509TrustManager()
            {
                @Override
                public X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException
                {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException
                {
                }
            }
        };
        SSLContext ctx = null;
        try
        {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        }
        catch (java.security.GeneralSecurityException ex)
        {
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        ClientConfig config = new DefaultClientConfig();
        try
        {
            config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
                    new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            },
                    ctx));
        }
        catch (Exception e)
        {
        }
        return config;
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
