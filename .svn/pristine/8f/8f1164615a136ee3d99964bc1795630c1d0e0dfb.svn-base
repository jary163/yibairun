package com.yibairun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yibairun.R;
import com.yibairun.application.AppController;
import com.yibairun.bean.User;
import com.yibairun.comm.Constant;
import com.yibairun.ui.components.CameraDialog;
import com.yibairun.ui.components.CameraDialog.CameraListener;
import com.yibairun.ui.components.PhotoViewDialog;
import com.yibairun.utils.BitmapUtils;
import com.yibairun.utils.CameraHelper;
import com.yibairun.utils.ValidateUtil;
import com.yibairun.utils.VolleyErrorHelper;

import java.io.File;

public class CertificationActivity extends BaseActivity {

	private EditText et_username, et_idcard;
	private ImageView iv_talk_pic, iv_idcard_marked, iv_display_idcard;
	private RelativeLayout rl_border_upload_idcard;
	private TextView tv_enter,tv_upload_idcard;
	private User user;
	private boolean isUsernameLegal;
	private boolean isPasswordLegal;
	/**
	 * 用户名、密码没有改变，只修改身份证信息
	 */
	private boolean isChangePic;
	private CameraDialog calculateDialog;
	private String imgIdcardPath;
	private String camera;
	private Bitmap uploadBitmap;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_certification);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		et_username = findView(R.id.et_username);
		et_idcard = findView(R.id.et_idcard);
		iv_talk_pic = findView(R.id.iv_talk_pic);
		iv_idcard_marked = findView(R.id.iv_idcard_marked);
		rl_border_upload_idcard = findView(R.id.rl_border_upload_idcard);
		iv_display_idcard = findView(R.id.iv_display_idcard);
		tv_enter = findView(R.id.tv_enter);
		tv_upload_idcard = findView(R.id.tv_upload_idcard);
	}

	@Override
	public void initDate() {
		/*bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(getResources().getString(R.string.certification));*/
		user = appController.getUserinfo().getUser();
		calculateDialog = new CameraDialog(context,R.style.CalculateDialog);
		camera = AppController.getInstance().getIdCardSavePath();
		display = getWindowManager().getDefaultDisplay();  
		
		setNameAndIdCardState();
		
		setPicState();
		
	}

	@Override
	public void initListener() {
		iv_talk_pic.setOnClickListener(this);
		tv_enter.setOnClickListener(this);
		iv_display_idcard.setOnClickListener(this);
		tv_upload_idcard.setOnClickListener(this);
		calculateDialog.setmCameraListener(new CameraListener() {
				
				@Override
				public void onPhonePicClicked() {
					calculateDialog.dismiss();
					CameraHelper.getInstance().getImageFromCamera(CertificationActivity.this,camera);
				}
				@Override
				public void onDcimClicked() {
					calculateDialog.dismiss();
					CameraHelper.getInstance().getImageFromAlbum(CertificationActivity.this);
				}
			});
		et_username.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// 判断姓名是否合理
				if(TextUtils.isEmpty(s.toString().trim())){
					isUsernameLegal = false;
				}else{
					isUsernameLegal = true;
				}
				setNextState();
			}
		});
		et_idcard.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(!TextUtils.isEmpty(s.toString().trim())){//判断身份证是否合理
					isPasswordLegal = true;
				}else{
					isPasswordLegal = false;
				}
				setNextState();
			}
		});
	}

	private void setNameAndIdCardState() {
		if (null != user && !TextUtils.isEmpty(user.getCardnum())
				&& !TextUtils.isEmpty(user.getRealname()))// 姓名不能为空
		{
			et_username.setText(user.getRealname());
			et_username.setEnabled(false);
			et_idcard.setText(user.getCardnum());
			et_idcard.setEnabled(false);
		}
	}
	
	private void setPicState() {
		if(!user.isUploadIdCard())
		{	
			rl_border_upload_idcard.setVisibility(View.VISIBLE);
			tv_upload_idcard.setVisibility(View.VISIBLE);
			iv_display_idcard.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置下一步图标是否点亮
	 */
	public void setNextState(){
		if((isUsernameLegal&&isPasswordLegal&&isChangePic)||//两种判断行为：一、三个字段均修改
				(isChangePic&&!et_username.isEnabled())){//二、只修改图片。并且编辑框不可用
			tv_enter.setEnabled(true);
			tv_enter.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			tv_enter.setEnabled(false);
			tv_enter.setTextColor(context.getResources().getColor(R.color.gray7));
		}
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_talk_pic:
			calculateDialog.show();
			break;
		case R.id.tv_enter:
			if((isChangePic&&!et_username.isEnabled())||ValidateUtil.isIdentityCard(et_idcard)){
				uploadFile(AppController.getInstance().getIdCardSavePath());
				hideSystemInput(this);
			}
			break;
		case R.id.iv_display_idcard://图片点击放大
			if(null!=uploadBitmap){
				PhotoViewDialog photoViewDialog = new PhotoViewDialog(context,R.style.PhotoViewDialog_Transparent);
				photoViewDialog.setCancelable(true);
				photoViewDialog.show();
			}
			break;
		case R.id.tv_upload_idcard:
			calculateDialog.show();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uri = null;
		if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE&&null!=data&&null!=data.getData()) {
			uri = data.getData();
			// to do find the path of pic by uri
			findPathFromUrl(uri);
			uploadBitmap = BitmapUtils.compressFile(imgIdcardPath,camera,Constant.PIC_IDCARD_HEIGHT,Constant.PIC_IDCARD_WIDTH,Constant.PIC_IDCARD_SIZE);
			Log.e("main", "PICK_IMAGE_uri:"+uri);
		} else if (requestCode == Constant.REQUEST_CODE_CAPTURE_CAMEIA&&resultCode!=Activity.RESULT_CANCELED) {
			Log.e("main", "date:"+data);
			if(data!=null){
				Log.e("main", "date.getextrss:"+data.getExtras()); 
			}
			uploadBitmap = BitmapUtils.compressFile(camera,Constant.PIC_IDCARD_HEIGHT,Constant.PIC_IDCARD_WIDTH,Constant.PIC_IDCARD_SIZE);
		}
		
		if(null!=uploadBitmap){
			rl_border_upload_idcard.setVisibility(View.VISIBLE);
			tv_upload_idcard.setVisibility(View.GONE);
			iv_display_idcard.setVisibility(View.VISIBLE);
			iv_display_idcard.setImageBitmap(uploadBitmap);
			isChangePic = true;
			setNextState();
			//iv_display_idcard.setBackgroundDrawable(new BitmapDrawable(bitmap));
		}
		//uploadFile(AppController.getInstance().getIdCardSavePath());
	}
	
	
	public void findPathFromUrl(Uri uri){
		if(uri==null){
			return;
		}
		String[] proj = {MediaStore.Images.Media.DATA};
		 
        //好像是android多媒体数据库的封装接口，具体的看Android文档
		Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        //按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        //最后根据索引值获取图片路径
        imgIdcardPath = cursor.getString(column_index);
	}
	
	
	/**
	 * 文件上传
	 * @param str
	 */
	public void uploadFile(final String str){
		pg.setTitle("正在上传身份信息");
		pg.setCancelable(true);
		pg.show();
		File file = new File(str);
		if(et_username.isEnabled()&&et_idcard.isEnabled()){//case 第一次实名认证
			api.userOperations().sMCertification(successLisnter, errorListener, appController.getAppKey(),
						et_username.getText().toString().trim(), et_idcard.getText().toString().trim(),file);
		}else{//只修改了图片信息
			api.userOperations().sMCertification(successLisnter, errorListener, appController.getAppKey(),
					null,null,file);
		}
	}
	private Listener<String> successLisnter = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			pg.dismiss();
			Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
			if(TextUtils.isEmpty(user.getRealname())||TextUtils.isEmpty(user.getCardnum())){//重新设置UserInfo中User的信息
				user.setRealname(et_username.getText().toString().trim());
				user.setCardnum(et_idcard.getText().toString().trim());
			}
			finish();
		}
	};
	private ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			pg.dismiss();
			Log.e("main", "case:"+error.getCause()+"   message:"+error.getMessage());
			Toast.makeText(context, VolleyErrorHelper.getMessage(error, context), Toast.LENGTH_SHORT).show();
			//Toast.makeText(context, "身份证上传失败,请稍后重试", Toast.LENGTH_SHORT).show();
		}
	};
	private Display display;

}
