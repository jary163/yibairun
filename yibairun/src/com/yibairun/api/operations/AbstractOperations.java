package com.yibairun.api.operations;

import android.content.Context;

import com.yibairun.utils.Lg;

public abstract class AbstractOperations {


    protected static final String API_URL =  "http://100run.nat123.net/api/";
    protected  Context ctx;
    protected  String accessToken;
    protected  String token;
    private boolean requireAuth;
    protected Lg lg = Lg.jLog();
    protected String deviceId;


    protected AbstractOperations() {

    }

    protected AbstractOperations(Context ctx) {
        this.ctx = ctx;
    }


    public AbstractOperations(String accessToken) {
        this();
        this.accessToken = accessToken;
    }


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}