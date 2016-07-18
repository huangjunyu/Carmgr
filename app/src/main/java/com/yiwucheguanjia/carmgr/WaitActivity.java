package com.yiwucheguanjia.carmgr;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/7/17.
 */
public class WaitActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_wait);
    }
}
