package com.yibairun.exception;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.util.Log;
/**
 * @author wzyax@qq.com
 * @version V1.0
 * @date 2012-12-25 下午5:21:25
 */
public class DefaultExceptionHandler implements UncaughtExceptionHandler {
    private static final String TAG = "DefaultExceptionHandler";
    private Context act = null;

    public DefaultExceptionHandler(Context act) {
        this.act = act;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 处理异常
        handleException(ex);
    }

    private void handleException(Throwable ex) {
        Log.e(TAG, "yibairun exception: ==========>" + getErrorInfo(ex));
        MobclickAgent.reportError(act, ex);//将错误报告发送到Umeng
        MobclickAgent.onKillProcess(act);// umeng需要（如果开发者调用 Process.kill 或者 System.exit 之类的方法杀死进程，请务必在此之前调用该方法，用来保存统计数据）
        
        //干掉当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    
    /** 
     * 获取错误的信息  
     * @param arg1 
     * @return 
     */  
    private String getErrorInfo(Throwable arg1) {  
        Writer writer = new StringWriter();  
        PrintWriter pw = new PrintWriter(writer);  
        arg1.printStackTrace(pw);  
        pw.close();  
        String error= writer.toString();  
        return error;  
    } 
}