package com.yiwucheguanjia.carmgr;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yiwucheguanjia.carmgr.welcome.WelcomActivity;

/**
 * Created by Administrator on 2016/7/31.
 */
public class CarmgrApllication extends Application {
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        isFirsLaunch();
    }
    protected void isFirsLaunch() {
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        //如果版本号为null，则跳到欢迎界面
        if (sharedPreferences.getString("version", null) == null) {
//            Intent welcomeInten = new Intent(CarmgrApllication.this, WelcomActivity.class);
//            this.startActivity(welcomeInten);
        }
    }

}
