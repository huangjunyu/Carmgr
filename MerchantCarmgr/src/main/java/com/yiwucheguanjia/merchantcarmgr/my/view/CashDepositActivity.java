package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/1.
 */
public class CashDepositActivity extends AppCompatActivity {
    @BindView(R.id.deposit_cash_tv)
    TextView depositCashTv;
    @BindView(R.id.deposit_recharge_Btn)
    Button rechargeTv;
    @BindView(R.id.deposit_withdraw_Btn)
    Button withdrawBtn;//提现
    @BindView(R.id.deposit_help_tv)
    TextView helpTv;
    private String depositCashStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_cash_deposit);
        ButterKnife.bind(this);
        try{
            depositCashStr = getIntent().getExtras().getString("deposit");
        }catch (Exception e){
            e.printStackTrace();
            depositCashStr = "";
        }
        depositCashTv.setText(depositCashStr);
    }

    @OnClick({R.id.deposit_goback_rl})void onClick(View view){
        switch (view.getId()){
            case R.id.deposit_goback_rl:
                finish();
                break;
            case R.id.deposit_cash_tv:
                break;
            case R.id.deposit_recharge_Btn:
                break;
            case R.id.deposit_withdraw_Btn:
                break;
            case R.id.deposit_help_tv:
                break;
            default:
                break;
        }
    }
}
