package cn.hi.eim.util;

import cn.hi.eim.R;
import android.content.Context;
import android.text.TextUtils;

/**
 * 系统工具集
 * @author zhangying
 *
 */
public class SystemUtils {

	/**
	 * 得到服务器可用的user名称
	 * @param userName
	 * @return
	 */
	public static String getUserNameToService(Context context,String userName){
		if(!TextUtils.isEmpty(userName)){
			if(!userName.contains("@")){
				userName = userName +"@"+ context.getResources().getString(R.string.xmpp_host);
			}else{
				userName = userName.split("@")[0] +" @ "+ context.getResources().getString(R.string.xmpp_host);
			}
		}
		return userName;
	}
	
	/**
	 * 得到本地可用的user名称
	 */
	public static String getUserNameToLocal(String userName){
		if(!TextUtils.isEmpty(userName)&&userName.contains("@")){
			userName = userName.split("@")[0];
		}
		return userName;
	}
}
