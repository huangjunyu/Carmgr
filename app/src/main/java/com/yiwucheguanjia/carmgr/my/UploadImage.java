package com.yiwucheguanjia.carmgr.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.controller.ImagePickerAdapter;
import com.yiwucheguanjia.carmgr.utils.PicassoImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/5.
 */
public class UploadImage extends Activity implements View.OnClickListener {
    private ImageView uploadDriveCredImg;//行驶证
    private Button uploadDriveCredBtn;//上传行驶证
    private ImageView uploadPolicyImg;//保险单
    private Button uploadPolicyBtn;//上传保险
    private Button nextBtn;//下一步
    private RelativeLayout gobackrl;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList;
    private ImagePicker imagePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),1);
        setContentView(R.layout.activity_uploadimage);
        initView();
        gobackrl.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }

    private void initView() {
        ButterKnife.bind(this);
        selImageList = new ArrayList<>();
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setMultiMode(false);
        uploadDriveCredImg = (ImageView) findViewById(R.id.upload_drive_cred_img);
        uploadDriveCredBtn = (Button) findViewById(R.id.upload_drive_cred_btn);
        uploadPolicyImg = (ImageView) findViewById(R.id.upload_policy_img);
        uploadPolicyBtn = (Button) findViewById(R.id.upload_policy_btn);
        nextBtn = (Button) findViewById(R.id.upload_next_btn);
        gobackrl = (RelativeLayout) findViewById(R.id.addcar_goback_rl);
        uploadDriveCredBtn.setOnClickListener(this);
        uploadPolicyBtn.setOnClickListener(this);
    }

    public void uploadDriveCred() {
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setCrop(false);
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(5);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 1);
    }
    public void uploadPolicy(){
        imagePicker.setImageLoader(new PicassoImageLoader());
        imagePicker.setCrop(false);
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(5);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                imagePicker.getImageLoader().displayImage(UploadImage.this, image.path, uploadDriveCredImg, 300, 300);
            } else if (data != null && requestCode == 2){
                Log.e("no data","no data");
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
            } else{
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addcar_goback_rl:
                finish();
                break;
            case R.id.upload_drive_cred_btn:
                uploadDriveCred();
                break;
            case R.id.upload_policy_btn:
                uploadPolicy();
                break;
            case R.id.upload_next_btn:
                Intent addCarIntent = new Intent(UploadImage.this, AddCarActivity.class);
                startActivity(addCarIntent);
                break;
            default:
                break;
        }
    }
}
