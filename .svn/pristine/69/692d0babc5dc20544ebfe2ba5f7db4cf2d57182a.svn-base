package com.yibairun.ui.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SlideSwitch extends View implements OnTouchListener {

	//���ؿ���ʱ�ı������ر�ʱ�ı�����������ť
	private Bitmap switch_on_Bkg, switch_off_Bkg, slip_Btn;
	private Rect on_Rect, off_Rect;
	
	//�Ƿ����ڻ���
	private boolean isSlipping = false;
	//��ǰ����״̬��trueΪ������falseΪ�ر�
	private boolean isSwitchOn = false;
	
	//��ָ����ʱ��ˮƽ���X����ǰ��ˮƽ���X
	private float previousX, currentX;
	
	//���ؼ�����
	private OnSwitchListener onSwitchListener;
	//�Ƿ������˿��ؼ�����
	private boolean isSwitchListenerOn = false;
	
	
	public SlideSwitch(Context context) {
		super(context);
		init();
	}
	
	
	public SlideSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	
	private void init() {
		setOnTouchListener(this);
	}
	
	
	public void setImageResource(int switchOnBkg, int switchOffBkg, int slipBtn) {
		switch_on_Bkg = BitmapFactory.decodeResource(getResources(), switchOnBkg);
		switch_off_Bkg = BitmapFactory.decodeResource(getResources(), switchOffBkg);
		slip_Btn = BitmapFactory.decodeResource(getResources(), slipBtn);	
		
		//�Ұ��Rect����������ť���Ұ��ʱ��ʾ���ؿ���
		on_Rect = new Rect(switch_off_Bkg.getWidth() - slip_Btn.getWidth(), 0, switch_off_Bkg.getWidth(), slip_Btn.getHeight());
		//����Rect����������ť������ʱ��ʾ���عر�
		off_Rect = new Rect(0, 0, slip_Btn.getWidth(), slip_Btn.getHeight());
	}
	
	
	public void setSwitchState(boolean switchState) {
		isSwitchOn = switchState;
	}
	
	
	public boolean getSwitchState() {
		return isSwitchOn;
	}
	
	
	public void updateSwitchState(boolean switchState) {
		isSwitchOn = switchState;
		invalidate();
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		//������ť��������
		float left_SlipBtn;
		
		//��ָ���������ߵ�ʱ���ʾ����Ϊ�ر�״̬���������Ұ�ߵ�ʱ���ʾ����Ϊ����״̬
		if(currentX < (switch_on_Bkg.getWidth() / 2)) {
			canvas.drawBitmap(switch_off_Bkg, matrix, paint);
		} else {
			canvas.drawBitmap(switch_on_Bkg, matrix, paint);
		}
		
		//�жϵ�ǰ�Ƿ����ڻ���
		if(isSlipping) {
			if(currentX > switch_on_Bkg.getWidth()) {
				left_SlipBtn = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
			} else {
				left_SlipBtn = currentX - slip_Btn.getWidth() / 2;
			}
		} else {
			//��ݵ�ǰ�Ŀ���״̬���û�����ť��λ��
			if(isSwitchOn) {
				left_SlipBtn = on_Rect.left;
			} else {
				left_SlipBtn = off_Rect.left;
			}
		}
		
		//�Ի�����ť��λ�ý����쳣�ж�
		if(left_SlipBtn < 0) {
			left_SlipBtn = 0;
		} else if(left_SlipBtn > switch_on_Bkg.getWidth() - slip_Btn.getWidth()) {
			left_SlipBtn = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
		}
		
		canvas.drawBitmap(slip_Btn, left_SlipBtn, 0, paint);
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(switch_on_Bkg.getWidth(), switch_on_Bkg.getHeight());
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()) {
		//����
		case MotionEvent.ACTION_MOVE:
			currentX = event.getX();
			break;
			
		//����
		case MotionEvent.ACTION_DOWN:
			if(event.getX() > switch_on_Bkg.getWidth() || event.getY() > switch_on_Bkg.getHeight()) {
				return false;
			}
			
			isSlipping = true;
			previousX = event.getX();
			currentX = previousX;
			break;
		
		//�ɿ�
		case MotionEvent.ACTION_UP:
			isSlipping = false;
			//�ɿ�ǰ���ص�״̬
			boolean previousSwitchState  = isSwitchOn;
			
			if(event.getX() >= (switch_on_Bkg.getWidth() / 2)) {
				isSwitchOn = true;
			} else {
				isSwitchOn = false;
			}
			
			//��������˼�����������ô˷���
			if(isSwitchListenerOn && (previousSwitchState != isSwitchOn)) {
				onSwitchListener.onSwitched(isSwitchOn);
			}
			break;
		
		default:
			break;
		}
		
		//���»��ƿؼ�
		invalidate();
		return true;
	}


	public void setOnSwitchListener(OnSwitchListener listener) {
		onSwitchListener = listener;
		isSwitchListenerOn = true;
	}
	
	
	public interface OnSwitchListener {
		abstract void onSwitched(boolean isSwitchOn);
	}
}
