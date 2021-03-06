package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.yibairun.R;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;

public class SetTradePasswordActivity extends BaseActivity {

	private EditText et_password,et_password_again;
	private boolean isUsernameLegal = false,isPasswordLegal = false;
	private TextView tv_enter;
	private String username,password; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_bank_set_tradepassword);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initView() {
		et_password = findView(R.id.et_password);
		et_password_again = findView(R.id.et_password_again);
		tv_enter = findView(R.id.tv_enter);
		
	}
	@Override
	public void initDate() {
       /* bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.action_setting_password));*/
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
	}
	@Override
	public void initListener() {
		tv_enter.setOnClickListener(this);
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
				//判断号码是否合理
				isUsernameLegal = true;
				setNextState();
			}
		});
		et_password_again.addTextChangedListener(new TextWatcher() {
			
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
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {

		case R.id.tv_enter:
			if(judgePassword()&&!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
				Intent intent = new Intent();
				intent.putExtra("username", username);
				intent.putExtra("password", password);
				intent.putExtra("tradepassword", et_password.getText().toString().trim());
				intent.putExtra("type", Constant.AUTHCODE_ACTIVITY_SET_TRADPASSWORD);
				startActivity(AuthCodeActivity.class,intent);
			}
			
			/*if(judgePassword()){
				pg.setTitle("正在设置交易密码，请稍后...");
				pg.show();
				api.userOperations().setPayPw(new Listener<StatusMessage>() {

					@Override
					public void onResponse(StatusMessage response) {
						pg.dismiss();
						Toast.makeText(context, response.getInfo().toString(), Toast.LENGTH_SHORT).show();
						if(response.getStatus()==1){
							final Intent intent = new Intent();
							startActivity(AddBankPasswordActivity.class);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						pg.dismiss();
						Toast.makeText(context, "内部错误  ，请重试", Toast.LENGTH_SHORT).show();
					}
				}, appController.getAppKey(), et_password.getText().toString().trim());

			}
*/
			break;
		case R.id.tv_protocol:
			break;
		default:
			break;
		}
	}
	
	/**
	 * 判断设置交易密码是否合理
	 */
	private boolean judgePassword() {
		if(!et_password.getText().toString().trim().equals(et_password_again.getText().toString().trim())){
			Toast.makeText(context, "两次交易不一样，请重新输入", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	private ErrorListener createMyReqErrorListener() {
		return null;
	}
	private Listener<UserInfo> createMyReqSuccessListener() {
		return null;
	}
	/**
	 * 设置确定图标是否点亮
	 */
	public void setNextState(){
		if(isUsernameLegal&&isPasswordLegal){
			tv_enter.setEnabled(true);
			tv_enter.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_enter.setEnabled(false);
			tv_enter.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
}
