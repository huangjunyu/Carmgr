package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class setting extends Activity implements View.OnClickListener{
    private ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initView();
    }
    private void initView(){
        goback = (ImageView)findViewById(R.id.setting_goback_imgbtn);
        goback.setOnClickListener(this);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_goback_imgbtn:
                this.finish();
                break;
            default:
                break;
        }
    }
}
