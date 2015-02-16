package com.yibairun.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.bean.Product;
import com.yibairun.bean.ProductList;
import com.yibairun.bean.Rate;
import com.yibairun.utils.DateUtil;
import com.yibairun.utils.ProductUtils;
import com.yibairun.utils.SystemUtils;
import com.yibairun.utils.UiUtil;

public class ProductAdapter extends BaseAdapter {
	private Context context;
	private ProductHolder myHolder;
	private List<Product> productList;
	private Product product;


	public ProductAdapter() {
	}

	public ProductAdapter(Context context, ProductList mProductList) {
		this.context = context;
		this.productList = mProductList.getProductList();
	}

	@Override
	public int getCount() {
		return productList.size();
	}
    public void addData(List<Product> productList){
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }
	@Override
	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		product = productList.get(position);
		if (convertView == null) {
			myHolder = new ProductHolder();
			convertView = View.inflate(context, R.layout.list_item_product,null);
			myHolder.tv_buys = (TextView) convertView.findViewById(R.id.tv_buys);
			myHolder.tv_limit_day = (TextView) convertView.findViewById(R.id.tv_limit_day);
			myHolder.tv_outof_day = (TextView) convertView.findViewById(R.id.tv_outof_day);
			myHolder.tv_plan_info = (TextView) convertView.findViewById(R.id.tv_plan_info);
			myHolder.ll_nuber_percent = (LinearLayout) convertView.findViewById(R.id.ll_nuber_percent);
			myHolder.ll_year_income = (LinearLayout) convertView.findViewById(R.id.ll_year_income);
			myHolder.tv_limit_day_unit = (TextView) convertView.findViewById(R.id.tv_limit_day_unit);
			myHolder.tv_outof_day_unit = (TextView) convertView.findViewById(R.id.tv_outof_day_unit);
			myHolder.iv_time = (ImageView) convertView.findViewById(R.id.iv_time);
			convertView.setTag(myHolder);
		} else {
			myHolder = (ProductHolder) convertView.getTag();
		}
		
		myHolder.tv_plan_info.setText(product.getTitle());
		if(1==product.getDate_status()){
			myHolder.tv_limit_day.setText(product.getDate());
			myHolder.tv_limit_day_unit.setVisibility(View.VISIBLE);
		}else {
			myHolder.tv_limit_day_unit.setVisibility(View.GONE);
			myHolder.tv_limit_day.setText(product.getDate());
		}
		myHolder.tv_buys.setText(String.valueOf(product.getNumber()));
		
		if(1==product.getStoptime_status()){
			myHolder.iv_time.setBackgroundResource(R.drawable.bg_product_sell);
			myHolder.tv_outof_day.setText(DateUtil.getDays(Integer.parseInt(product.getStoptime())));
			myHolder.tv_outof_day_unit.setVisibility(View.VISIBLE);
		}else{
			myHolder.iv_time.setBackgroundResource(R.drawable.bg_product_sold_out);
			myHolder.tv_outof_day_unit.setVisibility(View.GONE);
			myHolder.tv_outof_day.setText(product.getStoptime());
		}
		Rate rate = product.getRate();
		if(rate.getMin()==rate.getMax()){
			UiUtil.rateToString(myHolder.ll_nuber_percent,context,rate.getMin()+ "%");
		}else{
			UiUtil.rateToString(myHolder.ll_nuber_percent,context,rate.getMin() + "_" + rate.getMax() + "%");
		}

		return convertView;
	}

	protected void goToProductDetail() {
		
	}

	

	class ProductHolder {
		TextView tv_plan_info, tv_limit_day, tv_buys, tv_outof_day,tv_limit_day_unit,tv_outof_day_unit;
		LinearLayout ll_nuber_percent, ll_year_income;
		ImageView iv_time;
	}

	
}
