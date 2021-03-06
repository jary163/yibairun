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

import com.yibairun.R;
import com.yibairun.bean.ProductDetail;
import com.yibairun.comm.Constant;
import com.yibairun.utils.Boast;

public class ApplyBuyActivity extends BaseActivity {

	private TextView tv_play_info, tv_expect_yield, tv_income_money,
			tv_limit_day, tv_next,tv_protocol,tv_disclaimer;
	private EditText et_purchase_price;
	private ImageView iv_protocol, iv_excusatio;
	private boolean isCheckProtocol = true, isCheckExcusatio = true,
			isLegalPrise = false;
	private ProductDetail productDetail;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_buys_apply);
		super.onCreate(savedInstanceState);
    }

	@Override
	public void initView() {
		tv_play_info = findView(R.id.tv_play_info);
		tv_expect_yield = findView(R.id.tv_expect_yield);
		tv_income_money = findView(R.id.tv_income_money);
		tv_limit_day = findView(R.id.tv_limit_day);
		tv_next = findView(R.id.tv_next);
		et_purchase_price = findView(R.id.et_purchase_price);
		iv_protocol = findView(R.id.iv_protocol);
		iv_excusatio = findView(R.id.iv_excusatio);
		tv_protocol = findView(R.id.tv_protocol);
		tv_disclaimer = findView(R.id.tv_disclaimer);
	}

	@Override
	public void initDate() {
		productDetail = (ProductDetail) getIntent().getSerializableExtra(Constant.PRODUCT_DETAIL);
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(getResources().getString(R.string.title_apply_purchase));*/
		iv_protocol.setSelected(true);
		iv_excusatio.setSelected(true);

        tv_play_info.setText(productDetail.getTitle());
        tv_expect_yield.setText(productDetail.getRate().getMin()+"-"+productDetail.getRate().getMax());
        tv_income_money.setText(String.valueOf(Constant.APPLY_LIMIT_BUYTS));
        tv_limit_day.setText(String.valueOf(productDetail.getDate()));

		

	}

	@Override
	public void initListener() {
		iv_protocol.setOnClickListener(this);
		iv_excusatio.setOnClickListener(this);
		tv_next.setOnClickListener(this);
		tv_protocol.setOnClickListener(this);
		tv_disclaimer.setOnClickListener(this);
		et_purchase_price.addTextChangedListener(new TextWatcher() {

			private long startTime;
			private String beforeString;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				beforeString = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// 判断价格是否合理
				isLegalPrise = true;
				if(TextUtils.isEmpty(s.toString().trim())){
					isLegalPrise = false;
				}else if(Integer.parseInt(s.toString().trim())>1000000){
					if((System.currentTimeMillis()-startTime)>2000){
						Toast.makeText(context, "最大金额100万元", Toast.LENGTH_SHORT).show();
						startTime = System.currentTimeMillis();
					}
					et_purchase_price.setText(beforeString);
					et_purchase_price.setSelection(beforeString.length()-1);
				}
				setNextState();
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_next:
			int buyCount = Integer.valueOf(et_purchase_price.getText().toString());
			if(buyCount<Constant.APPLY_LIMIT_BUYTS){
				Boast.makeText(context, "收购金额不能小于"+Constant.APPLY_LIMIT_BUYTS+"元，请重新输入").show();
				return;
			}
			Intent intent = new Intent();
            intent.putExtra(BuyEnterActivity.EXTRA_PURCHASE_COUNT,buyCount);
            intent.putExtra(Constant.PRODUCT_DETAIL,productDetail);
			startActivity(BuyEnterActivity.class, intent);
			break;
		case R.id.iv_protocol:
			if (iv_protocol.isSelected()) {
				iv_protocol.setSelected(false);
				isCheckProtocol = false;
			} else {
				iv_protocol.setSelected(true);
				isCheckProtocol = true;
			}
			setNextState();
			break;
		case R.id.iv_excusatio:
			if (iv_excusatio.isSelected()) {
				iv_excusatio.setSelected(false);
				isCheckExcusatio = false;
			} else {
				iv_excusatio.setSelected(true);
				isCheckExcusatio = true;
			}
			setNextState();
			break;
		case R.id.tv_protocol:
			jumpToWebView(Constant.WEBVIEW_PROTOCOL);
			break;
		case R.id.tv_disclaimer:
			jumpToWebView(Constant.WEBVIEW_DISCLAIMER);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState() {
		if (isLegalPrise && isCheckExcusatio && isCheckProtocol) {
			tv_next.setEnabled(true);
			tv_next.setTextColor(context.getResources().getColor(R.color.white));
		} else {
			tv_next.setEnabled(false);
			tv_next.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
	

}
