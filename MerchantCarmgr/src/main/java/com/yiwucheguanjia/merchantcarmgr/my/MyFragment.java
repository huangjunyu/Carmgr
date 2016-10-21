package com.yiwucheguanjia.merchantcarmgr.my;

import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MyFragment extends Fragment {

    LinearLayout myFragmentView;
    @BindView(R.id.myft_user_header_rl)
    RelativeLayout userHeaderRl;
    @BindView(R.id.myft_order_week_rl)
    RelativeLayout orderWeekRl;
    @BindView(R.id.myft_income_rl)
    RelativeLayout incomeRl;
    @BindView(R.id.myft_assess_rl)
    RelativeLayout assessRl;
    @BindView(R.id.myft_grade_rl)
    RelativeLayout gradeRl;
    @BindView(R.id.myft_balance_rl)
    RelativeLayout balanceRl;//余额
    @BindView(R.id.myft_cash_deposit_rl)
    RelativeLayout cashDepositRl;
    @BindView(R.id.myft_merchant_profile_rl)
    RelativeLayout profileRl;//评价
    @BindView(R.id.myft_merchant_photo_rl)
    RelativeLayout photoRl;
    @BindView(R.id.myft_system_msg_rl)
    RelativeLayout systemMsgRl;
    @BindView(R.id.myft_setting_rl)
    RelativeLayout settingRl;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = (LinearLayout)inflater.inflate(R.layout.activity_my_fragment,container,false);
        ButterKnife.bind(this,myFragmentView);
        return myFragmentView;
    }

    @OnClick({R.id.myft_setting_rl,R.id.myft_system_msg_rl,R.id.myft_merchant_photo_rl,
    R.id.myft_merchant_profile_rl,R.id.myft_cash_deposit_rl,R.id.myft_balance_rl,R.id.myft_grade_rl,
    R.id.myft_assess_rl,R.id.myft_income_rl,R.id.myft_order_week_rl})
    void onClickView(View view){
        switch (view.getId()){
            case R.id.myft_setting_rl:
                Intent settingIntent = new Intent(getActivity(),SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.myft_system_msg_rl:
                Intent systemMsgIntent = new Intent(getActivity(),SystemMsgActivity.class);
                startActivity(systemMsgIntent);
                break;
            case R.id.myft_merchant_photo_rl:
                Intent merchantPhoIntent = new Intent(getActivity(),MerchantPhotoActivity.class);
                startActivity(merchantPhoIntent);
                break;
            case R.id.myft_merchant_profile_rl:
                break;
            case R.id.myft_cash_deposit_rl:
                break;
            default:
                break;
        }
    }
}
