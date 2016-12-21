package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;

/**
 * 添加车辆信息
 */
public class AddCarActivity extends Activity implements View.OnClickListener{
    private RippleView gobackImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),0);
        setContentView(R.layout.activity_addcar);
        initView();
    }
    private void initView(){
        gobackImg = (RippleView) findViewById(R.id.addcar_goback_rpw);
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
            case R.id.addcar_goback_rpw:
                this.finish();
        }
    }
}
