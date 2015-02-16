package com.yibairun.bean;

import com.google.gson.annotations.SerializedName;


public class VersionInfo extends StatusMessage {
    @SerializedName("data")
    private Version version;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
