package com.yiwucheguanjia.merchantcarmgr.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Administrator on 2016/11/13.
 */
public class NewActivityUtil {
    private static Activity activity;
    private static Class clas;
    public NewActivityUtil(Activity activity,Class clas) {
        this.activity = activity;
        this.clas = clas;
    }
    public static NewActivityUtil getInstance(){
        NewActivityUtil instance = null;
        if (instance == null) {
            instance = new NewActivityUtil(activity,clas);
        }
        return instance;
    }
    public void newActivity(){
        Intent newActivityIntent = new Intent(activity,clas);
        activity.startActivity(newActivityIntent);
        activity.finish();
    }
}
