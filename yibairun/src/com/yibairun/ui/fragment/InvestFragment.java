package com.yibairun.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.adapter.InvestAdapter;
import com.yibairun.bean.ProductInvestList;
import com.yibairun.bean.UserInfo;
import com.yibairun.ui.components.LoadMoreListView;
import com.yibairun.ui.components.LoadMoreListView.OnLoadMoreListener;
import com.yibairun.utils.VolleyErrorHelper;

public class InvestFragment extends ScrollTabHolderFragment implements AbsListView.OnScrollListener,OnRefreshListener,OnLoadMoreListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private View rootView;
    private LoadMoreListView loadMoreListView;
    private InvestAdapter investAdapter;
    private Context context;
    private int Type;
    private UserInfo mUserInfo;
    private static final String ARG_POSITION = "position";
    private ProductInvestList response;
    private InvestType investType;
    private int mPosition;
    private int page;
	private SwipeRefreshLayout swipeLayout;
    
    public enum InvestType{
    	/**
    	 * 投资中
    	 */
    	INVESTING,
    	/**
    	 * 投资结束
    	 */
    	INVESTED
    }
    
	public static ScrollTabHolderFragment newInstance(int position,InvestType investType) {
		InvestFragment fragment = new InvestFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        fragment.investType = investType;
        return fragment;
	}

    public InvestFragment() {
    }

    public void setDateAndType(UserInfo mUserInfo, Integer type) {
        this.mUserInfo = mUserInfo;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        rootView = inflater.inflate(R.layout.fragment_invest, container, false);
        loadMoreListView = (LoadMoreListView) rootView.findViewById(R.id.loadlistview);
        loadMoreListView.setOnScrollListener(this);

        
        
        pending_view = (RelativeLayout) rootView.findViewById(R.id.pending_view);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);  
        swipeLayout.setOnRefreshListener(this);  
        loadMoreListView.setOnLoadMoreListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        
        

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, loadMoreListView, false);
        loadMoreListView.addHeaderView(placeHolderView);
        
        initListener();
        page=0;
        getDateFromNet();
        return rootView;
    }
    

    private void initListener() {
    	loadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//TODO   数据不匹配，暂时不让点击
				/*	ProductInvest product = (ProductInvest) parent.getItemAtPosition(position);
	                Intent intent = new Intent();
	                Bundle mBundle = new Bundle();
					mBundle.putSerializable(Constant.PRODUCT, product);
					intent.putExtras(mBundle);
	                intent.setClass(context, ProductDetailGraphActivity.class);
	                //intent.putExtra(Constant.PRODUCT_ID, 173);
	                context.startActivity(intent);	*/			
			}
		});
		
	}

	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

	
    private void initDate() {
        investAdapter = new InvestAdapter(context,response.getProductList());
        loadMoreListView.setAdapter(investAdapter);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && loadMoreListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        loadMoreListView.setSelectionFromTop(1, scrollHeight);
    }

	public void getDateFromNet() {
		switch (investType) {
		case INVESTED:		
			api.productOperations().getOverInvestMentList(createMyReqSuccessListener(), createMyReqErrorListener(), appController.getAppKey(), ++page);
			break;
		case INVESTING:
			api.productOperations().getInvestingList(createMyReqSuccessListener(), createMyReqErrorListener(), appController.getAppKey(), ++page);
			break;
		default:
			break;
		}
		
	}
	
	

    private Listener<ProductInvestList> createMyReqSuccessListener() {
        return new Listener<ProductInvestList>() {
            @Override
            public void onResponse(ProductInvestList response) {
            	swipeLayout.setRefreshing(false); 
                pending_view.setVisibility(View.GONE);
                InvestFragment.this.response = response;
                if (page == 1 || loadMoreListView.getAdapter() == null) {
                	initDate();
               } else investAdapter.addData(response.getProductList());
			    loadMoreListView.setCanLoadMore(response.getStatus()>0);
			    loadMoreListView.onLoadMoreComplete();

            }
        };
    }

    protected Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	swipeLayout.setRefreshing(false); 
                pending_view.setVisibility(View.GONE);
				/* ErrorMessage msg = (ErrorMessage) error; */
                Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
            }
        };
    }

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		getDateFromNet();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page=0;
		getDateFromNet();
	}


}
