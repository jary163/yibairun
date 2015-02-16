package com.yibairun.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.util.UUID;


public class DeviceUtil {
	private static final String LOGTAG = DeviceUtil.class.getSimpleName();
	private static WifiManager wifiMgr;
	private static TelephonyManager telMgr;
	private static void initManager(Context context) {
		if (wifiMgr == null) {
			Object obj = context.getSystemService(Context.WIFI_SERVICE);
			if (obj != null) {
				wifiMgr = (WifiManager) obj;
			}
		}
		if (telMgr == null) {
			Object obj = context.getSystemService(Context.TELEPHONY_SERVICE);
			if (obj != null) {
				telMgr = (TelephonyManager) obj;
			}
		}
	}
	/**
	 * 获得设备唯一标识
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		try {
			initManager(context);
			if (telMgr != null) {//IMEI
				Log.i(LOGTAG, "telMgr is not null");
				String imei = telMgr.getDeviceId();
                Log.i(LOGTAG, "imei is " + imei);
				if (!isInvalid(imei)) {
					return imei;
				}
			}
			Log.i(LOGTAG, "telMgr is null");
			if (wifiMgr != null) {//MAC Addr
				Log.i(LOGTAG, "wifiMgr is not null");
				WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
				Log.i(LOGTAG, "wifiInfo is " + wifiInfo);
				if (wifiInfo != null) {
					String macAddr = wifiInfo.getMacAddress();
					Log.i(LOGTAG, "macAddr is " + macAddr);
					if (!isInvalid(macAddr)) {
						return "MAC:" + macAddr;
					}
				}
			}
			Log.i(LOGTAG, "wifiMgr is null");
			{//SIM SN
				String simSN = telMgr.getSimSerialNumber();
				Log.i(LOGTAG, "simSN is " + simSN);
				if (!isInvalid(simSN)) {
					return "SIMSN:" + simSN;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//UUID
		String uuid = getUUID(context);
		Log.i(LOGTAG, "uuid is " + uuid);
		if (!isInvalid(uuid)) {
			return "UUID:" + uuid;
		}
		return null;
	}
	private static String getUUID(Context context) {
		String uuid = null;
		SYSharedPreferences sysp = SYSharedPreferences.getInstance();
		if (sysp != null) {
			uuid = sysp.getString(SYSharedPreferences.KEY_UUID, null);
			if (isInvalid(uuid)) {
				uuid = UUID.randomUUID().toString();
				sysp.putString(SYSharedPreferences.KEY_UUID, uuid);
			}
		}
		return uuid;
	}
	private static boolean isInvalid(String str) {
		return str == null || str.trim().equals("") || str.trim().length()<5;
	}
    
	/**
	 * 获得设备唯一标识集合
	 * @param context
	 * @return
	 */
	public static JSONObject getDeviceIds(Context context) {
		JSONObject json = new JSONObject();
		try {
			initManager(context);
			if (telMgr != null) {
		        json.put("sn", telMgr.getSimSerialNumber());
		        json.put("imei", telMgr.getDeviceId());
		        json.put("subId", telMgr.getSubscriberId());
		        json.put("operName", telMgr.getNetworkOperatorName());
		        json.put("operId", telMgr.getNetworkOperator());
			}
			if (wifiMgr != null) {
				WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
				if (wifiInfo != null) {
					String macAddr = wifiInfo.getMacAddress();
					if (!isInvalid(macAddr)) {
						json.put("mac", macAddr);
					}
				}
			}
			json.put("model", android.os.Build.MODEL);
			json.put("version", android.os.Build.VERSION.RELEASE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp,字体的转换
     */ 
    public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    } 
	
}
