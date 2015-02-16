package com.yibairun.exception;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2014/8/30.
 * author:wangzhiyuan mailto:wzyax@qq.com
 */
public class CommmonException extends VolleyError{
    public CommmonException(String info) {
        super(info);
    }
}
