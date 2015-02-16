package com.yibairun.ui.components;

import com.yibairun.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class NumberView extends ImageView{

	private int resource;
	private LayoutInflater inflater;
	private Context context;
	private View view;
	public NumberView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NumberView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure((widthMeasureSpec*1)/4, (heightMeasureSpec*5)/4);
	}

}
