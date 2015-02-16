package com.yibairun.utils;

import android.os.CountDownTimer;
import android.util.Log;

public class MyCountDownTimer extends CountDownTimer {  
	private CountDownListener countDownListener;
	
    /** 
     *  
     * @param millisInFuture 
     *            表示以毫秒为单位 倒计时的总数 
     * @param countDownInterval 
     *            表示 间隔 多少微秒 调用一次 onTick 方法 
     */  
    public MyCountDownTimer(long millisInFuture, long countDownInterval,CountDownListener countDownListener) {  
        super(millisInFuture, countDownInterval);  
        this.countDownListener = countDownListener;
    }  

    @Override  
    public void onFinish() {  
    	if(null!=countDownListener){
    		countDownListener.onFinish();
    	}
    }  

    @Override  
    public void onTick(long millisUntilFinished) {  
    	if(null!=countDownListener){
    		countDownListener.onTick(millisUntilFinished);
    	}
    }  
    
    public interface CountDownListener{
    	/**
    	 * 倒计时变化时
    	 */
    	public void onTick(long millisUntilFinished);
    	
    	/**
    	 * 当倒计时完成时
    	 */
    	public void onFinish();
    }
} 