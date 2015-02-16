package com.yibairun.ui.activity;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.yibairun.R;
import com.yibairun.ui.fragment.GuideFragment;

public class GuideActivity extends BaseActivity {

	private GuidePageAdapter mAdapter;
	private ViewPager mPager;
	protected int curPage;
	protected int preState;

	@Override
	protected void onResume() {
		super.onResume();
		if (systemShare.getBoolean("isfirst", false)) {
			toMainActivity();
			this.finish();
		} else {
			Editor editor = systemShare.edit();
			editor.putBoolean("isfirst", true);
			editor.commit();

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
		// mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		// mIndicator.setViewPager(mPager);
		// mIndicator.setOnPageChangeListener(this);

		// btnEnter = (Button) findViewById(R.id.btnEnter);

	}

	@Override
	public void initView() {
		mPager = (ViewPager) findViewById(R.id.pager);
		View findView = findView(R.id.tv_name);
	}

	@Override
	public void initDate() {
		mAdapter = new GuidePageAdapter(getSupportFragmentManager());
		mPager.setAdapter(mAdapter);
		bar.hide();
	}

	@Override
	public void initListener() {
		mPager.setOnPageChangeListener(pageChangeListener);
	}

	class GuidePageAdapter extends FragmentPagerAdapter {
		Fragment[] frags = null;

		public GuidePageAdapter(FragmentManager fm) {
			super(fm);
			frags = new Fragment[3];

			frags[0] = new GuideFragment(R.layout.fragment_guide1);
			frags[1] = new GuideFragment(R.layout.fragment_guide2);
			frags[2] = new GuideFragment(R.layout.fragment_guide3);
		}

		@Override
		public Fragment getItem(int position) {
			return frags[position];
		}

		@Override
		public int getCount() {
			return frags.length;
		}

	}

	public interface WelcomeClickListener {
		/**
		 * 判断是否点击了
		 */
		public void click();
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			curPage = arg0;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (preState == 1 && arg0 == 0 && curPage == 2) {
				toMainActivity();
			}
			preState = arg0;
		}
	};

	private void toMainActivity() {
		startActivity(MainActivity.class);
	}

}
