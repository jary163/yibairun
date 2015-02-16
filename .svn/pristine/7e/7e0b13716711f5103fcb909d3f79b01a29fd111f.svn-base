package com.yibairun.ui.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yibairun.R;
import com.yibairun.bean.ProductDetail;
import com.yibairun.utils.UiUtil;

public class CameraDialog extends Dialog implements OnClickListener {

	private Context context;
	private CameraListener mCameraListener;

	public CameraDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CameraDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public CameraDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
	}

	
	public void setmCameraListener(CameraListener mCameraListener) {
		this.mCameraListener = mCameraListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_camera);
		initListener();
	}

	private void initListener() {
		findViewById(R.id.tv_phone_pic).setOnClickListener(this);
		findViewById(R.id.tv_dcim).setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_phone_pic:
			if (mCameraListener != null) {
				mCameraListener.onPhonePicClicked();
			}
			break;
		case R.id.tv_dcim:
			if (mCameraListener != null) {
				mCameraListener.onDcimClicked();
			}
			break;
		default:
			break;
		}
	}

	public interface CameraListener {
		/**
		 * 手机拍照
		 */
		public void onPhonePicClicked();

		/**
		 * 手机相册
		 */
		public void onDcimClicked();
	}
}
