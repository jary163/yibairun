package cn.hi.eim.activity;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.hi.eim.R;
import cn.hi.eim.ToChatActivity;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.exception.ErrorMessage;
import cn.hi.eim.manager.HIManager;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.NoticeManager;
import cn.hi.eim.model.Cards;
import cn.hi.eim.model.User;
import cn.hi.eim.util.Utils;
import cn.hi.eim.util.VolleyErrorHelper;
import cn.hi.eim.view.CardView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

@SuppressLint("ShowToast")
public class CardsActivity extends ActivitySupport {

	private ViewGroup center_layout;
	private Context context;
	private ImageView pileCard, rightBtn,maskingView;
	private int screenWidth;
	private int screenHeight;
	private int widthSpace, heightSpace;
	private int pileCardHeight;// 牌堆的高度
	private int pileCardWeight;// 牌堆的宽度
	private int titleHeight = 48;// 标题栏的高度
	private int paddingLeft = 20, paddingTop = 20;
	private Cards[] cards = new Cards[4]; // 可移动卡片
	private Cards[] backgroundCards = new Cards[4]; // 卡片背景
	private int delayTime = 100;// 延迟播放动画
	private int kingFrontImg = R.drawable.king_front;
	private int queenFrontImg = R.drawable.queen_front;
	private int kingBackImg = R.drawable.k_back;
	private int queenBackImg = R.drawable.q_back;
	private int backGroundKingImg = R.drawable.poker_king;
	private int backGroundQueenImg = R.drawable.poker_queen;
	private Point cardsPosition, pildCardsPosition;
	private AnimationSet mAnimationSet;
//	private CyZoomInImagePopupWindow popupWindow;
	
	protected User[] currentUsers;
	// protected User[] lastUsers;
	private boolean isBoy;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cards);
		context = this;
		pd = this.getProgressDialog();
		center_layout = (ViewGroup) findViewById(R.id.center_layout);
		rightBtn = (ImageView) findViewById(R.id.rightBtn);
		DisplayMetrics dm = new DisplayMetrics();
