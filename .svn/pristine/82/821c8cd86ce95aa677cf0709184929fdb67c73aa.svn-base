package cn.hi.eim;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hi.eim.activity.ActivitySupport;
import cn.hi.eim.activity.SettingAcitivty;
import cn.hi.eim.activity.im.ChatActivity;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.ContacterManager;
import cn.hi.eim.manager.MessageManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.User;
import cn.hi.eim.util.FileUtil;
import cn.hi.eim.util.StringUtil;
import cn.hi.eim.util.SystemUtils;

public class ToChatActivity extends ActivitySupport {

	private User user;
	private ImageView rightBtn,icoImage;
	private RelativeLayout cardLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_chat);
//		user = (User) getIntent().getSerializableExtra(User.userKey);
		user = (User)getIntent().getExtras().get(User.userKey);
		TextView userName = (TextView) this.findViewById(R.id.userName);
		icoImage = (ImageView) findViewById(R.id.icoLayout);
		icoImage.setImageBitmap(FileUtil.getImageFromAssetsFile(context, user.getAvatar()));
		cardLayout = (RelativeLayout) this.findViewById(R.id.cardLayout);
		cardLayout.setBackgroundResource(user.getGender()==0?R.drawable.queen_front:R.drawable.king_front);
		rightBtn = (ImageView) this.findViewById(R.id.rightBtn);
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setImageResource(R.drawable.setting);
		
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, SettingAcitivty.class);
				context.startActivity(intent);
			}
		});
		
		
		this.findViewById(R.id.startHi).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								createSubscriber(user.getJID(), "", null);
							} catch (XMPPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
					
				MessageManager.getInstance(context).saveUser(user);//拷久化到user化方便头像的获取
				Intent intent = new Intent(context, ChatActivity.class);
				/**测试数据*/
				//intent.putExtra("to", "q");
				/**测试数据*/
				
				/**正式数据*/
				intent.putExtra("to",user.getJID());
				/**正式数据*/
				
				
				startActivity(intent);
			}
		});
		userName.setText(user.getJID());
	}
	
	//TODO   需要看看
	protected void createSubscriber(String userJid, String nickname,
			String[] groups) throws XMPPException {
		
		XMPPConnection connection;
		try {
			connection = XmppConnectionManager.getInstance().getConnection();
			//String str = connection.getUser().substring(connection.getUser().indexOf("@"));
			connection.getRoster().createEntry(SystemUtils.getUserNameToService(context, userJid), nickname, groups);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
