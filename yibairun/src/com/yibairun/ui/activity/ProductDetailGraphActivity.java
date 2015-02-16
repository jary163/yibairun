package com.yibairun.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.yibairun.R;
import com.yibairun.bean.Product;
import com.yibairun.bean.ProductDetail;
import com.yibairun.bean.ProductDetailInfo;
import com.yibairun.bean.Rate;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;
import com.yibairun.listener.ProductListener;
import com.yibairun.ui.components.CalculateDialog;
import com.yibairun.ui.components.WaterViewGroupEx;
import com.yibairun.utils.ProductUtils;

public class ProductDetailGraphActivity extends BaseActivity implements WaterViewGroupEx.AnimationListener, ProductListener
{

	private int product_id;
	private WaterViewGroupEx vg_waterView;
	private Product mProduct;
	private Dialog calculateDialog;
	private EditText et_chat_content;
	//private ChartView myView;
	private LinearLayout ll_enter;
	private ProductDetail productDetail;
	private TextView tv_year_income, tv_bank_rate, tv_balance_rate;
	private LineChart mChart;
	private Matrix matrix;
	private int color1s[];
	private int color2s[];
	private int color0s[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_product_detail_graph);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {

		pending_view = findView(R.id.pending_view);
		vg_waterView = findView(R.id.vg_waterView);
		et_chat_content = findView(R.id.et_chat_content);
		ll_enter = findView(R.id.ll_enter);
		tv_year_income = findView(R.id.tv_year_income);
		tv_bank_rate = findView(R.id.tv_bank_rate);
		tv_balance_rate = findView(R.id.tv_balance_rate);
		mChart = findView(R.id.cv_graph);
		
	}

	@Override
	public void initDate() {
		mProduct = (Product) getIntent().getSerializableExtra(Constant.PRODUCT);
		product_id = mProduct.getId();
		matrix = new Matrix();
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(getResources().getString(R.string.title_product_detail));*/
		color1s = new int[] { getResources().getColor(R.color.blue),
				getResources().getColor(R.color.blue) };
		color2s = new int[] { getResources().getColor(R.color.blue1),
				getResources().getColor(R.color.blue1) };
		color0s = new int[] { getResources().getColor(R.color.white),
				getResources().getColor(R.color.white) };

		pending_view.setVisibility(View.VISIBLE);
		api.productOperations().getProductDetail(
				new Listener<ProductDetailInfo>() {

					@Override
					public void onResponse(ProductDetailInfo response) {
						productDetail = response.getProductDetail();
						pending_view.setVisibility(View.GONE);
						
						vg_waterView.setpBankEnd(ProductUtils.getFormatProgress((int) (productDetail.getBank_rate() * 100)),getResources().getColor(R.color.circle_first));//第三层view
						vg_waterView.setpBlack(ProductUtils.getFormatProgress((int) (productDetail.getZfb_rate() * 100)),getResources().getColor(R.color.blue1));//第二层view
						vg_waterView.startAnimation(ProductUtils.getFormatProgress(990),color0s);// //底层view  数值固定，需要修改
						refreshDate();
						setGraphDate();
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pending_view.setVisibility(View.GONE);
		                //Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();

					}
				}, product_id);

	}

	private void setGraphDate() {
		  mChart.setStartAtZero(true);

	        // enable the drawing of values into the chart
	        mChart.setDrawYValues(true);

	        mChart.setDrawBorder(true);
	        mChart.setBorderPositions(new BorderPosition[] {
	            BorderPosition.BOTTOM,BorderPosition.LEFT
	        });

	        // no description text
	        mChart.setDescription("");

	        // invert the y-axis
	        mChart.setInvertYAxisEnabled(false);

	        // enable value highlighting
	        mChart.setHighlightEnabled(true);

	        // enable touch gestures
	        mChart.setTouchEnabled(true);

	        // enable scaling and dragging
	        mChart.setDragEnabled(true);

	        // if disabled, scaling can be done on x- and y-axis separately
	        mChart.setPinchZoom(false);

	        // set an alternative background color
	        // mChart.setBackgroundColor(Color.GRAY);

/*	        // create a custom MarkerView (extend MarkerView) and specify the layout
	        // to use for it
	        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

	        // define an offset to change the original position of the marker
	        // (optional)
	        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

	        // set the marker to the chart
	        mChart.setMarkerView(mv);*/

	        // enable/disable highlight indicators (the lines that indicate the
	        // highlighted Entry)
	        mChart.setHighlightIndicatorEnabled(false);
	        mChart.setUnit("%");
	        // add data
	        setData();

	        // // restrain the maximum scale-out factor
	        // mChart.setScaleMinima(3f, 3f);
	        //
	        // // center the view to a specific position inside the chart
	        // mChart.centerViewPort(10, 50);

	        // get the legend (only possible after setting data)
	        Legend l = mChart.getLegend();
	        l.setPosition(LegendPosition.BELOW_CHART_RIGHT);
	        l.setFormSize(0f);
	        l.setTextSize(7f);
	        l.setXEntrySpace(4f);
	        
	        
	        // modify the legend ...
	        // l.setPosition(LegendPosition.LEFT_OF_CHART);
	        l.setForm(LegendForm.LINE);
	        setFirstMatrix();

	        // dont forget to refresh the drawing
	        mChart.invalidate();
		
	}

