package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginBaseFragmentActivity;

/**
 * Created by Administrator on 2016/7/13.
 */
public class SettingActivity extends Activity implements View.OnClickListener{
    private RelativeLayout exitRl;
    private RelativeLayout gobackRl;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),50);
        setContentView(R.layout.setting);
        initView();
    }
    private void initView(){
        exitRl = (RelativeLayout) findViewById(R.id.setting_exit_rl);
        gobackRl = (RelativeLayout) findViewById(R.id.setting_goback_rl);
        gobackRl.setOnClickListener(this);
        exitRl.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_goback_rl:
                this.finish();
                break;
            case R.id.setting_exit_rl:
                editor.remove("TOKEN");
                editor.remove("ACCOUNT");
                editor.commit();
                Intent intent = new Intent();
                intent.setAction("action.loginout");
                sendBroadcast(intent);
                Intent loginInten = new Intent(SettingActivity.this, LoginBaseFragmentActivity.class);
                startActivity(loginInten);
                finish();
                break;
            default:
                break;
        }
    }
}
