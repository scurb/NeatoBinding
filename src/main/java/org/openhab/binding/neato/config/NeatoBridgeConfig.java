/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.config;

/**
 * Parameters used for bridge configuration.
 *
 * @author Patrik WImnell
 * @author Florian Dietrich - Vendor added
 */
public class NeatoBridgeConfig {
    public String email; // serial port the gateway is attached to
    public String password; // ip address the gateway is attached to
    public String vendor; // Vendor for your robot. Only Neato and Vorwerk are currently supported
}