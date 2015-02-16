package cn.hi.eim.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.exception.DefaultExceptionHandler;
import cn.hi.eim.manager.RequestManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.User;

/**
 * 
 * 完整的退出应用.
 * 
 * @author shimiso
 */
public class EimApplication extends Application {
	
	private static final String TAG = "EimApplication";
	private User user,currentChatUser;
	private boolean videoState,pushState,sexState;
	private SharedPreferences settingPreferences;
	private IActivitySupport iActivitySupport;
	private String userJid;
	
	public String getUserJid() {
		return userJid;
	}
	public void setUserJid(String userJid) {
		this.userJid = userJid;
	}
	public boolean isVideoState() {
		return videoState;
	}
	public void setVideoState(boolean videoState) {
		this.videoState = videoState;
	}
	public boolean isPushState() {
		return pushState;
	}
	public void setPushState(boolean pushState) {
		this.pushState = pushState;
	}
	public boolean isSexState() {
		return sexState;
	}
	public void setSexState(boolean sexState) {
		this.sexState = sexState;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getCurrentChatUser() {
		return currentChatUser;
	}
	public void setCurrentChatUser(User currentChatUser) {
		this.currentChatUser = currentChatUser;
	}
	
	
	public IActivitySupport getiActivitySupport() {
		return iActivitySupport;
	}
	public void setiActivitySupport(IActivitySupport iActivitySupport) {
		this.iActivitySupport = iActivitySupport;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();

        if (Constant.DEBUG ) {
            Log.i(TAG, "app init : ################loading in debug mode");
        /*    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());*/
        } else{
        	Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(getApplicationContext()));
        }
    	XmppConnectionManager.getInstance().init();
    	
	}
	private void init() {
		RequestManager.init(this);
		settingPreferences = this.getSharedPreferences(Constant.SYSTEM_SET,
				MODE_PRIVATE);
		videoState = settingPreferences.getBoolean(Constant.SETTING_VIDEO, true);
		pushState = settingPreferences.getBoolean(Constant.SETTING_PUSH, true);
		sexState = settingPreferences.getBoolean(Constant.SETTING_SEX, true);
		
//		createImageCache();
	}
	private List<Activity> activityList = new LinkedList<Activity>();

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish
	public void exit() {
		XmppConnectionManager.getInstance().disconnect();
		for (Activity activity : activityList) {
			activity.finish();
		}
	}
	public void popActivityByName(Class<Activity> clz){
		for (Activity activity : activityList) {
			if(activity.getClass().getName().equals(clz.getName())){
				activity.finish();
				break;
			}
				
		}
	}
}
