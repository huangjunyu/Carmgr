package com.yiwucheguanjia.carmgr.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.LoginActivity;
import com.yiwucheguanjia.carmgr.adviewpager.ImageBean;
import com.yiwucheguanjia.carmgr.adviewpager.SamplePagerView;
import com.yiwucheguanjia.carmgr.personal.personalActivity;

/**
 * Created by Administrator on 2016/6/20.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private LinearLayout homeView;
    private LinearLayout homeLinearLayout;
    private RelativeLayout home_personal_rl;
    private SamplePagerView pagerView;
    private LinearLayout llRoot;
    private ImageBean bean;
    private static final String KEY_CONTENT = "HomeFragment:Content";
    public static HomeFragment newInstance(ImageBean bean) {
        HomeFragment fragment = new HomeFragment();
        fragment.bean = bean;
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = (LinearLayout) inflater.inflate(R.layout.home_view,null);
        init();

        homeLinearLayout = (LinearLayout) homeView.findViewById(R.id.homeLinearLayout);
        pagerView = new SamplePagerView(getActivity());
        pagerView.init();
        homeLinearLayout.addView(pagerView, 1);
        return homeView;

    }
    private void init(){
        home_personal_rl = (RelativeLayout)homeView.findViewById(R.id.home_personal_rl);
        home_personal_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_personal_rl:
                Intent personalIntent = new Intent(getActivity(),LoginActivity.class);
//                getActivity().startActivity(personalIntent);
                getActivity().startActivityForResult(personalIntent,1);
        }
    }
}
