/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.internal;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Set;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.neato.NeatoBindingConstants;
import org.openhab.binding.neato.handler.NeatoHandler;
import org.osgi.service.component.ComponentContext;

/**
 * The {@link NeatoHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Patrik Wimnell - Initial contribution
 * @author Florian Dietrich - Vendor added
 */
public class NeatoHandlerFactory extends BaseThingHandlerFactory {

    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .singleton(NeatoBindingConstants.THING_TYPE_VACUUMCLEANER);

    public static String email;
    public static String password;
    public static String vendor;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {

        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(NeatoBindingConstants.THING_TYPE_VACUUMCLEANER)) {
            return new NeatoHandler(thing);
        }

        return null;
    }

    @Override
    protected void activate(ComponentContext componentContext) {
        super.activate(componentContext);

        Dictionary<String, Object> properties = componentContext.getProperties();
        email = (String) properties.get("email");
        password = (String) properties.get("password");
        vendor = (String) properties.get("vendor");

    };
}
