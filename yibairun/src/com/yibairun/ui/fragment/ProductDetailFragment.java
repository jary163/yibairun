package com.yibairun.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.bean.Product;
import com.yibairun.bean.ProductDetail;
import com.yibairun.bean.Rate;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;
import com.yibairun.listener.ProductListener;
import com.yibairun.ui.activity.ApplyBuyActivity;
import com.yibairun.ui.activity.LoginActivity;
import com.yibairun.ui.components.WaterViewGroupEx;
import com.yibairun.utils.DateUtil;
import com.yibairun.utils.ProductUtils;

public class ProductDetailFragment extends BaseFragment implements ProductListener{
    private static final String ARG_SECTION_NUMBER = "section_number";
	private TextView tv_plan_info,tv_buys_limit,tv_product_total,tv_limit_day,tv_overplus_days,tv_year_income,tv_circle_total_people,tv_circle_total_money;
	private LinearLayout ll_enter;
	private ProductDetail productDetail;
	private Product mProduct;
	private WaterViewGroupEx vg_waterView;
	private Rate rate;
	private TextView tv_outof_day_unit;
	private TextView tv_limit_day_unit;

    public static ProductDetailFragment newInstance(int sectionNumber,ProductDetail productDetail, Product mProduct) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.productDetail = productDetail;
        fragment.mProduct = mProduct;

        return fragment;
    }

    public ProductDetailFragment() {

    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        pending_view = (RelativeLayout)rootView.findViewById(R.id.pending_view);
		tv_plan_info = (TextView) rootView.findViewById(R.id.tv_plan_info);
		tv_buys_limit = (TextView) rootView.findViewById(R.id.tv_buys_limit);
		tv_product_total = (TextView) rootView.findViewById(R.id.tv_product_total);
		tv_limit_day = (TextView) rootView.findViewById(R.id.tv_limit_day);
		tv_limit_day_unit = (TextView) rootView.findViewById(R.id.tv_limit_day_unit);
		tv_overplus_days = (TextView) rootView.findViewById(R.id.tv_overplus_days);
		tv_outof_day_unit = (TextView) rootView.findViewById(R.id.tv_outof_day_unit);
		tv_circle_total_people = (TextView) rootView.findViewById(R.id.tv_circle_total_people);
		tv_circle_total_money = (TextView) rootView.findViewById(R.id.tv_circle_total_money);
		ll_enter = (LinearLayout) rootView.findViewById(R.id.ll_enter);
		tv_year_income = (TextView) rootView.findViewById(R.id.tv_year_income);
		vg_waterView = (WaterViewGroupEx) rootView.findViewById(R.id.vg_waterView);
		initListener();
        return rootView;
    }
    


	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
		rate = productDetail.getRate();

		int color0s[] = new int[] { getResources().getColor(R.color.red),
				getResources().getColor(R.color.red) };
		
		tv_year_income.setText(rate.toString()+"%");
		tv_circle_total_people.setText(String.valueOf(mProduct.getNumber()));
		tv_circle_total_money.setText(String.valueOf(productDetail.getTotalmoney()));
		tv_product_total.setText(String.valueOf(productDetail.getMoney()));
		tv_plan_info.setText(mProduct.getTitle());
		
		if(1==mProduct.getDate_status()){
			tv_limit_day.setText(mProduct.getDate());
			tv_limit_day_unit.setVisibility(View.VISIBLE);
		}else {
			tv_limit_day_unit.setVisibility(View.GONE);
			tv_limit_day.setText(mProduct.getDate());
		}
		
		if(1==mProduct.getStoptime_status()){
			tv_overplus_days.setText(DateUtil.getDays(Integer.parseInt(mProduct.getStoptime())));
			tv_outof_day_unit.setVisibility(View.VISIBLE);
		}else{
			tv_outof_day_unit.setVisibility(View.GONE);
			tv_overplus_days.setText(mProduct.getStoptime());
		}
		
		
		vg_waterView.setRotate(-90);//设置旋转角度
		vg_waterView.setpBlack(ProductUtils.getFormatProgress(300),getResources().getColor(R.color.circle_first));//第二层view
		vg_waterView.startAnimation(ProductUtils.getFormatProgress(990),color0s);//底层view
		
		
    }

    private void initListener() {
    	ll_enter.setOnClickListener(this);
	}
	
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
		case R.id.ll_enter:
			//1.判断是否登录
			//流程跳转、跳转到注册界面、或者是购买界面、这里直接跳转到购买界面
            if(productDetail.getStoptime_status()==0){
                toast(R.string.product_investment__ended);
            }
			else if(TextUtils.isEmpty(appController.getAppKey())){
				Intent intent = new Intent();
				intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_PRODUCT_DETAIL);
				startActivity(LoginActivity.class,intent);
			}else if(TextUtils.isEmpty(appController.getUserinfo().getUser().getUsername())){//用户名为空代表只获取了appkey，而没有获取更详细的信息
				ProductUtils.getUserInfoFromNet(getActivity(), this);
			}else if(TextUtils.isEmpty(appController.getUserinfo().getUser().getRealname())){//用户没有通过实名认证
				ProductUtils.showNotCertificationDialog(getActivity());
			}else{
				goToApplyBuyPage();
			}
            break;
            default:
                break;
        }
        

    }

    private void goToApplyBuyPage(){
    	Intent intent = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(Constant.PRODUCT_DETAIL, productDetail);
		intent.putExtras(mBundle);
		startActivity(ApplyBuyActivity.class,intent);
    }
    
	@Override
	public void onGetProductInfo() {
		UserInfo userinfo = appController.getUserinfo();
		if (TextUtils.isEmpty(userinfo.getUser().getRealname())
				|| TextUtils.isEmpty(userinfo.getUser().getCardnum())) {
			ProductUtils.showNotCertificationDialog(getActivity());
		}else{
			goToApplyBuyPage();
		}
	}
    
}
