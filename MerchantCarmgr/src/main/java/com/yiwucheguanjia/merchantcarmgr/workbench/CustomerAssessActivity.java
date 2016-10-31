package com.yiwucheguanjia.merchantcarmgr.workbench;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.view.OneStarFragment;
import com.yiwucheguanjia.merchantcarmgr.workbench.view.ThreeStarFragment;
import com.yiwucheguanjia.merchantcarmgr.workbench.view.TwoStarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/23.
 */
public class CustomerAssessActivity extends FragmentActivity {
    @BindView(R.id.customer_assess_tl)
    protected TabLayout mTabLayout;
    private Fragment fragment1,fragment2,fragment3,fragment4,fragment5;//5星好评的各界面
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    @BindView(R.id.customer_assess_viewpager)
    protected ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_assess_activity);
        ButterKnife.bind(this);
        initView();

    }

    public void initView(){
        fragment1 = new FiveStarFragment();
        fragment2 = new FiveStarFragment();
        fragment3 = new ThreeStarFragment();
        fragment4 = new TwoStarFragment();
        fragment5 = new OneStarFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);

        //添加页卡标题
        mTitleList.add("1星");
        mTitleList.add("2星");
        mTitleList.add("3星");
        mTitleList.add("4星");
        mTitleList.add("5星");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(myFragmentPagerAdapter);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        public MyFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments){
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position%fragments.size());
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }

}
