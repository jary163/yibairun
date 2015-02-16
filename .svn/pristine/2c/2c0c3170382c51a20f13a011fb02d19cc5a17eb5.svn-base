package com.yibairun.ui.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yibairun.R;
import com.yibairun.comm.Constant;
import com.yibairun.listener.RecommendCallBackListener;

public class RecommendDialog extends Dialog implements OnClickListener {

	private Context context;
	private EditText et_recommend_info;
	private TextView tv_product_cancel,tv_product_recommend;
	private RecommendCallBackListener mRecommendCallBackListener;
	private SHARE_MEDIA type;
	private String shareMessage;

	public RecommendDialog(Context context,RecommendCallBackListener mRecommendCallBackListener,SHARE_MEDIA type) {
		super(context);
		this.context = context;
		this.mRecommendCallBackListener = mRecommendCallBackListener;
		this.type = type;
	}

	public RecommendDialog(Context context, int theme,SHARE_MEDIA type,String shareMessage,RecommendCallBackListener mRecommendCallBackListener) {
		super(context, theme);
		this.context = context;
		this.mRecommendCallBackListener = mRecommendCallBackListener;
		this.type = type;
		this.shareMessage = shareMessage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_product_recommend);
		et_recommend_info = (EditText) findViewById(R.id.et_recommend_info);
		tv_product_cancel = (TextView) findViewById(R.id.tv_product_cancel);
		tv_product_recommend = (TextView) findViewById(R.id.tv_product_recommend);
		initDate();
		initListener();
	}

	private void initDate() {
		et_recommend_info.setText(shareMessage);
		Editable etext = et_recommend_info.getText();
		Selection.setSelection(etext, etext.length());
	}

	private void initListener() {
		tv_product_cancel.setOnClickListener(this);
		tv_product_recommend.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_product_cancel:
			this.dismiss();
			break;
		case R.id.tv_product_recommend:
			mRecommendCallBackListener.onRecommendMsgEnter(et_recommend_info.getText().toString().trim(), type);
			this.dismiss();
		default:
			break;
		}
	}
}
