package com.yibairun.ui.components;

import com.yibairun.utils.SystemUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Scroller;
/**
 * 图标
 * @author ycl
 *
 */
public class ChartView extends View {
	public float YPoint = 380; // 原点的Y坐标
	public float XPoint = 85; // 原点的X坐标
	public float XScale = 100; // X的刻度长度
	public float YScale = 80; // Y的刻度长度
	public float XLength = 580; // X轴的长度
	public float YLength = 360; // Y轴的长度
	public String[] XLabel; // X的刻度
	public String[] YLabel; // Y的刻度
	public Float[] DataX; // X轴数据
	public Float[] DataY; // Y轴数据
	public String Title; // 显示的标题
	public String XTitle;//x轴文字
	public String colorTitle = "#ef4754";//文字颜色
	private int Zoom = 100;//放大值
	private float Scale_Factor_X;//x轴的比例因子
	private float Scale_Factor_Y;//x轴的比例因子
	private float screenW, screenH;
	private float lastX;
	private float lastY;
	private Scroller scroller;
	private Paint framPanint;
	private float total_Width = 0;
	private int count = 0;
	
	private Path path;
	public ChartView(Context context, AttributeSet attr) {
		super(context, attr);
		scroller=new Scroller(context);
		screenW = this.getWidth();
		screenH = this.getHeight();
		Log.e("main", "screenW:"+screenW);
		Log.e("main", "screenH:"+screenH);
		path = new Path();
		framPanint = new Paint();
		framPanint.setAntiAlias(true);
		framPanint.setStrokeWidth(2f);
		
		

	}

	public void SetInfo(String[] XLabels, String[] YLabels, Float[] floats, Float[] floats2,
			String strTitle,String xTitle) { 
		XLabel = XLabels;
		YLabel = YLabels;
		DataX = floats;
		DataY = floats2;
		Title = strTitle;
		this.XTitle = xTitle;
		
		Scale_Factor_X = Float.parseFloat(XLabels[1])/XScale;
		Scale_Factor_Y = Float.parseFloat(YLabels[1])/YScale;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(SystemUtils.dip2px(getContext(), 230), MeasureSpec.EXACTLY));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 重写onDraw方法
		if(DataX==null)
			return;
/*		Shader mShader = new LinearGradient(0, 0, 0, YPoint, new int[] {
			Color.argb(100, 0, 255, 255), Color.argb(45, 0, 255, 255),
			Color.argb(10, 0, 255, 255) }, null, Shader.TileMode.CLAMP);*/
		Shader mShader = new LinearGradient(0, 0, 0, YPoint, new int[] {//设置渐变颜色,起始颜色，中间颜色，终点颜色
				Color.argb(100, 255, 51, 51), Color.argb(45, 255, 51, 51),
				Color.argb(10, 255, 255, 255) }, null, Shader.TileMode.CLAMP);
		
		framPanint.setShader(mShader);
		
		
		// canvas.drawColor(Color.WHITE);//设置背景颜色
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);// 去锯齿
		//paint.setColor(Color.argb(255, 1, 255, 255));// 颜色
		paint.setColor(Color.parseColor(colorTitle));
		paint.setTextSize(30); // 设置轴文字大小
		Paint paint1 = new Paint();
		paint1.setStyle(Paint.Style.STROKE);
		paint1.setAntiAlias(true);// 去锯齿
		paint1.setColor(Color.RED);
		
		Paint paintTitle = new Paint();//设置圆圈上的文字
		paintTitle.setStyle(Paint.Style.STROKE);
		paintTitle.setAntiAlias(true);// 去锯齿
		paintTitle.setColor(Color.RED);
		paintTitle.setTextSize(30);
		
		Paint circlePaint = new Paint();//圆圈颜色
		circlePaint.setStyle(Paint.Style.FILL);  
		circlePaint.setAntiAlias(true);// 去锯齿
		circlePaint.setColor(Color.parseColor(colorTitle));
		// 设置Y轴(对于系统来讲屏幕的原点在左上角）
		canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); // 轴线
		for (int i = 0; i * YScale < YLength; i++) {
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
					* YScale, paint); // 刻度 XPoint+5画出了一条短的小横线
			try {
				if(i!=0){
					canvas.drawText(YLabel[i]+"%", XPoint - 50,
							YPoint - i * YScale + 10, paint); // 文字
				}
			} catch (Exception e) {
			}
		}
		canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
				+ 6, paint); // 箭头
		canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
				+ 6, paint);
		
		// 设置X轴
		canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint); // 轴线
		path.lineTo((XPoint+DataX[0]/Scale_Factor_X),  YPoint);//线包围的起始点
		//for (int i = 0; i * XScale < XLength; i++) {
		for (int i = 0; i < XLabel.length; i++) {
			try {
				Log.e("main", "length:"+i*XScale);
				if(i*XScale<XLength){
					
					canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale,YPoint - 5, paint); // 刻度
					canvas.drawText(XLabel[i], XPoint + i * XScale - 10,YPoint + 30, paint); // 文字
				}
				// 数据值
				//if (i > 0 && YCoord(DataX[i - 1]) != -999&& YCoord(DataX[i]) != -999){ // 保证有效数据
				if (i > 0 ){ // 保证有效数据
					canvas.drawLine((XPoint+DataX[i-1]/Scale_Factor_X), (YPoint - DataY[i-1]/Scale_Factor_Y),
							(XPoint+DataX[i]/Scale_Factor_X), (YPoint - DataY[i]/Scale_Factor_Y), paint1);
					//canvas.drawLine(XPoint + (i - 1) * XScale,YCoord(Data[i - 1]), XPoint + i * XScale,YCoord(Data[i]), paint);
					path.lineTo((XPoint+DataX[i-1]/Scale_Factor_X), (YPoint - DataY[i-1]/Scale_Factor_Y));					
				}
				canvas.drawCircle((XPoint+DataX[i]/Scale_Factor_X), (YPoint - DataY[i]/Scale_Factor_Y), 7,circlePaint);//画中心点
				
				canvas.drawText(DataY[i]+"%", (XPoint+DataX[i]/Scale_Factor_X-25),(YPoint - DataY[i]/Scale_Factor_Y-10), paintTitle); //绘制点上的百分数
			} catch (Exception e) {
			}
			count = i;
		}
		
		path.lineTo((XPoint+DataX[DataX.length-1]/Scale_Factor_X), (YPoint - DataY[DataY.length-1]/Scale_Factor_Y));//进行渐变包裹	
		path.lineTo(XPoint+DataX[DataX.length-1]/Scale_Factor_X, YPoint);
		path.lineTo((XPoint+DataX[0]/Scale_Factor_X), YPoint);
		path.close();
		canvas.drawPath(path, framPanint);
		
		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
				YPoint - 3, paint); // 箭头
		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
				YPoint + 3, paint);
	    paint.setTextSize(16);
		//canvas.drawText(Title, 150, 50, paint);//设置标题
		canvas.drawText(XTitle, (XPoint+XLength-50), YPoint+50,paint);//设置x轴标题
	}

}