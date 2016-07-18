package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/6/23.
 */
public class personalActivity extends Activity implements View.OnClickListener{
    private ImageView headerImg;
    private TextView userName;
    private ImageView addCarImg;
    private ImageView settingImg;
    private RelativeLayout personalResidualRl;
    private RelativeLayout personalDataRl;
    private RelativeLayout myCarRl;
    private RelativeLayout recordsBusiness;
    private RelativeLayout postAddress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        addCarImg.setOnClickListener(this);
        settingImg.setOnClickListener(this);
    }
    private void initView(){
        addCarImg = (ImageView)findViewById(R.id.personal_car_img);
        settingImg = (ImageView)findViewById(R.id.personal_setting);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_car_img:
                Intent intentUploadImage = new Intent(this, UploadImage.class);
                startActivity(intentUploadImage);
            break;
            case R.id.personal_setting:
                Intent intentSetting = new Intent(personalActivity.this,setting.class);
                startActivity(intentSetting);
                break;
            default:
                break;
        }
    }
}
