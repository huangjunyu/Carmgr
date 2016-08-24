package com.yiwucheguanjia.carmgr.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.andexert.library.RippleView;
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
    private RippleView gobackrpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_uploadimage);
        initView();
        gobackrpw.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }
    private void initView(){
        uploadDriveCredImg = (ImageView) findViewById(R.id.upload_drive_cred_img);
        uploadDriveCredBtn = (Button)findViewById(R.id.upload_drive_cred_btn);
        uploadPolicyImg = (ImageView)findViewById(R.id.upload_policy_img);
        uploadPolicyBtn = (Button)findViewById(R.id.upload_policy_btn);
        nextBtn = (Button)findViewById(R.id.upload_next_btn);
        gobackrpw = (RippleView)findViewById(R.id.addcar_goback_rpw);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addcar_goback_rpw:
                finish();
                break;
            case R.id.upload_drive_cred_btn:
                break;
            case R.id.upload_policy_btn:
                break;
            case R.id.upload_next_btn:
                Intent addCarIntent = new Intent(UploadImage.this,AddCarActivity.class);
                startActivity(addCarIntent);
                break;
            default:
                break;
        }
    }
}
