package com.yibairun.utils;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.yibairun.comm.Constant;

public class CameraHelper {

	private static final CameraHelper SINGER_INSTANCE = new CameraHelper();
	
	public static CameraHelper getInstance(){
		return SINGER_INSTANCE;
	}
	
	 /**
     * 从系统相册获取照片
     */
	public void getImageFromAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        activity.startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
    }
    
	/**
	 *  从照相机获取
	 * @param activity
	 * @param savePath  拍照保存地址
	 */
	public void getImageFromCamera(Activity activity, String savePath) {
		String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(savePath)));
            intent1.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            activity.startActivityForResult(intent1, Constant.REQUEST_CODE_CAPTURE_CAMEIA);
        }
        else {
            Toast.makeText(activity.getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }
}
