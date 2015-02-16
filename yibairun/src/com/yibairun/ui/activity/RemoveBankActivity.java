package com.yibairun.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.yibairun.R;
import com.yibairun.utils.UiUtil;

public class RemoveBankActivity extends BaseActivity{

	private EditText et_trade_password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_remove_bank);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void initView() {
		et_trade_password = findView(R.id.et_trade_password);
		
	}
	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.bank_remove_binding));*/
		
	}
	@Override
	public void initListener() {
		et_trade_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(et_trade_password.getText().toString().trim().equals("123456")){
					UiUtil.getDefaultToast(context, "您的银行卡\n已解除绑定！").show();
					startActivity(MyBanksActivity.class);
				}
			}
		});
		
	}
}
