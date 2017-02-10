package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.yiwucheguanjia.merchantcarmgr.R;

/**
 * Created by Administrator on 2016/10/21.
 */

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.my.controller.ImagePickerAdapter;
import com.yiwucheguanjia.merchantcarmgr.utils.GlideImageLoader;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MerchantPhotoActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    private SharedPreferences sharedPreferences;
    @BindView(R.id.merchant_pho_submit_btn)
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.merchant_photo_activity);
        ButterKnife.bind(this);
        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                Log.e("kwq", "jwnw");
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @OnClick({R.id.merchant_pho_submit_btn,R.id.setting_goback_rl})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.merchant_pho_submit_btn:
                formUpload();
                break;
            case R.id.setting_goback_rl:
                finish();
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    public void formUpload() {
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        ArrayList<File> files = new ArrayList<>();
        File file = null;
        InputStream inputStream = null;
        byte[] data = null;
        if (selImageList != null && selImageList.size() > 0) {
            for (int i = 0; i < selImageList.size(); i++) {
                files.add(new File(selImageList.get(i).path));
            }
            file = files.get(0);
            String fileString = file.toString();
            Log.e("big", file.length() + "," + fileString);

            try {
//                fileInputStream = new FileInputStream(file);
//                Log.e("fileInputStream", fileInputStream.toString());
//                fileInputStream.available();
//                data = new byte[fileInputStream.available()];
//                inputStream.read(data);
//                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {//如果没有图片，则不能上传
            Toast.makeText(MerchantPhotoActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        //拼接参数
        PostRequest okGo = OkGo.post(UrlString.APP_UPLOAD)//
                .tag(this);//
        okGo.params("username", "13560102795")
                .params("type", "merchants_introduce_img")
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", UrlString.APP_VERSION)
                .params("file_count", files.size());
        for (int i = 0; i < files.size(); i++) {
            okGo.params("file" + (i + 1), files.get(i));
        }
        okGo.execute(
                new MyStringCallback(MerchantPhotoActivity.this, "test") {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("string", s);
                        String imgPaths = "";//所有图片路径
                        try {
                            final JSONObject jsonObject = new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );
    }
}

