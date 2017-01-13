package org.openhab.binding.neato.internal.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeatoGeneralInfo {

    @SerializedName("productNumber")
    @Expose
    private String productNumber;
    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("firmware")
    @Expose
    private String firmware;
    @SerializedName("battery")
    @Expose
    private Battery battery;

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

}