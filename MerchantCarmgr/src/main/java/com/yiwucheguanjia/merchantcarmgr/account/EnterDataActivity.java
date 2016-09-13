package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/13.
 */
public class EnterDataActivity extends AppCompatActivity {
    @BindView(R.id.enter_data_goback_rl)
    RelativeLayout gobackRl;
    @BindView(R.id.enter_data_name_edit)
    EditText nameEdit;
    @BindView(R.id.enter_data_id_car_ed)
    EditText idNum;
    @BindView(R.id.enter_data_next_btn)
    Button nextBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.enter_data_goback_rl)void goback(){
        finish();
    }
    @OnClick(R.id.enter_data_next_btn) void nextStep(){
        Intent intent = new Intent(EnterDataActivity.this,EnterMerchantDataActivity.class);
        startActivity(intent);
    }

}
