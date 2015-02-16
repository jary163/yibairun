package cn.hi.eim.manager;

import android.net.Uri;
import android.util.Log;
import cn.hi.eim.model.User;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public class HIManager {

	private final String TAG = getClass().getSimpleName();
	private static HIManager mInstance;

	private static String HI_BASE = "http://www.17hi.com.cn:9090/plugins/hiservice";


	public static HIManager getInstance(){
		if(mInstance == null) {
			mInstance = new HIManager();
		}

		return mInstance;
	}

	public <T> void getDefaultHashtagTweets(Listener<User[]> listener, ErrorListener errorListener, String type, String gender,String username){
		getTweetForHashtag(listener, errorListener,type,gender,username);
	}

	public void getTweetForHashtag(Listener<User[]> listener, ErrorListener errorListener, String type, String gender,String username){

		Uri.Builder uriBuilder = Uri.parse(HI_BASE+"/hiservice").buildUpon()
				.appendQueryParameter("type", type)
				.appendQueryParameter("username", username)
				.appendQueryParameter("gender", gender);


		String uri = uriBuilder.build().toString();

		GsonRequest<User[]> request = new GsonRequest<User[]>(Method.GET
				, uri
				, User[].class
				, listener
				, errorListener);

		Log.i(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}

}
