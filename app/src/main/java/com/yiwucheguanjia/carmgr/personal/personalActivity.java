package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginActivity;
import com.yiwucheguanjia.carmgr.account.view.PersonalDataActivity;

/**
 * Created by Administrator on 2016/6/23.
 */
public class personalActivity extends Activity implements View.OnClickListener{
    private ImageView headerImg;
    private TextView userName;
    private ImageView addCarImg;
    private ImageView settingImg;
    private RelativeLayout personalResidualRl;
    private RelativeLayout myCarRl;
    private RelativeLayout recordsBusiness;
    private RelativeLayout postAddress;
    private RelativeLayout accountRl;
    private RelativeLayout personalDataRl;
    private SharedPreferences sharedPreferences;
    private TextView userNameTv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        setContentView(R.layout.activity_personal);
        initView();
        setUpAccout();
        addCarImg.setOnClickListener(this);
        settingImg.setOnClickListener(this);
        accountRl.setOnClickListener(this);
    }
    private void initView(){
        addCarImg = (ImageView)findViewById(R.id.personal_car_img);
        settingImg = (ImageView)findViewById(R.id.personal_setting);
        accountRl = (RelativeLayout)findViewById(R.id.personal_account_rl);
        userName = (TextView)findViewById(R.id.personal_header_txt);
        userNameTv = (TextView)findViewById(R.id.personal_header_txt);
        personalDataRl = (RelativeLayout)findViewById(R.id.personal_data_rl);
        personalDataRl.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){
            userNameTv.setText(sharedPreferences.getString("ACCOUNT",null));
        }else if (requestCode == 2 && resultCode == 1){
            userNameTv.setText(getText(R.string.register_login));
        }
    }
    protected void setUpAccout(){
        if (sharedPreferences.getString("TOKEN",null) != null &&
                sharedPreferences.getString("ACCOUNT",null) != null){
            userNameTv.setText(sharedPreferences.getString("ACCOUNT","unknow"));
        }else {
            userNameTv.setText(getText(R.string.register_login));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_car_img:
                Intent intentUploadImage = new Intent(this, UploadImage.class);
                startActivity(intentUploadImage);
            break;
            case R.id.personal_setting:
                Intent intentSetting = new Intent(personalActivity.this,settingActivity.class);
//                startActivity(intentSetting);
                startActivityForResult(intentSetting,2);
                break;
            case R.id.personal_account_rl:
                if (sharedPreferences.getString("TOKEN",null) == null){
                    Intent loginIntent = new Intent(personalActivity.this, LoginActivity.class);
                    startActivityForResult(loginIntent,1);
                }
                break;
            case R.id.personal_data_rl:
                Intent personalDataIntent = new Intent(personalActivity.this, PersonalDataActivity.class);
                startActivity(personalDataIntent);
            default:
                break;
        }
    }
}
