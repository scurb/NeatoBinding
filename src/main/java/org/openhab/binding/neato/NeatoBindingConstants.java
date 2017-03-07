/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link NeatoBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Patrik Wimnell - Initial contribution
 * @author Florian Dietrich - Vendor added
 */
public class NeatoBindingConstants {

    public static final String BINDING_ID = "neato";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_VACUUMCLEANER = new ThingTypeUID(BINDING_ID, "vacuumcleaner");

    // List of all Channel ids
    public final static String CHANNEL_BATTERY = "battery-level";
    public final static String CHANNEL_STATE = "state";
    public final static String CHANNEL_ERROR = "error";
    public final static String CHANNEL_AVAILABLESERVICES = "available-services";
    public final static String CHANNEL_VERSION = "version";
    public final static String CHANNEL_MODELNAME = "model-name";
    public final static String CHANNEL_FIRMWARE = "firmware";
    public final static String CHANNEL_ACTION = "action";
    public final static String CHANNEL_DOCKHASBEENSEEN = "dock-has-been-seen";
    public final static String CHANNEL_ISDOCKED = "is-docked";
    public final static String CHANNEL_ISSCHEDULED = "is-scheduled";
    public final static String CHANNEL_ISCHARGING = "is-charging";
    public final static String CHANNEL_AVAILABLECOMMANDS = "available-commands";
    public final static String COMMAND = "command";
    public final static String CHANNEL_NAME = "name";
    public final static String CHANNEL_CLEANINGCATEGORY = "cleaning-category";
    public final static String CHANNEL_CLEANINGMODE = "cleaning-mode";
    public final static String CHANNEL_CLEANINGMODIFIER = "cleaning-modifier";
    public final static String CHANNEL_CLEANINGSPOTWIDTH = "cleaning-spotwidth";
    public final static String CHANNEL_CLEANINGSPOTHEIGHT = "cleaning-spotheight";

    public final static String CONFIG_SECRET = "secret";
    public final static String CONFIG_NAME = "name";
    public final static String CONFIG_SERIAL = "serial";
    public final static String CONFIG_VENDOR = "vendor";
    public final static String CONFIG_REFRESHTIME = "refresh";

    public final static int NEATO_STATE_INVALID = 0;
    public final static int NEATO_STATE_IDLE = 1;
    public final static int NEATO_STATE_BUSY = 2;
    public final static int NEATO_STATE_PAUSED = 3;
    public final static int NEATO_STATE_ERROR = 4;

    public final static int NEATO_ACTION_INVALID = 0;
    public final static int NEATO_ACTION_HOUSECLEANING = 1;
    public final static int NEATO_ACTION_SPOTCLEANING = 2;
    public final static int NEATO_ACTION_MANUALCLEANING = 3;
    public final static int NEATO_ACTION_DOCKING = 4;
    public final static int NEATO_ACTION_USERMENUACTIVE = 5;
    public final static int NEATO_ACTION_SUSPENDEDCLEANING = 6;
    public final static int NEATO_ACTION_UPDATING = 7;
    public final static int NEATO_ACTION_COPYINGLOGS = 8;
    public final static int NEATO_ACTION_RECOVERINGLOCATION = 9;
    public final static int NEATO_ACTION_IECTEST = 10;

    public final static int NEATO_CLEAN_CATEGORY_MANUAL = 1;
    public final static int NEATO_CLEAN_CATEGORY_HOUSE = 2;
    public final static int NEATO_CLEAN_CATEGORY_SPOT = 3;

    public final static int NEATO_CLEAN_MODE_ECO = 1;
    public final static int NEATO_CLEAN_MODE_TURBO = 2;

    public final static int NEATO_CLEAN_MODIFIER_NORMAL = 1;
    public final static int NEATO_CLEAN_MODIFIER_DOUBLE = 2;

}
