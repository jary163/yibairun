package cn.hi.eim.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import cn.hi.eim.activity.EimApplication;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.User;

public class BaseServie extends Service {

	protected Context context;
	protected NotificationManager notificationManager;
	protected User chatUser;
	protected SharedPreferences settingPreferences;
	protected EimApplication eimApplication;
	protected boolean pushState;
	protected NetworkInfo info;
	protected ConnectivityManager connectivityManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		eimApplication = (EimApplication) getApplication();
		settingPreferences = getSharedPreferences(Constant.SYSTEM_SET,
				MODE_PRIVATE);
		pushState = settingPreferences.getBoolean(Constant.SETTING_PUSH, true);

	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
