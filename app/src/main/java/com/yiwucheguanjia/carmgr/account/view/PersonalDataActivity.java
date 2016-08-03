package com.yiwucheguanjia.carmgr.account.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PersonalDataActivity extends Activity implements View.OnClickListener{
    private ImageButton gobackImgBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_personal_data);
        initView();
    }
    protected void initView(){
        gobackImgBtn = (ImageButton)findViewById(R.id.personal_data_goback);
        gobackImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_data_goback:
                finish();
                break;
            default:
                break;
        }
    }
}
