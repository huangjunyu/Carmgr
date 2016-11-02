package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/1.
 */
public class CashDepositActivity extends Activity {
    @BindView(R.id.deposit_goback_imgbtn)
    ImageButton gobackImgBtn;
    @BindView(R.id.deposit_cash_tv)
    TextView depositCashTv;
    @BindView(R.id.deposit_recharge_Btn)
    Button rechargeTv;
    @BindView(R.id.deposit_withdraw_Btn)
    Button withdrawBtn;//提现
    @BindView(R.id.deposit_help_tv)
    TextView helpTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_deposit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.deposit_goback_imgbtn})void onClick(View view){
        switch (view.getId()){
            case R.id.deposit_goback_imgbtn:
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
