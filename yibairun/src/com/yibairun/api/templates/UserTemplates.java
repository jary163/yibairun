package com.yibairun.api.templates;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.JsonObject;
import com.yibairun.api.operations.AbstractOperations;
import com.yibairun.api.operations.UserOperations;
import com.yibairun.api.request.GsonRequest;
import com.yibairun.api.request.MultipartRequest;
import com.yibairun.bean.Bank;
import com.yibairun.bean.BankList;
import com.yibairun.bean.MessageList;
import com.yibairun.bean.StatusMessage;
import com.yibairun.bean.UserInfo;
import com.yibairun.manager.RequestManager;
import com.yibairun.utils.Lg;
import com.yibairun.utils.UrlConfig;

import org.apache.http.client.HttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserTemplates extends AbstractOperations implements UserOperations {

    public UserTemplates(Context ctx) {
        super(ctx);
    }

    @Override
    public void setToken(String token) {

    }

    @Override
    public void setDeviceId(String deviceId) {
        super.setDeviceId(deviceId);
    }

    @Override
    public void getUserInfo(Response.Listener<UserInfo> listener, Response.ErrorListener errorListener, String appkey) {
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.PERSONAL_GET_USERINFO).buildUpon().appendQueryParameter("appkey", String.valueOf(appkey));

        String uri = uriBuilder.build().toString();

        GsonRequest<UserInfo> request = new GsonRequest<UserInfo>( uri, UserInfo.class, listener, errorListener);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

    @Override
    public void login(Response.Listener<UserInfo> listener, Response.ErrorListener errorListener, String mobile, String password,String udid) {
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.LOGIN).buildUpon();
        //appendQueryParameter("mobile", String.valueOf(mobile)).appendQueryParameter("password", String.valueOf(password));

        HashMap mParams = new HashMap<String, String>();
        mParams.put("mobile",  String.valueOf(mobile));
        mParams.put("password",  String.valueOf(password));
        mParams.put("udid",  String.valueOf(udid));

        String uri = uriBuilder.build().toString();

        GsonRequest<UserInfo> request = new GsonRequest<UserInfo>(Request.Method.POST, uri, UserInfo.class, listener, errorListener,mParams);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

    /**
	 * @deprecated Use {@link #reg(Listener<UserInfo>,ErrorListener,String,String,String,String)} instead
	 */
	@Override
	public void reg(Listener<UserInfo> listener, ErrorListener errorListener, String mobile, String password, String verify) {
		reg(listener, errorListener, mobile, password, null, verify);
	}

	@Override
    public void reg(Listener<UserInfo> listener, ErrorListener errorListener, String mobile, String password, String paypassword, String verify) {
        // TODO Auto-generated method stub
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.REG).buildUpon();


        HashMap mParams = new HashMap<String, String>();
        mParams.put("mobile",  mobile);
        if(!TextUtils.isEmpty(password))
        	mParams.put("password",  password);
        if(!TextUtils.isEmpty(paypassword))
        	mParams.put("paypassword", paypassword);
        if (verify != null)
            mParams.put("verify",  verify);



        String uri = uriBuilder.build().toString();

        GsonRequest<UserInfo> request = new GsonRequest<UserInfo>(Request.Method.POST, uri, UserInfo.class, listener, errorListener,mParams);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

    @Override
    public void setPayPw(Listener<StatusMessage> listener, ErrorListener errorListener, String appkey, String password) {
        // TODO Auto-generated method stub
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.SET_PAYPASSWORD).buildUpon();
        uriBuilder.appendQueryParameter("appkey", String.valueOf(appkey)).appendQueryParameter("paypassword", String.valueOf(password));


        String uri = uriBuilder.build().toString();

        GsonRequest request = new GsonRequest(uri, StatusMessage.class, listener, errorListener);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

    @Override
    public void changePayPw(Listener<StatusMessage> listener, ErrorListener errorListener, String appkey, String old_paypassword, String new_paypassword) {
        // TODO Auto-generated method stub
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.CHANGE_PAYPASSWORD).buildUpon();
        uriBuilder.appendQueryParameter("appkey", String.valueOf(appkey)).appendQueryParameter("old_paypassword", String.valueOf(old_paypassword)).appendQueryParameter("new_paypassword", String.valueOf(new_paypassword));


        String uri = uriBuilder.build().toString();

        GsonRequest request = new GsonRequest(uri, StatusMessage.class, listener, errorListener);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

    @Override
    public void getPayPw(Listener<StatusMessage> listener, ErrorListener errorListener, String appkey, String paypassword, String verify) {
        // TODO Auto-generated method stub
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.GET_PAYPASSWORD).buildUpon();
        HashMap<String,String> mParams = new HashMap<String, String>();
        if(TextUtils.isEmpty(verify)){
            mParams.put("appkey", String.valueOf(appkey));
        }else{
            mParams.put("appkey", String.valueOf(appkey));
            mParams.put("paypassword", String.valueOf(paypassword));
            mParams.put("verify", String.valueOf(verify));
        }

        String uri = uriBuilder.build().toString();

        GsonRequest request = new GsonRequest(Request.Method.POST, uri, StatusMessage.class, listener, errorListener,mParams);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

    @Override
    public void getAllBankList(Listener<BankList> listener, ErrorListener errorListener, String appkey) {
        // TODO Auto-generated method stub
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.GET_ALL_BANK_LIST).buildUpon();
        uriBuilder.appendQueryParameter("appkey", String.valueOf(appkey));


        String uri = uriBuilder.build().toString();

        GsonRequest request = new GsonRequest( uri, BankList.class, listener, errorListener);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }
    @Override
    public void getPensonalMessageList(Listener<MessageList> listener, ErrorListener errorListener, String appkey,int page) {
        // TODO Auto-generated method stub
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.PERSONAL_MSG).buildUpon();
        if(appkey!=null)
        uriBuilder.appendQueryParameter("appkey", String.valueOf(appkey));
        uriBuilder.appendQueryParameter("page", String.valueOf(page));

        String uri = uriBuilder.build().toString();

        GsonRequest request = new GsonRequest(uri, MessageList.class, listener, errorListener);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }
    @Override
    public void uploadAvatvar(Listener<String> listener, ErrorListener errorListener, String appkey,File file) {
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.UP_IMG).buildUpon();

        Map<String,String> map = new HashMap<String, String>();
        map.put("appkey",appkey);

        String uri = uriBuilder.build().toString();

        MultipartRequest request = new MultipartRequest(uri, listener, errorListener,file,map);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }
    @Override
    public void uploadIdCard(Listener<String> listener, ErrorListener errorListener, String appkey,File file) {
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.UP_SFZ_IMG).buildUpon();

        Map<String,String> map = new HashMap<String, String>();
        map.put("appkey",appkey);

        String uri = uriBuilder.build().toString();

        MultipartRequest request = new MultipartRequest(uri, listener, errorListener,file,map);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }
    @Override
    public void sMCertification(Listener<String> listener, ErrorListener errorListener, String appkey,String realname,String card, File file) {
        Uri.Builder uriBuilder = Uri.parse(UrlConfig.SHIMING_CERTIFICATION).buildUpon();

        Map<String,String> map = new HashMap<String, String>();
       	map.put("appkey",appkey);
        
       	if(!TextUtils.isEmpty(realname)){
       		map.put("realname",realname);
       		map.put("card",card);
       	}

        String uri = uriBuilder.build().toString();

        MultipartRequest request = new MultipartRequest(uri, listener, errorListener,file,map);

        lg.i(request.toString());
        RequestManager.addToRequestQueue(request);
    }

	@Override
	public void getAppMessageList(Listener<MessageList> listener,
			ErrorListener errorListener, int page) {
		 Uri.Builder uriBuilder = Uri.parse(UrlConfig.APP_MSG).buildUpon();
	        uriBuilder.appendQueryParameter("page", String.valueOf(page));

	        String uri = uriBuilder.build().toString();

	        GsonRequest request = new GsonRequest(uri, MessageList.class, listener, errorListener);

	        lg.i(request.toString());
	        RequestManager.addToRequestQueue(request);
	}


}