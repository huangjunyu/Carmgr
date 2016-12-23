package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;

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
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
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
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            if (action.equals("action.post_manage")) {//
//            } else if (TextUtils.equals(action,"action.loginout")) {//退出此界面以及所管理的fragment
//                HomeActivty.this.finish();
//            } else if (action.equals("action.appointment")) {//跳到预约界面
//            }
        }
    };
}
