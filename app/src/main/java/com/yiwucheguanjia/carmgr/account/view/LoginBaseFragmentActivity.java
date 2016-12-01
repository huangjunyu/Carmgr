package com.yiwucheguanjia.carmgr.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.controller.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 用户投诉处理.
 */
public class LoginBaseFragmentActivity extends FragmentActivity implements View.OnClickListener{
    private Fragment doneFragment,noFragment;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    TabLayout mTabLayout;
    ViewPager mViewPager;
    TextView registerTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        ButterKnife.bind(this);
        mTabLayout = (TabLayout)findViewById(R.id.complain_deal_tl);
        mViewPager = (ViewPager)findViewById(R.id.complain_deal_viewpager);
        registerTv = (TextView)findViewById(R.id.login_register_tv);
        findViewById(R.id.setting_goback_rl).setOnClickListener(this);
        findViewById(R.id.login_register_tv).setOnClickListener(this);
        initView();
    }

    public void initView(){
        doneFragment = new AccountLoginFragment();
        noFragment = new PhoneLoginFragment();
        fragments.add(doneFragment);
        fragments.add(noFragment);

        //页卡标题添加
        mTitleList.add(getResources().getText(R.string.account_login).toString());
        mTitleList.add(getResources().getText(R.string.phone_login_quick).toString());

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,mTitleList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(myFragmentPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_goback_rl:
                finish();
                break;
            case R.id.login_register_tv:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }
    }
}
