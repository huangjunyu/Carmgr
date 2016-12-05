package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yiwucheguanjia.carmgr.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/2.
 */
public class AccountBalanceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.balance_goback_rl,R.id.balance_income_detail,R.id.balance_pay_manager,
    R.id.balance_bank_car_Btn,R.id.balance_withdraw_Btn})void onClick(View view){
        switch (view.getId()){
            case R.id.balance_goback_rl:
                finish();
                break;
            case R.id.balance_income_detail:
                break;
            case R.id.balance_pay_manager:
                break;
            case R.id.balance_bank_car_Btn:
                break;
            case R.id.balance_withdraw_Btn:
                break;

            default:
                break;
        }
    }
}
