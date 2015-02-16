package cn.hi.eim.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hi.eim.R;
import cn.hi.eim.model.User;
import cn.hi.eim.util.FileUtil;
import cn.hi.eim.util.Utils;

public class CardView extends RelativeLayout {

	private Context context;
	private View rootView;
	private TextView userName;
	private ImageView icoImage;
	private RelativeLayout rootLayout;
	private User user;
	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}
	public CardView(Context context) {
		super(context);
		this.context = context;
		initView();
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(MeasureSpec.makeMeasureSpec(Utils.cardWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(Utils.cardHeight, MeasureSpec.EXACTLY));
	}
	
	private void initView() {
		rootView = View.inflate(context, R.layout.activity_front_cards, this);
		userName = (TextView) rootView.findViewById(R.id.userName);
		icoImage = (ImageView) rootView.findViewById(R.id.icoLayout);
		rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);
	}
	
	public void setBackgroundResource(int resId){
		initUserState(View.INVISIBLE);
		rootLayout.setBackgroundResource(resId);
	}
	
	public void setBackground(Bitmap bitmap){
		this.setBackground(new BitmapDrawable(bitmap));
	}
	
	public void setBackground(Drawable drawable){
		initUserState(View.INVISIBLE);
		rootLayout.setBackground(drawable);
	}
	
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public User getUser() {
		return user;
	}
	/**
	 * 将user相关的信息设置在卡片上，需要先设置卡片background,否则当前卡片的背景状态设置无效
	 */
	public void initUser(){
		initUserState(View.VISIBLE);
		userName.setText(user.getJID());
		icoImage.setImageBitmap(FileUtil.getImageFromAssetsFile(context, user.getAvatar()));
	}
	
	public void initUserState(int state){
		userName.setVisibility(state);
		icoImage.setVisibility(state);
	}

 
}
