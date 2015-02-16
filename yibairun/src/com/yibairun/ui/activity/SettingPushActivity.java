package com.yibairun.ui.activity;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.yibairun.R;
import com.yibairun.comm.Constant;

public class SettingPushActivity extends BaseActivity {

	private TextView tv_morning, tv_afternoon, tv_evening;
	private ToggleButton tb_push_setting;
	private boolean push_setting;
	private int push_setting_time = 0;
	private Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_setting_push);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		tv_morning = findView(R.id.tv_morning);
		tv_afternoon = findView(R.id.tv_afternoon);
		tv_evening = findView(R.id.tv_evening);
		tb_push_setting = findView(R.id.tb_push_setting);
	}

	@Override
	public void initDate() {
      /*  bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.setting_push));*/
		setPushState();

	}

	@Override
	public void initListener() {
		tv_morning.setOnClickListener(this);
		tv_afternoon.setOnClickListener(this);
		tv_evening.setOnClickListener(this);
		tb_push_setting.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				saveState(Constant.PUSH_SETTING, isChecked);
				setPushState();
				setEnaable(isChecked);
			}
		});
	}

	/**
	 * 推送状态设置
	 */
	private void setPushState() {
		push_setting = systemShare.getBoolean(Constant.PUSH_SETTING, true);
		tb_push_setting.setChecked(push_setting);//设置开关的打开状态
		setEnaable(push_setting);//设置推送时间按钮是否可以被点击
		if (push_setting) {
			push_setting_time = systemShare.getInt(Constant.PUSH_SETTING_TIME,0);
			switch (push_setting_time) {
			case Constant.PUSH_TIME_MORNING:
				tv_morning.setSelected(true);
				break;
			case Constant.PUSH_TIME_AFTERNOON:
				tv_afternoon.setSelected(true);
				break;
			case Constant.PUSH_TIME_EVENING:
				tv_evening.setSelected(true);
				break;
			default:
				break;
			}
		}else{
			tb_push_setting.setSelected(false);
			resetState();
		}
	}
	
	/**
	 * sharepreferences的状态
	 * 
	 * @param saveName
	 * @param bool
	 */
	public void saveState(String saveName, boolean bool) {
		edit = systemShare.edit();
		edit.putBoolean(saveName, bool);
		edit.commit();// 保存数据信息
	}
	
	public void saveState(String saveName, int nums) {
		edit = systemShare.edit();
		edit.putInt(saveName, nums);
		edit.commit();// 保存数据信息
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_morning:
			resetState();
			tv_morning.setSelected(true);
			saveState(Constant.PUSH_SETTING_TIME, Constant.PUSH_TIME_MORNING);
			break;
		case R.id.tv_afternoon:
			resetState();
			tv_afternoon.setSelected(true);
			saveState(Constant.PUSH_SETTING_TIME, Constant.PUSH_TIME_AFTERNOON);
			break;
		case R.id.tv_evening:
			resetState();
			tv_evening.setSelected(true);
			saveState(Constant.PUSH_SETTING_TIME, Constant.PUSH_TIME_EVENING);
			break;
		default:
			break;
		}
	}

	/**
	 * 重置所有按钮的状态
	 */
	private void resetState() {
		tv_morning.setSelected(false);
		tv_afternoon.setSelected(false);
		tv_evening.setSelected(false);
	}

	/**
	 * 设置推送时间的点击状态
	 */
	private void setEnaable(boolean enable){
		tv_morning.setEnabled(enable);
		tv_afternoon.setEnabled(enable);
		tv_evening.setEnabled(enable);
	}
}