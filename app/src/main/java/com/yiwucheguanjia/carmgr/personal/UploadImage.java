package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/5.
 */
public class UploadImage extends Activity implements View.OnClickListener{
    private ImageView uploadDriveCredImg;//行驶证
    private Button uploadDriveCredBtn;//上传行驶证
    private ImageView uploadPolicyImg;//保险单
    private Button uploadPolicyBtn;//上传保险
    private Button nextBtn;//下一步
    private ImageButton goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimage);
        initView();
        goback.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }
    private void initView(){
        uploadDriveCredImg = (ImageView) findViewById(R.id.upload_drive_cred_img);
        uploadDriveCredBtn = (Button)findViewById(R.id.upload_drive_cred_btn);
        uploadPolicyImg = (ImageView)findViewById(R.id.upload_policy_img);
        uploadPolicyBtn = (Button)findViewById(R.id.upload_policy_btn);
        nextBtn = (Button)findViewById(R.id.upload_next_btn);
        goback = (ImageButton)findViewById(R.id.upload_goback_imgbtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_goback_imgbtn:
                finish();
                break;
            case R.id.upload_drive_cred_btn:
                break;
            case R.id.upload_policy_btn:
                break;
            case R.id.upload_next_btn:
                Intent addCarIntent = new Intent(UploadImage.this,AddCar.class);
                startActivity(addCarIntent);
                break;
            default:
                break;
        }
    }
}
