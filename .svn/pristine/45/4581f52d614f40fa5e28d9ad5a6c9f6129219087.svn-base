package com.yibairun.api.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.yibairun.exception.CommmonException;
import com.yibairun.manager.RequestManager;
import com.yibairun.utils.Lg;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Wrapper for Volley requests to facilitate parsing of json responses.
 *
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {
    private static final int MY_SOCKET_TIMEOUT_MS = 30000;
    private Map<String, String> params;
    protected Lg lg = Lg.jLog();
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
     * @param method        Request type.. Method.GET etc
     * @param url           path for the requests
     * @param objectClass   expected class type for the response. Used by gson for serialization.
     * @param listener      handler for the response
     * @param errorListener handler for errors
     */
    public GsonRequest(String url, Class<T> objectClass, Listener<T> listener, ErrorListener errorListener) {

        this(Method.GET,url,objectClass,listener,errorListener,null);



    }

    public GsonRequest(int method, String url, Class<T> objectClass, Listener<T> listener, ErrorListener errorListener, Map<String, String> params) {
        super(method, url, errorListener);
        this.params = params;
        this.mClass = objectClass;
        this.mListener = listener;
        mGson = new Gson();

             /* setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            response.data, HTTP.UTF_8);
            lg.i("GsonRequest===>>" + json);
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int status = jsonObject.get("status").getAsInt();
            if (status == 0) {
                return Response.error(new CommmonException(jsonObject.get("info").getAsString()));
            } else
                return Response.success(mGson.fromJson(json, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
    public static byte[] getDataInDiskCache(String key){
        Cache.Entry entry = RequestManager.getRequestQueue().getCache().get(key);
        return entry ==null? null : entry.data;
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }


}
