package com.yibairun.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class MyDialog  extends ProgressDialog{  
  
	public MyDialog(Context context, int theme) {  
        super(context, theme);  
        // TODO dvsdfads  
    }  
  
    public MyDialog(Context context) {  
        super(context);  
          
    }  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
          
//      progressDialog.setIndeterminate(true);  
//        setCancelable(false);  
  
        //progressDialog.show()  
//        setContentView(R.layout.progressdialog_layout);  
          setCancelable(false);
    }  
    
    /**
     * 设置文字样式
     * @param title
     */
    @Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
//		super.setTitle(title);
    	setMessage(title);
	} 



	public void showDialog()  
    {  
        show();  
    }

	@Override
	public void setMessage(CharSequence message) {
		// TODO Auto-generated method stub
		super.setMessage(message);
	}


}  