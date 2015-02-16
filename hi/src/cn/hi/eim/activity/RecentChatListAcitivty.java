package cn.hi.eim.activity;

import java.util.List;

import org.jivesoftware.smackx.packet.VCard;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.hi.eim.R;
import cn.hi.eim.activity.im.ChatActivity;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.ContacterManager;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.UserManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.ChartHisBean;
import cn.hi.eim.model.User;
import cn.hi.eim.util.StringUtil;
import cn.hi.eim.util.Utils;
import cn.hi.eim.view.RecentChartAdapter;
import cn.hi.eim.view.SwipeListView;

public class RecentChatListAcitivty extends ActivitySupport implements
		RecentChartAdapter.onRightItemClickListener {

	private SwipeListView inviteList;
	private ImageView rightBtn, pileCardImageView;
	private List<ChartHisBean> inviteNotices;
	private RecentChartAdapter noticeAdapter;
	private RelativeLayout titleLayout;
	private CyZoomInImagePopupWindow popupWindow;
	private int pileCardHeight;
	private int pileCardWeight;
	private int titleHeight = 48;// 标题栏的高度
	private int paddingTop = 20;
	private int screenWidth;
	private int screenHeight;
	private int widthSpace;
	private int heightSpace;
	private Point pildCardsPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacter_tab3);

		initPileCards();

		initViews();

		initListener();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.NEW_MESSAGE_ACTION);
		registerReceiver(receiver, filter);
	}

	/**
	 * 初始化牌堆的位置
	 */
	private void initPileCards() {
		DisplayMetrics dm = new DisplayMetrics();
		popupWindow = new CyZoomInImagePopupWindow(this);

		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		pileCardHeight = dm.heightPixels / 5;
		pileCardWeight = (pileCardHeight * 2) / 3;
		// 标题栏高度
		titleHeight = Utils.dip2px(this, titleHeight);

		// 窗口的宽度
		screenWidth = dm.widthPixels;
		// 窗口高度
		screenHeight = dm.heightPixels - pileCardHeight - titleHeight;

		widthSpace = (screenWidth - 60) / 2;
		heightSpace = (screenHeight - 60) / 2;

		// 牌堆位置
		pildCardsPosition = new Point((screenWidth - pileCardWeight) / 2,
				dm.heightPixels - paddingTop - pileCardHeight);
	}

	/*
	 * @Override protected void onPause() { // unregisterReceiver(receiver);
	 * super.onPause(); }
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	};

	/*
	 * @Override protected void onResume() { IntentFilter filter = new
	 * IntentFilter(); filter.addAction(Constant.NEW_MESSAGE_ACTION);
	 * registerReceiver(receiver, filter); super.onResume(); }
	 */

	@SuppressLint("ResourceAsColor")
	private void initViews() {
		inviteList = (SwipeListView) findViewById(R.id.main_invite_list);
		pileCardImageView = (ImageView) findViewById(R.id.pilecard_img);
		rightBtn = (ImageView) findViewById(R.id.rightBtn);
		rightBtn.setImageResource(R.drawable.setting);
		titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		titleLayout.setBackgroundColor(R.color.chat_send_msg);
		refreshDate(true);

	}

	private void initListener() {
		rightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, SettingAcitivty.class);
				context.startActivity(intent);
			}
		});

		pileCardImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, CardsActivity.class);
				context.startActivity(intent);
				// ((Activity) context).finish();
				// RecentChatListAcitivty.this.finish();
			}
		});
		// noticeAdapter.setOnRightItemClickListener(this);

	}

	/**
	 * 通知点击
	 */
	private OnClickListener contacterOnClickJ = new OnClickListener() {

		@Override
		public void onClick(View v) {
			User user = (User) v.findViewById(R.id.new_content).getTag();
			createChat(user);

			// 取消当前条目的离线消息的通知
			MessageManager.getInstance(context).updateTypeForOffLine(TextUtils.isEmpty(user.getJID())?user.getName():user.getJID());

		}
	};

	/**
	 * 创建一个聊天
	 * 
	 * @param user
	 */
	protected void createChat(User user) {
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra("to", user.getName());
		startActivity(intent);
	}

	/**
	 * 刷新当前数据
	 * @param isOpenPd  是否显示加载进度框
	 */
	private void refreshDate(final boolean isOpenPd) {

		inviteNotices = MessageManager.getInstance(context)
				.getRecentContactsWithLastMsg();
		
		setDateForListView();

	}

	/**
	 * 重新设置listview的数据
	 */
	public void setDateForListView() {
		noticeAdapter = new RecentChartAdapter(context, inviteNotices,
				inviteList.getRightViewWidth());
		noticeAdapter.setOnClickListener(contacterOnClickJ);
		inviteList.setAdapter(noticeAdapter);
		noticeAdapter.setOnRightItemClickListener(RecentChatListAcitivty.this);
		// noticeAdapter.notifyDataSetChanged();
	}

	/**
	 * 将user信息加载到chartHisbean中
	 * 
	 * @param inviteNotices2
	 */
	private void initUserList(List<ChartHisBean> inviteNotices) {
		User u;
		String jid;
		ChartHisBean notice;
		for (int i = 0; i < inviteNotices.size(); i++) {
			notice = inviteNotices.get(i);
			jid = notice.getFrom();
			u = notice.getUser();
			if (u == null) {
				try {
					jid = StringUtil.getJidByName(context, jid);
					u = ContacterManager
							.getByUserJid(jid, XmppConnectionManager
									.getInstance().getConnection());
					/** 获取用户信息 ***/
					VCard vCard = UserManager.getInstance(context)
							.getUserVCard(jid);
					u.setvCard(vCard);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (null == u) {// 表示抛出异常，需要自定义user对象
					u = new User();
					u.setJID(jid);
					u.setName(jid);
					u.setvCard(new VCard());
				}
				notice.setUser(u);
			}

		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			refreshDate(false);
		}

	};

	@Override
	public void onRightItemClick(View v, final int position) {

		MessageManager.getInstance(context).delChatHisWithSb(
				inviteNotices.get(position).getFrom());
		inviteNotices.remove(position);
		setDateForListView();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		inviteNotices.removeAll(inviteNotices);
		//ssetDateForListView();
		
		refreshDate(true);
	}
	@Override
	public void onBackPressed() {
		// 当按下返回键，并且任务栈只有一个任务了，并且当前剩下的任务不是登录界面，则直接返回到桌面
			goHome();
			MessageManager.getInstance(context).delAllChat(context);
	

	}
}
