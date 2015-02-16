package cn.hi.eim.activity.im;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.packet.VCard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.hi.eim.R;
import cn.hi.eim.activity.EimApplication;
import cn.hi.eim.activity.LoginActivity;
import cn.hi.eim.activity.MainActivity;
import cn.hi.eim.activity.RecentChatListAcitivty;
import cn.hi.eim.manager.ContacterManager;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.UserManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.Expression;
import cn.hi.eim.model.IMMessage;
import cn.hi.eim.model.User;
import cn.hi.eim.util.FaceUtils;
import cn.hi.eim.util.FileUtil;
import cn.hi.eim.util.StringUtil;
import cn.hi.eim.util.SystemUtils;
import cn.hi.eim.view.ExpressionImageAdapter;
import cn.hi.eim.view.MyOnPageChangeListener;
import cn.hi.eim.view.MyPagerAdapter;

public class ChatActivity extends AChatActivity {
	private Context context;
	private ListView listView;
	private LinearLayout faceLayout;
	public LinearLayout indexLayout;
	private RelativeLayout titleLayout;
	private ViewPager faceVp;
	private ImageView titleBack, rightBtn, iv_status, plusImg;
	private TextView centerText, tvChatTitle, messageSendBtn = null;
	private EditText messageInput = null;
	private View listHead;
	private MessageListAdapter adapter = null;
	// private ImageButton userInfo;
	private Button listHeadButton;
	private PopupWindow popupWindow;
	private User user;// 聊天人
	private String to_name;
	private ArrayList<GridView> grids;
	private MyPagerAdapter myPagerAdapter;
	public static List<Expression> expressionList = new ArrayList<Expression>();
	public static ChatActivity self;
	private static final int columns = 6, rows = 3,
			pageExpressionCount = 3 * 6 - 1;
	int expression_wh = -1;
	private int recordCount, titleHeight;
	//private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		expression_wh = (int) this.getResources().getDimension(
				R.dimen.chat_expression_wh);
		//pd = this.getProgressDialog();
		context = this;
		self = this;
		initView();
		initDate();
		initListener();
	}

	/**
	 * 创建一个聊天
	 * 
	 * @param user
	 */
	protected void createChat() {
		Intent intent = new Intent(context, RecentChatListAcitivty.class);
		startActivity(intent);
	}

	private void initView() {
		faceLayout = (LinearLayout) findViewById(R.id.ll_expression);
		indexLayout = (LinearLayout) findViewById(R.id.ll_vp_selected_index);
		faceVp = (ViewPager) findViewById(R.id.vp_id);
		titleBack = (ImageView) findViewById(R.id.leftBtn);
		rightBtn = (ImageView) findViewById(R.id.rightBtn);
		plusImg = (ImageView) findViewById(R.id.plus_img);
		titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		centerText = (TextView) findViewById(R.id.base_title_tv);
		listView = (ListView) findViewById(R.id.chat_list);
		LayoutInflater mynflater = LayoutInflater.from(context);
		listHead = mynflater.inflate(R.layout.chatlistheader, null);
		listHeadButton = (Button) listHead.findViewById(R.id.buttonChatHistory);

		listHeadButton.setOnClickListener(chatHistoryCk);

		messageInput = (EditText) findViewById(R.id.chat_content);
		messageSendBtn = (TextView) findViewById(R.id.chat_sendbtn);

	}

	public void initDate() {
		try {
			String jid = StringUtil.getJidByName(context,to);
			user = ContacterManager.getByUserJid(jid, XmppConnectionManager
					.getInstance().getConnection());
			/**获取用户信息***/
			VCard vCard =UserManager.getInstance(context).getUserVCard(jid);
			user.setvCard(vCard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		listView.setCacheColorHint(0);
		//listView.addHeaderView(listHead);
		adapter = new MessageListAdapter(ChatActivity.this, getMessages(),
				listView);
		listView.setAdapter(adapter);

		faceVp.setOnPageChangeListener(new MyOnPageChangeListener());

		titleBack.setImageResource(R.drawable.back);
		rightBtn.setImageResource(R.drawable.more);
	/*	titleLayout.setBackgroundColor(getResources().getColor(
				R.color.chat_title_background))*/;

		initTitle();

		((EimApplication) getApplication())
				.setCurrentChatUser(new User(to_name));
	}

	private void initTitle() {
		if (null == user) {
			to_name = StringUtil.getUserNameByJid(to);
		} else {
			to_name = TextUtils.isEmpty(user.getName()) ? user.getJID() : user
					.getName();
		}
		centerText.setText(SystemUtils.getUserNameToLocal(to_name));

	}

	public void initListener() {
		titleBack.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		messageSendBtn.setOnClickListener(this);
		plusImg.setOnClickListener(this);
		messageInput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				faceLayout.postDelayed(new Runnable() {

					@Override
					public void run() {
						if (faceLayout.getVisibility() == View.VISIBLE) {
							faceLayout.setVisibility(View.GONE);
						}
					}
				}, 200);

				return false;
			}
		});

		/*listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				new AlertDialog.Builder(context).setTitle("确定要删除当前记录吗?")
				.setNeutralButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						List<IMMessage> messages = getMessages();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
				return false;
			}
		});*/
	}

	@Override
	protected void receiveNewMessage(IMMessage message) {

	}

	@Override
	protected void refreshMessage(List<IMMessage> messages) {

		adapter.refreshList(messages);
	}

	@Override
	protected void onResume() {

		super.onResume();

		adapter.refreshList(getMessages());
		if (null == myPagerAdapter) {
			expressionList = FaceUtils.initExpression();// 初始化表情布局
			List<List<Expression>> lists = initGridViewData();// 填充GridView数据
			grids = new ArrayList<GridView>();
			int gv_padding_lr = (int) getResources().getDimension(
					R.dimen.chat_gv_padding_lr);
			int gv_padding_bt = (int) getResources().getDimension(
					R.dimen.chat_gv_padding_bt);
			int gv_spacing = (int) getResources().getDimension(
					R.dimen.chat_gv_spacing);
			int chat_dot_margin_lr = (int) getResources().getDimension(
					R.dimen.chat_dot_margin_lr);
			int chat_dot_wh = (int) getResources().getDimension(
					R.dimen.chat_dot_wh);
			for (int i = 0; i < lists.size(); i++) {
				List<Expression> l = lists.get(i);
				if (null != l) {
					// --生成当前GridView--//
					final GridView gv = new GridView(this);
					gv.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					gv.setNumColumns(columns);
					gv.setGravity(Gravity.CENTER);
					gv.setPadding(gv_padding_lr, gv_padding_bt, gv_padding_lr,
							0);
					gv.setHorizontalSpacing(gv_spacing);
					gv.setVerticalSpacing(gv_spacing);
					ExpressionImageAdapter expressionImageAdapter = new ExpressionImageAdapter(
							this, l);
					gv.setAdapter(expressionImageAdapter);
					// --点击列表事件处理--//
					gv.setOnItemClickListener(new OnItemClickListener() {
						/*
						 * (non-Javadoc)
						 * 
						 * @see android.widget.AdapterView.OnItemClickListener#
						 * onItemClick(android.widget.AdapterView,
						 * android.view.View, int, long)
						 */
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Expression e = (Expression) gv
									.getItemAtPosition(arg2);
							int index = messageInput.getSelectionStart();
							Editable edit = messageInput.getEditableText();// 获取EditText的文字
							String content_all = edit.toString();
							String content_forward = content_all.substring(0,
									index);
							String reg = "\\[#[1-9][0-9]?\\]";
							if (e.getDrableCode() < 0) {// 点击删除按钮
								if (index > 0) {
									boolean delExpression = false;
									Pattern p = Pattern.compile(reg);
									Matcher matcher = p
											.matcher(content_forward);
									// 因为这里表情代码最长为5，所以这里减5
									boolean found = false;
									if (content_forward.length() >= 4) {// 如果光标前字符少于4个，说明不可能为表情
										if (content_forward.length() == 4) {
											found = matcher
													.find(content_forward
															.length() - 4);
										} else {
											found = matcher
													.find(content_forward
															.length() - 5);
										}
										if (found) {
											String flag = matcher.group();
											if (content_forward.substring(
													content_forward.length()
															- flag.length(),
													content_forward.length())
													.equals(flag)) {
												delExpression = true;
												edit.delete(
														index - flag.length(),
														index);
											}
										}
									}
									if (!delExpression) {
										edit.delete(index - 1, index);
									}
								}
							} else {
								ImageSpan imageSpan = new ImageSpan(FileUtil
										.getImageFromAssetsFile(
												context,
												"ico/face/e_"
														+ e.getDrableCode()
														+ ".png"));
								SpannableString spannableString = new SpannableString(
										e.getCode());
								spannableString.setSpan(imageSpan, 0, e
										.getCode().length(),
										Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

								if (index < 0 || index >= edit.length()) {
									edit.append(spannableString);
								} else {
									edit.insert(index, spannableString);
								}
							}
						}
					});
					grids.add(gv);
					// --生成索引图--//
					ImageView iv = new ImageView(this);
					android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(
							chat_dot_wh, chat_dot_wh);
					lp.leftMargin = chat_dot_margin_lr;
					lp.rightMargin = chat_dot_margin_lr;
					iv.setLayoutParams(lp);
					if (i == 0) {
						iv.setBackgroundResource(R.drawable.page_focused);
					} else {
						iv.setBackgroundResource(R.drawable.page_unfocused);
					}
					indexLayout.addView(iv);
				}
			}
			myPagerAdapter = new MyPagerAdapter(grids);
			faceVp.setAdapter(myPagerAdapter);
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// Log.e("main", "newintet:");
		setIntent(intent);
		// here we can use getIntent() to get the extra data.
	}

	@Override
	protected void onPause() {
		super.onPause();
		//listView.setVisibility(View.INVISIBLE);
		//centerText.setText("");
		((EimApplication) getApplication()).setCurrentChatUser(null);
	}

	/*
	 * @Override protected void onStop() { super.onStop();
	 * listView.removeAllViews(); }
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.leftBtn:
			createChat();
			break;
		case R.id.rightBtn:
			initPop(this.findViewById(R.id.title_layout));
			break;
		case R.id.chat_sendbtn:
			String message = messageInput.getText().toString();
			if ("".equals(message)) {
				Toast.makeText(ChatActivity.this, "不能为空", Toast.LENGTH_SHORT)
						.show();
			} else {

				try {
					sendMessage(message);
					messageInput.setText("");
				} catch (Exception e) {
					showToast("信息发送失败");
					messageInput.setText(message);
				}
				// closeInput();
			}

			break;
		case R.id.plus_img:
			if (faceLayout.getVisibility() == View.GONE) {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(this.getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				faceLayout.setVisibility(View.VISIBLE);
			} else {
				faceLayout.setVisibility(View.GONE);
			}
			break;
		case R.id.report_tv:
			Toast.makeText(context, "举报成功", Toast.LENGTH_SHORT).show();
			popupWindow.dismiss();
			break;
		case R.id.black_list_tv:
			Toast.makeText(context, "拉黑成功", Toast.LENGTH_SHORT).show();
			popupWindow.dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chat_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menu_return_main_page:
			intent.setClass(context, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.menu_relogin:
			intent.setClass(context, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.menu_exit:
			isExit();
			break;
		}
		return true;

	}

	/**
	 * 点击进入聊天记录
	 */
	private OnClickListener chatHistoryCk = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent in = new Intent(context, ChatHistoryActivity.class);
			in.putExtra("to", to);
			startActivity(in);
		}
	};

	/**
	 * 填充GridView所需要的数据
	 */
	private List<List<Expression>> initGridViewData() {
		List<List<Expression>> lists = new ArrayList<List<Expression>>();
		List<Expression> list = null;
		for (int i = 0; i < expressionList.size(); i++) {
			if (i % pageExpressionCount == 0) {// 一页数据已填充完成
				if (null != list) {
					list.add(new Expression(-1, "backSpace"));// 添加删除键
					lists.add(list);
				}
				list = new ArrayList<Expression>();
			}
			list.add(expressionList.get(i));
			// 最后一个表情，并且不是当前页最后一个表情时，后面添加删除键
			if (i >= expressionList.size() - 1) {
				list.add(new Expression(-1, "backSpace"));// 添加删除键
				lists.add(list);
			}
		}
		return lists;
	}

	/**
	 * 初始化popwindow
	 */
	public void initPop(View parent) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		View view = View.inflate(context, R.layout.chat_pop_item, null);
		view.findViewById(R.id.report_tv).setOnClickListener(this);
		view.findViewById(R.id.black_list_tv).setOnClickListener(this);
		int[] location = new int[2];
		int popWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.popupWindow_width);
		parent.getLocationOnScreen(location);
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setHeight((titleLayout.getHeight() * 3) / 2);
		popupWindow.setWidth((dm.widthPixels * 3) / 10);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int xPos = (int) (windowManager.getDefaultDisplay().getWidth() - popupWindow
				.getWidth());
		popupWindow.showAsDropDown(parent, xPos, 0);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		createChat();
	}
	private class MessageListAdapter extends BaseAdapter {

		private List<IMMessage> items;
		private Context context;
		private ListView adapterList;
		private LayoutInflater inflater;

		public MessageListAdapter(Context context, List<IMMessage> items,
				ListView adapterList) {
			this.context = context;
			this.items = items;
			this.adapterList = adapterList;
		}

		public void refreshList(List<IMMessage> items) {
			this.items = items;
			this.notifyDataSetChanged();
			adapterList.setSelection(items.size() - 1);
			Log.e("main", "size:"+items.size());
		}

		final Html.ImageGetter imageGetter_resource = new Html.ImageGetter() {
			public Drawable getDrawable(String source) {
				Drawable drawable = null;
				source = FaceUtils.getImgNum(source);
				drawable = FileUtil.getImageFromAssetsToDrawable(context,
						"ico/face/e_" + source + ".png");
				drawable.setBounds(0, 0, expression_wh, expression_wh);// 设置显示的图像大小
				return drawable;
			};
		};

		@Override
		public int getCount() {
			return items == null ? 0 : items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			IMMessage message = items.get(position);
			String content = FaceUtils.chineseConvert(FaceUtils.replaceSpaceToCode(message.getContent()));
			if (message.getMsgType() == 1) {
				convertView = this.inflater.inflate(
						R.layout.formclient_chat_out, null);
			} else {
				convertView = this.inflater.inflate(
						R.layout.formclient_chat_in, null);
			}
			TextView useridView = (TextView) convertView
					.findViewById(R.id.formclient_row_userid);
			TextView dateView = (TextView) convertView
					.findViewById(R.id.formclient_row_date);
			TextView msgView = (TextView) convertView
					.findViewById(R.id.formclient_row_msg);
			ImageView headImg = (ImageView) convertView
					.findViewById(R.id.from_head);

			if (message.getMsgType() == 1) {
				// useridView.setText("我");
				headImg.setImageBitmap(FileUtil.getImageFromAssetsFile(context,
						eimApplication.getUser().getvCard().getField("avatar")));
			} else {
				if (null == user) {
					// useridView.setText(StringUtil.getUserNameByJid(to));
				} else {
					// useridView.setText(user.getName());
					headImg.setImageBitmap(FileUtil.getImageFromAssetsFile(
							context, user.getvCard().getField("avatar")));
				}

			}
			dateView.setText(message.getTime());
			msgView.setText(Html.fromHtml(content, imageGetter_resource, null));
			return convertView;
		}

	}
}
