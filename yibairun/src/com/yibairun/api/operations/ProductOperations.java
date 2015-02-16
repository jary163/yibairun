package com.yibairun.api.operations;

import com.android.volley.Response;
import com.yibairun.bean.InvitationCode;
import com.yibairun.bean.OrderlInfo;
import com.yibairun.bean.ProductDetailInfo;
import com.yibairun.bean.ProductInvestList;
import com.yibairun.bean.ProductList;
import com.yibairun.bean.StatusMessage;
import com.yibairun.bean.UserInfo;
import com.yibairun.bean.VersionInfo;

public interface ProductOperations {
    public abstract void setToken(String token);
    public abstract void setDeviceId(String deviceId);
	/**
	 * 产品列表
	 * @param listener
	 * @param errorListener
	 * @param page
	 */
     void getProductList(Response.Listener<ProductList> listener, Response.ErrorListener errorListener,int page);
     /**
      * 產品詳情
      * @param listener
      * @param errorListener
      * @param id
      */
     void getProductDetail(Response.Listener<ProductDetailInfo> listener, Response.ErrorListener errorListener,int id);

    /**
     * 获取订单号
     * @param money
     */
    void getOrderId(Response.Listener<OrderlInfo> listener, Response.ErrorListener errorListener,String appkey,double money);

    /**
     * 投资中的产品
     * @param listener
     * @param errorListener
     * @param appkey
     * @param page
     */
     void getInvestingList(Response.Listener<ProductInvestList> listener, Response.ErrorListener errorListener,String appkey, int page);

    /**
     * 获取已经结束的投资产品
     * @param listener
     * @param errorListener
     * @param appkey
     * @param page
     */
     void getOverInvestMentList(Response.Listener<ProductInvestList> listener, Response.ErrorListener errorListener,String appkey, int page);

    /**
     * 购买
     * @param listener
     * @param errorListener
     * @param appkey
     * @param money
     * @param pid
     * @param paypassword
     */
    void buyPorduct(Response.Listener<StatusMessage> listener, Response.ErrorListener errorListener,String appkey, double money,int pid,String paypassword);

    /**
     * 提现
     * @param listener
     * @param errorListener
     * @param appkey
     * @param money
     * @param bank_id
     * @param paypassword
     */
    void getCash(Response.Listener<StatusMessage> listener, Response.ErrorListener errorListener,String appkey, double money,int bank_id,String paypassword);
    
    
    /**
     * 版本检测更新
     * @param listener
     * @param errorListener
     */
    void getVersion(Response.Listener<VersionInfo> listener,Response.ErrorListener errorListener);
    
    
    /**
     * @功能描述 获取邀请码
     * @param listener
     * @param errorListener
     * @param appkey
     */
    void GetInviteCode(Response.Listener<InvitationCode> listener,Response.ErrorListener errorListener,String appkey);
}
