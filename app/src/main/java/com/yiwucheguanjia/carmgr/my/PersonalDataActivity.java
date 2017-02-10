package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PersonalDataActivity extends Activity implements View.OnClickListener{
    private RelativeLayout gobackRl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),70);
        setContentView(R.layout.activity_personal_data);
        initView();
    }
    protected void initView(){
        gobackRl = (RelativeLayout)findViewById(R.id.personal_data_goback_rpw);
        gobackRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_data_goback_rpw:
                finish();
                break;
            default:
                break;
        }
    }
}
