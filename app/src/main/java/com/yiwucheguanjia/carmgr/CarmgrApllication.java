package com.yiwucheguanjia.carmgr;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yiwucheguanjia.carmgr.city.db.DBManager;
import com.yiwucheguanjia.carmgr.welcome.WelcomActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/31.
 */
public class CarmgrApllication extends Application {
    private SharedPreferences sharedPreferences;

    public ArrayList<String> getEara() {
        return eara;
    }

    public void setEara(ArrayList<String> eara) {
        this.eara = eara;
    }

    private ArrayList<String> eara;
    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    private String positionStr;
    @Override
    public void onCreate() {
        super.onCreate();
        dbManager = new DBManager(getApplicationContext());
        dbManager.openDatabase();
    }

    private DBManager dbManager;

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.closeDatabase();
    }

}
