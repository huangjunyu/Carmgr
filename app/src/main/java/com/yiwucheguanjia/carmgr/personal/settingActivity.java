package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;

import com.andexert.library.RippleView;
import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginActivity;

/**
 * Created by Administrator on 2016/7/13.
 */
public class settingActivity extends Activity implements View.OnClickListener{
    private RippleView exitRpw;
    private RippleView gobackRpw;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),10);
        setContentView(R.layout.setting);
        initView();
    }
    private void initView(){
        exitRpw = (RippleView) findViewById(R.id.setting_exit_rl);
        gobackRpw = (RippleView) findViewById(R.id.setting_goback_rpv);
        gobackRpw.setOnClickListener(this);
        exitRpw.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_goback_rpv:
                this.finish();
                break;
            case R.id.setting_exit_rl:
                editor.remove("TOKEN");
                editor.remove("ACCOUNT");
                editor.commit();
//                setResult(1);
//                getCallingActivity();
                Intent intent = new Intent();
                intent.setAction("action.loginout");
                sendBroadcast(intent);
                Intent loginInten = new Intent(settingActivity.this, LoginActivity.class);
                startActivity(loginInten);
                finish();
                break;
            default:
                break;
        }
    }
}
