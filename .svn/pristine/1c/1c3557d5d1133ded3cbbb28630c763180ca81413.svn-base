package com.yibairun.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yibairun.api.operations.YibaiRunApi;
import com.yibairun.application.AppController;
import com.yibairun.listener.OnFragmentComunicationListener;
import com.yibairun.utils.MyDialog;


/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment implements OnClickListener {

    protected YibaiRunApi api;
    protected RelativeLayout pending_view;
    protected AppController appController;
	protected MyDialog pg;
	protected  OnFragmentComunicationListener onFagmentlistener;
    public BaseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
		pg = new MyDialog(getActivity());
    	return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	pg = new MyDialog(getActivity());
    }
    
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        appController = (AppController) activity.getApplicationContext();
        api = appController.getApi();
        try {
			onFagmentlistener = (OnFragmentComunicationListener) activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Activity must implents OnFragmentComunicationListener");
		}
    }
    @Override
    public void onClick(View v) {

    }

    protected void startActivity(Class clas) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clas);
        getActivity().startActivity(intent);
    }

    protected void startActivity(Class clas, Intent intent) {
        intent.setClass(getActivity(), clas);
        getActivity().startActivity(intent);
    }
    
	protected void toast(int resid) {
		toast(getString(resid));
	}

	protected void toast(String tips) {
		Toast.makeText(getActivity(), tips, Toast.LENGTH_SHORT).show();
	}


}
