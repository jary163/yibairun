package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yibairun.R;
import com.yibairun.utils.UiUtil;

public class MyBanksOptionActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_bank_option);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {

	}

	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.action_bank_cards));*/

	}

	@Override
	public void initListener() {
		findView(R.id.tv_payment_default).setOnClickListener(this);
		findView(R.id.tv_remove_binding).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_payment_default:
			UiUtil.getDefaultToast(context, "已经默认使用该卡支付！").show();
			break;
		case R.id.tv_remove_binding:
			Intent intent = new Intent();
			startActivity(RemoveBankActivity.class, intent);
			break;
		default:
			break;
		}
	}

}
