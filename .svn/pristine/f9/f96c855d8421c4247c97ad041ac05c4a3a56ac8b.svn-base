package com.yibairun.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yibairun.api.operations.YibaiRunApi;
import com.yibairun.application.AppController;
import com.yibairun.comm.Constant;
import com.yibairun.swipebacklayout.lib.app.SwipeBackActivity;
import com.yibairun.utils.Lg;
import com.yibairun.utils.MyDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class BaseActivity extends SwipeBackActivity implements
		OnClickListener {

	protected Context context;
	protected YibaiRunApi api;
	protected RelativeLayout pending_view;
	protected AppController appController;
	protected MyDialog pg;
	protected ActionBar bar;
	// protected ImageView iv_title_left, iv_title_right;
	// protected TextView tv_centerText, tv_title_left, tv_title_right;
	protected SharedPreferences systemShare;
	protected Lg lg = Lg.jLog();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setOverflowShowingAlways();
		context = this;
		appController = (AppController) context.getApplicationContext();
		pg = new MyDialog(context);
		api = AppController.getInstance().getApi();
		bar = getActionBar();
		// bar.setDisplayShowHomeEnabled(false);
		appController = (AppController) getApplication();
		appController.addActivity(this);
		systemShare = this.getSharedPreferences(Constant.SYSTEM_SHARE,
				MODE_PRIVATE);


	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initView();
        initDate();
        initListener();
    }

    /**
	 * 初始化view控件
	 */
	public abstract void initView();

	/**
	 * 初始化数据
	 */
	public abstract void initDate();

	/**
	 * 初始化监听器
	 */
	public abstract void initListener();

	protected void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
//			e.printStackTrace();
            lg.e(e.getMessage());
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	protected void startActivity(Class clas) {
		Intent intent = new Intent();
		intent.setClass(context, clas);
		context.startActivity(intent);
	}

	protected void toast(int resid) {
		toast(getString(resid));
	}

	protected void toast(String tips) {
		Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
	}

	protected void startActivity(Class clas, Intent intent) {
		intent.setClass(context, clas);
		context.startActivity(intent);
	}

	protected <T extends View> T findView(int id) {
		return (T) findViewById(id);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int keycode = keyCode;

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 登录页面跳转
	 * 
	 * @param intent
	 *            跳转到登录界面时需要传递的参数
	 */
	protected boolean gotoLogin(Intent intent) {
		if (TextUtils.isEmpty(appController.getAppKey())) {
			startActivity(LoginActivity.class, intent);
			return true;
		} else {
			return false;
		}
	}

	protected void jumpToWebView(int type){
		Intent intent = new Intent();
		intent.setClass(context, WebViewActivity.class);
		intent.putExtra(Constant.ACTIVITY_TYPE, type);
		context.startActivity(intent);
	}
	
	/**
	 * 隐藏键盘
	 * @param activity
	 */
	protected void hideSystemInput(Activity activity){
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);//  (WidgetSearchActivity是当前的Activity)
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
    	MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
    	MobclickAgent.onResume(this);
	}
}
