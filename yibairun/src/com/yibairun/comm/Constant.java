package com.yibairun.comm;

public class Constant {

    public enum IGeituiMsg{
        PRODUCTS,ACCOUNT,INFORMATION
    }
	public static final Integer APPLY_LIMIT_BUYTS = 100;//最低限购金额

	
	public static final Integer PERSON_INVESTING = 0;
	public static final Integer PERSON_INVEST_STOP = 1;
	/**
	 * 推送时间选择
	 */
	public static final int PUSH_TIME_MORNING = 0;//上午10点
	public static final int PUSH_TIME_AFTERNOON = 1;//上午10点
	public static final int PUSH_TIME_EVENING = 2;//上午10点
	
	public static final float PIC_HEAD_WIDTH = 300f;//头像保存的宽度 px
	public static final float PIC_HEAD_HEIGHT = 300f;//头像保存的宽度 px
	public static final int PIC_HEAD_SIZE = 30;//头像保存的尺寸大小  kb
	
	public static final float PIC_IDCARD_WIDTH = 480f;//头像保存的宽度 px
	public static final float PIC_IDCARD_HEIGHT = 800f;//头像保存的宽度 px
	public static final int PIC_IDCARD_SIZE = 100;//头像保存的尺寸大小  kb
	
	/**
	 * 验证码
	 */
	public static final int AUTHCODE_ACTIVITY_REGISTER = 1;// 注册
	public static final int AUTHCODE_ACTIVITY_MODIFYPASSWORD = 2;// 修改交易密码
	public static final int AUTHCODE_ACTIVITY_ADDBANK = 3;//添加银行卡
	public static final int AUTHCODE_ACTIVITY_SET_TRADPASSWORD = 4;//设置交易密码
	
	/**
	 * 各个Activity
	 */
	public static final String ACTIVITY_TYPE = "type";
	public static final int ACTIVITY_PRODUCT_DETAIL_GRAPH = 0x1001;//产品详情图表
	public static final int ACTIVITY_PRODUCT_DETAIL = 0x1002;//产品详情
	public static final int ACTIVITY_RECHARGE = 0x1003;//充值界面
	public static final int FRAGMENT_PERSONAL = 0x1004;//个人信息界面
	public static final int FRAGMENT_MESSAGE = 0x1005;//公共消息界面
	public static final int FRAGMENT_RECOMMEND = 0x1006;//推荐界面
	public static final int ACTIVITY_SETTING_ABOUT = 0x1007;//设置关于界面
	public static final int ACTIVITY_HELP = 0x1008;//帮助界面
	public static final int ACTIVITY_ACCOUNT_MANAGER = 0x1009;//账户管理界面
	
	/**
	 * WEBVIEW 请求连接
	 */
	public static final int WEBVIEW_PROTOCOL = 0x10001;//协议URL跳转
	public static final int WEBVIEW_DISCLAIMER = 0x10002;//免责URL跳转

	
	/**
	 * 请求码
	 */
	public static final int REQUEST_CODE_WITHDRAWACTIVITY = 0x101;
	public static final int RESULT_CODE_WITHDRAWACTIVITY = 0x1001;
	public static final int REQUEST_CODE_PICK_IMAGE = 0x102;
	public static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x103;
	public static final int REQUEST_CODE_SELECTBANK = 0x104;
	public static final int RESULT_CODE_SELECTBANK = 0x1004;

	
	
	/**
	 * sharepreference数据保存
	 */
	public static final String SYSTEM_SHARE = "system_share";
	public static final String PUSH_SETTING = "push_setting";
	public static final String PUSH_SETTING_TIME = "push_setting_time";
	
	/**
	 * intent参数传递
	 */
	public static final String PRODUCT_ID = "product_id";//产品id
	public static final String PRODUCT= "product";//产品内容
	public static final String PRODUCT_DETAIL = "product_detail";//产品内容
	public static final String WITHDRAW_DETAIL = "withdraw_detail";//提现详情
	public static final String VERIFY = "verify";//验证码
	public static final String MESSAGE_DETAIL = "message_detail";//消息详情
	public static final String BROADCAST_IGETUI_HOMEPAGE_EXTRA = "broadcast_igetui_homepage_extra";//消息详情

	/**
	 * 照片保存文件名
	 */
	public static final String PIC_HEAD = "head.png";//用户图像文件名
	public static final String PIC_IDCARD = "idcard.png";//用户图像文件名

    /**
     * ACTION
     */
	
	public static final String ACTION_GEITUI_RECEIVER= "action_geitui_receiver";




	public static final String QQ_APP_ID = "801537772";//qq开发者账号

	public static final String QQ_APP_KEY="d887ac5ddedd0036e8b044c209a252ae";//qq开发者secrete
	
	public static final String WEIXIN_APP_ID = "wx945b2be70c7fa74a";//微信开发者账号
	
	public static final String WEIXIN_APP_SECRET="a0837a47c6ebe9b8e85c80d75ac6e6c0";//微信开发者secrete	
	
	//public static String SHARE_APPEND_URL = "http://www.100run.com/user/app.html?key=";//分享url链接
	//public static final String SHARE_APPEND_URL_UNLOGIN = "我正在亿百润投资理财，赚到了,点击http://www.100run.com/user/app.html";//分享url链接(未登录)
	
	
	public static final String SHARE_DEFAULT_URL = "http://www.100run.com";//分享url链接
	
	public static final String AD_WEBVIEW = "http://www.100run.com/game.html";//分享url链接
	
	public static  String SHARE_CIRCLE_CONTENT = "我正在亿百润投资理财，赚到了";//
	
	public static final String SHARE_TITLE = "亿百润";//
	
	public static final String WEBVIEW_HELPER_URL = "http://www.100run.com/help/cjwt.html";//帮助URL连接
	
	public static final String WEBVIEW_PROTOCOL_URL = "http://www.100run.com/help/xieyi.html";//协议URL连接
	
	public static final String WEBVIEW_DISCLAIMER_URL = "http://www.100run.com/help/xieyi.html";//免责URL连接

	
}