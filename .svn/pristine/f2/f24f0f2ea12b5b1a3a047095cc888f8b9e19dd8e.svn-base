package cn.hi.eim.activity;

import com.umeng.update.UmengUpdateAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.hi.eim.R;

public class WelcomeActivity extends ActivitySupport {


    private ImageView iv;
    private Animation mFadeIn;
    private Animation mFadeInScale;
    private Animation mFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //检查是否自动跟新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        setContentView(R.layout.activity_welcome);
        //   logo_tip = (ImageView) this.findViewById(R.id.logo_tip);
        iv = (ImageView) this.findViewById(R.id.logo);
//        task = new GetLandingTask().execute();
        init();
        setListener();
    }

    /**
     * 初始�?
     */
    private void init() {
        initAnim();
        iv.startAnimation(mFadeIn);
    }

    /**
     * 初始化动�?
     */
    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(WelcomeActivity.this,
                R.anim.v5_0_1_guide_welcome_fade_in);
        mFadeIn.setDuration(1000);
        mFadeInScale = AnimationUtils.loadAnimation(WelcomeActivity.this,
                R.anim.v5_0_1_guide_welcome_fade_in_scale);
        mFadeInScale.setDuration(6000);
        mFadeOut = AnimationUtils.loadAnimation(WelcomeActivity.this,
                R.anim.v5_0_1_guide_welcome_fade_out);
        mFadeOut.setDuration(1000);
    }

    private void setListener() {

        mFadeIn.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(mFadeOut);
            }
        });
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
                // logo_tip.setVisibility(View.GONE);

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent( context,LoginActivity.class);
              
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
    }


}