	/**
	 * 设置折线图数据
	 */
    private void setData() {
    	int count = 0;
    	List<Rate> rate_range = productDetail.getRate_range();
    	ArrayList<Entry> yVals = new ArrayList<Entry>();
    	yVals = getDate(rate_range);
    	count = (int) (rate_range.get(rate_range.size()-1).getMax()/10000)+5;//最大值为当前集合元素的最后一个值   单位为万     多出5是为了显示最后一个参数
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }
/*        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
                                                           // ((mult *
                                                           // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }*/

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "（单位:万元）");//TODO  X轴标题

        set1.setLineWidth(1.5f);
        set1.setCircleSize(4f);

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        // set data
        mChart.setData(data);
    }
	
	
	@Override
	public void initListener() {
		// iv_title_left.setOnClickListener(this);
		et_chat_content.setOnClickListener(this);
		vg_waterView.setOnClickListener(this);
		vg_waterView.setmAnimationListener(this);
		ll_enter.setOnClickListener(this);
	}

	public void refreshDate() {
		tv_year_income.setText(productDetail.getRate().toString() + "%");
		tv_bank_rate.setText(productDetail.getBank_rate() + "%");
		tv_balance_rate.setText(productDetail.getZfb_rate() + "%");
		List<Rate> rate_range = productDetail.getRate_range();
/*		myView.SetInfo(new String[] { "0", "5", "10", "15", "20" }, // X轴刻度
				new String[] { "", "3", "6", "9", "12" }, // Y轴刻度
				ProductUtils.getMaxRate(productDetail.getRate_range(), "max"), // 数据x
				ProductUtils.getMaxRate(productDetail.getRate_range(), "rate"), // 数据y
				"图标的标题", "(单位：万)");*/
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if(null==productDetail){
			Toast.makeText(context, "亲，小亿没有获取到数据哦，请返回上个界面重新获取", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.et_chat_content:
			if(productDetail.getStoptime_status()==0){
                toast(R.string.product_investment__ended);
            }else{
            	calculateDialog = new CalculateDialog(context,R.style.CalculateDialog,productDetail,mProduct);
            	calculateDialog.show();
            }
			break;
		case R.id.ll_enter:
			UserInfo userinfo = appController.getUserinfo();
            if(productDetail.getStoptime_status()==0){
                toast(R.string.product_investment__ended);
            }else if(mProduct.getDate_status()==0){
            	toast(R.string.product_investmenting);
            }
			else if (TextUtils.isEmpty(appController.getAppKey())) {
				intent.putExtra(Constant.ACTIVITY_TYPE,Constant.ACTIVITY_PRODUCT_DETAIL_GRAPH);
				startActivity(LoginActivity.class, intent);
			} else if(TextUtils.isEmpty(appController.getUserinfo().getUser().getUsername())){//用户名为空代表只获取了appkey，而没有获取更详细的信息
				ProductUtils.getUserInfoFromNet(this, this);
			}else if(TextUtils.isEmpty(appController.getUserinfo().getUser().getRealname())){//用户没有通过实名认证
				ProductUtils.showNotCertificationDialog(this);
			}else{
              goToApplyBuyPage();
			}
			break;
		case R.id.vg_waterView:
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(Constant.PRODUCT_DETAIL, productDetail);
			mBundle.putSerializable(Constant.PRODUCT, mProduct);
			intent.putExtras(mBundle);
			startActivity(ProductDetailActivity.class,intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onFirstAnimaEnd(int progress) {
		findView(R.id.ll_year_income).setVisibility(View.VISIBLE);
	}

	@Override
	public void onSecondAnimaEnd(int progress) {
		findView(R.id.ll_balance_rate).setVisibility(View.VISIBLE);
	}

	@Override
	public void onThridAnimaEnd(int progress) {
		findView(R.id.ll_bank_rate).setVisibility(View.VISIBLE);
	}
	
    /**
     * 测试数据，entry(第一个为Y，第二个为X)
     * @return
     */
    public ArrayList<Entry> getDate(List<Rate> rate_range){
    	ArrayList<Entry> yVals = new ArrayList<Entry>();
    	for (Rate rate : rate_range) {
			yVals.add(new Entry(rate.getRate(),(int) (rate.getMax()/10000)));//单位为万
		}
    	return yVals;
    }
    
    /**
     * 设置初始的缩放大小
     */
    private void setFirstMatrix(){
    	matrix.postScale(15, 1f, 50, 50);
        mChart.refreshTouch(matrix);
    }

    
    private void goToApplyBuyPage(){
    	Intent intent = new Intent();
    	intent.putExtra(Constant.PRODUCT_DETAIL,productDetail);
		startActivity(ApplyBuyActivity.class, intent);
    }
    
	@Override
	public void onGetProductInfo() {
		UserInfo userinfo = appController.getUserinfo();
		if (TextUtils.isEmpty(userinfo.getUser().getRealname())
				|| TextUtils.isEmpty(userinfo.getUser().getCardnum())) {
			ProductUtils.showNotCertificationDialog(this);
		}else{
			goToApplyBuyPage();
		}
	}
}
