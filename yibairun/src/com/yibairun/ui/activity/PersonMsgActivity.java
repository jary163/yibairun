package com.yibairun.ui.activity;

import android.os.Bundle;

import com.yibairun.R;
import com.yibairun.listener.OnFragmentComunicationListener;

public class PersonMsgActivity extends BaseActivity implements OnFragmentComunicationListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_msg);

	}

	@Override
	public void initView() {
	
	}

	@Override
	public void initDate() {
//		getAsyDate();
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("个人消息");*/
		
	}

	@Override
	public void initListener() {

	}

	public void getPersonMsgFromNet(){
		
	}

	@Override
	public void OnLoginListener(boolean isSuccess) {
		// TODO Auto-generated method stub
		
	}

}
