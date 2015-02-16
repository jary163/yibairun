package com.yibairun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.bean.Bank;
import com.yibairun.bean.BankList;
import com.yibairun.comm.Constant;
import com.yibairun.utils.BankUtils;
import com.yibairun.utils.VolleyErrorHelper;

import java.util.List;

public class MyBanksActivity extends BaseActivity {

	private ListView lv_my_bank;
	private MyBankAdapter myBankAdapter;
	private List<Bank> bankList;
	private BankHolder bankHolder;
	private int type = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_my_bank);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		lv_my_bank = findView(R.id.lv_my_bank);
        pending_view = (RelativeLayout) findViewById(R.id.pending_view);

	}

	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.action_bank_cards));*/
        pending_view.setVisibility(View.VISIBLE);
        getIntentInfo();
        getBankInfo();

	}

	/**
	 * 获取参数信息
	 */
	private void getIntentInfo() {
		if(getIntent()!=null){
			type = getIntent().getIntExtra((Constant.ACTIVITY_TYPE),-1);
		}
	}

	/**
	 * 获取银行卡信息
	 */
	private void getBankInfo() {
		pending_view.setVisibility(View.VISIBLE);
		api.userOperations().getAllBankList(new Listener<BankList>() {

			@Override
			public void onResponse(BankList response) {
				pending_view.setVisibility(View.GONE);
				bankList = response.getBankList();
				refreshDate();
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				pending_view.setVisibility(View.GONE);
				Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
			}
		}, appController.getAppKey());
	}

	private void refreshDate(){
		myBankAdapter = new MyBankAdapter();
		lv_my_bank.setAdapter(myBankAdapter);
		
	}
	@Override
	public void initListener() {

		lv_my_bank.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {}
		});

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		default:
			break;
		}
	}

	class MyBankAdapter extends BaseAdapter {

		private Bank bank;

		@Override
		public int getCount() {
			return bankList.size();
		}

		@Override
		public Object getItem(int position) {
			return bankList.get(position); 
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			//view = View.inflate(context, R.layout.activity_my_bank_item,null);
			if(convertView==null){
				convertView = View.inflate(context, R.layout.activity_my_bank_item,null);
				bankHolder = new BankHolder();
				bankHolder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
				bankHolder.tv_bank =  (TextView) convertView.findViewById(R.id.tv_bank);
				bankHolder.bank_card_nums = (TextView) convertView.findViewById(R.id.bank_card_nums);
				bankHolder.tv_add_bank = convertView.findViewById(R.id.tv_add_bank);
				bankHolder.ll_my_cards = convertView.findViewById(R.id.ll_my_cards);
				convertView.setTag(bankHolder);
			}else{
				bankHolder = (BankHolder) convertView.getTag();
			}
			if(position==bankList.size()){
				switch (type) {
				case Constant.ACTIVITY_RECHARGE:
					bankHolder.ll_my_cards.setVisibility(View.GONE);
					//bankHolder.tv_add_bank.setVisibility(View.GONE);
					break;
				default:
					bankHolder.ll_my_cards.setVisibility(View.GONE);
					//bankHolder.tv_add_bank.setVisibility(View.GONE);
					break;
				}
				return convertView;
			}else{
				bank = bankList.get(position);
				bankHolder.ll_my_cards.setVisibility(View.VISIBLE);
				bankHolder.tv_add_bank.setVisibility(View.GONE);
			}
			
			bankHolder.iv_logo.setImageBitmap(BankUtils.getBankIco(context, BankUtils.bankMap.get(bank.getBanktype())));
			bankHolder.tv_bank.setText(bank.getBanktype());
			bankHolder.bank_card_nums.setText(BankUtils.formatBankNum(bank.getBanknum()));
			return convertView;
		}

	}
	
	class BankHolder{
		ImageView iv_logo;
		TextView tv_bank,bank_card_nums;
		View ll_my_cards,tv_add_bank;
		
	}

}
