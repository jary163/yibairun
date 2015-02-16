
package com.yibairun.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class BitmapUtils {

	/**
	 * 质量压缩方法(将图片缩小到size以下)
	 * @param image
	 * @return
	 */
	public static byte[] compressImage(Bitmap image,int size) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( (baos.toByteArray().length/1024f)>size) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			Log.e("main", "options:"+options+"  length:"+baos.toByteArray().length/1024f);
			options -= 6;//每次都减少6
			baos.reset();//重置baos即清空baos
			if(options<=0)
				break;
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
		}
		return baos.toByteArray();
		
/*		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yibairun/head/113.jpg"));
			int len = 0;  
			byte[] buf = new byte[1024];  
			while ((len = isBm.read(buf, 0, 1024)) != -1) {  
				fos.write(buf, 0, len);  
			}  
			fos.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/yibairun/head/113.jpg")));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
		//Log.e("main", "length:last:bitmap:"+bitmap.getByteCount());
	}
	
	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩）
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getimage(String srcPath,float height,float width) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = height;//这里设置高度为height
		float ww = width;//这里设置宽度为width
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;//压缩好比例大小后再进行质量压缩
	}
	
    
    public static byte[] compressBitmap(Bitmap bitmap,float size){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        int quality=100;
          while (baos.toByteArray().length / 1024f>size) {
                   quality=quality-4;// 每次都减少4
                   baos.reset();// 重置baos即清空baos
                  if(quality<=0){
                                break;
                 }
               bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
     }
     return baos.toByteArray();
}
    
    public static Bitmap getimg(String str){
    	byte[] bytes;
    	bytes=Base64.decode(str, 0);
    	return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }  
    
    /**
     * 图片压缩   并保存到源文件目录
     * @param filePath  需要压缩的图片目录
     * @param height    压缩高度
     * @param width     压缩宽度
     * @param size      压缩之后的尺寸大小
     * @return 压缩之后的图片
     */
    public static Bitmap compressFile(String filePath,float height,float width,int size){
    	File file = new File(filePath);
    	Bitmap bitmap =null;
    	if(file.exists()){
    		/** 
        	 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 
        	 */  
        	int degree = readPictureDegree(file.getAbsolutePath());  
    		bitmap = getimage(filePath,height,width);//尺寸压缩
    		/** 
        	 * 把图片旋转为正的方向 
        	 */  
        	bitmap = rotaingImageView(degree, bitmap);  
    		//autoRectifyBitmap(bitmap, file);//处理图片旋转
    		byte[] compressImage = compressImage(bitmap,size);//质量压缩
    		saveBitmapBytesToFile(compressImage, file);//文件替换保存
    		//Log.e("main", "压缩之后的大小为:"+bitmap.getByteCount());
    		return bitmap;
    	}
    		return bitmap;
    }
    
    /**
     * 
     * @param srcPath 图片源文件地址
     * @param desPath 图片目的文件地址
     * @param height  图片高度
     * @param width   图片宽度
     * @param size    图片尺寸
     * @return
     */
    public static Bitmap compressFile(String srcPath,String desPath,float height,float width,int size){
    	File file = new File(srcPath);
    	Bitmap bitmap =null;
    	if(file.exists()){
    		/** 
        	 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 
        	 */  
        	int degree = readPictureDegree(file.getAbsolutePath());  
    		bitmap = getimage(srcPath,height,width);//尺寸压缩
    		/** 
        	 * 把图片旋转为正的方向 
        	 */  
        	bitmap = rotaingImageView(degree, bitmap);  
    		//autoRectifyBitmap(bitmap, file);//处理图片旋转
    		byte[] compressImage = compressImage(bitmap,size);//质量压缩
    		saveBitmapBytesToFile(compressImage, new File(desPath));//文件替换保存
    		//Log.e("main", "压缩之后的大小为:"+bitmap.getByteCount());
    		return bitmap;
    	}
    		return bitmap;
    }
    
    
    public static boolean compressBmpToFile(Bitmap bmp,File file){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 60;//个人喜欢从80开始,
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100&&options>0) { 
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.PNG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("main", "压缩失败");
			return false;
		}
		Log.e("main", "压缩成功:"+bmp.getByteCount());
		return true;
	}
    
    public static boolean saveBitmapBytesToFile(byte[] bytes,File file){
    	ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);//把压缩后的数据baos存放到ByteArrayInputStream中
    	FileOutputStream fos = null;
    	try {
			fos = new FileOutputStream(file);
			int len = 0;  
			byte[] buf = new byte[1024];  
			while ((len = isBm.read(buf, 0, 1024)) != -1) {  
				fos.write(buf, 0, len);  
			}  
			fos.close();  
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public static boolean saveBitmapToFile(Bitmap bitmap,File file){
    	Log.e("main", "filesavepath:"+file.getAbsolutePath()+"   filesize:"+bitmap.getByteCount());
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){//第一次保存为了旋转而获取文件
                out.flush();
                out.close();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    	
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){   
        // 获得图片的宽高   
        int width = bm.getWidth();   
        int height = bm.getHeight();   
        // 计算缩放比例   
        float scaleWidth = ((float) newWidth) / width;   
        float scaleHeight = ((float) newHeight) / height;   
        // 取得想要缩放的matrix参数   
        Matrix matrix = new Matrix();   
        matrix.postScale(scaleWidth, scaleHeight);   
        // 得到新的图片   
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);   
        return newbm;   
    } 
    
    
    /**
     * 自动校正bitmap旋转状态
     */
    public static void autoRectifyBitmap(Bitmap bitmap,File file){
    	/** 
    	 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 
    	 */  
    	int degree = readPictureDegree(file.getAbsolutePath());  
    	/** 
    	 * 把图片旋转为正的方向 
    	 */  
    	bitmap = rotaingImageView(degree, bitmap);  
    }
    
	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
    
    /**
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
		Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
        		bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
}

