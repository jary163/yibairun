package com.yibairun.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/24.
 * author:wangzhiyuan mailto:wzyax@qq.com
 */
public class Message implements Serializable {
    private int id;
    private String title;
    private String content;
    private long createtime;//单位秒

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
