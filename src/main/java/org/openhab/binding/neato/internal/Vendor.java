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

/**
 *
 * Abstract class representing any vendor of your robot.
 *
 * @author Florian Dietrich
 *
 *
 */
public abstract class Vendor {
    /**
     * Return URL for beehive host
     *
     * @return
     */
    public abstract String getBeehiveUrl();

    /**
     * Return Nucleo url for controlling your robot
     *
     * @return
     */
    public abstract String getNucleoUrl();

    /**
     *
     * Some vendors are using self signed certificates. Here each vendor class
     * has to implement an own method for executing http requests.
     *
     * @param httpMethod
     * @param url
     * @param httpHeaders
     * @param content
     * @param contentType
     * @param timeout
     * @return
     * @throws IOException
     */
    public abstract String executeRequest(String httpMethod, String url, Properties httpHeaders, InputStream content,
            String contentType, int timeout) throws IOException;
}
