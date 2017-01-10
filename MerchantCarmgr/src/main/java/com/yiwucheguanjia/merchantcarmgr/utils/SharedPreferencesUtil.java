package com.yiwucheguanjia.merchantcarmgr.utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/27.
 */
public class SharedPreferencesUtil {
    public SharedPreferences sharedPreferences;
    public Activity activity;
    public volatile static SharedPreferencesUtil sharedPreferencesUtil;

    public static SharedPreferencesUtil getInstance(Activity activity) {
        SharedPreferencesUtil instance = null;
        if (instance == null) {
            if (instance == null) {
                instance = new SharedPreferencesUtil(activity);
                sharedPreferencesUtil = instance;
            }
        }
        return sharedPreferencesUtil;
    }

//    public volatile static Tools tools;
//    public static Tools getInstance(){
//        Tools instance = null;
//        if (tools == null){
//            if (instance == null){
//                instance = new Tools();
//                tools = instance;
//            }
//        }
//        return instance;
//    }

    SharedPreferencesUtil(Activity activity) {
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("CARMGR_MERCHANT", activity.MODE_PRIVATE);
    }

    public String usernameSharedPreferences() {
        return sharedPreferences.getString("ACCOUNT", null);
//        return null;
    }

    public String tokenSharedPreference() {
        return sharedPreferences.getString("TOKEN", null);
    }

    ;
}
