package com.yibairun.api.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.yibairun.exception.CommmonException;
import com.yibairun.utils.Lg;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MultipartRequest extends Request<String> {

    private final Gson mGson;
    // private MultipartEntity entity = new MultipartEntity();
protected Lg lg = Lg.jLog();
    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;
    private static final String FILE_PART_NAME = "file";

    private final Response.Listener<String> mListener;
    private final File mFilePart;
    private final Map<String, String> mStringPart;

    public MultipartRequest(String url,Response.Listener<String> listener, Response.ErrorListener errorListener,
                             File file,
                            Map<String, String> mStringPart) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        this.mStringPart = mStringPart;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        Charset chars = Charset.forName("UTF-8");
//        entity.setCharset(chars);
        buildMultipartEntity();
        mGson = new Gson();
    }

    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    private void buildMultipartEntity() {
        if(mFilePart!=null)
        entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            entity.addTextBody(entry.getKey(), entry.getValue(), ContentType.create(
                    HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8));// for json ContentType.APPLICATION_JSON
        }
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//        return Response.success("Uploaded", getCacheEntry());
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            response.data, HTTP.UTF_8);
            lg.i("GsonRequest===>>" + json);
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int status = jsonObject.get("status").getAsInt();
            if (status == 0) {
                return Response.error(new CommmonException(jsonObject.get("info").getAsString()));
            } else
                return Response.success(mGson.fromJson(jsonObject.get("info").getAsString(), String.class), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}