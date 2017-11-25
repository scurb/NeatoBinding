/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.neato.NeatoBindingConstants;
import org.openhab.binding.neato.internal.NeatoRobot;
import org.openhab.binding.neato.internal.VendorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link NeatoHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Patrik Wimnell - Initial contribution
 * @author Florian Dietrich - Vendor added
 */
public class NeatoHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(NeatoHandler.class);

    private String vacuumSerialNumber;
    private String vacuumSecret;
    private String vacuumName;
    private String vendorName;

    private NeatoRobot mrRobot;

    private BigDecimal refreshTime;
    private ScheduledFuture<?> refreshTask;
    private static BigDecimal DEFAULTREFRESHTIME = new BigDecimal(60.0);

    public NeatoHandler(Thing thing) {
        super(thing);
    }

    public ScheduledFuture<?> getRefreshTask() {
        return this.refreshTask;
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (channelUID.getId().equals(NeatoBindingConstants.COMMAND)) {
            logger.debug("Ok - will handle command for CHANNEL_COMMAND");
            // TODO: handle command

            try {
                mrRobot.sendCommand(command);
            } catch (Exception exc) {

            }
            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");

        }
        this.refreshStateAndUpdate();
    }

    @Override
    public void initialize() {

        logger.info("Will boot up Neato Vacuum Cleaner binding!");
        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.

        Configuration config = getThing().getConfiguration();
        vacuumSecret = (String) config.get(NeatoBindingConstants.CONFIG_SECRET);
        vacuumName = (String) config.get(NeatoBindingConstants.CONFIG_NAME);
        vacuumSerialNumber = (String) config.get(NeatoBindingConstants.CONFIG_SERIAL);
        vendorName = (String) config.get(NeatoBindingConstants.CONFIG_VENDOR);

        logger.info("Got config settings for {}-{} with serial number: {}", vendorName, vacuumName, vacuumSecret);

        super.initialize();

        try {
            refreshTime = (BigDecimal) config.get(NeatoBindingConstants.CONFIG_REFRESHTIME);
            if (refreshTime.intValue() <= 0) {
                throw new IllegalArgumentException("Refresh time must be positive number!");
            }
        } catch (Exception e) {
            logger.warn("Refresh time [{}] is not valid. Falling back to default value: {}. {}", refreshTime,
                    DEFAULTREFRESHTIME, e.getMessage());
            refreshTime = DEFAULTREFRESHTIME;
        }

        mrRobot = new NeatoRobot(vacuumSerialNumber, vacuumSecret, vacuumName, VendorFactory.createVendor(vendorName));
        try {
            if (mrRobot.sendGetState()) {
                // Long running initialization should be done asynchronously in background.
                updateStatus(ThingStatus.ONLINE);

                // Get additional information - should we care about this?
                mrRobot.sendGetRobotInfo();
            }
        } catch (Exception ex) {
            // Note: When initialization can NOT be done set the status with more details for further
            // analysis. See also class ThingStatusDetail for all available status details.
            // Add a description to give user information to understand why thing does not work
            // as expected. E.g.
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
            // "Can not access device as username and/or password are invalid");

            logger.error("Error getting robot info");
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, ex.getMessage());

        }

        this.startAutomaticRefresh();

    }

    public void refreshStateAndUpdate() {
        try {
            mrRobot.sendGetState();
            mrRobot.sendGetGeneralInfo();

            List<Channel> channels = getThing().getChannels();

            for (Channel channel : channels) {
                publishChannelIfLinked(channel.getUID());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startAutomaticRefresh() {

        Runnable refresher = new Runnable() {
            @Override
            public void run() {
                try {
                    refreshStateAndUpdate();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.error("Error refreshing state");
                }

            }
        };

        this.refreshTask = scheduler.scheduleAtFixedRate(refresher, 0, refreshTime.intValue(), TimeUnit.SECONDS);
        logger.debug("Start automatic refresh at {} minutes", refreshTime.intValue());
    }

    private void publishChannelIfLinked(ChannelUID channelUID) {
        String channelID = channelUID.getId();
        logger.debug("Will try to publish changes to channel {} - lets see if any links exists!", channelID);
        if (isLinked(channelID)) {
            logger.debug("Yes, channel is linked. Will publish updates!");
            State state = null;
            switch (channelID) {
                case NeatoBindingConstants.CHANNEL_BATTERY:
                    state = new DecimalType(mrRobot.getState().getDetails().getCharge());
                    break;
                case NeatoBindingConstants.CHANNEL_STATE:
                    state = new StringType(mrRobot.getState().getStateString().toString());
                    break;
                case NeatoBindingConstants.CHANNEL_VERSION:
                    state = new StringType(mrRobot.getState().getVersion().toString());
                    break;
                case NeatoBindingConstants.CHANNEL_ERROR:
                    state = new StringType(mrRobot.getState().getError().toString());
                    break;
                case NeatoBindingConstants.CHANNEL_MODELNAME:
                    state = new StringType(mrRobot.getState().getMeta().getModelName().toString());
                    break;
                case NeatoBindingConstants.CHANNEL_FIRMWARE:
                    state = new StringType(mrRobot.getState().getMeta().getFirmware().toString());
                    break;
                case NeatoBindingConstants.CHANNEL_ACTION:
                    state = new StringType(mrRobot.getState().getActionString().toString());
                    break;
                case NeatoBindingConstants.CHANNEL_DOCKHASBEENSEEN:
                    if (mrRobot.getState().getDetails().getDockHasBeenSeen()) {
                        state = OnOffType.ON;
                    } else {
                        state = OnOffType.OFF;
                    }
                    break;
                // case CHANNEL_AVAILABLECOMMANDS:
                // state = new StringType(mrRobot.getState().getAvailableCommands().toString());
                // break;
                case NeatoBindingConstants.CHANNEL_ISCHARGING:
                    if (mrRobot.getState().getDetails().getIsCharging()) {
                        state = OnOffType.ON;
                    } else {
                        state = OnOffType.OFF;
                    }

                    break;
                case NeatoBindingConstants.CHANNEL_ISSCHEDULED:
                    if (mrRobot.getState().getDetails().getIsScheduleEnabled()) {
                        state = OnOffType.ON;
                    } else {
                        state = OnOffType.OFF;
                    }
                    break;
                case NeatoBindingConstants.CHANNEL_ISDOCKED:
                    if (mrRobot.getState().getDetails().getIsDocked()) {
                        state = OnOffType.ON;
                    } else {
                        state = OnOffType.OFF;
                    }
                    break;
                case NeatoBindingConstants.CHANNEL_NAME:
                    state = new StringType(mrRobot.getName());
                    break;
                case NeatoBindingConstants.CHANNEL_CLEANINGCATEGORY:
                    state = new StringType(mrRobot.getState().getCleaning().getCategoryString());
                    break;
                case NeatoBindingConstants.CHANNEL_CLEANINGMODE:
                    state = new StringType(mrRobot.getState().getCleaning().getModeString());
                    break;
                case NeatoBindingConstants.CHANNEL_CLEANINGMODIFIER:
                    state = new StringType(mrRobot.getState().getCleaning().getModifierString());
                    break;
                case NeatoBindingConstants.CHANNEL_CLEANINGSPOTWIDTH:
                    state = new DecimalType(mrRobot.getState().getCleaning().getSpotWidth());
                    break;
                case NeatoBindingConstants.CHANNEL_CLEANINGSPOTHEIGHT:
                    state = new DecimalType(mrRobot.getState().getCleaning().getSpotHeight());
                    break;
            }

            if (state != null) {
                updateState(channelID, state);
            } else {
                logger.debug("Can not update channel with ID : {} - channel name might be wrong!", channelID);
            }
        } else {
            logger.debug("Well - channel {} does not seem to have a valid link. Will not publish changes!", channelID);
        }
    }
}
