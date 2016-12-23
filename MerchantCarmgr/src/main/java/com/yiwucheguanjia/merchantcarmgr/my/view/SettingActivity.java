package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.account.HomeActivty;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/20.
 */
public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_exit)
    Button settingBn;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT",MODE_PRIVATE);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.setting_goback_rl,R.id.setting_exit})
    void click(View view){
        switch (view.getId()){
            case R.id.goback_imgbtn:
                break;
            case R.id.setting_exit:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("TOKEN");
                editor.commit();
                Intent intent = new Intent();
                intent.setAction("action.loginout");
                sendBroadcast(intent);
                Intent loginInten = new Intent(SettingActivity.this, HomeActivty.class);
                startActivity(loginInten);
                finish();
                break;
            default:
                break;
        }
    }
}
