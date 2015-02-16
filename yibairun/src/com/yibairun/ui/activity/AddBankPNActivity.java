package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.comm.Constant;

public class AddBankPNActivity extends BaseActivity{

	private EditText et_bank_phonenum;
	private boolean isPhoneLegal = false,isCheckProtocol = false;
	private ImageView iv_user_protocol;
	private TextView tv_next,tv_protocol;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_add_bank_phonenums);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initView() {
		et_bank_phonenum = findView(R.id.et_bank_phonenum);
		iv_user_protocol = findView(R.id.iv_user_protocol);
		tv_next = findView(R.id.tv_next);
		tv_protocol = findView(R.id.tv_protocol);
/*		iv_title_left = findView(R.id.iv_title_left);
		tv_centerText = findView(R.id.tv_centerText);*/
		
	}
	@Override
	public void initDate() {
       /* bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.add_bank));	*/
		
	}
	@Override
	public void initListener() {
		iv_user_protocol.setOnClickListener(this);
		tv_next.setOnClickListener(this);
		tv_protocol.setOnClickListener(this);
		et_bank_phonenum.addTextChangedListener(new TextWatcher() {
			
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
				isPhoneLegal = true;
				setNextState();
			}
		});
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_user_protocol:
			if(iv_user_protocol.isSelected()){
				iv_user_protocol.setSelected(false);
				isCheckProtocol = false;
			}else{
				iv_user_protocol.setSelected(true);
				isCheckProtocol = true;
			}
			setNextState();
			break;
		case R.id.tv_next:
			//INFO  需要向网络发送请求，判断手机号是否正确
			Intent intent = new Intent();
			intent.putExtra("type", Constant.AUTHCODE_ACTIVITY_ADDBANK);
			startActivity(AuthCodeActivity.class,intent);
			break;
		case R.id.tv_protocol:
			break;
		default:
			break;
		}
	}
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if(isPhoneLegal&&isCheckProtocol){
			tv_next.setEnabled(true);
			tv_next.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_next.setEnabled(false);
			tv_next.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
}