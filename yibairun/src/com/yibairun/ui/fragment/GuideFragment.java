package com.yibairun.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yibairun.ui.activity.GuideActivity;


@SuppressLint("ValidFragment")
public class GuideFragment extends Fragment {

    private int resource;
    private GuideActivity.WelcomeClickListener welcomeClickListener;

    public GuideFragment() {
    }

    public GuideFragment(int resource, GuideActivity.WelcomeClickListener... welcomeClickListener) {
        this.resource = resource;
        if (welcomeClickListener.length > 0) this.welcomeClickListener = welcomeClickListener[0];
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), resource, null);
        if (welcomeClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    welcomeClickListener.click();
                }
            });
        }


/*		ImageView guideImg = (ImageView) view.findViewById(R.id.guide_img);

		guideImg.setImageResource(guide.getGuideImgSource());*/

        return view;
    }


}
