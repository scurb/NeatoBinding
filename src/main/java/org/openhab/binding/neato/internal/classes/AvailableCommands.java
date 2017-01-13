package org.openhab.binding.neato.internal.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableCommands {

    @SerializedName("start")
    @Expose
    private Boolean start;
    @SerializedName("stop")
    @Expose
    private Boolean stop;
    @SerializedName("pause")
    @Expose
    private Boolean pause;
    @SerializedName("resume")
    @Expose
    private Boolean resume;
    @SerializedName("goToBase")
    @Expose
    private Boolean goToBase;

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Boolean getResume() {
        return resume;
    }

    public void setResume(Boolean resume) {
        this.resume = resume;
    }

    public Boolean getGoToBase() {
        return goToBase;
    }

    public void setGoToBase(Boolean goToBase) {
        this.goToBase = goToBase;
    }

}