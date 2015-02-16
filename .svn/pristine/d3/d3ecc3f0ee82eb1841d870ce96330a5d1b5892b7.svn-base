package cn.hi.eim.manager;

import java.io.UnsupportedEncodingException;

import android.util.Log;
import cn.hi.eim.exception.ErrorMessage;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Wrapper for Volley requests to facilitate parsing of json responses. 
 * 
 * @param <T>
 */
public class GsonRequest<T> extends Request<T>{

	/**
	 * Gson parser 
	 */
	private final Gson mGson;
	
	/**
	 * Class type for the response
	 */
	private final Class<T> mClass;
	
	
	/**
	 * Callback for response delivery 
	 */
	private final Listener<T> mListener;
	
	/**
	 * @param method
	 * 		Request type.. Method.GET etc
	 * @param url
	 * 		path for the requests
	 * @param objectClass
	 * 		expected class type for the response. Used by gson for serialization.
	 * @param listener
	 * 		handler for the response
	 * @param errorListener
	 * 		handler for errors
	 */
	public GsonRequest(int method
						, String url
						, Class<T> objectClass
						, Listener<T> listener
						, ErrorListener errorListener) {
		
		super(method, url, errorListener);
		this.mClass = objectClass;
		this.mListener = listener;
		mGson = new Gson();
		
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Log.i("GsonRequest===>>", json);
			return Response.success(mGson.fromJson(json, mClass),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
		
	}

/*	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError){
		 if( volleyError instanceof NetworkError) {
			 return new ErrorMessage("ÍøÂçÁ¬½ÓÊ§°Ü", 1001);
         } else if( volleyError instanceof ServerError) {
        	    NetworkResponse response = volleyError.networkResponse;
       	     if(response != null && response.data != null){
       	         switch(response.statusCode){
       	             case 405:
       	                  return  trimMessage(response.data);
       	             }
       	            //Additional cases
       	     }
         } else if( volleyError instanceof AuthFailureError) {
         } else if( volleyError instanceof ParseError) {
         } else if( volleyError instanceof NoConnectionError) {
         } else if( volleyError instanceof TimeoutError) {
         }
		
	 
		return null;
	
	    }
	public ErrorMessage trimMessage(byte[] json){
		String jsonStr = new String(json);
		Log.e("GsonRequest===>>",  jsonStr);
        Gson gson = new Gson();
        ErrorMessage msg =  gson.fromJson(jsonStr, ErrorMessage.class);
        return msg;
 
}*/
		
}
