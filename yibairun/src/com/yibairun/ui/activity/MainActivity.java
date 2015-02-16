package com.yibairun.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.readystatesoftware.viewbadger.BadgeView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.update.UmengUpdateAgent;
import com.yibairun.R;
import com.yibairun.comm.Constant;
import com.yibairun.listener.OnFragmentComunicationListener;
import com.yibairun.ui.components.PagerSlidingTabStrip;
import com.yibairun.ui.fragment.MessageFragment;
import com.yibairun.ui.fragment.PersonalFragment;
import com.yibairun.ui.fragment.ProductFragment;
import com.yibairun.ui.fragment.RecommendFragment;
import com.yibairun.utils.BankUtils;

import java.util.Locale;

import static com.yibairun.comm.Constant.IGeituiMsg;

public class MainActivity extends BaseActivity implements OnFragmentComunicationListener {

	/**http://ask.make-money-article.com/que/20622107
	*http://blog.csdn.net/guolin_blog/article/details/26365683
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	MenuItem loginItem,myBankItem;

//	private ActionBar actionBar;
    private PagerSlidingTabStrip tabs;
    private DisplayMetrics dm;
    private int lastPosition = 0;//用于记录上一次position的位置，默认为0
    
    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = getResources().getDisplayMetrics();
        PushManager.getInstance().initialize(this.getApplicationContext());
       /* Tag tag = new Tag();
        tag.setName("cherish");
        PushManager.getInstance().setTag(this,new Tag[]{tag});*/
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(context);//友盟自动更新
        getSwipeBackLayout().setEnableGesture(false);
	}

	@Override
	public void initView() {

		mViewPager = (ViewPager) findViewById(R.id.pager);
       tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        mViewPager.setOffscreenPageLimit(4);
//        mViewPager.setPageTransformer(true,new DepthPageTransformer());
	}

	@Override
	public void initDate() {
/*
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);*/

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.

		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
        tabs.setViewPager(mViewPager);
//        tabs.setTabsValue(dm);
/*		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}*/
        parseIntentExtra(getIntent());
	}

	@Override
	public void initListener() {
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
			/*	if(position==1&&TextUtils.isEmpty(appController.getAppKey())){
					Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
					//startActivity(LoginActivity.class);
					mViewPager.setCurrentItem(lastPosition);
				}else{
					if(position==1){
						PersonalFragment mPersonalFragment = PersonalFragment.newInstance(2);
						if(mPersonalFragment instanceof InvestDateListener){
							((InvestDateListener)mPersonalFragment).getDateFromNet();
						}
					} 
					lastPosition = position;
				}*/
                setBageTips(position,"0");
			}
		});



        IntentFilter filter=new IntentFilter();
        filter.addAction(Constant.ACTION_GEITUI_RECEIVER);
        context.registerReceiver(receiver, filter);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		loginItem = menu.findItem(R.id.action_login);
		myBankItem = menu.findItem(R.id.action_bank_cards);
		if (!TextUtils.isEmpty(appController.getAppKey())) {
			loginItem.setVisible(false);
		}
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		if(null==appController.getUserinfo()||appController.getUserinfo().getUser().getIs_first_cash()!=1){//首次提现，隐藏菜单栏中我的银行卡item
			myBankItem.setVisible(false);
		}else{
			myBankItem.setVisible(true);
		}
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_login:
			if(loginItem.getTitle().equals("登录")){
				login();
			}
			return true;
		case R.id.action_settings:
			startActivity(SettingActivity.class);
			return true;

		case R.id.action_bank_cards:
			startActivity(MyBanksActivity.class);
			return true;
		case R.id.action_account_manager:
			if(!gotoLogin(getIntent())){
				startActivity(AccountManagerActivity.class);
			}
			return true;
		case android.R.id.home:
			Toast.makeText(context, "返回上一级目录", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void login() {
		Intent i = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(null!=loginItem){
			setLoginItemSatet();
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			switch (position) {
			case 0:

                return ProductFragment.newInstance(position + 1);
			case 1:
				return PersonalFragment.newInstance(position + 1);
			case 2:
				return MessageFragment.newInstance(position + 1);
			case 3:
				return RecommendFragment.newInstance(position + 1);
            //appController.getAppKey()!=null?ProductFragment.newInstance(position + 1):RecommendFragment.newInstance(position + 1);//
			default:
				break;
			}
			return null;
		}



        @Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			case 3:
				return getString(R.string.title_section4);
			}
			return null;
		}
	}


	
/*	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		
	}*/
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//firstTime
                    return true;
                } else {
                    appController.exit();
                    System.exit(0);
                }
                break;
        }

        return super.onKeyUp(keyCode, event);
    }

	@Override
	public void OnLoginListener(boolean isSuccess) {
		setLoginItemSatet();
	}
	
	/**
	 * 设置登陆按钮的状态
	 */
	private void setLoginItemSatet() {
		if(null!=loginItem){
			loginItem.setVisible(true);
			if(null!=appController.getUserinfo()&&null!=appController.getUserinfo().getUser()&&!TextUtils.isEmpty(appController.getAppKey())){
				if(!TextUtils.isEmpty(appController.getUserinfo().getUser().getMobile())){
					loginItem.setTitle(BankUtils.formatMobileNum(appController.getUserinfo().getUser().getMobile(),3,7));
				}else{
					loginItem.setVisible(false);
				}
			}else{
				loginItem.setTitle("登录");
			}
		}
	}



    public  BroadcastReceiver receiver = new  BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constant.ACTION_GEITUI_RECEIVER.equals(intent.getAction())){
                parseIntentExtra(intent);
            }
        }

    };
    private void parseIntentExtra(Intent intent){
        String [] msg =   intent.getStringArrayExtra(Constant.BROADCAST_IGETUI_HOMEPAGE_EXTRA);
        if(msg!=null) {

            	switch (IGeituiMsg.valueOf(msg[1].toUpperCase())) {
            	case PRODUCTS:
            		setBageTips(0, msg[2]);
            		break;
            	case ACCOUNT:
            		setBageTips(1, msg[2]);
            		break;
            	case INFORMATION:
            		setBageTips(2, msg[2]);
            		break;
            	}
        }
    }
    private void setBageTips(int pos,String msg){
        BadgeView badgeView = tabs.getCurrentBageView(pos);

        try {
            int count =Integer.parseInt(msg);
            if(count>0){
                badgeView.setText(String.valueOf(count>99?99:count));
                badgeView.show();
            }else{
                badgeView.hide();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onPause() {
    	super.onPause();

    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	/**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
           ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
