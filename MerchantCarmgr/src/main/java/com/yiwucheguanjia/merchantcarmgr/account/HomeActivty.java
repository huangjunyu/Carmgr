package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/11.
 */
public class HomeActivty extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @BindView(R.id.home_login_btn)
    Button loginBtn;
    @BindView(R.id.home_enter_btn)
    Button enterBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        setContentView(R.layout.activity_first_page);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.home_login_btn) void login(){
        Intent intent = new Intent(HomeActivty.this,LoginActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.home_enter_btn) void enter(){
        Intent intent = new Intent(HomeActivty.this,EnterRegisterActivity.class);
        startActivity(intent);
    }

    private void firstView(){
        if (sharedPreferences.getString("VERSION", null) == null) {
//            setContentView(R.layout.guide);
            //入驻后将入驻信息保存，再次打开APP的时候，判断是否入驻显示该界面，入驻但未登录则转到登录界面
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("VERSION", UrlString.APP_VERSION);
            editor.commit();
        } else {

        }
    }
}
