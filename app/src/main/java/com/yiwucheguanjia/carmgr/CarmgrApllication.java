package com.yiwucheguanjia.carmgr;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yiwucheguanjia.carmgr.city.db.DBManager;
import com.yiwucheguanjia.carmgr.welcome.WelcomActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/31.
 */
public class CarmgrApllication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        dbManager = new DBManager(getApplicationContext());
        dbManager.openDatabase();
//        OkHttpUtils.getInstance().getOkHttpClient().cache();
    }

    private DBManager dbManager;

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.closeDatabase();
    }

}
