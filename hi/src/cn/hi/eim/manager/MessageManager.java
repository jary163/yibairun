package cn.hi.eim.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hi.eim.R;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.db.DBManager;
import cn.hi.eim.db.SQLiteTemplate;
import cn.hi.eim.db.SQLiteTemplate.RowMapper;
import cn.hi.eim.model.ChartHisBean;
import cn.hi.eim.model.IMMessage;
import cn.hi.eim.model.Notice;
import cn.hi.eim.model.User;
import cn.hi.eim.util.FileUtil;
import cn.hi.eim.util.StringUtil;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * ��Ϣ��ʷ��¼��
 * 
 * @author shimiso
 */
public class MessageManager {
	private static MessageManager messageManager = null;
	private static DBManager manager = null;
	private static String databaseName;

	private MessageManager(Context context) {
		SharedPreferences sharedPre = context.getSharedPreferences(
				Constant.LOGIN_SET, Context.MODE_PRIVATE);
		databaseName = sharedPre.getString(Constant.USERNAME, null);
		manager = DBManager.getInstance(context, databaseName);
	}

	public static MessageManager getInstance(Context context) {
		//if (messageManager == null) {
			messageManager = new MessageManager(context);//Ϊʲôû����ȫ�˳�
		//}
		return messageManager;
	}

