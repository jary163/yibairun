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

import com.yibairun.R;
import com.yibairun.comm.Constant;

public class ModifyTradePasswordActivity extends BaseActivity {

	private EditText et_new_password,et_again_new_password;
	private TextView tv_submit;
	private boolean isCurrentPasswordLegal = false,isNewPasswordLegal = false,isPasswordLegal = false;
	private String before_new_password="",before_again_new_password="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_modify_trade_password);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initView() {
/*		iv_title_left = findView(R.id.iv_title_left);
		tv_centerText = findView(R.id.tv_centerText);*/
		et_new_password = findView(R.id.et_new_password);
		et_again_new_password = findView(R.id.et_again_new_password);
		tv_submit = findView(R.id.tv_submit);
		
	}
	@Override
	public void initDate() {
       /* bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.account_modify_password));*/
		
	}
	@Override
	public void initListener() {
		tv_submit.setOnClickListener(this);
		et_new_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				before_new_password = s.toString().trim();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断号码是否合理
				if(s.toString().length()!=before_new_password.length()
						&&s.toString().replace(" ", "").equals(before_new_password)){
					et_new_password.setText(before_new_password);
				}
				if(!TextUtils.isEmpty(s.toString().trim())){
					isPasswordLegal = true;
				}else{
					isPasswordLegal = false;
				}
				setNextState();
				et_new_password.setSelection(et_new_password.getText().toString().length());
			}
		});
		et_again_new_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				before_again_new_password = s.toString().trim();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断号码是否合理
				if(s.toString().length()!=before_again_new_password.length()
						&&s.toString().replace(" ", "").equals(before_again_new_password)){
					et_again_new_password.setText(before_again_new_password);
				}
				if(!TextUtils.isEmpty(s.toString().trim())){
					isNewPasswordLegal = true;
				}else{
					isNewPasswordLegal = false;
				}
				setNextState();
				et_again_new_password.setSelection(et_again_new_password.getText().toString().length());
			}
		});
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_submit:
			submitTradePW();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 提交修改交易密码的申请
	 */
	public void submitTradePW(){
		if(!et_again_new_password.getText().toString().trim().equals(et_new_password.getText().toString().trim())){
			Toast.makeText(context, "两次密码不一样，请重新输入", Toast.LENGTH_SHORT).show();
			return;
		}
		final Intent intent = new Intent();
		intent.putExtra("modifyTradePassword", et_new_password.getText().toString().trim());
		intent.putExtra("type", Constant.AUTHCODE_ACTIVITY_MODIFYPASSWORD);
		startActivity(AuthCodeActivity.class,intent);
	}
	
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if(isNewPasswordLegal&&isPasswordLegal){
			tv_submit.setEnabled(true);
			tv_submit.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_submit.setEnabled(false);
			tv_submit.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
}
