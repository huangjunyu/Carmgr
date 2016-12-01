package com.yiwucheguanjia.carmgr.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.controller.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {
    @BindView(R.id.customer_assess_tl)
    protected TabLayout mTabLayout;
    private Fragment fragment1,fragment2,fragment3,fragment4,fragment5;//5星好评的各界面
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<ImageView> imageViews = new ArrayList<>();//页卡图标
    @BindView(R.id.customer_assess_viewpager)
    protected ViewPager mViewPager;

    private int[] tabIcons = {
            R.mipmap.after_sale,
            R.mipmap.after_sale,
            R.mipmap.after_sale,
            R.mipmap.after_sale,
            R.mipmap.after_sale
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initView();
    }
    public void initView(){
        fragment5 = new AllFragment();
        fragment4 = new AllFragment();
        fragment3 = new AllFragment();
        fragment2 = new AllFragment();
        fragment1 = new AllFragment();
        fragment1 = new AllFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        imageViews.add(new ImageView(this));
        //添加页卡标题
        mTitleList.add("1星");
        mTitleList.add("2星");
        mTitleList.add("3星");
        mTitleList.add("4星");
        mTitleList.add("5星");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
//        mTabLayout.getTabAt(0).setIcon(R.mipmap.after_sale);
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));
//        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.mipmap.after_sale));
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments,mTitleList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        mViewPager.setCurrentItem(1);
        mViewPager.setCurrentItem(0);//直接设置0的话竟然不起作用,好吧.就这样迂回一下吧
//        mTabLayout.setTabsFromPagerAdapter(myFragmentPagerAdapter);
    }
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));//返回一个view，可以自由定义布局
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2));
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
