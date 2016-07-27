package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class setting extends Activity implements View.OnClickListener{
    private ImageView goback;
    private RelativeLayout exitRl;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_WORLD_READABLE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.setting);
        initView();
    }
    private void initView(){
        goback = (ImageView)findViewById(R.id.setting_goback_imgbtn);
        exitRl = (RelativeLayout)findViewById(R.id.setting_exit_rl);
        exitRl.setOnClickListener(this);
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
            case R.id.setting_exit_rl:
                editor.remove("TOKEN");
                editor.remove("ACCOUNT");
                editor.remove("PASSWORD");
                editor.commit();
                break;
            default:
                break;
        }
    }
}
