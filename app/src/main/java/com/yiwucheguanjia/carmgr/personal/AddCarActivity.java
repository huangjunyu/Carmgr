package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yiwucheguanjia.carmgr.R;

/**
 * 添加车辆信息
 */
public class AddCarActivity extends Activity implements View.OnClickListener{
    private ImageView gobackImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_addcar);
        initView();
    }
    private void initView(){
        gobackImg = (ImageView) findViewById(R.id.addcar_goback_imgbtn);
        gobackImg.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addcar_goback_imgbtn:
                this.finish();
        }
    }
}
