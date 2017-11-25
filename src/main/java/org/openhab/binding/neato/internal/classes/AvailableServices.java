/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.internal.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Patrik Wimnell - Initial contribution
 * @author Florian Dietrich - Vendor added
 */

public class AvailableServices {

    @SerializedName("houseCleaning")
    @Expose
    private String houseCleaning;

    @SerializedName("spotCleaning")
    @Expose
    private String spotCleaning;

    @SerializedName("manualCleaning")
    @Expose
    private String manualCleaning;

    @SerializedName("easyConnect")
    @Expose
    private String easyConnect;

    @SerializedName("schedule")
    @Expose
    private String schedule;

    @SerializedName("generalInfo")
    @Expose
    private String generalInfo;

    public String getGeneralInfo() {
        return generalInfo;
    }

    public void setGenenralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getHouseCleaning() {
        return houseCleaning;
    }

    public void setHouseCleaning(String houseCleaning) {
        this.houseCleaning = houseCleaning;
    }

    public String getSpotCleaning() {
        return spotCleaning;
    }

    public void setSpotCleaning(String spotCleaning) {
        this.spotCleaning = spotCleaning;
    }

    public String getManualCleaning() {
        return manualCleaning;
    }

    public void setManualCleaning(String manualCleaning) {
        this.manualCleaning = manualCleaning;
    }

    public String getEasyConnect() {
        return easyConnect;
    }

    public void setEasyConnect(String easyConnect) {
        this.easyConnect = easyConnect;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

}