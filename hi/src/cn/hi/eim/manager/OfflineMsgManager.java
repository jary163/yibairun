package cn.hi.eim.manager;

import java.util.Calendar;
import java.util.Iterator;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.OfflineMessageManager;

import android.content.Context;
import android.util.Log;
import cn.hi.eim.activity.ActivitySupport;
import cn.hi.eim.activity.IActivitySupport;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.model.IMMessage;
import cn.hi.eim.util.DateUtil;

/**
 * 
 * 离线信息管理类.
 * 
 * @author shimiso
 */
public class OfflineMsgManager {
	private static OfflineMsgManager offlineMsgManager = null;
	private IActivitySupport activitySupport;
	private String username;
	private Context context;

	private OfflineMsgManager(IActivitySupport activitySupport) {
		this.activitySupport = activitySupport;
		this.context = activitySupport.getContext();
	}

	public static OfflineMsgManager getInstance(IActivitySupport activitySupport) {
		if (offlineMsgManager == null) {
			offlineMsgManager = new OfflineMsgManager(activitySupport);
		}

		return offlineMsgManager;
	}

	/**
	 * 处理离线消息
	 * @param connection
	 * @param string
	 */
	public void dealOfflineMsg(XMPPConnection connection,String username){
		this.username = username;
		dealOfflineMsg(connection);
	}
	
	/**
	 * 
	 * 处理离线消息.
	 * 
	 * @param connection
	 * @author shimiso
	 * @update 2012-7-9 下午5:45:32
	 */
	public void dealOfflineMsg(XMPPConnection connection) {
		OfflineMessageManager offlineManager = new OfflineMessageManager(
				connection);
		int addTime = 1;
		Calendar c = Calendar.getInstance();
		String time;
		
		try {
			Iterator<org.jivesoftware.smack.packet.Message> it = offlineManager.getMessages();
			if(it!=null){//当离线消息不为空的时候，将当前用户名保存到sharePrence中//为了解决bug(当用户登录的时候，用户名还没有保存，就已经去取上个用户的数据库了。所以需要多加入一步操作)
				((ActivitySupport)context).getPreferences().edit()
				.putString(Constant.USERNAME, username).commit();
			}
			Log.i("离线消息数量: ", "" + offlineManager.getMessageCount());
			while (it.hasNext()) {
				org.jivesoftware.smack.packet.Message message = it.next();
				Log.i("收到离线消息", "Received from 【" + message.getFrom()
						+ "】 message: " + message.getBody());
				if (message != null && message.getBody() != null
						&& !message.getBody().equals("null")) {
					//String time = (String) message.getProperty(IMMessage.KEY_TIME);系统提供的方法
					c.add(Calendar.SECOND, addTime);//本地时间加上addtime的值;
					IMMessage msg = new IMMessage();
					time = DateUtil.date2Str(c.getTime(),Constant.MS_FORMART);//TODO   不知道传送过来的time是什么格式的，所以先不用传过来的time，本地给它初始化一个时间
					//msg.setTime(time == null ? DateUtil.getCurDateStr(addTime) : time);
					msg.setTime(time);
					msg.setContent(message.getBody());
					if (Message.Type.error == message.getType()) {
						msg.setType(IMMessage.ERROR);
					} else {
						msg.setType(IMMessage.SUCCESS);
					}
					String from = message.getFrom().split("/")[0];
					msg.setFromSubJid(from);

/*					// 生成通知
					NoticeManager noticeManager = NoticeManager
							.getInstance(context);
					Notice notice = new Notice();
					notice.setTitle("会话信息");
					notice.setNoticeType(Notice.CHAT_MSG);
					notice.setContent(message.getBody());
					notice.setFrom(from);
					notice.setStatus(Notice.UNREAD);
					notice.setNoticeTime(time == null ? DateUtil
							.getCurDateStr() : time);*/

					// 历史记录
					IMMessage newMessage = new IMMessage();
					newMessage.setMsgType(2);
					newMessage.setFromSubJid(from);
					newMessage.setContent(message.getBody());
					newMessage.setTime(time);
					//newMessage.setTime(time == null ? DateUtil.getCurDateStr(addTime): time);
					MessageManager.getInstance(context).saveIMMessage(
							newMessage);

/*					long noticeId = noticeManager.saveNotice(notice);
					if (noticeId != -1) {
						Intent intent = new Intent(Constant.NEW_MESSAGE_ACTION);
						intent.putExtra(IMMessage.IMMESSAGE_KEY, msg);
						intent.putExtra("noticeId", noticeId);
						context.sendBroadcast(intent);
						activitySupport.setNotiType(
								R.drawable.icon,
								context.getResources().getString(
										R.string.new_message),
								notice.getContent(), ChatActivity.class, from);
					}*/
				}
				addTime++;//为了不让时间一样
			}

			offlineManager.deleteMessages();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
