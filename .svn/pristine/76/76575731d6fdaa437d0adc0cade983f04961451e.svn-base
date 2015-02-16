package com.yibairun.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yibairun.R;

public class UiUtil {

	public static Toast getDefaultToast(Context context, String msg) {
		View layout = View.inflate(context, R.layout.toast_personal, null);
		TextView title = (TextView) layout.findViewById(R.id.tv_toast_info);
		title.setText(msg);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		return toast;
	}
	
	/**
	 * 文字装换成图片，并进行填充
	 */
	public static  void rateToString(ViewGroup parent ,Context context,String rate) {
		parent.removeAllViews();
		// rate.split(regularExpression)
		char[] array = rate.toCharArray();
		for (char c : array) {
			ImageView imageView = ProductUtils.getImageView(context, c);
			if (imageView != null) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, 0, SystemUtils.dip2px(context, 2), 0);
				imageView.setLayoutParams(lp);
				parent.addView(imageView);
			}
		}
		// 追加最后一个%
		addPerCentView(parent,context);

	}

	public static  void addPerCentView(ViewGroup parent ,Context context) {
		TextView percentView = new TextView(context);
		percentView.setText("%");
		percentView.setTextAppearance(context, R.style.PerentStyle);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		lp.setMargins(0, 0, 0, SystemUtils.dip2px(context, 12));
		percentView.setLayoutParams(lp);
		parent.addView(percentView);
	}
	
}
