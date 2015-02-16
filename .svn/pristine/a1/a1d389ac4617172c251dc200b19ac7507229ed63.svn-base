package cn.hi.eim.receiver;

import org.jivesoftware.smack.XMPPConnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.service.ReConnectService;

public class ReConnectionBroadcastReceiver extends BroadcastReceiver {

	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		String action = intent.getAction();
//		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
			Log.d("mark", "网络状态已经改变");
			connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			XMPPConnection connection;
			try {
				connection = XmppConnectionManager.getInstance()
						.getConnection();
				info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					if (!connection.isConnected()) {
						//reConnect(connection);
//						new ReConnectTask(connection).execute();
						Intent reConnectService = new Intent(context, ReConnectService.class);
						context.startService(reConnectService);
						
					
					} else {
						sendInentAndPre(Constant.RECONNECT_STATE_SUCCESS);
						/*Toast.makeText(context, "用户已上线!", Toast.LENGTH_LONG)
						.show();*/
					}
				} else {
					sendInentAndPre(Constant.RECONNECT_STATE_FAIL);
					Toast.makeText(context, "网络断开,用户已离线!", Toast.LENGTH_LONG)
					.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		
	}
	private void sendInentAndPre(boolean isSuccess) {
		Intent intent = new Intent();
		SharedPreferences preference = context.getSharedPreferences(Constant.LOGIN_SET,
				0);
		// 保存在线连接信息
		preference.edit().putBoolean(Constant.IS_ONLINE, isSuccess).commit();
		intent.setAction(Constant.ACTION_RECONNECT_STATE);
		intent.putExtra(Constant.RECONNECT_STATE, isSuccess);
		context.sendBroadcast(intent);
	}
	
}