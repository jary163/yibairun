package cn.hi.eim.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import cn.hi.eim.activity.EimApplication;
import cn.hi.eim.util.Lg;

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
        sendMessage(ex);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            //
        }
        // 处理异常
        handleException(ex);
    }

    private void sendMessage(Throwable ex) {
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append(ex.getMessage());
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString());
        }
        // 待续,心情好的时候再搞...

    }

    private void handleException(Throwable ex) {
        Lg.e(TAG, "haokan exception: ==========>" + ex.getLocalizedMessage());
        ((EimApplication)act.getApplicationContext()).getiActivitySupport().stopService();//异常退出时停止服务
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}