	/**
	 * 
	 * ������Ϣ.
	 * 
	 * @param msg
	 * @author shimiso
	 * @update 2012-5-16 ����3:23:15
	 */
	public long saveIMMessage(IMMessage msg) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		if (StringUtil.notEmpty(msg.getContent())) {
			contentValues.put("content", StringUtil.doEmpty(msg.getContent()));
		}
		if (StringUtil.notEmpty(msg.getFromSubJid())) {
			contentValues.put("msg_from",
					StringUtil.doEmpty(msg.getFromSubJid()));
		}
		contentValues.put("msg_type", msg.getMsgType());
		contentValues.put("msg_time", msg.getTime());
		return st.insert("im_msg_his", contentValues);
	}
	public long saveUser(User user) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		if(getUserByJid(user.getJID())!=null)//�����ݿ���ڵ�ǰ��¼ʱ������������
			return 0;
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", user.getJID());
		contentValues.put("avatar", user.getAvatar());
		contentValues.put("gender", user.getGender());
		return st.insert("user", contentValues);
	}
	
	/**
	 * �ӱ������ݿ��ȡuser��Ϣ
	 * @param jid
	 * @return
	 */
	public User getUserByJid(String jid){
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.queryForObject(new RowMapper<User>() {

			@Override
			public User mapRow(Cursor cursor, int index) {
				User user = new User();
				user.setName(cursor.getString(cursor.getColumnIndex("name")));
				user.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
				user.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
				return user;
			}
		}, "select * from user where name=?", new String[]{jid});
	}
	
	

	/**
	 * 
	 * ����״̬.
	 * 
	 * @param status
	 * @author shimiso
	 * @update 2012-5-16 ����3:22:44
	 */
	public void updateStatus(String id, Integer status) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues contentValues = new ContentValues();
		contentValues.put("status", status);
		st.updateById("im_msg_his", id, contentValues);
	}

	/**
	 * 
	 * ������ĳ�˵������¼�����¼
	 * 
	 * @param pageNum
	 *            �ڼ�ҳ
	 * @param pageSize
	 *            Ҫ��ļ�¼����
	 * @return
	 * @author shimiso
	 * @update 2012-7-2 ����9:31:04
	 */
	public List<IMMessage> getMessageListByFrom(String fromUser, int pageNum,
			int pageSize) {
		if (StringUtil.empty(fromUser)) {
			return null;
		}
//		int fromIndex = (pageNum - 1) * pageSize;
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		List<IMMessage> list = st.queryForList(
				new RowMapper<IMMessage>() {
					@Override
					public IMMessage mapRow(Cursor cursor, int index) {
						IMMessage msg = new IMMessage();
						msg.setContent(cursor.getString(cursor
								.getColumnIndex("content")));
						msg.setFromSubJid(cursor.getString(cursor
								.getColumnIndex("msg_from")));
						msg.setMsgType(cursor.getInt(cursor
								.getColumnIndex("msg_type")));
						msg.setTime(cursor.getString(cursor
								.getColumnIndex("msg_time")));
						return msg;
					}
				},
//				"select content,msg_from, msg_type,msg_time from im_msg_his where msg_from=? and content!= '' order by msg_time desc limit ? , ? ",
				"select content,msg_from, msg_type,msg_time from im_msg_his where msg_from=? and content!= '' order by msg_time desc  ",//��ѯ���������¼����ҳ����Ϊ���ı����ٵĻ��Ʋ������̫��ļ�¼
//				new String[] { "" + fromUser, "" + fromIndex, "" + pageSize });
		new String[] { "" + fromUser });
				
		return list;

	}

	/**
	 * 
	 * ������ĳ�˵������¼����
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-7-2 ����9:31:04
	 */
	public int getChatCountWithSb(String fromUser) {
		if (StringUtil.empty(fromUser)) {
			return 0;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st
				.getCount(
						"select _id,content,msg_from msg_type  from im_msg_his where msg_from=?",
						new String[] { "" + fromUser });

	}

	/**
	 * ɾ����ĳ�˵������¼ author shimiso
	 * 
	 * @param fromUser
	 */
	public int delChatHisWithSb(String fromUser) {
		if (StringUtil.empty(fromUser)) {
			return 0;
		}
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.deleteByCondition("im_msg_his", "msg_from=?",
				new String[] { "" + fromUser });
	}

	
	/**
	 * ���µ�ǰ�û��������˵������¼
	 */
	public void updateChatForEveryOne(){
		//ɾ����ͬ�ļ�¼�����������һ��
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		st.execSQL("delete from im_msg_his  where not exists (select notice_from from im_notice  where im_msg_his.msg_from=im_notice.notice_from  )");
		String sql = "delete from im_msg_his where _id not in (select * from (select  max(_id) from im_msg_his group by msg_from) as im_msg_his)";
		st.execSQL(sql);
		
		//�����еļ�¼�����ÿ�
		ContentValues values = new ContentValues();
		values.put("content", "");
		st.update("im_msg_his", values, null,null);

	}
	
	
	/**
	 * ��������Ϣ��Ϊ�Ѷ�(msg_type��״̬0��ʾ)
	 * @param from  ��Ϣ��Դ
	 */
	public void updateTypeForOffLine(String from){
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues values = new ContentValues();
		values.put("msg_type", "0");
		st.update("im_msg_his", values, "msg_from = ? and msg_type = '2'",new String[]{from});
	}
	
	/**
	 * 
	 * ��ȡ����������������һ����Ϣ��δ����Ϣ����
	 * 
	 * @return
	 * @author shimiso
	 * @update 2012-5-16 ����3:22:53
	 */
	public List<ChartHisBean> getRecentContactsWithLastMsg() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		List<ChartHisBean> list = st
				.queryForList(
						new RowMapper<ChartHisBean>() {

							@Override
							public ChartHisBean mapRow(Cursor cursor, int index) {
								ChartHisBean notice = new ChartHisBean();
								User u = new User();
								notice.setId(cursor.getString(cursor
										.getColumnIndex("_id")));
								notice.setContent(cursor.getString(cursor
										.getColumnIndex("content")));
								notice.setFrom(cursor.getString(cursor
										.getColumnIndex("msg_from")));
								notice.setNoticeTime(cursor.getString(cursor
										.getColumnIndex("msg_time")));
								u.setName(notice.getFrom());
								u.setAvatar(cursor.getString(cursor
										.getColumnIndex("avatar")));
								u.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
								notice.setUser(u);
								return notice;
							}
						},
//						"select m.[_id],m.[content],m.[msg_time],m.msg_from from im_msg_his  m join (select msg_from,max(msg_time) as time from im_msg_his group by msg_from) as tem  on  tem.time=m.msg_time and tem.msg_from=m.msg_from order by msg_time desc",
						"select m.[_id] _id,m.[content] content,m.[msg_time] msg_time,m.[msg_from] msg_from,u.[avatar] avatar,u.[gender] gender from im_msg_his  m , (select msg_from,max(msg_time) as time from im_msg_his group by msg_from)  tem ,(select name,avatar,gender from user)  u where tem.time=m.msg_time and tem.msg_from=m.msg_from and u.[name] =m.msg_from order by msg_time desc",
						null);
		for (ChartHisBean b : list) {
			//int count = st.getCount("select _id from im_notice where status=? and type=? and notice_from=?",new String[] { "" + Notice.UNREAD,"" + Notice.CHAT_MSG, b.getFrom() });
			int count = st.getCount("select * from im_msg_his where msg_from=? and msg_type=? and content!=''", new String[]{""+b.getFrom(),"2"});
			b.setNoticeSum(count);
		}
		return list;
	}
	
	
	/**
	 * ɾ�����е������¼,�ͱ���һ����¼
	 */
	public void delAllChat(Context mContext){
		
		updateChatForEveryOne();
	}


}
