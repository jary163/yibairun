package com.yibairun.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.bean.StatusMessage;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;
import com.yibairun.utils.BankUtils;
import com.yibairun.utils.MyCountDownTimer;
import com.yibairun.utils.UiUtil;
import com.yibairun.utils.VolleyErrorHelper;

import java.text.MessageFormat;

public class AuthCodeActivity extends BaseActivity  implements MyCountDownTimer.CountDownListener {

	private SMSReceiver smsReceiver;
	private EditText et_authcode;
	private TextView tv_next, tv_get_authcode,tv_send_number_info;
	private String username, password,modifyTradePassword,tradePassword;
	private int type;
	private String mobile;
	private TextView count_down;
	private MyCountDownTimer mc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_authcode);
		super.onCreate(savedInstanceState);


		// 需要注册一个广播接受者，接收到系统短信之后自动填写。
	}

	@Override
	public void initView() {
		et_authcode = findView(R.id.et_authcode);
		tv_next = findView(R.id.tv_next);
		tv_send_number_info = findView(R.id.tv_send_number_info);
		count_down = (TextView) findViewById(R.id.count_down);  
        mc = new MyCountDownTimer(300000, 1000,this);  
	}

	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(context.getResources().getString(R.string.authcode));*/
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		modifyTradePassword = getIntent().getExtras().getString("modifyTradePassword");
		tradePassword = getIntent().getExtras().getString("tradepassword");
		
		type = getIntent().getExtras().getInt("type");
		smsReceiver = new SMSReceiver();
		IntentFilter intentFilter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		this.registerReceiver(smsReceiver, intentFilter); // 动态注册广播

		//设置要发送的号码，显示在界面上
		if(appController.getUserinfo()!=null&&appController.getUserinfo().getUser()!=null){
			username = appController.getUserinfo().getUser().getMobile();//修改电话号码
		}
		setMessageInfo();
		
		/**发送并获取验证码*/
		getAuthCode(type);
	}

	/**
	 * 设置发送号码信息
	 */
	private void setMessageInfo() {
		String message = tv_send_number_info.getText().toString().trim();
		message = MessageFormat.format(message, BankUtils.formatMobileNum(username, 3, 7));
		tv_send_number_info.setText(message);
	}

	@Override
	public void initListener() {
		tv_next.setOnClickListener(this);
		count_down.setOnClickListener(this);
		et_authcode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				setNextState();
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.count_down:
			getAuthCode(type);
			break;
		case R.id.tv_next:
			pg.setTitle("正在处理数据，请稍后...");
			pg.show();
			switch (type) {
			case Constant.AUTHCODE_ACTIVITY_REGISTER:
			case Constant.AUTHCODE_ACTIVITY_SET_TRADPASSWORD:
				api.userOperations().reg(new Listener<UserInfo>() {
					@Override
					public void onResponse(UserInfo response) {
						pg.dismiss();
						appController.setAppKey(response.getUser().getAppkey());
						UiUtil.getDefaultToast(context, "恭喜您，注册成功\n快去购买吧")
								.show();
						startActivity(MainActivity.class);
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pg.dismiss();
		                Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();

					}
				}, username, password, tradePassword, et_authcode.getText().toString().trim());
				break;
			case Constant.AUTHCODE_ACTIVITY_MODIFYPASSWORD://修改交易，带着验证码到下一个界面
				api.userOperations().getPayPw(new Listener<StatusMessage>() {

					@Override
					public void onResponse(StatusMessage response) {
						pg.dismiss();
						Toast.makeText(context, response.getInfo(), Toast.LENGTH_SHORT).show();
						startActivity(MainActivity.class);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						pg.dismiss();
		                Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
					}
				}, appController.getAppKey(), modifyTradePassword, et_authcode.getText().toString().trim());
				break;
			case Constant.AUTHCODE_ACTIVITY_ADDBANK:
				UiUtil.getDefaultToast(context, "添加银行卡成功！").show();
				startActivity(MyBanksActivity.class);
			default:
				break;
			}
			break;
		case R.id.tv_protocol:
			break;
		default:
			break;
		}
	}

	/**
	 * 获取验证码
	 */
	private void getAuthCode(int type) {
		pg.setTitle("正在获取验证码，请稍后...");
		pg.show();
		switch (type) {
		case Constant.AUTHCODE_ACTIVITY_REGISTER:
		case Constant.AUTHCODE_ACTIVITY_SET_TRADPASSWORD:
			api.userOperations().reg(new Listener<UserInfo>() {
				@Override
				public void onResponse(UserInfo response) {
					onSmsSuccess();
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					onSmsFailed(error);
				}
			}, username,password, tradePassword, null);
			break;
		case Constant.AUTHCODE_ACTIVITY_MODIFYPASSWORD:
			api.userOperations().getPayPw(new Listener<StatusMessage>() {//第一次访问这个接口，为了获取验证码

				@Override
				public void onResponse(StatusMessage response) {
					onSmsSuccess();
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					onSmsFailed(error);
				}
			}, appController.getAppKey(), null, null);
			
			break;
		case Constant.AUTHCODE_ACTIVITY_ADDBANK:
			break;
		default:
			break;
		}
		
	}

	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState() {
		if (!TextUtils.isEmpty(et_authcode.getText().toString().trim())) {
			tv_next.setEnabled(true);
			tv_next.setTextColor(context.getResources().getColor(R.color.white));
		} else {
			tv_next.setEnabled(false);
			tv_next.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);
	}

	class SMSReceiver extends BroadcastReceiver {

		public static final String TAG = "main";

		// android.provider.Telephony.Sms.Intents

		public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

		@Override
		public void onReceive(Context context, Intent intent) {
			String authCode = null;
			if (intent.getAction().equals(SMS_RECEIVED_ACTION))

			{

				SmsMessage[] messages = getMessagesFromIntent(intent);

				for (SmsMessage message : messages)

				{

					Log.i(TAG, message.getOriginatingAddress() + " : " +

					message.getDisplayOriginatingAddress() + " : " +

					message.getDisplayMessageBody() + " : " +

					message.getTimestampMillis());
					authCode = message.getDisplayMessageBody();
					authCode = authCode.replaceAll(".*(：\\d{4,6}).*", "$1")
							.split("：")[1];// 获取验证码
				}
				et_authcode.setText(authCode);
				
				Log.e("main", "authcode:"+authCode);

			}
		}

		public final SmsMessage[] getMessagesFromIntent(Intent intent)

		{

			Object[] messages = (Object[]) intent.getSerializableExtra("pdus");

			byte[][] pduObjs = new byte[messages.length][];

			for (int i = 0; i < messages.length; i++)

			{

				pduObjs[i] = (byte[]) messages[i];

			}

			byte[][] pdus = new byte[pduObjs.length][];

			int pduCount = pdus.length;

			SmsMessage[] msgs = new SmsMessage[pduCount];

			for (int i = 0; i < pduCount; i++)

			{

				pdus[i] = pduObjs[i];

				msgs[i] = SmsMessage.createFromPdu(pdus[i]);

			}

			return msgs;

		}
	}

	public void onSmsSuccess(){
		pg.dismiss();
		mc.start(); 	//开始倒计时
		count_down.setEnabled(false);
		count_down.setTextColor(context.getResources().getColor(R.color.gray7));
	}
	
	public void onSmsFailed(VolleyError error){
		pg.dismiss();
		mc.cancel();
		count_down.setEnabled(true);
		count_down.setTextColor(context.getResources().getColor(R.color.white));
		Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onTick(long millisUntilFinished) {
		count_down.setText(millisUntilFinished / 1000+"秒后重新获取");
	}

	@Override
	public void onFinish() {
		count_down.setText("重新获取");
		count_down.setEnabled(true);
		count_down.setTextColor(context.getResources().getColor(R.color.white));
	}
	@Override
	protected void onStop() {
		super.onStop();
		mc.cancel();
	}
}
