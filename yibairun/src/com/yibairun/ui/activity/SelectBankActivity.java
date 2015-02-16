package com.yibairun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
import com.yibairun.utils.StringUtil;
import com.yibairun.utils.VolleyErrorHelper;

import java.util.ArrayList;
import java.util.List;

public class SelectBankActivity extends BaseActivity{

	private ListView lv_bank;
	private List<Bank> bankList;
	private BankHandler bankHandler;
	private BankAdapter bankAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_select_bank);
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void initView() {
		lv_bank = findView(R.id.lv_bank);
        pending_view = (RelativeLayout) findViewById(R.id.pending_view);
	}
	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(context.getResources().getString(R.string.withdraw_select_bank_cards));*/
		bankList = new ArrayList<Bank>();
		bankAdapter = new BankAdapter();
		getBankInfo();
	}
	@Override
	public void initListener() {
		lv_bank.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				bankHandler = (BankHandler) view.getTag();
				bankHandler.tv_bank_city.setTextColor(getResources().getColor(R.color.red));
				bankHandler.tv_bank_nums.setTextColor(getResources().getColor(R.color.red));
				bankHandler.tv_bank_type.setTextColor(getResources().getColor(R.color.red));
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(Constant.WITHDRAW_DETAIL, (Bank) bankAdapter.getItem(position));
				intent.putExtras(bundle);
				SelectBankActivity.this.setResult(Constant.RESULT_CODE_WITHDRAWACTIVITY,intent);
				SelectBankActivity.this.finish();
			}
		});
	}
	
	/**
	 * 刷新数据
	 */
	public void refreshDate(){
		lv_bank.setAdapter(bankAdapter);
		//bankAdapter.notifyDataSetChanged();    TODO  需要切换
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
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_protocol:
			break;
		default:
			break;
		}
	}
	
	class BankAdapter extends BaseAdapter{
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
			bank = bankList.get(position);
			if(convertView==null){
				convertView = View.inflate(context, R.layout.activity_select_bank_item, null);
				bankHandler = new BankHandler();
				bankHandler.tv_bank_type = (TextView) convertView.findViewById(R.id.tv_bank_type);
				bankHandler.tv_bank_city = (TextView) convertView.findViewById(R.id.tv_bank_city);
				bankHandler.tv_bank_nums = (TextView) convertView.findViewById(R.id.tv_bank_nums);
				convertView.setTag(bankHandler);
			}else{
				bankHandler = (BankHandler) convertView.getTag();
			}
			bankHandler.tv_bank_type.setText(StringUtil.ToDBC(bank.getBanktype()));
			bankHandler.tv_bank_city.setText(StringUtil.ToDBC(bank.getCity()));
			bankHandler.tv_bank_nums.setText(bank.getBanknum());
			return convertView;
		}
		
	}
	
	class BankHandler {
		private TextView tv_bank_type,tv_bank_city,tv_bank_nums;
	}
	
}
