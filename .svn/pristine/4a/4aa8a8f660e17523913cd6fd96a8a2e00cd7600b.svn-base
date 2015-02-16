package com.yibairun.ui.components;

import uk.co.senab.photoview.PhotoView;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.yibairun.R;
import com.yibairun.application.AppController;

public class PhotoViewDialog extends Dialog{

	private Context context;
	private PhotoView iv_photo;
	private Bitmap displayBitmap;
	protected PhotoViewDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public PhotoViewDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public PhotoViewDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view_dialog);
		iv_photo = (PhotoView) findViewById(R.id.iv_photo);
		iv_photo.setImageBitmap(BitmapFactory.decodeFile(AppController.getInstance().getIdCardSavePath()));
	}

}
