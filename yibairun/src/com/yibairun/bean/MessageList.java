package com.yibairun.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2014/8/24.
 * author:wangzhiyuan mailto:wzyax@qq.com
 */
public class MessageList extends StatusMessage {
    @SerializedName("data")
    private List<Message> messageList;

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
