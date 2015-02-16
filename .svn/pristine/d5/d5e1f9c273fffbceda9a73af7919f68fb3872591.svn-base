package com.yibairun.ui.components;

import com.yibairun.R;
import com.yibairun.utils.SystemUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoComposingTextView extends TextView {
	private final String namespace = "http://blog.csdn.net/Justin_Dong1122";
	private String text;
	private float textSize;
	private Paint paint1 = new Paint();
	private float paddingLeft;
	private float paddingRight;
	private float textShowWidth;
	private int textColor;
	private float lineSpacing;

	public AutoComposingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//text = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
		textSize = attrs.getAttributeIntValue(namespace, "textSize", 18);
		textSize = SystemUtils.dip2px(context, textSize);
		textColor = attrs.getAttributeIntValue(namespace, "textColor",context.getResources().getColor(R.color.gray5));
		lineSpacing = attrs.getAttributeFloatValue(namespace, "lineSpacing",0.8f);
		lineSpacing = SystemUtils.dip2px(context,lineSpacing);
		paint1.setTextSize(textSize);
		paint1.setColor(textColor);
		paint1.setAntiAlias(true);
		textShowWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - SystemUtils.dip2px(context, 65);
	}
	

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		this.text = text.toString();
		//invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int lineCount = 0;
		if(text != null)
		{
			char[] textCharArray = text.toCharArray();
			// 已绘的宽度
			float drawedWidth = 0;
			float charWidth;
			for (int i = 0; i < textCharArray.length; i++) {
				charWidth = paint1.measureText(textCharArray, i, 1);
				if(textCharArray[i] == '\n' || textCharArray[i] == '\r')
				{
					lineCount++;
					drawedWidth = 0;
				}
				if (textShowWidth - drawedWidth < charWidth) {
					lineCount++;
					drawedWidth = 0;
				}
				canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,(lineCount + 1) * textSize+(lineCount+1)*lineSpacing, paint1);
				drawedWidth += charWidth;
			}
			setHeight((lineCount + 1) * ((int) textSize+(int)lineSpacing) + 5);
			setWidth((int) textShowWidth);
		}		
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
