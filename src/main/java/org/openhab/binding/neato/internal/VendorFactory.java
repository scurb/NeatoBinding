/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.internal;

/**
 *
 *
 * @author Florian Dietrich
 *
 *
 */
public class VendorFactory {

    public static Vendor createVendor(String name) {
        if ((name == null) || name.toLowerCase().equals(VendorNeato.getVendorName()) || name.equals("")) {
            return new VendorNeato();
        }

        if ((name != null) && name.toLowerCase().equals(VendorVorwerk.getVendorName())) {
            return new VendorVorwerk();
        }
        return null;
    }
}
