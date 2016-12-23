package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/2.
 */
public class AccountBalanceActivity extends AppCompatActivity {
    @BindView(R.id.balance_balance_tv)
    TextView depositTv;

    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_account_balance);
        ButterKnife.bind(this);
        try {
            balance = getIntent().getExtras().getString("balance");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exception", e.toString());
            balance = "";
        }
        depositTv.setText(balance);
    }

    @OnClick({R.id.balance_goback_rl, R.id.balance_income_detail, R.id.balance_pay_manager,
            R.id.bank_car_Btn, R.id.balance_withdraw_Btn})
    void onClick(View view) {
        switch (view.getId()) {
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
