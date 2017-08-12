/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.common.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

/**
 *
 * @author reemeeka
 */
public class SSLUtil {

    public static SSLContext getInsecureSSLContext()
            throws KeyManagementException, NoSuchAlgorithmException {
        final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws java.security.cert.CertificateException {
                   // trust all
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws java.security.cert.CertificateException {
                    //trust all
                }

            }
        };

        final SSLContext sslcontext = SSLContext.getInstance("SSL");
        sslcontext.init(null, trustAllCerts,new java.security.SecureRandom());
        return sslcontext;
    }
}
