package com.yibairun.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.igexin.sdk.PushManager;
import com.yibairun.R;
import com.yibairun.bean.UserInfo;
import com.yibairun.utils.SYSharedPreferences;
import com.yibairun.utils.VolleyErrorHelper;

public class LoginActivity extends BaseActivity {

	private EditText et_username, et_password;
	private TextView tv_login, tv_regiest;
	private UserInfo userinfo;
	private boolean isUsernameLegal = false;
	private boolean isPasswordLegal = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		tv_login = (TextView) findViewById(R.id.tv_login);
		tv_regiest = (TextView) findViewById(R.id.tv_regiest);
	}

	@Override
	public void initDate() {
//		bar.setDisplayHomeAsUpEnabled(true);
//		bar.setTitle(getResources().getString(R.string.login));

	}

	@Override
	public void initListener() {
		tv_login.setOnClickListener(this);
		tv_regiest.setOnClickListener(this);
		et_username.addTextChangedListener(new TextWatcher() {
			


			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断号码是否合理
				isUsernameLegal = true;
				setNextState();
			}
		});
		et_password.addTextChangedListener(new TextWatcher() {
			


			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断密码是否合理
				isPasswordLegal = true;
				setNextState();
			}
		});

	}

	private Listener<UserInfo> createMyReqSuccessListener() {
		return new Listener<UserInfo>() {
			@Override
			public void onResponse(UserInfo response) {
				pg.dismiss();
				switch (response.getStatus()) {
				case 1:
					Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
					appController.setAppKey(response.getUser().getAppkey());
                    response.getUser().setPassword(et_password.getText().toString().trim());
                    SYSharedPreferences.getInstance().putString("mobile",et_username.getText().toString().trim());
                    SYSharedPreferences.getInstance().putString("password",et_password.getText().toString().trim());
					appController.setUserinfo(response);
					finish();
					//startActivity(MainActivity.class);
					break;
				case 0:
					Toast.makeText(context, response.getInfo(),Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};
	}

	protected Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				pg.dismiss();
				Toast.makeText(context,
						VolleyErrorHelper.getMessage(error, context),
						Toast.LENGTH_SHORT).show();
			}
		};
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_login:
			pg.setTitle("正在登录，请稍后...");
			pg.show();
			api.userOperations().login(createMyReqSuccessListener(),
					createMyReqErrorListener(),
					et_username.getText().toString().trim(),
					et_password.getText().toString().trim(), PushManager.getInstance().getClientid(context));
			break;
		case R.id.tv_regiest:
			startActivity(RegisterActivity.class);
			break; 
		default:
			break;
		}
	}
	
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if(isUsernameLegal&&isPasswordLegal){
			tv_login.setEnabled(true);
			tv_login.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_login.setEnabled(false);
			tv_login.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}

}