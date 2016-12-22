package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/2.
 */
public class AccountBalanceActivity extends Activity {

    private String intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);
        ButterKnife.bind(this);
        intent = getIntent().getExtras().getString("balance");

    }

    @OnClick({R.id.balance_goback_rl,R.id.balance_income_detail,R.id.balance_pay_manager,
    R.id.bank_car_Btn,R.id.balance_withdraw_Btn})void onClick(View view){
        switch (view.getId()){
            case R.id.balance_goback_rl:
                finish();
                break;
            case R.id.balance_income_detail:
                break;
            case R.id.balance_pay_manager:
                break;
            case R.id.bank_car_Btn:
                break;
            case R.id.balance_withdraw_Btn:
                break;

            default:
                break;
        }
    }
}
