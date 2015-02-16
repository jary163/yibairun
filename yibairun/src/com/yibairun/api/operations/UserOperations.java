package com.yibairun.api.operations;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.yibairun.bean.Bank;
import com.yibairun.bean.BankList;
import com.yibairun.bean.MessageList;
import com.yibairun.bean.StatusMessage;
import com.yibairun.bean.UserInfo;

import java.io.File;

public interface UserOperations {

    // NWVmNEljbDZKZ05GNXFpeFkwN3E5Y2pidDRjZlYyOElXRThPWVVJbWRXcUJjTmZFclk3MHhvQXI5Z0xHcUoxNmU4Z1BnOFNab0FV
    // NzFiOWFwTEh1dnZjNmlraHZwdk55Ry9pbnRYb0w4MENkYjlEZlRnQ2Q2ZHgwVFVTUGdrUC8wbUJETkFxLzFRVC9mc0NRcmt6M1Jn
    public abstract void setToken(String token);
    public abstract void setDeviceId(String deviceId);
    /**
     * 获取个人信息
     *
     * @param listener
     * @param errorListener
     * @param appkey
     */
    void getUserInfo(Response.Listener<UserInfo> listener, Response.ErrorListener errorListener, String appkey);

    /**
     * 用户登录
     *
     * @param listener
     * @param errorListener
     * @param mobile
     * @param password
     */
    void login(Listener<UserInfo> listener, ErrorListener errorListener, String mobile, String password,String udid);

    /**
	 * 用户注册
	 *
	 * @param listener
	 * @param errorListener
	 * @param mobile
	 * @param password
	 * @param verify
	 * @deprecated Use {@link #reg(Listener<UserInfo>,ErrorListener,String,String,String,String)} instead
	 */
	void reg(Listener<UserInfo> listener, ErrorListener errorListener, String mobile, String password, String verify);
	/**
     * 用户注册
     *
     * @param listener
     * @param errorListener
     * @param mobile
     * @param password
     * @param paypassword TODO
     * @param verify
     */
    void reg(Listener<UserInfo> listener, ErrorListener errorListener, String mobile, String password, String paypassword, String verify);

    /**
     * 设置交易密码
     *
     * @param listener
     * @param errorListener
     * @param appkey
     * @param password
     */
    void setPayPw(Listener<StatusMessage> listener, ErrorListener errorListener, String appkey, String password);

    /**
     * 修改交易密码
     *
     * @param listener
     * @param errorListener
     * @param appkey
     * @param old_paypassword
     * @param new_paypassword
     */
    void changePayPw(Listener<StatusMessage> listener, ErrorListener errorListener, String appkey, String old_paypassword, String new_paypassword);

    /**
     * 找回交易密码
     *
     * @param listener
     * @param errorListener
     * @param appkey
     * @param paypassword
     * @param verify
     */
    void getPayPw(Listener<StatusMessage> listener, ErrorListener errorListener, String appkey, String paypassword, String verify);

    /**
     * 获取所有银行卡列表
     * @param listener
     * @param errorListener
     * @param appkey
     */
    public void getAllBankList(Listener<BankList> listener, ErrorListener errorListener, String appkey);

    /**
     * 头像上传
     * @param listener
     * @param errorListener
     * @param appkey
     * @param file
     */
    public void uploadAvatvar(Listener<String> listener, ErrorListener errorListener, String appkey,File file);

    /**
     * 身份证上传
     * @param listener
     * @param errorListener
     * @param appkey
     * @param file
     */
    public void uploadIdCard(Listener<String> listener, ErrorListener errorListener, String appkey,File file);

    /**
     * 实名认证
     * @param listener
     * @param errorListener
     * @param appkey
     * @param realname
     * @param card
     * @param file
     */
    public void sMCertification(Listener<String> listener, ErrorListener errorListener, String appkey,String realname,String card, File file);
    /**
     * 获取个人消息列表
     * @param listener
     * @param errorListener
     * @param appkey
     * @param page
     */
     void getPensonalMessageList(Listener<MessageList> listener, ErrorListener errorListener, String appkey,int page);

    /**
     * 系统消息
     * @param listener
     * @param errorListener
     * @param page
     */
     void getAppMessageList(Listener<MessageList> listener, ErrorListener errorListener,int page);
}
