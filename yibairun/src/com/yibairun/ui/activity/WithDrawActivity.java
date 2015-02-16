package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.bean.Bank;
import com.yibairun.bean.StatusMessage;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;
import com.yibairun.utils.ProductUtils;
import com.yibairun.utils.StringUtil;
import com.yibairun.utils.UiUtil;
import com.yibairun.utils.VolleyErrorHelper;

public class WithDrawActivity extends BaseActivity{

	private TextView tv_bank_type,tv_bank_city,tv_bank_nums,tv_bank_name,tv_submit,tv_balance,tv_username,tv_idCard;
	private EditText et_withdraw_money,et_withdraw_password;
	protected boolean isTradeMoneyLegal;
	protected boolean isTradePasswordLegal;
	private UserInfo userinfo;
	private Bank bank;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_withdraw);
		super.onCreate(savedInstanceState);
		
		//需要注册一个广播接受者，接收到系统短信之后自动填写。
	}
	
	@Override
	public void initView() {
		tv_bank_type = findView(R.id.tv_bank_type);
		tv_bank_city = findView(R.id.tv_bank_city);
		tv_bank_nums = findView(R.id.tv_bank_nums);
		tv_bank_name = findView(R.id.tv_bank_name);
		tv_balance = findView(R.id.tv_balance);
		tv_submit = findView(R.id.tv_submit);
		tv_username = findView(R.id.tv_username);
		tv_idCard = findView(R.id.tv_idCard);
		et_withdraw_money = findView(R.id.et_withdraw_money);
		et_withdraw_password = findView(R.id.et_withdraw_password);
		
	}
	@Override
	public void initDate() { 
		userinfo = appController.getUserinfo();
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(context.getResources().getString(R.string.account_withdraw));*/
		tv_balance.setText(ProductUtils.getFormatNum(userinfo.getUser().getMoney()));
		tv_username.setText(userinfo.getUser().getUsername());
		tv_idCard.setText(userinfo.getUser().getCardnum());
		
		
		
	}
	@Override
	public void initListener() {
		findView(R.id.tv_select_cards).setOnClickListener(this);
		findView(R.id.tv_submit).setOnClickListener(this);
		et_withdraw_money.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断提现金额是否合理
				if(!TextUtils.isEmpty(s.toString().trim())){
					isTradeMoneyLegal = true;
					String[] split = s.toString().trim().split("\\.");
					if(split.length==2&&split[1].length()>2){
						et_withdraw_money.setText(s.toString().trim().substring(0, s.toString().trim().length()-1));
						et_withdraw_money.setSelection(et_withdraw_money.getText().toString().trim().length());
					}
				}else{
					isTradeMoneyLegal = false;
				}
				setNextState();
			}
		});
		et_withdraw_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断交易密码是否合理
				if(!TextUtils.isEmpty(s.toString().trim())){
					isTradePasswordLegal = true;
				}else{
					isTradePasswordLegal = false;
				}
				setNextState();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_protocol:
			break;
		case R.id.tv_select_cards:
			Intent intent = new Intent(context, SelectBankActivity.class);
			/**测试数据*/
			Bank bank = new Bank();
			bank.setBanktype(tv_bank_type.getText().toString());
			bank.setCity(tv_bank_city.getText().toString());
			bank.setBanknum(tv_bank_nums.getText().toString());
			bank.setBankname(tv_bank_name.getText().toString());
			/**测试数据*/
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constant.WITHDRAW_DETAIL, bank);
			intent.putExtras(bundle);
			startActivityForResult(intent, Constant.REQUEST_CODE_WITHDRAWACTIVITY);
			break;
		case R.id.tv_submit:
			if(Float.parseFloat(et_withdraw_money.getText().toString().trim())<=0){
				Toast.makeText(context, "充值金额不能小于1分，请重新输入", Toast.LENGTH_SHORT).show();
				return;
			}
			pg.setTitle("正在提交订单，请稍后...");
			pg.show();
			subWithDrawApply();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 提交提现申请
	 */
	private void subWithDrawApply(){
		api.productOperations().getCash(new Listener<StatusMessage>() {

			@Override
			public void onResponse(StatusMessage response) {
				pg.hide();
				if(response.getStatus()==1){
					UiUtil.getDefaultToast(context, "您的申请已提交，请等待后台审核！").show();
					startActivity(MainActivity.class);
				}else{
					Toast.makeText(context,response.getInfo(),Toast.LENGTH_SHORT).show();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				pg.hide();
				Toast.makeText(context,VolleyErrorHelper.getMessage(error, context),Toast.LENGTH_SHORT).show();
			}
		}, appController.getAppKey(), Double.parseDouble(et_withdraw_money.getText().toString().trim()), 
		bank.getId(), et_withdraw_password.getText().toString().trim());//TODO  bank_id需要修改
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if(requestCode==Constant.REQUEST_CODE_WITHDRAWACTIVITY&&resultCode==Constant.RESULT_CODE_WITHDRAWACTIVITY){
			bank = (Bank) intent.getExtras().getSerializable(Constant.WITHDRAW_DETAIL);
			setBankDate(bank);
			setNextState();
		}
		
	}
	
	//TODO 需要将银行卡初始化
	public void setBankDate(Bank bank){
		tv_bank_type.setText(bank.getBanktype());
		tv_bank_city.setText(bank.getCity());
		tv_bank_nums.setText(bank.getBanknum());
		tv_bank_name.setText(bank.getBankname());
	}
	
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if(isTradeMoneyLegal&&isTradePasswordLegal&&null!=bank){
			tv_submit.setEnabled(true);
			tv_submit.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_submit.setEnabled(false);
			tv_submit.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
	
}
