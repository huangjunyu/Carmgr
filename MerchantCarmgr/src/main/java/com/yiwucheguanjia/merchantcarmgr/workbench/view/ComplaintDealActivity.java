package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * 用户投诉处理.
 */
public class ComplaintDealActivity extends FragmentActivity {
    private Fragment doneFragment,noFragment;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    @BindView(R.id.complain_deal_tl)
    TabLayout mTabLayout;
    @BindView(R.id.complain_deal_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.complain_deal_goback_rl)
    RelativeLayout gobackRl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_complain_deal);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        doneFragment = new DoneFragment();
        noFragment = new NoFragment();
        fragments.add(doneFragment);
        fragments.add(noFragment);

        //页卡标题添加
        mTitleList.add("已处理");
        mTitleList.add("未处理");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,mTitleList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(myFragmentPagerAdapter);
    }
    @OnClick(R.id.complain_deal_goback_rl)
    void click(){
        finish();
    }
}
