package com.yiwucheguanjia.carmgr.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.controller.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {
    @BindView(R.id.customer_assess_tl)
    protected TabLayout mTabLayout;
    @BindView(R.id.order_goback_rl)
    protected RelativeLayout gobackRl;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;//5星好评的各界面
    private Fragment fragment6;
    private Fragment fragment7;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<ImageView> imageViews = new ArrayList<>();//页卡图标
    @BindView(R.id.customer_assess_viewpager)
    protected ViewPager mViewPager;

    private int[] tabIcons = {
            R.drawable.all_tab,
            R.drawable.wait_pay_tab,
            R.drawable.wait_use_tab,
            R.drawable.going_tab,
            R.drawable.done_tab,
            R.drawable.wait_access_tab,
            R.drawable.refund_tab
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initView();
    }
    @OnClick(R.id.order_goback_rl)
    void onClick(){
        finish();
    }
    public void initView(){
        fragment1 = new AllFragment();
        fragment2 = new AllFragment();
        fragment3 = new AllFragment();
        fragment4 = new AllFragment();
        fragment5 = new AllFragment();
        fragment6 = new AllFragment();
        fragment7 = new AllFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        fragments.add(fragment6);
        fragments.add(fragment7);
        //添加页卡标题
        mTitleList.add("全部");
        mTitleList.add("待付款");
        mTitleList.add("待使用");
        mTitleList.add("进行中");
        mTitleList.add("已完成");
        mTitleList.add("待评价");
        mTitleList.add("退款/售后");

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,mTitleList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        mViewPager.setCurrentItem(1);
        mViewPager.setCurrentItem(0);//直接设置0的话竟然不起作用,好吧.就这样迂回一下吧
    }
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));//返回一个view，可以自由定义布局
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2));
        mTabLayout.getTabAt(3).setCustomView(getTabView(3));
        mTabLayout.getTabAt(4).setCustomView(getTabView(4));
        mTabLayout.getTabAt(5).setCustomView(getTabView(5));
        mTabLayout.getTabAt(6).setCustomView(getTabView(6));
    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(mTitleList.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }
}
