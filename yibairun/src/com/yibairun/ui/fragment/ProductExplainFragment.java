package com.yibairun.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.bean.Message;
import com.yibairun.bean.ProductDetail;
import com.yibairun.comm.Constant;

public class ProductExplainFragment extends BaseFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ProductDetail mProductDetail;
    private TextView tv_product_des;

    public static ProductExplainFragment newInstance(int sectionNumber,ProductDetail productDetail) {
        ProductExplainFragment fragment = new ProductExplainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.mProductDetail = productDetail;
        return fragment;
    }

    public ProductExplainFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_explain, container, false);
        tv_product_des = (TextView) rootView.findViewById(R.id.tv_product_des);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
    	if(!TextUtils.isEmpty(mProductDetail.getOtherinfo())){
    		tv_product_des.setText(Html.fromHtml(mProductDetail.getOtherinfo()));
    	}
    }
}