//		popupWindow = new CyZoomInImagePopupWindow(this);

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
		Utils.cardWidth = widthSpace;
		heightSpace = (screenHeight - 60) / 2;
		Utils.cardHeight = heightSpace;

		// 牌堆位置
		pildCardsPosition = new Point((screenWidth - pileCardWeight) / 2,
				dm.heightPixels - paddingTop - pileCardHeight);

		// 卡片起始位置
		cardsPosition = new Point((screenWidth - widthSpace) / 2,
				dm.heightPixels - paddingTop - heightSpace);


		// 初始化四个view控件
		initTitle();
		initViews();
		initCards();

		// 通过不同的顺序来安排牌的覆盖顺序
		addViews();

		//添加蒙版
		addMasking();
		// 开始动画
		// startAnimation();
		// getUserFromService();
	}

	/**
	 * 蒙版添加
	 */
	private void addMasking() {
		if(preferences.getBoolean(Constant.IS_FIRSTSTART, true)){
			maskingView = new ImageView(this);
			maskingView.setBackgroundResource(R.drawable.hi_masking);
			RelativeLayout.LayoutParams pileCardParams = new RelativeLayout.LayoutParams(
					android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
					android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
			maskingView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					center_layout.removeView(maskingView);
				}
			});
			center_layout.addView(maskingView, pileCardParams);
			preferences.edit().putBoolean(Constant.IS_FIRSTSTART, false).commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// currentUsers = null;
		// lastUsers = null;
		tryInitUser();
		resertCardsView();
		// getUserFromService();
	};

	/**
	 * 试图去初始化user
	 */
	private void tryInitUser() {
		String userName;
		eimApplication.getUser();
		if(eimApplication.getUser()==null&&context!=null){
			userName = preferences.getString(Constant.USERNAME, "");//从sharepre获取user
			User user = MessageManager.getInstance(context).getUserByJid(userName);//从数据库中获取user对象
			eimApplication.setUser(user);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	public void onBackPressed() {
		goHome();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}

	private Listener<User[]> createMyReqSuccessListener() {
		return new Listener<User[]>() {
			@Override
			public void onResponse(User[] response) {
				pd.dismiss();
				currentUsers = response;
					matchUserCards();
					startAnimation();
				
				// lastUsers = response;
			}
		};
	}

	protected Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				pd.dismiss();
				/*ErrorMessage msg = (ErrorMessage) error;*/
				Toast.makeText(context,VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT)
						.show();
			}
		};
	}

	/**
	 * 添加需要的布局对象
	 */
	private void addViews() {
		for (int i = 3; i >= 0; i--) {
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
					android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.topMargin = backgroundCards[i].getTop();
			layoutParams.leftMargin = backgroundCards[i].getLeft();
			layoutParams.height = backgroundCards[i].getHeight();
			layoutParams.width = backgroundCards[i].getWidth();
			center_layout.addView(backgroundCards[i].getView(), layoutParams);
			center_layout.addView(cards[i].getView());
		}

	}

	/**
	 * 重置所有view控件的初始状态
	 */

	private void resertCardsView() {
		resertBackgroundResource();
		// 设置可移动card初始化位置
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.topMargin = cardsPosition.y;
		layoutParams.leftMargin = cardsPosition.x;
		// layoutParams.height = heightSpace;
		// layoutParams.width = widthSpace;

		for (int i = 0; i < 4; i++) {
			cards[i].getView().setLayoutParams(layoutParams);
			// cards[i].getView().setBackgroundResource(defaultImg);
			cards[i].getView().setTag(false);// 设置默认图片的初始状态
			cards[i].getView().setVisibility(View.GONE);
		}

	}

	/**
	 * 重置所有卡片的布局背景
	 */
	private void resertBackgroundResource() {
		// 获取当前用户的性别
		isBoy = eimApplication.getUser().getGender() == 1 ? true : false;
		if (settingPreferences.getBoolean("sex_setting", true)) {
			if (isBoy) {
				for (int i = 0; i < 4; i++) {
					cards[i].getView().setBackgroundResource(queenBackImg);
					backgroundCards[i].getView().setBackgroundResource(
							backGroundQueenImg);
				}
			} else {
				for (int i = 0; i < 4; i++) {
					cards[i].getView().setBackgroundResource(kingBackImg);
					backgroundCards[i].getView().setBackgroundResource(
							backGroundKingImg);
				}
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (i <= 1) {//TODO  头两个放入皇后，后两个放入皇帝
					cards[i].getView().setBackgroundResource(queenBackImg);
					backgroundCards[i].getView().setBackgroundResource(
							backGroundQueenImg);
				} else {
					cards[i].getView().setBackgroundResource(kingBackImg);
					backgroundCards[i].getView().setBackgroundResource(
							backGroundKingImg);
				}
			}
		}
	}

	/**
	 * 初始化标题
	 */
	private void initTitle() {
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setImageResource(R.drawable.setting);
		rightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, SettingAcitivty.class);
				context.startActivity(intent);
			}
		});
	}

	/**
	 * 初始化imageview控件
	 */
	private void initViews() {
		// 初始化牌堆的位置
		pileCard = new ImageView(this);
		pileCard.setBackgroundResource(R.drawable.card_button);
		RelativeLayout.LayoutParams pileCardParams = new RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		pileCardParams.topMargin = pildCardsPosition.y;
		pileCardParams.leftMargin = pildCardsPosition.x;
		pileCardParams.height = pileCardHeight;
		pileCardParams.width = pileCardWeight;
		pileCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getUserFromService();
			}
		});
		center_layout.addView(pileCard, pileCardParams);
	}

	/**
	 * 初始化卡片
	 */
	private void initCards() {
		int num = 0;
		CardView cardView;
		ImageView backgroundImageView;
		for (int i = 1; i < 3; i++) {
			for (int j = 1; j < 3; j++) {
				cardView = new CardView(this);
				// cardView.setBackgroundResource(defaultImg);
				cardView.setOnClickListener(new ImgViewListener());
				// cardView.setVisibility(View.GONE);//默认视图为隐藏

				backgroundImageView = new ImageView(this);
				// backgroundImageView.setBackgroundResource(backGroundImg);
				backgroundImageView.setVisibility(View.VISIBLE);

				cards[num] = new Cards(paddingLeft * j + widthSpace * (j - 1),
						paddingTop * i + heightSpace * (i - 1) + titleHeight,
						widthSpace, heightSpace, cardView);

				backgroundCards[num] = new Cards(paddingLeft * j + widthSpace
						* (j - 1), paddingTop * i + heightSpace * (i - 1)
						+ titleHeight, widthSpace, heightSpace,
						backgroundImageView);
				num++;
			}
		}
	}

	/**
	 * 从服务器获取user对象
	 */
	private void getUserFromService() {
		pd.setMessage("正在为您匹配好友...");
		pd.show();
		User user = eimApplication.getUser();
		if (settingPreferences.getBoolean("sex_setting", true)) {
			HIManager.getInstance().getDefaultHashtagTweets(
					createMyReqSuccessListener(), createMyReqErrorListener(),
					String.valueOf(3), String.valueOf(user.getGender()),user.getJID());
			// user.getvCard().getField("gender"),user.getJID());
		} else {
			HIManager.getInstance().getDefaultHashtagTweets(
					createMyReqSuccessListener(), createMyReqErrorListener(),
					String.valueOf(3), String.valueOf(-1),
					eimApplication.getUser().getJID());
		}
	}

	/**
	 * 将user和card类对应上，方便卡片翻转之后card类直接使用其user对象
	 */
	private void matchUserCards() {
		if (currentUsers.length == 1) {
			for (int i = 0; i < 4; i++) {
				((CardView) cards[i].getView()).setUser(currentUsers[0]);
			}
		} else {
			//有问题，当服务器还没有返回数据的时候，就已经初始化背景图片了，所以当服务器返回数据之后，需要人工介入，干扰返回的user顺序.满足(头两个放入皇后，后两个放入皇帝的顺序)
			if(currentUsers[0].getGender()==0){
				((CardView) cards[0].getView()).setUser(currentUsers[0]);
				((CardView) cards[1].getView()).setUser(currentUsers[0]);
				((CardView) cards[2].getView()).setUser(currentUsers[1]);
				((CardView) cards[3].getView()).setUser(currentUsers[1]);
			}else{
				((CardView) cards[0].getView()).setUser(currentUsers[1]);
				((CardView) cards[1].getView()).setUser(currentUsers[1]);
				((CardView) cards[2].getView()).setUser(currentUsers[0]);
				((CardView) cards[3].getView()).setUser(currentUsers[0]);
			}

		}
		Log.e("main", "user:" + currentUsers[0].getAvatar());
	}

	/**
	 * 开始执行所有的动画
	 */
	private void startAnimation() {
		// 重置卡片的位置
		resertCardsView();
		setClickAble(false);// 将view可点击状态设置成false;
		// 执行动画
		new AnimationAsyTask().execute(0, delayTime);
		new AnimationAsyTask().execute(1, delayTime + 100);
		new AnimationAsyTask().execute(2, delayTime + 150);
		new AnimationAsyTask().execute(3, delayTime + 200);
		center_layout.postDelayed(new Runnable() {

			@Override
			public void run() {
				setClickAble(true);
			}
		}, (delayTime * 4 + 800));// 等动画执行完了之后再将可点击状态打开
	}

	/**
	 * 位移动画
	 */
	private void startTranslateAnimation(final float fromXDelta,
			final float toXDelta, final float fromYDelta, final float toYDelta,
			final View view) {
		TranslateAnimation anim = new TranslateAnimation(fromXDelta
				- cardsPosition.x, toXDelta - cardsPosition.x, fromYDelta
				- cardsPosition.y, toYDelta - cardsPosition.y);
		anim.setDuration(500);
		anim.setFillAfter(true);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// 开始之前播放音乐；
				if (eimApplication.isVideoState()) {
					Utils.PreapareMusic(context, "music/449.wav");
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {// 实际改变view控件的位置

				int left = view.getLeft()
						+ (int) ((toXDelta - cardsPosition.x) - (fromXDelta - cardsPosition.x));
				int top = view.getTop()
						+ (int) ((toYDelta - cardsPosition.y) - (fromYDelta - cardsPosition.y));
				int width = view.getWidth();
				int height = view.getHeight();
				view.clearAnimation();
				/* view.layout(left, top, left + width, top + height); */
				RelativeLayout.LayoutParams pileCardParams = new RelativeLayout.LayoutParams(
						android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
						android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
				pileCardParams.topMargin = top;
				pileCardParams.leftMargin = left;
				// pileCardParams.height = height;
				// pileCardParams.width = width;
				view.setLayoutParams(pileCardParams);// 重新绘制view的宽高

			}
		});
		view.startAnimation(anim);
	}

	/**
	 * 翻转动画
	 */
	public void turnAnimation(final View v) {
		final CardView cardView = ((CardView) v);
		// 通过AnimationUtils得到动画配置文件(/res/anim/back_scale.xml)
		Animation animation = AnimationUtils.loadAnimation(CardsActivity.this,
				R.anim.back_scale);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				User user = cardView.getUser();
				cardView.setBackgroundResource(cardView.getUser().getGender() == 0 ? queenFrontImg
						: kingFrontImg); // 执行翻转动画
				cardView.initUser(); // 显示用户信息
				v.startAnimation(AnimationUtils.loadAnimation(
						CardsActivity.this, R.anim.front_scale));
				v.setTag(true);
				setOtherCardsClickAble(); // 设置其他卡片不可被点击
			}
		});
		v.startAnimation(animation);
	}

	/**
	 * 当某张卡片被点开之后，将其他卡片设置成不可被点击
	 */
	public void setOtherCardsClickAble() {
		for (int i = 0; i < 4; i++) {
			if (!(Boolean) cards[i].getView().getTag()) {
				cards[i].getView().setClickable(false);
				cards[i].getView().setFocusable(false);
			}
		}
	}

	/**
	 * 设置可点击状态
	 * 
	 * @param clickAble
	 */
	public void setClickAble(boolean clickAble) {
		pileCard.setClickable(clickAble);
		pileCard.setFocusable(clickAble);
		for (int i = 0; i < 4; i++) {
			cards[i].getView().setClickable(clickAble);
			cards[i].getView().setFocusable(clickAble);
		}
	}

	/**
	 * 点击监听器
	 * 
	 * @author Administrator
	 * 
	 */
	class ImgViewListener implements OnClickListener {
		@Override
		public void onClick(final View v) {

			if ((Boolean) v.getTag()) { // 只执行放大，缩小操作

				// popupWindow.zoomIn(v, (float) 2, 2);

			} else { // 执行翻转动画
				if (currentUsers == null) {
					Toast.makeText(context, "系统正在给您寻觅佳音，请稍等一会儿哦！",
							Toast.LENGTH_LONG).show();
				} else {
					turnAnimation(v);

					v.postDelayed(new Runnable() {

						@Override
						public void run() {
							Intent intent = new Intent();
							Bundle bundle = new Bundle();
							User user = ((CardView) v).getUser();
							bundle.putSerializable(User.userKey, user);
							intent.putExtras(bundle);
							intent.setClass(context, ToChatActivity.class);
							startActivity(intent);
						}
					}, 500);
				}
			}
		}

	}

	
	/*  @Override public void onBackPressed() { 
		  super.onBackPressed(); isExit();
	}
	*/

	/**
	 * 延时执行动画
	 */
	class AnimationAsyTask extends AsyncTask<Integer, Void, Void> {
		private int delay, currentView;// 延迟时间，当前执行的card
		private View view;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Integer... params) {
			currentView = params[0];
			delay = params[1];
			SystemClock.sleep(delay);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			view = cards[currentView].getView();
			view.setVisibility(View.VISIBLE);
			startTranslateAnimation(cardsPosition.x,
					cards[currentView].getLeft(), cardsPosition.y,
					cards[currentView].getTop(), view);
		}
	}

}
