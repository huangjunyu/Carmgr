package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 需要添加一个广播，当登录或入驻后，将此界面关闭
 */
public class HomeActivty extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_first_page);
        ButterKnife.bind(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.loginout");
        intentFilter.addAction("action.enter_success");
        intentFilter.addAction("login_success");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }

    @OnClick({R.id.home_login_btn, R.id.home_enter_btn})
    void login(View view) {
        switch (view.getId()) {
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

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, "action.loginout")) {//退出此界面以及所管理的fragment
            } else if (TextUtils.equals(action,"action.enter_success")){
                finish();
            } else if (TextUtils.equals(action,"login_success")){
                finish();
            }
        }
    };
}
