package com.yiwucheguanjia.carmgr.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.order.OrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MyFragmetn extends Fragment{

    private View homeView;

    @BindView(R.id.my_setting_img)
    ImageView setingImg;
    @BindView(R.id.my_msg_img)
    ImageView msgImg;
    @BindView(R.id.my_account_rl)
    RelativeLayout accountRl;
    @BindView(R.id.my_header_img)
    ImageView headerImg;
    @BindView(R.id.my_header_tv)
    TextView userNameTv;
    @BindView(R.id.my_mycar_rl)
    RelativeLayout mycarRl;
    @BindView(R.id.my_addcar_img)
    ImageView addCarImg;
    @BindView(R.id.my_order_rl)
    RelativeLayout orderRl;
    @BindView(R.id.pro_wait_use_rl)
    RelativeLayout waitUseRl;
    @BindView(R.id.pro_going_rl)
    RelativeLayout gingRl;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sharedPreferences = getActivity().getSharedPreferences("CARMGR", getActivity().MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.activity_my,container,false);
        ButterKnife.bind(this,homeView);
        return homeView;
    }
    @OnClick({R.id.my_setting_img,R.id.my_msg_img,R.id.my_account_rl,R.id.my_header_img,
            R.id.my_header_tv,R.id.my_mycar_rl,R.id.my_addcar_img,R.id.my_order_rl,
            R.id.progress_all_rl,R.id.pro_wait_pay_rl,R.id.pro_wait_use_rl,R.id.pro_going_rl,R.id.pro_done_rl,R.id.pro_wait_assess_rl,R.id.pro_after_sale__rl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.my_setting_img:
                break;
            case R.id.my_msg_img:
                break;
            case R.id.my_account_rl:
                break;
            case R.id.my_header_img:
                break;
            case R.id.my_header_tv:
                break;
            case R.id.my_mycar_rl:
                break;
            case R.id.my_addcar_img:
                break;
            case R.id.my_order_rl:
                break;
            case R.id.progress_all_rl://全部
                Intent allIntent = new Intent(getActivity(), OrderActivity.class);
                startActivity(allIntent);
                break;
            case R.id.pro_wait_pay_rl://待付款
                break;
            case R.id.pro_wait_use_rl://待使用
                break;
            case R.id.pro_going_rl://进行中
                break;
            case R.id.pro_done_rl://已完成
                break;
            case R.id.pro_wait_assess_rl://待评价
                break;
            case R.id.pro_after_sale__rl://退款/售后
                break;
            case R.id.my_account_balance_rl://账户余额
                break;
            case R.id.my_collect_rl://我的收藏
                break;
            case R.id.my_data_rl://个人资料
                break;
            case R.id.my_records_rl://历史业务
                break;
            case R.id.my_post_rl://邮寄地址
                break;
            default:
                break;

        }
    }

}
