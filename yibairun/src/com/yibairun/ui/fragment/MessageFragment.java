package com.yibairun.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.adapter.MsgAdapter;
import com.yibairun.bean.Message;
import com.yibairun.bean.MessageList;
import com.yibairun.comm.Constant;
import com.yibairun.ui.activity.MessageDetailActivity;
import com.yibairun.ui.components.LoadMoreListView;
import com.yibairun.ui.components.LoadMoreListView.OnLoadMoreListener;
import com.yibairun.utils.VolleyErrorHelper;

public class MessageFragment extends BaseFragment implements OnRefreshListener,OnLoadMoreListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private LoadMoreListView loadMoreListView;
    private View rootView;
    private Context context;
    private MsgAdapter msgAdapter;
	private int page;
	private SwipeRefreshLayout swipeLayout;
	private int type;

    public static MessageFragment newInstance(int sectionNumber) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
    

    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getActivity().getIntent();
		type = intent.getIntExtra(Constant.ACTIVITY_TYPE, -1);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        loadMoreListView = (LoadMoreListView) rootView.findViewById(R.id.lv_product);
        pending_view = (RelativeLayout) rootView.findViewById(R.id.pending_view);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);  
        swipeLayout.setOnRefreshListener(this);  
        loadMoreListView.setOnLoadMoreListener(this);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4  
        /*swipeLayout.setColorScheme(android.R.color.white,  
                android.R.color.holo_green_light,  
                android.R.color.holo_orange_light, android.R.color.holo_red_light); */
        
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,  
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        pending_view = (RelativeLayout) rootView.findViewById(R.id.pending_view);
        
        initDate();
        initListener();
        return rootView;
    }

    private void initDate() {
    	pending_view.setVisibility(View.VISIBLE);
    	getAsyDate();
    }

    private void initListener() {
    	loadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> viewGroup, View view, int position,
					long id) {
				if(type!=Constant.FRAGMENT_PERSONAL){
					Message message = (Message) loadMoreListView.getItemAtPosition(position);
	                Intent intent = new Intent();
	                Bundle mBundle = new Bundle();
					mBundle.putSerializable(Constant.MESSAGE_DETAIL, message);
					intent.putExtras(mBundle);
	                intent.setClass(context, MessageDetailActivity.class);
	                context.startActivity(intent);
				}
			}
		});
    }
    
    public void getAsyDate(){
    	switch (type) {
		case Constant.FRAGMENT_PERSONAL:
			api.userOperations().getPensonalMessageList(createMyReqSuccessListener(), createMyReqErrorListener(),appController.getAppKey(), ++page);
			break;
		default:
			api.userOperations().getAppMessageList(createMyReqSuccessListener(), createMyReqErrorListener(), ++page);
			break;
		}
    }
    
    private Listener<MessageList> createMyReqSuccessListener() {
    	return new Listener<MessageList>() {

			@Override
			public void onResponse(MessageList response) {
				pending_view.setVisibility(View.GONE);
				swipeLayout.setRefreshing(false); 
			    if (page == 1 || loadMoreListView.getAdapter() == null) {
			    	 msgAdapter = new MsgAdapter(context,response.getMessageList());
                    msgAdapter.setType(type);
			         loadMoreListView.setAdapter(msgAdapter);
                } else msgAdapter.addAll(response.getMessageList());
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
				Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
			}
        };
    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if(swipeLayout.isRefreshing()){
			page=0;
			getAsyDate();
			
		}
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		getAsyDate();
	}

}
