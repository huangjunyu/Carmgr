package com.yiwucheguanjia.merchantcarmgr.account;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/17.
 */
public class MerchantEnter extends FragmentActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment homeFragment;
    private Fragment merchantFragment;
    private Fragment jionFragment;
    private Fragment currentFragment;
    @BindView(R.id.enter_operate_data)
    TextView operateDataTv;
    @BindView(R.id.enter_merchant_img)
    ImageView guideImg1;
    @BindView(R.id.enter_merchant_data_tv)
    TextView merchantDataTv;
    @BindView(R.id.merchant_data_img)
    ImageView guideImg2;
    @BindView(R.id.enter_jion_data_tv)
    TextView jionDataTv;
    @BindView(R.id.enter_data_goback_rl)
    RelativeLayout gobackRl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_enter);
        ButterKnife.bind(this);
        initTab();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.next_one");
        intentFilter.addAction("action.next_two");
//        intentFilter.addAction("action.appointment");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }
    @OnClick(R.id.enter_data_goback_rl)
    void setGobackRl(){
        finish();
    }

    /**
     * 点击下一步的时候，切换fragment
     * @param i 标记的fragment
     */
    public void nextStep(int i){
        if(i == 0){
            if(homeFragment == null){
                homeFragment = new OperateDataFragment();
            }
            addShowFragment(fragmentManager.beginTransaction(),homeFragment);
            operateDataTv.setTextColor(ContextCompat.getColor(this,R.color.orange));
            guideImg1.setImageResource(R.mipmap.register_right_pre);
            merchantDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg2.setImageResource(R.mipmap.register_right_nor);
            jionDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
        }else if (i == 1){
            if (merchantFragment == null){
                merchantFragment = new MerchantDataFragment();
            }
            Log.e("1","one");
            addShowFragment(fragmentManager.beginTransaction(),merchantFragment);
            operateDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg1.setImageResource(R.mipmap.register_right_pre);
            merchantDataTv.setTextColor(ContextCompat.getColor(this,R.color.orange));
            guideImg2.setImageResource(R.mipmap.register_right_pre);
            jionDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
        }else if ((i == 2)){
            if (jionFragment == null){
                jionFragment = new JionDataFragment();
            }
            addShowFragment(fragmentManager.beginTransaction(),jionFragment);
            operateDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg1.setImageResource(R.mipmap.register_right_nor);
            merchantDataTv.setTextColor(ContextCompat.getColor(this,R.color.buseness_black));
            guideImg2.setImageResource(R.mipmap.register_right_nor);
            jionDataTv.setTextColor(ContextCompat.getColor(this,R.color.orange));
        }
    }
    public void addShowFragment(FragmentTransaction fragmentTransaction,Fragment fragment){
        if (currentFragment == fragment){
            return;
        }
        if (!fragment.isAdded()){
            fragmentTransaction.hide(currentFragment).add(R.id.content_layout,fragment).commitAllowingStateLoss();
        }else {
            fragmentTransaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
            //此处可用广播通知切换界面
        }
        currentFragment = fragment;
    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (homeFragment == null) {
            homeFragment = new OperateDataFragment();
        }

        if (!homeFragment.isAdded()) {
            // 提交事务
            fragmentManager.beginTransaction().add(R.id.content_layout, homeFragment).commit();
            // 记录当前Fragment
            currentFragment = homeFragment;
        }
    }
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.next_one")) {
                nextStep(1);

            } else if (action.equals("action.next_two")) {
                nextStep(2);
            } else if (action.equals("action.next_three")) {
                }else {
                }
        }
    };
    @Override
    protected void onDestroy() { // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
