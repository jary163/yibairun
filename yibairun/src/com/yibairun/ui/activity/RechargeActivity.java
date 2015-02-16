package com.yibairun.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.qmoney.Config;
import com.qmoney.base.MemCardInfo;
import com.qmoney.third.OrderInfo;
import com.qmoney.third.PayRequest;
import com.qmoney.tools.FusionField;
import com.qmoney.tools.RSATool;
import com.qmoney.ui.PayService;
import com.yibairun.R;
import com.yibairun.bean.Bank;
import com.yibairun.bean.OrderlInfo;
import com.yibairun.bean.UserInfo;
import com.yibairun.comm.Constant;
import com.yibairun.utils.BankUtils;
import com.yibairun.utils.ProductUtils;
import com.yibairun.utils.StringUtil;
import com.yibairun.utils.VolleyErrorHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class RechargeActivity extends BaseActivity {



	private TextView tv_accout_balance,tv_bank,tv_bank_type,tv_card_nums;
    private TextView tv_submit;
    private ImageView iv_logo;
	private EditText et_recharge_money;//,et_trade_password;
	private boolean isTradePassword;//交易密码是否合理
	private boolean isTradeMoney;//交易金额是否合理
	private LinearLayout ll_my_cards;//选择银行卡
    private OrderlInfo orderlInfo;
	private UserInfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge);
        super.onCreate(savedInstanceState);

    }



    public void initView() {
       /* bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(context.getResources().getString(R.string.recharge));*/
    	et_recharge_money = findView(R.id.et_recharge_money);
    	tv_bank = findView(R.id.tv_bank);
    	tv_bank_type = findView(R.id.tv_bank_type);
    	tv_card_nums = findView(R.id.tv_card_nums);
    	et_recharge_money = findView(R.id.et_recharge_money);
//    	et_trade_password = findView(R.id.et_trade_password);
    	tv_submit = findView(R.id.tv_submit);
    	ll_my_cards = findView(R.id.ll_my_cards);
    	iv_logo = findView(R.id.iv_logo);
    	tv_accout_balance = findView(R.id.tv_accout_balance);
	}
    /*** 初始化商户私钥  ****/
    @Override
    public void initDate() {
    	userinfo = appController.getUserinfo();
        try {
            RSATool.init(getAssets().open(Config.PRIVATE_KEY_PATH)) ;

        } catch (IOException e) {
            e.printStackTrace() ;
        }

        tv_accout_balance.setText(ProductUtils.getFormatNum(userinfo.getUser().getMoney()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private int checkInfo() {
        int msgId = -1 ;

        if(TextUtils.isEmpty(orderlInfo.getOrder().getSn())){
            msgId = R.string.order_num_null ;
        }

        else if (TextUtils.isEmpty(et_recharge_money.getText().toString())) {
            msgId = R.string.order_amt_null ;
        }
       /* else if (TextUtils.isEmpty(merchantName.getText().toString())) {
            msgId = R.string.order_merchant_null ;
        } else if (TextUtils.isEmpty(productName.getText().toString())) {
            msgId = R.string.order_product_null ;
        } */else if (TextUtils.isEmpty(appController.getUserinfo().getUser().getUsername())) {
            msgId = R.string.order_partner_null ;
        }

        return msgId ;
    }
    /*** 支付 ****/
    private void pay(){
        PayRequest request = getPayRequest() ;

        if(null != request){
            PayService.pay(request) ;           //调用支付接口
        }
    }
    /***************************************************
     * 得到支付响应
     * 2013-12-2
     */
    private PayRequest getPayRequest() {
        PayRequest request = null ;

        int msgId = checkInfo() ;

        if(msgId != -1){
            new AlertDialog.Builder(context)
                    .setTitle(R.string.error_info_title)
                    .setMessage(msgId)
                    .setNegativeButton(R.string.sure, null)
                    .show();
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss") ;
            String date = format.format(new Date()) ;

            OrderInfo orderInfo = new OrderInfo() ;
            orderInfo.setOrderId(orderlInfo.getOrder().getSn()) ;       //设置订单Id
            orderInfo.setAmt(String.valueOf(Math.round(orderlInfo.getOrder().getPrice()))) ;           //设置订单金额String.valueOf(orderlInfo.getOrder().getPrice())
            orderInfo.setMerchantName(orderlInfo.getOrder().getCompanyname()) ; //商户名称
            orderInfo.setProductName(orderlInfo.getOrder().getTitle()) ;   //商品名称
            orderInfo.setUnitPrice("1") ;       //订单单价
            orderInfo.setTotal("1") ;               //订单总价
            orderInfo.setMerchantOrderTime(date) ;                         //订单时间

            String orderSignSrc = PayService.generateOrderSignSrc(orderInfo) ;   //调用插件api生成签名的源串
//        	  System.out.println(" ---- " + orderSignSrc);
            String orderSign = RSATool.sign(orderSignSrc) ;       //商户客户端调用商户服务器对订单加签
            orderInfo.setOrderSign(orderSign) ;                   //设置订单签名

            String partnerUserId = appController.getUserinfo().getUser().getUsername() ;  //用户标识
            // 支付后回调商户的intent包名
            String callbackPackageName = "com.qmoney";

            // 支付后回调商户的intent完整类名
            String callbackClassName = "com.qmoney.MainActivity";

            String querySignSrc = PayService.generateCardQuerySignSrc(
                    Config.MEMBERCODE, Config.MERCHANTID, partnerUserId) ;
            String querySign = RSATool.sign(querySignSrc) ;        //设置卡列表查询签名
            orderInfo.setQuerySign(querySign) ;

            FusionField.orderInfo = orderInfo ;

//            String cardFullPan = bankNum.getText().toString() ;
            //保存对应银行Id的银行名称
            MemCardInfo memCardInfo = new MemCardInfo() ;
//            memCardInfo.setCardName(bankName.getText().toString()) ;
            FusionField.memCardInfo = memCardInfo ;


//        	  request = new PayRequest(MainActivity.this, MainActivity.class, payButton,
//        			  callbackPackageName, callbackClassName,
//        			  Config.MEMBERCODE, Config.MERCHANTID, partnerUserId,
//        			  null, null, null, Config.URL_TEST_SERVER, orderInfo) ;

            request = new PayRequest(this,
                    this.getClass(), tv_submit,
                    callbackPackageName, callbackClassName,
                    Config.MEMBERCODE, Config.MERCHANTID,       //商户自己的会员号，商户号
                    partnerUserId, "", "", null,"" ,
                    Config.URL_TEST_SERVER, orderInfo);
        }

        return request ;
    }




	public void initListener() {
		tv_submit.setOnClickListener(this);
		ll_my_cards.setOnClickListener(this);
		et_recharge_money.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断号码是否合理
				if(!TextUtils.isEmpty(s.toString().trim())){
					isTradeMoney = true;
					String[] split = s.toString().trim().split("\\.");
					if(split.length==2&&split[1].length()>2){
						et_recharge_money.setText(s.toString().trim().substring(0, s.toString().trim().length()-1));
						et_recharge_money.setSelection(et_recharge_money.getText().toString().trim().length());
					}
				}else{
					isTradeMoney = false;
				}
				setNextState();
			}
		});
		
	/*	et_trade_password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				//判断密码是否合理
				//判断号码是否合理
				if(!TextUtils.isEmpty(s.toString().trim())){
					isTradePassword = true;
				}else{
					isTradePassword = false;
				}
				setNextState();
			}
		});*/
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
		/*	UiUtil.getDefaultToast(context, "充值成功").show();
			startActivity(MainActivity.class);
*/
			if(Float.parseFloat(et_recharge_money.getText().toString().trim())<=0){
				Toast.makeText(context, "提现金额不能小于1分，请重新输入", Toast.LENGTH_SHORT).show();
				return;
			}
            pg.setTitle("正在生成订单，请稍后...");
            pg.show();
            getOrderId();
			break;
		case R.id.ll_my_cards:
			Intent intent = new Intent();
			intent.setClass(context, MyBanksActivity.class);
			intent.putExtra(Constant.ACTIVITY_TYPE, Constant.ACTIVITY_RECHARGE);
			startActivityForResult(intent, Constant.REQUEST_CODE_SELECTBANK);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if(requestCode==Constant.REQUEST_CODE_SELECTBANK&&resultCode==Constant.RESULT_CODE_SELECTBANK){
			Bank bank = (Bank) intent.getExtras().getSerializable(Constant.WITHDRAW_DETAIL);
			setBankDate(bank);
		}
	}
	
	/**
	 * 设置银行数据
	 * @param bank
	 */
	private void setBankDate(Bank bank) {
        ll_my_cards.setBackgroundResource(R.drawable.bg_mybank);
		iv_logo.setImageBitmap(BankUtils.getBankIco(context, BankUtils.bankMap.get(bank.getBanktype())));
		tv_bank.setText(bank.getBanktype());
		tv_card_nums.setText(BankUtils.formatBankNum(bank.getBanknum()));
	}



	private void getOrderId() {
		double orderAmount = Double.parseDouble(et_recharge_money.getText()
				.toString());
		api.productOperations().getOrderId(createMyReqSuccessListener(),
				createMyReqErrorListener(), appController.getAppKey(),
				orderAmount);
	}

	private Response.Listener<OrderlInfo> createMyReqSuccessListener() {
		return new Response.Listener<OrderlInfo>() {
			@Override
			public void onResponse(OrderlInfo response) {
				// pending_view.setVisibility(View.GONE);
				pg.dismiss();
				orderlInfo = response;
				pay();
			}
		};
	}

    protected Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                pending_view.setVisibility(View.GONE);
				/* ErrorMessage msg = (ErrorMessage) error; */
                pg.dismiss();
                Toast.makeText(context,VolleyErrorHelper.getMessage(error, context),Toast.LENGTH_SHORT).show();
            }
        };
    }
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if(/*isTradePassword&&*/isTradeMoney){//判断逻辑需要调整
			tv_submit.setEnabled(true);
			tv_submit.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_submit.setEnabled(false);
			tv_submit.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
}
