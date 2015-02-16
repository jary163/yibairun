package cn.hi.eim.service;

import java.util.TimerTask;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import cn.hi.eim.activity.EimApplication;
import cn.hi.eim.activity.MainActivity;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.util.Utils;

/**
 * 
 * 重连接服务.
 * 
 * @author shimiso
 */
public class ReConnectService extends BaseServie {

	private String action;
	private SharedPreferences preferences;

	@Override
	public void onCreate() {
		super.onCreate();
		preferences = getSharedPreferences(Constant.LOGIN_SET, 0);
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mFilter.addAction(Constant.ACTION_RECONNECT_SERVICE);
		registerReceiver(reConnectionBroadcastReceiver, mFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(reConnectionBroadcastReceiver);
		super.onDestroy();
	}

	BroadcastReceiver reConnectionBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)
					|| action.equals(Constant.ACTION_RECONNECT_SERVICE)) {
				Log.d("mark", "网络状态已经改变");
				XMPPConnection connection;
				try {
					connection = XmppConnectionManager.getInstance()
							.getConnection();
					connectivityManager = (ConnectivityManager) context
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					info = connectivityManager.getActiveNetworkInfo();
					Log.d("mark", "网络状态已经改变:" + info);
					if (info != null && info.isAvailable()) {
						if (!connection.isConnected()) {
							// reConnect(connection);
							new ReConnectTask(connection).execute();
						} else {
							sendInentAndPre(Constant.RECONNECT_STATE_SUCCESS);
							//Toast.makeText(context, "用户已上线!", Toast.LENGTH_LONG).show();
						}
					} else {
						sendInentAndPre(Constant.RECONNECT_STATE_FAIL);
						connection.disconnect();// 执行离线操作
						//Toast.makeText(context, "网络断开,用户已离线!",Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	};

	/**
	 * 
	 * 递归重连，直连上为止.
	 * 
	 * @author shimiso
	 * @update 2012-7-10 下午2:12:25
	 */
	public void reConnect(XMPPConnection connection) {

		if (info == null || !info.isAvailable())
			return;
		try {
			connection.connect();

			// 服务器断开，需要重新初始化所有的服务
			connection.login(preferences.getString(Constant.USERNAME, ""),
					preferences.getString(Constant.PASSWORD, ""), "android");
			EimApplication applicationContext = (EimApplication) context
					.getApplicationContext();
			applicationContext.getiActivitySupport().startService();

			if (connection.isConnected()) {
				Presence presence = new Presence(Presence.Type.available);
				connection.sendPacket(presence);
			}
		} catch (XMPPException e) {
			Log.e("ERROR", "XMPP连接失败!", e);
			reConnect(connection);
		}
	}

	private void sendInentAndPre(boolean isSuccess) {
		Intent intent = new Intent();
		SharedPreferences preference = getSharedPreferences(Constant.LOGIN_SET,
				0);
		// 保存在线连接信息
		preference.edit().putBoolean(Constant.IS_ONLINE, isSuccess).commit();
		intent.setAction(Constant.ACTION_RECONNECT_STATE);
		intent.putExtra(Constant.RECONNECT_STATE, isSuccess);
		sendBroadcast(intent);
	}

	class ReConnectTask extends AsyncTask<Void, Void, Void> {

		private XMPPConnection connection;

		public ReConnectTask(XMPPConnection connection) {
			super();
			this.connection = connection;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			reConnect(connection);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (connection.isConnected()) {
				//Toast.makeText(context, "用户已上线!", Toast.LENGTH_LONG).show();
			}
		}
	}

}
