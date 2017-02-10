package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/23.
 */
public class CustomerAssessActivity extends FragmentActivity {
    @BindView(R.id.customer_assess_tl)
    protected TabLayout mTabLayout;
    @BindView(R.id.assess_goback_rl)
    RelativeLayout gobackRl;
    private Fragment[] fragmentCollect = {new OneStarFragment(),new TwoStarFragment(),new ThreeStarFragment(),new FourStarFragment(),new FiveStarFragment()};
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    @BindView(R.id.customer_assess_viewpager)
    protected ViewPager mViewPager;
    private String[] stars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_customer_assess);
        ButterKnife.bind(this);
        stars = getResources().getStringArray(R.array.stars);
        initView();
    }
    public void initView(){
        for (int i = 0;i < 5;i++){
            fragments.add(fragmentCollect[i]);
            mTitleList.add(stars[i]);
            mTabLayout.addTab(mTabLayout.newTab().setText(stars[i]));
        }
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,mTitleList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(myFragmentPagerAdapter);
    }
    @OnClick(R.id.assess_goback_rl)
    void click(){
        finish();
    }
}
