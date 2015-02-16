package cn.hi.eim.service;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.packet.VCard;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.hi.eim.R;
import cn.hi.eim.activity.EimApplication;
import cn.hi.eim.activity.im.ChatActivity;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.NoticeManager;
import cn.hi.eim.manager.UserManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.IMMessage;
import cn.hi.eim.model.Notice;
import cn.hi.eim.model.User;
import cn.hi.eim.util.DateUtil;
import cn.hi.eim.util.SystemUtils;

/**
 * 
 * 聊天服务.
 * 
 * @author shimiso
 */
public class IMChatService extends BaseServie {

	private NotificationManager mNM;
	private Method mStartForeground;
	private Method mStopForeground;
	private Object[] mStartForegroundArgs = new Object[2];
	private Object[] mStopForegroundArgs = new Object[1];
	private int numberId;
	private Map<String, Integer> noticeMap;
	private XMPPConnection conn;

	private static final Class[] mStartForegroundSignature = new Class[] {
			int.class, Notification.class };
	private static final Class[] mStopForegroundSignature = new Class[] { boolean.class };

	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		try {
			mStartForeground = IMChatService.class.getMethod("startForeground",
					mStartForegroundSignature);
			mStopForeground = IMChatService.class.getMethod("stopForeground",
					mStopForegroundSignature);
		} catch (NoSuchMethodException e) {
		}
		Notification notification = new Notification();
		// 注意使用 startForeground ，id 为 0 将不会显示 notification
		startForegroundCompat(1, notification);

		noticeMap = new HashMap<String, Integer>();
		numberId = 2;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initChatManager();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	private void initChatManager() {
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		try {
			conn = XmppConnectionManager.getInstance().getConnection();
			conn.addPacketListener(pListener, new MessageTypeFilter(
					Message.Type.chat));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	PacketListener pListener = new PacketListener() {

		@Override
		public void processPacket(Packet arg0) {
			Message message = (Message) arg0;
			chatUser = ((EimApplication) getApplication()).getCurrentChatUser();
			if (message != null && message.getBody() != null
					&& !message.getBody().equals("null")) {
				IMMessage msg = new IMMessage();
				// String time = (String)
				// message.getProperty(IMMessage.KEY_TIME);
				String time = DateUtil.date2Str(Calendar.getInstance(),
						Constant.MS_FORMART);
				msg.setTime(time);
				msg.setContent(message.getBody());
				msg.setToSubJid(message.getTo());
				if (Message.Type.error == message.getType()) {
					msg.setType(IMMessage.ERROR);
				} else {
					msg.setType(IMMessage.SUCCESS);
				}
				
			
				String from = message.getFrom().split("/")[0];
				msg.setFromSubJid(from);

				// 生成通知
				NoticeManager noticeManager = NoticeManager
						.getInstance(context);
				Notice notice = new Notice();
				notice.setTitle("会话信息");
				notice.setNoticeType(Notice.CHAT_MSG);
				notice.setContent(message.getBody());
				notice.setFrom(from);
				notice.setTo(message.getTo());
				notice.setStatus(Notice.UNREAD);
				notice.setNoticeTime(time);

				// 历史记录
				IMMessage newMessage = new IMMessage();
				newMessage.setMsgType(0);
				newMessage.setFromSubJid(from);
				newMessage.setContent(message.getBody());
				newMessage.setTime(time);
			
				
				User user = new User();
				/**获取用户信息***/
				 VCard vCard = UserManager.getInstance(context).getUserVCard(message.getFrom().split("/")[0]);
				user.setJID(from);
				user.setGender( Integer.valueOf(vCard.getField("gender")));
				user.setAvatar(vCard.getField("avatar"));
				
				newMessage.setToSubJid(message.getTo());
				MessageManager.getInstance(context).saveIMMessage(newMessage);
				MessageManager.getInstance(context).saveUser(user);
				long noticeId = -1;

				noticeId = noticeManager.saveNotice(notice);
				
				Intent intent = new Intent(Constant.NEW_MESSAGE_ACTION);
				intent.putExtra(IMMessage.IMMESSAGE_KEY, msg);
				intent.putExtra("notice", notice);

				// 如果是当前聊天界面则不用通知，但是需要刷新界面
				if (chatUser != null
						&& message.getFrom() != null
						&& SystemUtils.getUserNameToLocal(message.getFrom())
								.equals(chatUser.getJID())) {
					sendBroadcast(intent);
					return;
				}
				if (noticeId != -1) {
					sendBroadcast(intent);
					setNotiType(R.drawable.ic_launcher, getResources()
							.getString(R.string.new_message),
							notice.getContent(), ChatActivity.class, from);

				}

			}

		}

	};

	/**
	 * 
	 * 发出Notification的method.
	 * 
	 * @param iconId
	 *            图标
	 * @param contentTitle
	 *            标题
	 * @param contentText
	 *            你内容
	 * @param activity
	 * @author shimiso
	 * @update 2012-5-14 下午12:01:55
	 */
	private void setNotiType(int iconId, String contentTitle,
			String contentText, Class activity, String from) {
		if (!eimApplication.isPushState())
			return;
		/*
		 * if (!eimApplication.isPushState()) return;
		 */
		/*
		 * 创建新的Intent，作为点击Notification留言条时， 会运行的Activity
		 */
		Intent notifyIntent = new Intent(this, activity);
		notifyIntent.putExtra("to", from);
		// notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		/* 创建PendingIntent作为设置递延运行的Activity */
		PendingIntent appIntent = PendingIntent.getActivity(this, 0,
				notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		/* 创建Notication，并设置相关参数 */
		Notification myNoti = new Notification();
		// 点击自动消失
		myNoti.flags = Notification.FLAG_AUTO_CANCEL;
		/* 设置statusbar显示的icon */
		myNoti.icon = iconId;
		/* 设置statusbar显示的文字信息 */
		myNoti.tickerText = contentTitle;
		/* 设置notification发生时同时发出默认声音 */
		if (eimApplication.isVideoState())
			myNoti.defaults = Notification.DEFAULT_SOUND;
		/* 设置Notification留言条的参数 */
		myNoti.setLatestEventInfo(this, contentTitle, contentText, appIntent);
		/* 送出Notification */
		if (noticeMap.containsKey(from)) {
			notificationManager.cancel(noticeMap.get(from));
			notificationManager.notify(noticeMap.get(from), myNoti);
		} else {
			notificationManager.notify(numberId, myNoti);
			noticeMap.put(from, numberId);
			numberId++;
		}
	}

	// 以兼容性方式开始前台服务
	private void startForegroundCompat(int id, Notification n) {
		if (mStartForeground != null) {
			mStartForegroundArgs[0] = id;
			mStartForegroundArgs[1] = n;
			try {
				mStartForeground.invoke(this, mStartForegroundArgs);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		mNM.notify(id, n);
	}

	// 以兼容性方式停止前台服务
	private void stopForegroundCompat(int id) {
		if (mStopForeground != null) {
			mStopForegroundArgs[0] = Boolean.TRUE;
			try {
				mStopForeground.invoke(this, mStopForegroundArgs);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		// 在 setForeground 之前调用 cancel，因为我们有可能在取消前台服务之后
		// 的那一瞬间被kill掉。这个时候 notification 便永远不会从通知一栏移除
		mNM.cancel(id);
	}
}
