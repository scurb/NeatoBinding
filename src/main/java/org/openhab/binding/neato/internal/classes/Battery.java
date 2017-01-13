package org.openhab.binding.neato.internal.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Battery {

    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("timeToEmpty")
    @Expose
    private Integer timeToEmpty;
    @SerializedName("timeToFullCharge")
    @Expose
    private Integer timeToFullCharge;
    @SerializedName("totalCharges")
    @Expose
    private Integer totalCharges;
    @SerializedName("manufacturingDate")
    @Expose
    private String manufacturingDate;
    @SerializedName("authorizationStatus")
    @Expose
    private Integer authorizationStatus;
    @SerializedName("vendor")
    @Expose
    private String vendor;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTimeToEmpty() {
        return timeToEmpty;
    }

    public void setTimeToEmpty(Integer timeToEmpty) {
        this.timeToEmpty = timeToEmpty;
    }

    public Integer getTimeToFullCharge() {
        return timeToFullCharge;
    }

    public void setTimeToFullCharge(Integer timeToFullCharge) {
        this.timeToFullCharge = timeToFullCharge;
    }

    public Integer getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(Integer totalCharges) {
        this.totalCharges = totalCharges;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Integer getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(Integer authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

}