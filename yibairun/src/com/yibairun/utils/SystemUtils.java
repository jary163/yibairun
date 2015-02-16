package com.yibairun.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yibairun.application.AppController;
import com.yibairun.bean.StatusMessage;
import com.yibairun.ui.activity.LoginActivity;

public class SystemUtils {


	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 匹配手机号码
	 * @param value
	 * @return
	 */
	public static boolean matchMobile(String value) {

		String regExp = "^[1]([0-9][0-9]{1}|59|58|88|89)[0-9]{8}$";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(value);

		return m.find();// boolean

	}
	
	public enum ResponseType{
		/**
		 * 直接返回操作
		 */
		DIRECT_RETURN,
		/**
		 * 清空appkey
		 */
		CLEAR_APPKEY,
		/**
		 * 跳转到登录界面
		 */
		JUMP_LOGIN
	}
	
    /**
     * 处理返回数据
     * @param goToLogin  		判断是否执行登录操作
     * @param responseType 		登录超时事件处理类型
     * @param object	 		得到的response
     */
    protected void managerResponse(Object object,ResponseType responseType,Context context){
    
    	if(object instanceof StatusMessage){
			StatusMessage message = (StatusMessage) object;
			Toast.makeText(context, message.getInfo(), Toast.LENGTH_SHORT).show();
			if (message.getStatus() == 0) {
				switch (responseType) {
				case CLEAR_APPKEY:
					((AppController)context.getApplicationContext()).setAppKey("");
					break;
				case JUMP_LOGIN:
					((AppController)context.getApplicationContext()).setAppKey("");
					context.startActivity(new Intent(context, LoginActivity.class));
					break;
				case DIRECT_RETURN:
					return;
				default:
					break;
				}
    		}
    	}
    }
}
