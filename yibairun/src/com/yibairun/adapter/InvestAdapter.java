package com.yibairun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.bean.ProductInvest;

import java.util.List;

public class InvestAdapter extends BaseAdapter {
	private Context context;
	private ProductHolder investHolder;
	private List<ProductInvest> mProductList;

	public InvestAdapter() {
	}

	public InvestAdapter(Context context,List<ProductInvest> productList) {
		this.context = context;
		this.mProductList = productList;
	}

	@Override
	public int getCount() {
		return mProductList.size();
	}

	@Override
	public Object getItem(int position) {
		return mProductList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.e("main", "investadapter:"+position);
		ProductInvest product = mProductList.get(position);
		if (convertView == null) { 
			investHolder = new ProductHolder();
			convertView = View.inflate(context, R.layout.list_item_investing,
					null);
			investHolder.tv_limit_day = (TextView) convertView.findViewById(R.id.tv_limit_day);
			investHolder.tv_plan_info = (TextView) convertView.findViewById(R.id.tv_plan_info);
			investHolder.tv_normal_invest = (TextView) convertView.findViewById(R.id.tv_normal_invest);
			investHolder.tv_current_invest = (TextView) convertView.findViewById(R.id.tv_current_invest);
			investHolder.tv_year_invest = (TextView) convertView.findViewById(R.id.tv_year_invest);
			investHolder.tv_balance = (TextView) convertView.findViewById(R.id.tv_balance);
			investHolder.tv_limit_time = (TextView) convertView.findViewById(R.id.tv_limit_time);
			
			
			convertView.setTag(investHolder);
		} else {
			investHolder = (ProductHolder) convertView.getTag();
		}
        investHolder.tv_plan_info.setText(product.getTitle());//设置标题
        investHolder.tv_limit_day.setText("到期时间:"+product.getStoptime());//设置到期时间
        investHolder.tv_current_invest.setText(String.valueOf(product.getIncome_money()));//设置当前收益
        investHolder.tv_year_invest.setText(String.valueOf(product.getRate()));//设置年化收益
        investHolder.tv_balance.setText(String.valueOf(product.getMoney()));//设置余额
        investHolder.tv_limit_time.setText(String.valueOf(product.getDate()));//设置期限
        investHolder.tv_normal_invest.setText(product.getProstatus());//设置投资状态

		return convertView;
	}


	class ProductHolder {
		TextView tv_plan_info, tv_limit_day, tv_normal_invest, tv_current_invest,tv_year_invest,tv_balance,tv_limit_time;
		LinearLayout ll_nuber_percent;
	}


	public void addData(List<ProductInvest> productList) {
		// TODO Auto-generated method stub
		mProductList.addAll(productList);
		notifyDataSetChanged();
	}

}
