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

import com.yibairun.R;

public class AddBankNNActivity extends BaseActivity{

	private EditText et_bank_cardnum;
	private ImageView iv_clear_nums;
	private TextView tv_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_name_nums);

	}
	@Override
	public void initView() {
		et_bank_cardnum = findView(R.id.et_bank_cardnum);
		iv_clear_nums = findView(R.id.iv_clear_nums);
		tv_next = findView(R.id.tv_next);
		
	}
	@Override
	public void initDate() {
        /*bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.add_bank));	*/
		
	}
	@Override
	public void initListener() {
		iv_clear_nums.setOnClickListener(this);
		tv_next.setOnClickListener(this); 
		et_bank_cardnum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(!TextUtils.isEmpty(s.toString())){
					iv_clear_nums.setVisibility(View.VISIBLE);
				}else{
					iv_clear_nums.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//INFO  卡号需要发送到服务器进行验证，然后才能带着相关信息跳转到下一个界面
				if(et_bank_cardnum.getText().toString().trim().equals("6222801147711239222")){
					tv_next.setEnabled(true);
					tv_next.setTextColor(context.getResources().getColor(R.color.white));
				}else{
					tv_next.setEnabled(false);
					tv_next.setTextColor(context.getResources().getColor(R.color.gray7));
				}
			}
		});
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_next:
			Intent intent = new Intent();
			startActivity(AddBankPNActivity.class);
			break;
		case R.id.iv_clear_nums:
			et_bank_cardnum.setText("");
			break;
		default:
			break;
		}
	}
}
