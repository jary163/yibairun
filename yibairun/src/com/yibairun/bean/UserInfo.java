package com.yibairun.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2014/7/26.
 * mailto:wzyax@qq.com
 */
public class UserInfo extends StatusMessage {
    @SerializedName("data")
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
