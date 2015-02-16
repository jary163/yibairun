package com.yibairun.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RelativeLayout;

import com.yibairun.R;
import com.yibairun.bean.Product;
import com.yibairun.bean.ProductDetail;
import com.yibairun.comm.Constant;
import com.yibairun.listener.OnFragmentComunicationListener;
import com.yibairun.ui.fragment.ProductDetailFragment;
import com.yibairun.ui.fragment.ProductExplainFragment;

public class ProductDetailActivity extends BaseActivity implements OnFragmentComunicationListener{

	private ProductDetail productDetail;
	private Product mProduct;
	private ViewPager mViewPager;
	private MyViewPagerAdapter myViewPagerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_product_detail);
		super.onCreate(savedInstanceState);
	}

	@Override 
	public  void initView() {

		pending_view = (RelativeLayout) findViewById(R.id.pending_view);
		mViewPager = findView(R.id.viewPager);
	}

	@Override
	public void initDate() {
		productDetail = (ProductDetail) getIntent().getSerializableExtra(Constant.PRODUCT_DETAIL);
		mProduct = (Product) getIntent().getSerializableExtra(Constant.PRODUCT);
       /* bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(getResources().getString(R.string.title_product_detail));*/

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
	}

	@Override
	public void initListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(0==position){
					bar.setTitle(getResources().getString(R.string.title_product_info));
				}else{
					bar.setTitle(getResources().getString(R.string.title_product_detail));
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private class MyViewPagerAdapter extends FragmentPagerAdapter{
		Fragment mFragment[] = new Fragment[2];
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragment[0] = ProductDetailFragment.newInstance(1,productDetail,mProduct);
			mFragment[1] = ProductExplainFragment.newInstance(2,productDetail);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragment[position];
		}

		@Override
		public int getCount() {
			return mFragment.length;
		}
		
	}

	@Override
	public void OnLoginListener(boolean isSuccess) {
		
	}
}
