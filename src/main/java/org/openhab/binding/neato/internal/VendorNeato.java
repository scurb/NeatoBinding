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
import java.util.Properties;

import org.openhab.io.net.http.HttpUtil;

/**
 *
 * @author Florian Dietrich
 *
 */
public class VendorNeato extends Vendor {

    private static final String BEEHIVE_URL = "https://beehive.neatocloud.com";
    private static final String NUCLEO_URL = "https://nucleo.neatocloud.com:4443";
    private static final String VENDOR_NAME = "neato";

    @Override
    public String getBeehiveUrl() {
        return BEEHIVE_URL;
    }

    @Override
    public String getNucleoUrl() {
        return NUCLEO_URL;
    }

    public static String getVendorName() {
        return VENDOR_NAME;
    }

    @Override
    public String toString() {
        return VENDOR_NAME;
    }

    @Override
    public String executeRequest(String httpMethod, String url, Properties httpHeaders, InputStream content,
            String contentType, int timeout) throws IOException {
        return HttpUtil.executeUrl(httpMethod, url, httpHeaders, content, contentType, timeout);
    }

}
