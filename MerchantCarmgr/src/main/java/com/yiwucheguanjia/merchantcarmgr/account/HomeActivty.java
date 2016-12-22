package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 需要添加一个广播，当登录或入驻后，将此界面关闭
 */
public class HomeActivty extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
//    @BindView(R.id.home_login_btn)
//    Button loginBtn;
//    @BindView(R.id.home_enter_btn)
//    Button enterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        setContentView(R.layout.activity_first_page);
        findViewById(R.id.home_login_btn).setOnClickListener(this);
        findViewById(R.id.home_enter_btn).setOnClickListener(this);
//        ButterKnife.bind(this);
        firstView();
    }

//    @OnClick({R.id.home_login_btn, R.id.enter_enter_btn})
//    void login(View view) {
//        switch (view.getId()) {
//            case R.id.home_login_btn:
//                Intent loginIntent = new Intent(HomeActivty.this, LoginActivity.class);
//                startActivity(loginIntent);
//                break;
//            case R.id.enter_enter_btn:
//                Intent enterRegisterIntent = new Intent(HomeActivty.this, EnterRegisterActivity.class);
//                startActivity(enterRegisterIntent);
//                break;
//            default:
//                break;
//        }
//
//    }

    private void firstView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_login_btn:
                Intent loginIntent = new Intent(HomeActivty.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.home_enter_btn:
                Intent enterRegisterIntent = new Intent(HomeActivty.this, EnterRegisterActivity.class);
                startActivity(enterRegisterIntent);
                break;
            default:
                break;
        }
    }
}
