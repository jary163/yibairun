package cn.hi.eim.model;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class Cards {


	private int left,top,width,height;
	private View view;
	
	public Cards(int left,int top,int width,int height,View view){
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.view = view;
		//initView();
	}
	
	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}
	

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void initView() {
		LayoutParams params = new LayoutParams(width, height);
		view.setLayoutParams(params);
	}

	public View getView() {
		return view;
	}

	//是否显示的默认图片
	public boolean turnView(){
		
		return false;
	}
	
	
}
