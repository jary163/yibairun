package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.yibairun.R;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;
import com.yibairun.utils.SystemUtils;

public class RegisterActivity extends BaseActivity{

	private EditText et_regiest_username,et_regiest_password;
	private boolean isUsernameLegal = false,isPasswordLegal = false,isCheckProtocol = false;
	private ImageView iv_protocol;
	private TextView tv_next,tv_protocol;
	private String before_regiest_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_regiest);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initView() {
		et_regiest_username = findView(R.id.et_regiest_username);
		et_regiest_password = findView(R.id.et_regiest_password);
		iv_protocol = findView(R.id.iv_protocol);
		tv_next = findView(R.id.tv_next);
		tv_protocol = findView(R.id.tv_protocol);
/*		iv_title_left = findView(R.id.iv_title_left);
		tv_centerText = findView(R.id.tv_centerText);*/
		
	}
	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(context.getResources().getString(R.string.regiest));*/
		
	}
	@Override
	public void initListener() {
		iv_protocol.setOnClickListener(this);
		tv_next.setOnClickListener(this);
		tv_protocol.setOnClickListener(this);
		et_regiest_username.addTextChangedListener(new TextWatcher() {
			
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
		et_regiest_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				before_regiest_password = s.toString();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断密码是否合理
				if(s.toString().length()!=before_regiest_password.length()
						&&s.toString().replace(" ", "").equals(before_regiest_password)){
					et_regiest_password.setText(before_regiest_password);
				}
				if(!TextUtils.isEmpty(s.toString().trim())){
					isPasswordLegal = true;
				}else{
					isPasswordLegal = false;
				}
				et_regiest_password.setSelection(et_regiest_password.getText().toString().length());
			}
		});
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_protocol:
			if(iv_protocol.isSelected()){
				iv_protocol.setSelected(false);
				isCheckProtocol = false;
			}else{
				iv_protocol.setSelected(true);
				isCheckProtocol = true;
			}
			setNextState();
			break;
		case R.id.tv_next:
			if(!SystemUtils.matchMobile(et_regiest_username.getText().toString().trim())){
				Toast.makeText(context, "手机号码格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("username", et_regiest_username.getText().toString().trim());
			intent.putExtra("password", et_regiest_password.getText().toString().trim());
			intent.putExtra("type", Constant.AUTHCODE_ACTIVITY_REGISTER);
			startActivity(SetTradePasswordActivity.class,intent);
			//startActivity(AuthCodeActivity.class,intent);
			break;
		case R.id.tv_protocol:
			jumpToWebView(Constant.WEBVIEW_PROTOCOL);
			break;
		default:
			break;
		}
	}
	
	private ErrorListener createMyReqErrorListener() {
		return null;
	}
	private Listener<UserInfo> createMyReqSuccessListener() {
		return null;
	}
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if(isUsernameLegal&&isPasswordLegal&&isCheckProtocol){
			tv_next.setEnabled(true);
			tv_next.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_next.setEnabled(false);
			tv_next.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
}
