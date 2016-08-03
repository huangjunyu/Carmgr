package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class settingActivity extends Activity implements View.OnClickListener{
    private RelativeLayout exitRl;
    private ImageButton gobackImgBtn;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.setting);
        initView();
    }
    private void initView(){
        exitRl = (RelativeLayout)findViewById(R.id.setting_exit_rl);
        gobackImgBtn = (ImageButton)findViewById(R.id.setting_goback_imgbtn);
        gobackImgBtn.setOnClickListener(this);
        exitRl.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_goback_imgbtn:
                this.finish();
                break;
            case R.id.setting_exit_rl:
                editor.remove("TOKEN");
                editor.remove("ACCOUNT");
                editor.remove("PASSWORD");
                editor.commit();
                setResult(1);
                getCallingActivity();
                finish();
                break;
            default:
                break;
        }
    }
}
