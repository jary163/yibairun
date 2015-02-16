/*
package com.yibairun.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibairun.R;
import com.yibairun.swipebacklayout.lib.app.SwipeBackActivity;
import com.yibairun.utils.Lg;

public abstract class AbstractAc1 extends SwipeBackActivity  {


	protected Lg lg = Lg.jLog();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.abac_1);

	}


    */
/**
     * 设置主内容区域的layoutRes
     * @param layoutResId
     *//*

    public void alabSetContentView(int layoutResId) {
        LinearLayout llContent = (LinearLayout) findViewById(R.id.llContent1);
        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutResId, null);
        llContent.addView(v);
    }
    */
/**
     * 隐藏左侧按钮
     *//*

    public void alabHideButtonLeft(boolean bSetHide) {
        Button bt = alabGetButtonLeft();
        if (null != bt) {
            if (bSetHide) bt.setVisibility(View.INVISIBLE);
            else bt.setVisibility(View.VISIBLE);
        }
    }
    */
/**
     * 隐藏右侧按钮
     *//*

    public void alabHideButtonRight(boolean bSetHide) {
        Button bt = alabGetButtonRight();
        if (null != bt) {
            if (bSetHide) bt.setVisibility(View.INVISIBLE);
            else bt.setVisibility(View.VISIBLE);
        }
    }
    */
/**
     * 得到模板上导航栏的左侧按钮，一般为[返回]
     * @return 成功则返回Button对象，失败则返回null。
     *//*

    public Button alabGetButtonLeft() {
        return (Button) findViewById(R.id.btBack1);
    }
    */
/**
     * 得到模板上导航栏的右侧按钮，一般为[刷新]
     * @return 成功则返回Button对象，失败则返回null。
     *//*

    public Button alabGetButtonRight() {
        return (Button) findViewById(R.id.btRefresh1);
    }
    */
/**
     * 设置模板上导航栏中间的标题文字
     * @param titleText
     * @return 修改成功返回true，失败返回false
     *//*

    public boolean alabSetBarTitleText(String titleText) {
        TextView tv = (TextView) findViewById(R.id.txBarTitle1);
        if (null != tv) {
            tv.setText(titleText);
            return true;
        }
        return false;
    }

}
*/
