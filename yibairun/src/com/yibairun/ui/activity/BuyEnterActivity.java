package com.yibairun.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.bean.ProductDetail;
import com.yibairun.bean.StatusMessage;
import com.yibairun.comm.Constant;
import com.yibairun.utils.ProductUtils;
import com.yibairun.utils.VolleyErrorHelper;

public class BuyEnterActivity extends BaseActivity {

    private TextView tv_expect_yield, tv_income_money, tv_limit_day, tv_next,tv_play_info;
    private EditText et_enter_password;
    private boolean isLegalPassword = false;
    private ProductDetail productDetail;
    public static final String EXTRA_PURCHASE_COUNT = "extra_purchase_count";
    private int purchase_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_buys_enter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
    	tv_play_info = findView(R.id.tv_play_info);
        tv_expect_yield = findView(R.id.tv_expect_yield);
        tv_income_money = findView(R.id.tv_income_money);
        tv_limit_day = findView(R.id.tv_limit_day);
        tv_next = findView(R.id.tv_next);
        et_enter_password = findView(R.id.et_enter_password);
    }

    @Override
    public void initDate() {
        /*bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(getResources().getString(R.string.buys_enter));*/
        productDetail = (ProductDetail) getIntent().getSerializableExtra(Constant.PRODUCT_DETAIL);
        purchase_money = getIntent().getIntExtra(EXTRA_PURCHASE_COUNT, 0);
        tv_play_info.setText(productDetail.getTitle());
        tv_expect_yield.setText(ProductUtils.getRateFromMoney(productDetail.getRate_range(), purchase_money).getRate()+"");
        //tv_expect_yield.setText(productDetail.getRate().toString());	
        tv_limit_day.setText(productDetail.getDate());
        tv_income_money.setText(String.valueOf(purchase_money));


    }

    @Override
    public void initListener() {
        tv_next.setOnClickListener(this);
        et_enter_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 判断密码是否合理
                isLegalPassword = true;
                setNextState();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_next:
                pg.setMessage("正在进行购买，请稍后...");
                pg.show();
    /*		tv_next.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					startActivity(BuyResultActivity.class);
				}
			}, 1000);*/
                buy();
                break;
            default:
                break;
        }
    }

    private void buy() {
        api.productOperations().buyPorduct(new Listener<StatusMessage>() {
            @Override
            public void onResponse(StatusMessage response) {
            	pg.hide();
                switch (response.getStatus()){
                    case 1:
                        startActivity(BuyResultActivity.class);
                        break;
                    default:
                        Toast.makeText(context, response.getInfo(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pg.hide();
                Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
            }
        }, appController.getAppKey(), purchase_money, productDetail.getId(), et_enter_password.getText().toString());
    }

    /**
     * 设置下一步图标是否点亮
     */
    public void setNextState() {
        if (isLegalPassword) {
            tv_next.setEnabled(true);
            tv_next.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            tv_next.setEnabled(false);
            tv_next.setTextColor(context.getResources().getColor(R.color.gray7));
        }
    }
}
