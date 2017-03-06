/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author Florian Dietrich
 *
 */
public class VendorVorwerk extends Vendor {

    private static final String BEEHIVE_URL = "https://vorwerk-beehive-production.herokuapp.com";
    private static final String NUCLEO_URL = "https://nucleo.ksecosys.com";
    private static final String VENDOR_NAME = "vorwerk";

    @Override
    public String getBeehiveUrl() {
        return BEEHIVE_URL;
    }

    @Override
    public String getNucleoUrl() {
        return NUCLEO_URL;
    }

    @Override
    public String toString() {
        return VENDOR_NAME;
    }

    public static String getVendorName() {
        return VENDOR_NAME;
    }

    @Override
    public String executeRequest(String httpMethod, String url, Properties httpHeaders, InputStream content,
            String contentType, int timeout) throws IOException {

        URL requestUrl = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) requestUrl.openConnection();
        applyNucleoSslConfiguration(connection);
        connection.setRequestMethod(httpMethod);
        for (String propName : httpHeaders.stringPropertyNames()) {
            connection.addRequestProperty(propName, httpHeaders.getProperty(propName));
        }
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        content.reset();
        IOUtils.copy(content, connection.getOutputStream());

        java.io.InputStream is = connection.getInputStream();
        return IOUtils.toString(is);
    }

    /**
     * Trust the self signed certificate.
     *
     * @param connection
     */
    public void applyNucleoSslConfiguration(HttpsURLConnection connection) {
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(this.getClass().getClassLoader().getResourceAsStream("keystore.jks"), "geheim".toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslctx = SSLContext.getInstance("SSL");
            sslctx.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            connection.setSSLSocketFactory(sslctx.getSocketFactory());
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
