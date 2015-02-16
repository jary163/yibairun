package cn.hi.eim.listener;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.ConnectionListener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.hi.eim.activity.IActivitySupport;
import cn.hi.eim.activity.MainActivity;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.util.Utils;

/** 
 * 连接监听类 
 *  
 * @author Administrator 
 *  
 */  
public class ServiceConnectionListener implements ConnectionListener {  
    private Timer tExit;  
    private String username;  
    private String password;  
    private int logintime = 2000;  
    private static Context context;
    private static ServiceConnectionListener serviceConnectionListener;
    
    private ServiceConnectionListener(){};
    
    public void geta(){};
    
    public static  ServiceConnectionListener getInstance(Context cont){
    	context = cont;
    	if(serviceConnectionListener==null){
    		serviceConnectionListener = new ServiceConnectionListener();
    	}
    	return serviceConnectionListener;
    }
    
    @Override  
    public void connectionClosed() {  
        // 关闭连接 
        //XmppConnectionManager.getInstance().disconnect();  
        // 重连服务器  
        //sendReConnBroderCast();
    }  
  
    @Override  
    public void connectionClosedOnError(Exception e) {  
        Log.e("TaxiConnectionListener", "连接关闭异常");  
        // 判断账号是否为已登录
        //boolean error = e.getMessage().equals("stream:error (conflict)");  
        //判断是否为服务器down掉
        boolean serviceError = e.getMessage().equals("stream:error (system-shutdown)");
        Log.e("TaxiConnectionListener", e.getMessage()+"    serviceerror:"+serviceError);
        if (serviceError) {  
            // 关闭连接
        	XmppConnectionManager.getInstance().disconnect();  
        	//关闭服务
            // 重连服务器  
        	sendReConnBroderCast();
        }  
    }  
  
    /**
     * 发送请求重连服务器的通知
     */
    public void sendReConnBroderCast(){
    	Intent intent = new Intent();
		intent.setAction(Constant.ACTION_RECONNECT_SERVICE);
		context.sendBroadcast(intent);
    }
    
    @Override  
    public void reconnectingIn(int arg0) {  
    }  
  
    @Override  
    public void reconnectionFailed(Exception arg0) {  
    }  
  
    @Override  
    public void reconnectionSuccessful() {  
    }  
  
}  
