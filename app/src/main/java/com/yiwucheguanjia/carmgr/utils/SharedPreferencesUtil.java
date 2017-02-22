package com.yiwucheguanjia.carmgr.utils;

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

    SharedPreferencesUtil(Activity activity) {
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("CARMGR", activity.MODE_PRIVATE);
    }

    public String usernameSharedPreferences() {
        return sharedPreferences.getString("ACCOUNT", null);
    }

    public String tokenSharedPreference() {
        return sharedPreferences.getString("TOKEN", null);
    }
    public float longitudeSharedPreferences(){
        return sharedPreferences.getFloat("longitude",23.125178f);
    }
    public float latitudeSharedPreferences(){
        return sharedPreferences.getFloat("latitude",113.280637f);
    }
}
