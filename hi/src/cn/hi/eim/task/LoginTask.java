package cn.hi.eim.task;

import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smackx.packet.VCard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;
import cn.hi.eim.R;
import cn.hi.eim.activity.CardsActivity;
import cn.hi.eim.activity.EimApplication;
import cn.hi.eim.activity.IActivitySupport;
import cn.hi.eim.activity.RecentChatListAcitivty;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.listener.ServiceConnectionListener;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.OfflineMsgManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.ChartHisBean;
import cn.hi.eim.model.LoginConfig;
import cn.hi.eim.model.User;

/**
 * 
 * 登录异步任务.
 * 
 * @author shimiso
 */
public class LoginTask extends AsyncTask<String, Integer, Integer> {
	private ProgressDialog pd;
	private Context context;
	private IActivitySupport activitySupport;
	private LoginConfig loginConfig;

	public LoginTask(IActivitySupport activitySupport, LoginConfig loginConfig) {
		this.activitySupport = activitySupport;
		this.loginConfig = loginConfig;
		this.pd = activitySupport.getProgressDialog();
		this.context = activitySupport.getContext();
	}

	@Override
	protected void onPreExecute() {
		pd.setTitle("请稍等");
		pd.setMessage("正在登录...");
		pd.show();
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... params) {
		SystemClock.sleep(1000);// 防止网络状态差的情况下，connect还没初始化成功
		return login();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
	}

	@Override
	protected void onPostExecute(Integer result) {
		pd.dismiss();
		switch (result) {
		case Constant.LOGIN_SECCESS: // 登录成功
			activitySupport.saveLoginConfig(loginConfig);// 保存用户配置信息
			activitySupport.startService(); // 初始化各项服务

			Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();

			List<ChartHisBean> inviteNotices = MessageManager.getInstance(
					context).getRecentContactsWithLastMsg();
			if (null != inviteNotices && inviteNotices.size() > 0) {
				intent.setClass(context, RecentChatListAcitivty.class);
			} else {
				intent.setClass(context, CardsActivity.class);
			}

			/*
			 * if (loginConfig.isFirstStart()) {// 如果是首次启动
			 * intent.setClass(context, GuideViewActivity.class);
			 * loginConfig.setFirstStart(false); } else {
			 * intent.setClass(context, MainActivity.class); }
			 */
			context.startActivity(intent);
			((Activity) context).finish();
			break;
		case Constant.LOGIN_ERROR_ACCOUNT_PASS:// 账户或者密码错误
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.message_invalid_username_password),
					Toast.LENGTH_SHORT).show();
			break;
		case Constant.SERVER_UNAVAILABLE:// 服务器连接失败
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.message_server_unavailable),
					Toast.LENGTH_SHORT).show();
			break;
		case Constant.LOGIN_ERROR:// 未知异常
			Toast.makeText(
					context,
					context.getResources().getString(
							R.string.unrecoverable_error), Toast.LENGTH_SHORT)
					.show();
			break;
		}
		super.onPostExecute(result);
	}

	// 登录
	private Integer login() {
		String username = loginConfig.getUsername();
		String password = loginConfig.getPassword();
		try {
			XMPPConnection connection = XmppConnectionManager.getInstance()
					.getConnection();
			connection.connect();
			connection.login(username, password, "android"); // 登录
			OfflineMsgManager.getInstance(activitySupport).dealOfflineMsg(
					connection,username);// 处理离线消息

			connection.sendPacket(new Presence(Presence.Type.available));
			connection.addConnectionListener(ServiceConnectionListener
					.getInstance(context));// 添加监听器
			if (loginConfig.isNovisible()) {// 隐身登录
				Presence presence = new Presence(Presence.Type.unavailable);
				Collection<RosterEntry> rosters = connection.getRoster()
						.getEntries();
				for (RosterEntry rosterEntry : rosters) {
					presence.setTo(rosterEntry.getUser());
					connection.sendPacket(presence);
				}
			}
			loginConfig.setUsername(username);
			if (loginConfig.isRemember()) {// 保存密码
				loginConfig.setPassword(password);
			} else {
				loginConfig.setPassword("");
			}
			loginConfig.setOnline(true);

			/** 获取用户信息 ***/
			VCard vCard = new VCard();
			vCard.load(connection); // load own VCard
			// vCard.load(connection, username); // load someone's VCard

			User user = new User();
			user.setJID(username);
			user.setvCard(vCard);
			user.setGender( Integer.valueOf(vCard.getField("gender")));
			((EimApplication) context.getApplicationContext()).setUser(user);
			MessageManager.getInstance(context).saveUser(user);//拷久化到user化方便头像的获取

			return Constant.LOGIN_SECCESS;
		} catch (Exception xee) {
			if (xee instanceof XMPPException) {
				XMPPException xe = (XMPPException) xee;
				final XMPPError error = xe.getXMPPError();
				int errorCode = 0;
				if (error != null) {
					errorCode = error.getCode();
				}
				if (errorCode == 401) {
					return Constant.LOGIN_ERROR_ACCOUNT_PASS;
				} else if (errorCode == 403) {
					return Constant.LOGIN_ERROR_ACCOUNT_PASS;
				} else {
					return Constant.SERVER_UNAVAILABLE;
				}
			} else {
				return Constant.LOGIN_ERROR;
			}
		}
	}
}
