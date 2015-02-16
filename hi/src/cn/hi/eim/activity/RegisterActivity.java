package cn.hi.eim.activity;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.packet.VCard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.hi.eim.R;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.listener.ServiceConnectionListener;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.LoginConfig;
import cn.hi.eim.model.User;
import cn.hi.eim.util.FileUtil;

/**
 * 
 * 注册
 * 
 * @author shimiso
 */
public class RegisterActivity extends ActivitySupport {
	private EditText edt_username, edt_pwd;
	private Button btn_register;
	private LoginConfig loginConfig;
	private TextView gender_boy, gender_girl;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 检测网络和版本
		validateInternet();
		// 初始化xmpp配置
		XmppConnectionManager.getInstance().init();

	}

	/**
	 * 
	 * 初始化.
	 */
	protected void init() {
		loginConfig = getLoginConfig();
		edt_username = (EditText) findViewById(R.id.ui_username_input);
		edt_pwd = (EditText) findViewById(R.id.ui_password_input);
		btn_register = (Button) findViewById(R.id.ui_register_btn);
		gender_boy = (TextView) findViewById(R.id.gender_boy);
		gender_girl = (TextView) findViewById(R.id.gender_girl);

		gender_boy.setOnClickListener(this);
		gender_girl.setOnClickListener(this);
		btn_register.setOnClickListener(this);

		gender_boy.setSelected(true);
		
		pd = getProgressDialog();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.gender_boy:
			gender_boy.setSelected(true);
			gender_girl.setSelected(false);
			break;
		case R.id.gender_girl:
			gender_girl.setSelected(true);
			gender_boy.setSelected(false);
			break;
		case R.id.ui_register_btn:
			new RegisterTask().execute(edt_username.getText().toString(),edt_pwd.getText().toString(),gender_boy.isSelected()?"1":"0");
			break;
		default:
			break;
		}
	}

	class RegisterTask extends AsyncTask<String, Void, Integer>{

		@Override
		protected void onPreExecute() {
			pd.setTitle("请稍等");
			pd.setMessage("正在注册...");
			pd.show();
			super.onPreExecute();
		}
		
		@Override
		protected Integer doInBackground(String... params) {
			return registered(params[0],params[1],"1".equals(params[2]));
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			pd.dismiss();
			switch (result) {
			case Constant.REGISTER_EXIST_USER:
				Toast.makeText(getApplicationContext(), "这个账号已经存在",
						Toast.LENGTH_SHORT).show();
				break;
			case Constant.REGISTER_FAIL:
				Toast.makeText(getApplicationContext(), "注册失败",
						Toast.LENGTH_SHORT).show();
				break;
			case Constant.REGISTER_SECCESS:
				Toast.makeText(getApplicationContext(), "注册成功",
						Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(RegisterActivity.this,CardsActivity.class);
				intent.putExtra("USERID", edt_username.getText().toString());
				startActivity(intent);
				finish();
				break;
			case Constant.REGISTER_SERVER_NOT_RESPONSE:
				Toast.makeText(getApplicationContext(), "服务器没有返回结果",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
			super.onPostExecute(result);
			
		}
		
	}
	
	
	/**
	 * 
	 * @param accounts		用户名
	 * @param password		密码
	 * @param sex			性别   1为男，0为女
	 * @return
	 */
	private Integer registered(String accounts,String password,boolean sex) {
		int serverResult = 0;
		XMPPConnection connection = null;
		try {
			connection = XmppConnectionManager.getInstance().getConnection();
			connection.connect();

			String gender, avatar;
			if (sex) {
				gender = 1 + "";
				avatar = "ico/male/M-"
						+ FileUtil.genatorFileName(this, "ico/male") + ".jpg";
			} else {
				gender = 0 + "";
				avatar = "ico/female/F-"
						+ FileUtil.genatorFileName(this, "ico/female") + ".jpg";
			}

			Registration reg = new Registration();
			reg.setType(IQ.Type.SET);
			reg.setTo(connection.getServiceName());
			reg.setUsername(accounts);
			reg.setPassword(password);
			reg.addAttribute("gender", gender);
			reg.addAttribute("avatar", avatar);

			reg.addAttribute("android", "geolo_createUser_android");
			PacketFilter filter = new AndFilter(new PacketIDFilter(
					reg.getPacketID()), new PacketTypeFilter(IQ.class));
			PacketCollector collector = connection
					.createPacketCollector(filter);
			connection.sendPacket(reg);
			connection.addConnectionListener(ServiceConnectionListener.getInstance(context));//添加监听器
			IQ result = (IQ) collector.nextResult(SmackConfiguration
					.getPacketReplyTimeout());
			// Stop queuing results
			collector.cancel();// 停止请求results（是否成功的结果）
			if (result == null) {
				serverResult = Constant.REGISTER_SERVER_NOT_RESPONSE;
			} else if (result.getType() == IQ.Type.ERROR) {
				if (result.getError().toString()
						.equalsIgnoreCase("conflict(409)")) {
					serverResult = Constant.REGISTER_EXIST_USER;
				} else {
					serverResult = Constant.REGISTER_FAIL;
				}
			} else if (result.getType() == IQ.Type.RESULT) {

				connection.login(accounts, password,"android");
				Presence presence = new Presence(Presence.Type.available);
				connection.sendPacket(presence);
				// DialogFactory.ToastDialog(this, "QQ注册", "亲，恭喜你，注册成功了！");

				/*********** save vcard ***************/
				VCard vCard = new VCard();
				vCard.load(connection);
				vCard.setField("gender", gender);
				vCard.setField("avatar", avatar);

				vCard.save(connection);

				User user = new User();
				user.setJID(accounts);
				user.setvCard(vCard);
				user.setGender(Integer.parseInt(gender));

				loginConfig.setUsername(accounts);
				loginConfig.setPassword(password);
				saveLoginConfig(loginConfig);// 保存用户配置信息
				startService(); // 开启各项服务
				eimApplication.setUser(user);
				MessageManager.getInstance(context).saveUser(user);//拷久化到user化方便头像的获取

			}
		} catch (Exception e) {
			serverResult = Constant.REGISTER_FAIL;
			e.printStackTrace();
		}
		return serverResult;

	}
}
