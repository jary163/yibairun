package com.yibairun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yibairun.R;

public class BuyResultActivity extends BaseActivity {

	private TextView tv_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_buys_success);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		tv_next = findView(R.id.tv_next);
	}

	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(getResources().getString(R.string.buys_success_tow));*/

	}

	@Override
	public void initListener() {
		tv_next.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(MainActivity.class);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_next:
			startActivity(MainActivity.class);
			break;
		default:
			break;
		}
	}
}
