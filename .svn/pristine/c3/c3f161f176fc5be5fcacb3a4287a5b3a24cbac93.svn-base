package cn.hi.eim.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import cn.hi.eim.R;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.XmppConnectionManager;

public class SettingAcitivty extends ActivitySupport {

	private ImageView leftBtn, pileCardImageView;
	private TextView centerText;
	private RelativeLayout titleLayout,aboutUs,logoffLayout;
	private ToggleButton pushSwitch, sexSwitch, videoSwitch;
	private Editor edit;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initViews();

		initDate();

		initListener();
	}

	private void initViews() {
		aboutUs = (RelativeLayout) findViewById(R.id.about_us);
		titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		pushSwitch = (ToggleButton) findViewById(R.id.push_switch);
		sexSwitch = (ToggleButton) findViewById(R.id.oterhsex_switch);
		videoSwitch = (ToggleButton) findViewById(R.id.video_switch);
		centerText = (TextView) findViewById(R.id.base_title_tv);
		leftBtn = (ImageView) findViewById(R.id.leftBtn);
		logoffLayout = (RelativeLayout) findViewById(R.id.logoff_layout);
	}

	@SuppressLint("ResourceAsColor")
	private void initDate() {
		titleLayout.setBackgroundColor(R.color.chat_title_background);
		leftBtn.setImageResource(R.drawable.back);
		centerText.setText(getString(R.string.setting));
		sexSwitch.setChecked(settingPreferences.getBoolean(Constant.SETTING_SEX, true));
		videoSwitch.setChecked(settingPreferences.getBoolean(Constant.SETTING_VIDEO, true));
		pushSwitch.setChecked(settingPreferences.getBoolean(Constant.SETTING_PUSH, true));
		eimApplication.setSexState(settingPreferences.getBoolean(Constant.SETTING_SEX, true));
		eimApplication.setVideoState(settingPreferences.getBoolean(Constant.SETTING_VIDEO, true));
		eimApplication.setPushState(settingPreferences.getBoolean(Constant.SETTING_PUSH, true));
		
		pd = getProgressDialog();
		
	}

	private void initListener() {
		leftBtn.setOnClickListener(this);
		aboutUs.setOnClickListener(this);
		sexSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				saveState(Constant.SETTING_SEX, isChecked);
				eimApplication.setSexState(isChecked);
			}
		});
		videoSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				saveState(Constant.SETTING_VIDEO, isChecked);
				eimApplication.setVideoState(isChecked);
			}
		});
		pushSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				saveState(Constant.SETTING_PUSH, isChecked);
				eimApplication.setPushState(isChecked);
			}
		});
		logoffLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new logOffAsyTask().execute();
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.leftBtn:
			this.finish();
			break;
		case R.id.about_us:
			Intent intent = new Intent(context,AboutUsAcitivty.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 保存多选框选择状态
	 * 
	 * @param saveName
	 * @param bool
	 */
	public void saveState(String saveName, boolean bool) {
		edit = settingPreferences.edit();
		edit.putBoolean(saveName, bool);
		edit.commit();// 保存数据信息
	}
	
	class logOffAsyTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setTitle("请稍等");
			pd.setMessage("正在注销...");
			pd.show();
			stopService();//暂停所有服务
			((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();//取消当前用户所有的通知
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			XmppConnectionManager.getInstance().disconnect();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			Intent intent = new Intent();
			intent.setClass(context, LoginActivity.class);
			intent.putExtra(Constant.LOGIN_ACTION, Constant.LOGIN_ACTION_LOGOFF);
			startActivity(intent);
			finish();
		}
	}
}
