package org.openhab.binding.neato.internal.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("isCharging")
    @Expose
    private Boolean isCharging;
    @SerializedName("isDocked")
    @Expose
    private Boolean isDocked;
    @SerializedName("isScheduleEnabled")
    @Expose
    private Boolean isScheduleEnabled;
    @SerializedName("dockHasBeenSeen")
    @Expose
    private Boolean dockHasBeenSeen;
    @SerializedName("charge")
    @Expose
    private Integer charge;

    public Boolean getIsCharging() {
        return isCharging;
    }

    public void setIsCharging(Boolean isCharging) {
        this.isCharging = isCharging;
    }

    public Boolean getIsDocked() {
        return isDocked;
    }

    public void setIsDocked(Boolean isDocked) {
        this.isDocked = isDocked;
    }

    public Boolean getIsScheduleEnabled() {
        return isScheduleEnabled;
    }

    public void setIsScheduleEnabled(Boolean isScheduleEnabled) {
        this.isScheduleEnabled = isScheduleEnabled;
    }

    public Boolean getDockHasBeenSeen() {
        return dockHasBeenSeen;
    }

    public void setDockHasBeenSeen(Boolean dockHasBeenSeen) {
        this.dockHasBeenSeen = dockHasBeenSeen;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

}