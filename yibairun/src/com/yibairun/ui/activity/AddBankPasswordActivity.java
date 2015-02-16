package com.yibairun.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.yibairun.R;

public class AddBankPasswordActivity extends BaseActivity{

	private EditText et_addbank_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_add_bank_password);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initView() {
		et_addbank_password = findView(R.id.et_addbank_password);
		
	}
	@Override
	public void initDate() {
       /* bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.add_bank));	*/
	}
	@Override
	public void initListener() {
		et_addbank_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(et_addbank_password.getText().toString().trim().equals("123456")){
					startActivity(AddBankNNActivity.class);
				}
			}
		});
		
	}
}
