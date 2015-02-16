package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yibairun.R;

public class AccountManagerActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);

	}
	
	@Override
	public void initView() {
	/*	bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.account_manger));*/
        
	}
	@Override
	public void initDate() {
        
	}
	@Override
	public void initListener() {
		//iv_title_left.setOnClickListener(this);
		findView(R.id.tv_modify_password).setOnClickListener(this);
		findView(R.id.tv_withdraw).setOnClickListener(this);
		findView(R.id.tv_uploadidcard).setOnClickListener(this);
       
	}
	
   
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_withdraw:
            if(appController.getUserinfo().getUser().getIs_first_cash()!=1) {
                Toast.makeText(context, "首次提现请到网站完成", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(WithDrawActivity.class);
            }
			break;
		case R.id.tv_modify_password:
			Intent intent = new Intent();
			startActivity(ModifyTradePasswordActivity.class, intent);
			break;
		case R.id.tv_uploadidcard:
			startActivity(CertificationActivity.class);
			break;
		default:
			break;
		}
	}
	
	
}
