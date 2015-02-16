package cn.hi.eim.activity;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import cn.hi.eim.R;
import cn.hi.eim.model.ChartHisBean;
import cn.hi.eim.view.RecentChartAdapter;

public class AboutUsAcitivty extends ActivitySupport {

	private ImageView leftBtn, pileCardImageView;
	private TextView centerText;
	private RelativeLayout titleLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);

		initViews();

		initDate();

		initListener();
	}

	private void initViews() {
		titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		centerText = (TextView) findViewById(R.id.base_title_tv);
		leftBtn = (ImageView) findViewById(R.id.leftBtn);
	}

	private void initDate() {
		titleLayout.setBackgroundColor(getResources().getColor(R.color.chat_title_background));
		leftBtn.setImageResource(R.drawable.back);
		centerText.setText(getString(R.string.aboutus));
	}

	private void initListener() {
		leftBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.leftBtn:
			this.finish();
			break;
		default:
			break;
		}
	}
}
