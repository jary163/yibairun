package cn.hi.eim.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import cn.hi.eim.R;

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
        setContentView(R.layout.progressdialog_layout);  
          
    }  
    
 



	public void showDialog()  
    {  
        show();  
    }  
}  