package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import com.yiwucheguanjia.merchantcarmgr.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/11.
 */
public class HomeActivty extends AppCompatActivity {
    @BindView(R.id.home_login_btn)
    Button loginBtn;
    @BindView(R.id.home_enter_btn)
    Button enterBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }
    @OnClick(R.id.home_login_btn) void login(){
        Intent intent = new Intent(HomeActivty.this,LoginActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.home_enter_btn) void enter(){
        Intent intent = new Intent(HomeActivty.this,EnterActivity.class);
        startActivity(intent);
    }
}
