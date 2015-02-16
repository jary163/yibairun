package com.yibairun.ui.components;



import java.util.Timer;
import java.util.TimerTask;

import com.yibairun.R;
import com.yibairun.utils.SystemUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public class WaterViewGroupEx extends ViewGroup {
	/** 起始位置 (对圆有效) */
	private int mStartAngle = 0;
	
	/**
	 * 圆的起始位置
	 */
	private int mStartPosition = 0;
	/**
	 * 园的最底层大小  默认为整个圆圈
	 */
	private int backTotal = 360;
	/** 范围(0-100) */
	private int mRange = 0;
	/** 范围(0-100) */
	private int mOldRange = 0;
	/** 当前位置(百分比) */
	private int mCurrentPosition = 0;
	/** 每次绘制跨度(百分比) */
	private int mSpanPercent = 1;
	private int span = 0;
	/** 绘制时间间隔 */
	private int mFrameTime = 5;

	/** bank的结束位置 */
	private int pBankEnd = 0;
	/** 空白结束位置 */
	private int pBlack = 0;

	private MyView mView;

	private Drawable drawable;

	private float mWidth;

	private float mHeight;
	/** 底图绘制方式 */
	private int type = 0;
	private int TYPE_DRAW_ARC = 0;
	private int TYPE_DRAW_RECT = 1;
	
	/**
	 * 画布旋转角度
	 */
	private int rotate = 0;

	/** 是否正在绘制 */
	private boolean isDraw = false;
	/***/
	private int mMode = 0;
	/** 增长 */
	public static final int MODE_INCREASE = 0;
	/** 降低 */
	public static final int MODE_DECREASE = 1;
	/** 线性颜色百分比界限 */
	private int[] mRectLimitNum;
	/** 线性颜色百分比对应颜色 */
	private int[] mRectLimitColor;
	/** 背景色 */
	private int mBackgroundColor;
	private AnimationListener mAnimationListener;

	/** 各图层颜色值 */
	private int p_yearColor = 0xFFFFFFFF;
	private int p_bankColor = 0xFFFB929C;
	private int p_balanceColor = 0xFFF93E36;

	private Context mContext;

	private float mStrokeWidth = 30;

	private float circleWidth = 12;

	private int childOffX = 0;

	private int childOffY = 0;
	
	

	public WaterViewGroupEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public WaterViewGroupEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray tArray = context.obtainStyledAttributes(attrs,
				R.styleable.WaterViewGroup);
		drawable = tArray.getDrawable(0);
		type = tArray.getInt(1, TYPE_DRAW_ARC);
//		mFrameTime = tArray.getInt(3, 20);
		mSpanPercent = tArray.getInt(4, 1);
		mStartAngle = tArray.getInt(5, 0);
		mBackgroundColor = tArray.getInt(6, 0xff000000);
		// mStrokeWidth = tArray.getInt(R.styleable.WaterViewGroup_stroke_width,
		// 30);

		mStrokeWidth = SystemUtils.dip2px(context,
				tArray.getInt(R.styleable.WaterViewGroup_stroke_width, 10));
		// System.out.println("mStrokeWidth" + mStrokeWidth);
		childOffX = SystemUtils.dip2px(context,
				tArray.getInt(R.styleable.WaterViewGroup_child_off_x, 0));
		childOffY = SystemUtils.dip2px(context,
				tArray.getInt(R.styleable.WaterViewGroup_child_off_y, 0));

		mView = new MyView(context, attrs);
		addView(mView);
		mMode = MODE_INCREASE;
		span = mSpanPercent;
		mContext = context;
	}

	public WaterViewGroupEx(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int count = this.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = this.getChildAt(i);
			if (child instanceof MyView) {
				int w = getWidth();
				int h = getHeight();
				mWidth = w;
				mHeight = h;
				child.layout(0, 0, w, h);
			} else {// 居中显示
				int w = child.getMeasuredWidth();
				int h = child.getMeasuredHeight();
				int l1 = ((getWidth() - w) >> 1) + childOffX;
				int t1 = ((getHeight() - h) >> 1) + childOffY;
				int r1 = ((getWidth() - w) >> 1) + w + childOffX;
				int b1 = ((getHeight() - h) >> 1) + h + childOffY;
				child.layout(l1, t1, r1, b1);
			}
		}
	}

	/**
	 * 设置线性填充界限（只对rect有效）
	 * 
	 * @param limitNum
	 *            线性颜色百分比界限
	 * @param limitColor
	 *            线性颜色百分比对应颜色
	 */
	public void setRectLimit(int[] limitNum, int[] limitColor) {
		mRectLimitColor = limitColor;
		mRectLimitNum = limitNum;
	}

	public void setBackgroundColor(int backgroundColor) {
		mBackgroundColor = backgroundColor;
	}
	

	public void setBackTotal(int backTotal) {
		this.backTotal = backTotal;
	}



	public void setmAnimationListener(AnimationListener mAnimationListener) {
		this.mAnimationListener = mAnimationListener;
	}

	/**
	 * 设置第三层view的进度和颜色
	 * 
	 * @param pBankEnd
	 */
	public void setpBankEnd(int pBankEnd,int color) {
		this.pBankEnd = pBankEnd;
		mView.setBankColor(color);
	}

	/**
	 * 设置空白区域所占面积
	 * 
	 * @param pBlack
	 */
	public void setpBlack(int pBlack,int color) {
		this.pBlack = pBlack;
		mView.setYearColor(color);
	}

	public void setP_yearColor(int p_yearColor) {
		this.p_yearColor = p_yearColor;
	}

	public void setP_bankColor(int p_bankColor) {
		this.p_bankColor = p_bankColor;
	}

	public void setP_balanceColor(int p_balanceColor) {
		this.p_balanceColor = p_balanceColor;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	/**
	 * 设置扇形渐变色
	 * 
	 * @param arcColors
	 */
	public void setArcColor(int[] arcColors) {
		mView.setArcColors(arcColors);
	}
	
	

	public void setmStartPosition(int mStartPosition) {
		this.mStartPosition = mStartPosition;
	}

	public void init() {
		if (type == TYPE_DRAW_ARC) {
			mCurrentPosition = 0;
			mRange = 0;
		} else if (type == TYPE_DRAW_RECT) {
			mCurrentPosition = 100;
			mRange = 100;
		}

	}
	
	/**
	 * 设置底层颜色
	 */
	public void setBackColor(int color){
		mView.setYearColor(color);
	}

	/**
	 * 开始动画
	 * 
	 * @param range
	 */
	public void startAnimation(int range, int mode) {
		if (isDraw || mRange == range)
			return;
		mMode = mode;
		if (mMode == MODE_INCREASE) {
			span = mSpanPercent;
		} else if (mMode == MODE_DECREASE) {
			span = -mSpanPercent;
		}
		mRange = range;
		mView.start();
	}

	/**
	 * 开始动画
	 * 
	 * @param range
	 */
	public void startAnimation(int range) {
		if (isDraw || mRange == range)
			return;
		if (mRange < range) {
			mMode = MODE_INCREASE;
			span = mSpanPercent;
		} else if (mRange > range) {
			mMode = MODE_DECREASE;
			span = -mSpanPercent;
		}
		mRange = range;
		mView.start();
	}
	
	/**
	 * 开始动画
	 * @param range
	 * @param color
	 * @param
	 */
	public void startAnimation(int range,int[] color){
		mView.setmPaintColor(color);
		this.startAnimation(range);
	}

	class MyView extends View {

		private Paint mPaint = null;

		private Timer mTask;

		private Shader mShader;

		private RectF rect;

		private Paint p_year = null, p_bank = null, p_balance = null;

		private Paint cPaint = null;
		private RectF topClipRect, bottomClipRect;

		public MyView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			// TODO Auto-generated constructor stub
			init(context);
		}

		public MyView(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
			init(context);
		}

		public MyView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			init(context);
		}

		private void init(Context context) {
			mPaint = new Paint();
			cPaint = new Paint();
			mPaint.setAntiAlias(true);
			cPaint.setAntiAlias(true);
			if (type == TYPE_DRAW_ARC) {
				cPaint.setColor(0xccffffff);
				cPaint.setStyle(Paint.Style.FILL);

				mPaint.setColor(0xccffffff);
				mCurrentPosition = 0;
				mRange = 0;
				mPaint.setStrokeWidth(SystemUtils.px2dip(context, circleWidth+3));
				mPaint.setStyle(Paint.Style.STROKE);

			} else if (type == TYPE_DRAW_RECT) {
				mRectLimitNum = new int[] { 100, 79, 30 };
				mRectLimitColor = new int[] { 0xff35bdb2, 0xfff7b32e,
						0xffff5777 };
				mCurrentPosition = 100;
				mRange = 100;
			}

			initPaint(context);
		}

		
		private void initPaint(Context context) {

			p_year = new Paint();
			p_year.setColor(p_yearColor);
			p_year.setStyle(Paint.Style.STROKE);
			p_year.setStrokeWidth(SystemUtils.px2dip(context, circleWidth));
			p_year.setAntiAlias(true);

/*			p_balance = new Paint();
			p_balance.setColor(p_balanceColor);
			p_balance.setStyle(Paint.Style.STROKE);
			p_balance.setStrokeWidth(SystemUtils.px2dip(context, circleWidth));
			p_balance.setAntiAlias(true);*/

			p_bank = new Paint();
			p_bank.setColor(p_bankColor);
			p_bank.setStyle(Paint.Style.STROKE);
			p_bank.setStrokeWidth(SystemUtils.px2dip(context, circleWidth+5));
			p_bank.setAntiAlias(true);
			
		}

		private void setArcColors(int[] colors) {
			mShader = new SweepGradient(mWidth / 2, mWidth / 2, colors, null);
			mPaint.setShader(mShader);
			cPaint.setShader(mShader);
		}

		/**
		 * 设置第二层圆圈颜色
		 */
		public void setYearColor(int color){
			p_year.setColor(color);
			//p_year.setStyle(Paint.Style.STROKE);
			//p_year.setStrokeWidth(SystemUtils.px2dip(context, circleWidth));
			//p_year.setAntiAlias(true);
			//p_year.setColor(color);
		}
		
		/**
		 * 设置第三层颜色
		 * @param color
		 */
		public void setBankColor(int color){
			p_bank.setColor(color);
		}
		
		/**
		 * 设置动画层的颜色
		 * @param
		 */
		public void setmPaintColor(int[] colors){
			mShader = new SweepGradient(mWidth / 2, mWidth / 2, colors, null);
			mPaint.setShader(mShader);
		}
		
		
		public void start() {
			mTask = new Timer();
			mTask.schedule(new TimerTask() {
				@Override
				public void run() {

					mCurrentPosition += span;
					isDraw = true;
					if (mMode == MODE_INCREASE) {
						if (mRange < mOldRange) {
							if (mCurrentPosition >= 100) {
								mCurrentPosition -= 100;
								mOldRange = mRange;
							}
						} else {
							if (mCurrentPosition >= mRange) {
								mOldRange = mRange;
								mTask.cancel();
								isDraw = false;
							}
						}
					} else if (mMode == MODE_DECREASE) {
						if (mCurrentPosition <= 0)
							mCurrentPosition = 0;
						if (mCurrentPosition <= mRange) {
							mOldRange = mRange;
							mTask.cancel();
							isDraw = false;
						}
					}
					postInvalidate();
				}
			}, mFrameTime, mFrameTime);
		}

		private RectF crect = new RectF(0, 0, 0, 0);

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if(rotate!=0){
				canvas.rotate(rotate); 
		        canvas.translate(-getHeight(), 0); 
			}
			canvas.drawColor(mBackgroundColor);
			if (type == TYPE_DRAW_ARC) {
				if (rect == null) {
					rect = new RectF(0 + (mStrokeWidth / 2),
							0 + (mStrokeWidth / 2),
							mWidth - (mStrokeWidth / 2), mHeight
									- (mStrokeWidth / 2));
				}



				// Log.e("main", "rect:"+rect+"  p:"+p);
				if (mStartAngle == 0) {// 绘制底图进度条
					if (topClipRect == null) {
						mPaint.setStrokeJoin(Paint.Join.ROUND);
						mPaint.setStrokeCap(Paint.Cap.ROUND);
						topClipRect = new RectF(0, 0, mWidth, mWidth / 2);
						bottomClipRect = new RectF(0, mWidth / 2, mWidth,
								mWidth);
					}
					//float sweepAngle = 360f * mCurrentPosition / 100;
					float sweepAngle = mCurrentPosition;
					canvas.save();
					canvas.clipRect(bottomClipRect);
					canvas.drawArc(rect, mStartPosition, sweepAngle, false, mPaint);

					
					
					//Log.e("main", "mRange:"+mRange+"   BLACK:"+pBlack+"   sweepAngle:"+sweepAngle);
					if(pBlack>=sweepAngle){
						canvas.drawArc(rect, mStartPosition, sweepAngle, false, p_year);
						//Log.e("main", "绘制第二层voer");//第二层绘制
						if(mAnimationListener!=null&&sweepAngle!=0&&pBlack/2<sweepAngle)//当第二段动画执行一半时
						mAnimationListener.onSecondAnimaEnd(pBlack);
					}else{
						//Log.e("main", "绘制第一层voer");//当第二层绘制完之后直接显示第一层的文字
						if(mAnimationListener!=null)
						mAnimationListener.onFirstAnimaEnd(pBlack);
						canvas.drawArc(rect, mStartPosition, pBlack, false, p_year);// 绘制第二层
					}
					
					if(pBankEnd!=0){
						if(pBankEnd>=sweepAngle){
							canvas.drawArc(rect, mStartPosition, sweepAngle, false, p_bank);// 绘制第三层进度条
						}else{
							//Log.e("main", "绘制第三层over");
							if(mAnimationListener!=null)
							mAnimationListener.onThridAnimaEnd(pBankEnd);
							canvas.drawArc(rect, mStartPosition, pBankEnd, false, p_bank);
						}
					}
					
					canvas.restore();

					if (sweepAngle - 180 >= 0) {
						float angle = sweepAngle - 180 == 0 ? 0.1f
								: sweepAngle - 180;
						canvas.save();
						canvas.clipRect(topClipRect);
						canvas.drawArc(rect, 180, angle, false, mPaint);
						canvas.restore();
					}
				}
				

				

			}

			/*
			 * if (drawable != null) canvas.drawBitmap(((BitmapDrawable)
			 * drawable).getBitmap(), 0, 0, mPaint);
			 */
		}
	}

	public interface AnimationListener{
		/**
		 * 当第一个动画执行完毕
		 */
		public void onFirstAnimaEnd(int progress);
		/**
		 * 当第二个动画执行完毕
		 */
		public void onSecondAnimaEnd(int progress);
		/**
		 * 当第三个动画执行完毕
		 */
		public void onThridAnimaEnd(int progress);
	}
}
