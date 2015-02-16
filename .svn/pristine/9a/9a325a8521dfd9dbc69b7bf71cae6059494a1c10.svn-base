package cn.hi.eim.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class CyZoomInImagePopupWindow {
	private final int TAG_DISMISS = 9999; 
	class Position{
		int x;
		int y;
	}
	private PopupWindow popupWindow;
	private ImageView imgScale;
	private Drawable drawable;
	private View targetView;
	
	private int POP_WIDTH;
	private int POP_HEIGHT;
	
	
	private Position toPostion;
	private Position fromPosition;
	private float MULTIPLEX;
	private float MULTIPLEY;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == TAG_DISMISS)
			popupWindow.dismiss();
		};
	};

	public CyZoomInImagePopupWindow(Context context) {
		init(context);
	}

	private void init(Context context) {
		fromPosition = new Position();
		toPostion = new Position();
		
		DisplayMetrics dm=new DisplayMetrics();
		 ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		 POP_WIDTH =dm.widthPixels;
		 POP_HEIGHT = dm.heightPixels;
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);
		imgScale = new ImageView(context);
		layout.addView(imgScale);
		popupWindow = new PopupWindow(layout,WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
//		popupWindow.setBackgroundDrawable(null);
		layout.setFocusable(true);
		layout.setFocusableInTouchMode(true);
		layout.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK ){
					popupWindow.dismiss();
				}
				return false;
			}
		});
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				targetView.setBackgroundDrawable(drawable);
			}
		});
		
		layout.setBackgroundColor(Color.BLACK);
		layout.getBackground().setAlpha(150);
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				imgScale.clearAnimation();
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.setInterpolator(new OvershootInterpolator(1F));
				
				imgScale.setBackgroundDrawable(drawable);
				 ScaleAnimation scaleAnimation = new ScaleAnimation(MULTIPLEX,1,MULTIPLEY,1,
				 Animation.RELATIVE_TO_SELF, 0.5f,
				 Animation.RELATIVE_TO_SELF, 0.5f);
				 animationSet.addAnimation(scaleAnimation);
				 
				 TranslateAnimation translateAnimation = new TranslateAnimation(toPostion.x, fromPosition.x, toPostion.y, fromPosition.y);
				 animationSet.addAnimation(translateAnimation);
				 
				 animationSet.setDuration(400);
				 animationSet.setFillAfter(true);
				 imgScale.setAnimation(animationSet);
				 handler.sendEmptyMessageDelayed(TAG_DISMISS, 400);
				 //用这个监听处理dismiss会在4.1上报空指针错误，原因不明，现在的替换方案就是用handler来处理dismiss操作
//				animationSet.setAnimationListener(new AnimationListener() {
//					public void onAnimationStart(Animation animation) {}
//					public void onAnimationRepeat(Animation animation) {}
//					public void onAnimationEnd(Animation animation) {
//						popupWindow.dismiss();
//					}
//				});
			
			}

		});
	}
	
	/**
	 * 
	 * @param view   		�?��缩放的控�?	 * @param multipleX		x的缩�?	 * @param mutipleY		y的缩�?	 */
	public void zoomIn(View view,float multipleX, float mutipleY) {
		if (popupWindow == null || imgScale == null)
			return;
		MULTIPLEX = multipleX;
		MULTIPLEY = mutipleY;
		targetView = view;
		// 设置位置
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		params.width = view.getLayoutParams().width;
		params.height = view.getLayoutParams().height;
		int[] location = new int[2];
		//获取在整个屏幕内的绝对坐标，注意这个值是要从屏幕顶端算起，也就是包括了�?知栏的高度�?
		view.getLocationOnScreen(location);
	    int x = location[0];
	    int y = location[1];
		params.setMargins(x, y, 0, 0);
		imgScale.setLayoutParams(params);
		popupWindow.showAtLocation(view, 0, 0, 0);
		drawable = view.getBackground();
		view.setBackgroundDrawable(null);
		
		
		int width = view.getWidth();
		int height = view.getHeight();
		fromPosition.x = 0;
		fromPosition.y = 0;
		//先平移后放大
		toPostion.x =  (int) ((POP_WIDTH - width )/2) - x;
		//先平移后放大
		toPostion.y =  (int) ( (POP_HEIGHT - height)/2)  - y;
		
		// 放大
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.setInterpolator(new OvershootInterpolator(1F));
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, multipleX, 1, mutipleY,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		animationSet.addAnimation(scaleAnimation);
		//float fromXDelta 动画�?��的点离当前View X坐标上的差�? 
		//float toXDelta 动画结束的点离当前View X坐标上的差�? 
		//float fromYDelta 动画�?��的点离当前View Y坐标上的差�? 
		//float toYDelta 动画�?��的点离当前View Y坐标上的差�? 
		TranslateAnimation translateAnimation = new TranslateAnimation(fromPosition.x, toPostion.x, fromPosition.y , toPostion.y);
		animationSet.addAnimation(translateAnimation);
		
		animationSet.setDuration(500);
		animationSet.setFillAfter(true);
		imgScale.setBackgroundDrawable(drawable);
		imgScale.setAnimation(animationSet);
		
	}
	
	

}     