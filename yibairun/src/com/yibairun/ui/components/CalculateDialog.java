package com.yibairun.ui.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.bean.Product;
import com.yibairun.bean.ProductDetail;
import com.yibairun.utils.Boast;
import com.yibairun.utils.DateUtil;
import com.yibairun.utils.ProductUtils;

public class CalculateDialog extends Dialog implements OnClickListener {

	private Context context;
	private ImageView iv_close_dialog, iv_clear_date;
	private TextView tv_income_yby,tv_income_zfb,tv_income_bank,tv_buy_days;
	private SeekBar sb_bank,sb_zfb,sb_yby;//单位进度为万
	private EditText chat_content;
	private ProductDetail productDetail;
	private Product mProduct;
	private static final int rate = 100;//比率单位万
	private String beforeString;

	public CalculateDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CalculateDialog(Context context, int theme,ProductDetail productDetail,Product mProduct) {
		super(context, theme);
		this.context = context;
		this.productDetail = productDetail;
		this.mProduct = mProduct;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_product_calculate);
		iv_close_dialog = (ImageView) findViewById(R.id.iv_close_dialog);
		iv_clear_date = (ImageView) findViewById(R.id.iv_clear_date);
		chat_content = (EditText) findViewById(R.id.chat_content);
		tv_buy_days = (TextView) findViewById(R.id.tv_buy_days);
		tv_income_yby = (TextView) findViewById(R.id.tv_income_yby);
		tv_income_zfb = (TextView) findViewById(R.id.tv_income_zfb);
		tv_income_bank = (TextView) findViewById(R.id.tv_income_bank);
		sb_bank = (SeekBar) findViewById(R.id.sb_bank);
		sb_zfb = (SeekBar) findViewById(R.id.sb_zfb);
		sb_yby = (SeekBar) findViewById(R.id.sb_yby);
		initDate();
		initListener();
	}

	private void initDate() {
		if(mProduct.getDate_status()==1){
			tv_buy_days.setText(mProduct.getDate());
		}else{
			tv_buy_days.setText("0");
		}
		tv_buy_days.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				InputMethodManager inputManager =(InputMethodManager)chat_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(chat_content, 0);
			}
		}, 100);
	}

	private void initListener() {
		iv_close_dialog.setOnClickListener(this);
		iv_clear_date.setOnClickListener(this);
		chat_content.addTextChangedListener(new TextWatcher() {

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
				int count = 0;
				String content = chat_content.getText().toString().trim();
				if(TextUtils.isEmpty(content)){
					count = 0;
				}else if(content.trim().startsWith("0")){
					chat_content.setText(beforeString);
					return ;
				}else{
					if(Integer.parseInt(content)>1000000){
                        Boast.showText(context,"最大金额100万元");
//						Toast.makeText(context, , Toast.LENGTH_SHORT).show();
						chat_content.setText(beforeString);
						chat_content.setSelection(beforeString.length()-1);
						return;
					}
					count = Integer.parseInt(content);
					
				}
				float moneyToYby = getCalculateDate(ProductUtils.getRateFromMoney(productDetail.getRate_range(), count).getRate(), count);
				float moneyToZfb = getCalculateDate(productDetail.getZfb_rate(), count);
				float moneyToBank = getCalculateDate(productDetail.getBank_rate(), count);
				tv_income_yby.setText(String.valueOf(moneyToYby));
				tv_income_zfb.setText(String.valueOf(moneyToZfb));
				tv_income_bank.setText(String.valueOf(moneyToBank));
				sb_bank.setProgress((int) (moneyToBank/rate));
				sb_zfb.setProgress((int) (moneyToZfb/rate));
				sb_yby.setProgress((int) (moneyToYby/rate));
				
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_close_dialog:
		case R.id.iv_clear_date:
			this.dismiss();
			break;

		default:
			break;
		}
	}
    /**
     * 获取收益率
     * @param rate
     * @param data
     * @return
     */
    private float getCalculateDate(float rate,int data){
    	if(mProduct.getDate_status()==1){
    		return DateUtil.getFormatNumber(data*Integer.parseInt(mProduct.getDate())*rate/36500);
    	}
    	return 0;
    }
}