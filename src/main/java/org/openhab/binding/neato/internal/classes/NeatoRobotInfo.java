package org.openhab.binding.neato.internal.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeatoRobotInfo {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("reqId")
    @Expose
    private String reqId;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private RobotInfoData data;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public RobotInfoData getData() {
        return data;
    }

    public void setData(RobotInfoData data) {
        this.data = data;
    }

}