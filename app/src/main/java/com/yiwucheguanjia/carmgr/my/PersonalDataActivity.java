package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.andexert.library.RippleView;
import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PersonalDataActivity extends Activity implements View.OnClickListener{
    private RippleView gobackImgBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),0);
        setContentView(R.layout.activity_personal_data);
        initView();
    }
    protected void initView(){
        gobackImgBtn = (RippleView)findViewById(R.id.personal_data_goback_rpw);
        gobackImgBtn.setOnClickListener(this);
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
