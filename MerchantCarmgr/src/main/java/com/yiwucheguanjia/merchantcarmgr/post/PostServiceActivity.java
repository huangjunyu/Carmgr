package com.yiwucheguanjia.merchantcarmgr.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.my.controller.ImagePickerAdapter;
import com.yiwucheguanjia.merchantcarmgr.utils.GlideImageLoader;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/19.
 */
public class PostServiceActivity extends Activity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{

    private final static int RESULT_SELECTED = 0;
    private final static int RESULT_NOTHING_SELECT = 1;
    private final static int REQUEST_ZERO = 0;
    private final static int REQUEST_ONE = 1;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.post_sc_goback)
    RelativeLayout postRl;
    @BindView(R.id.post_sc_title)
    EditText titleEd;
    @BindView(R.id.post_sc_content)
    EditText contentEd;
    @BindView(R.id.post_sc_price_ed)
    EditText priceEd;
    @BindView(R.id.post_sc_service_type)
    TextView serviceType;
    @BindView(R.id.post_sc_service_scope)
    TextView serviceScope;
    @BindView(R.id.post_sc_post_btn)
    Button submitBtn;
    @BindView(R.id.post_sc_service_tv)
    TextView serviceTypeSelTv;
    @BindView(R.id.post_sc_sc_scope_ed)
    EditText serviceLimitsEd;
    private ImagePicker imagePicker;
    private int maxImgCount = 8;               //允许选择图片最大数
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> imageItems;
    FileInputStream fileInputStream = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service);
        ButterKnife.bind(this);
        //最好放到 Application oncreate执行
        initImagePicker();
        initWidget();
    }

    @OnClick({R.id.post_sc_goback, R.id.post_sc_post_btn, R.id.post_sc_service_tv})
    void onClickView(View view) {

        switch (view.getId()) {
            case R.id.post_sc_goback:
                finish();
                break;
            case R.id.post_sc_title:
                break;
            case R.id.post_sc_content:
                break;
            case R.id.post_sc_price_ed:
                break;
            case R.id.post_sc_service_tv:
                Intent serviceTypeIntent = new Intent(PostServiceActivity.this, ServiceTypeActivity.class);
                startActivityForResult(serviceTypeIntent, 0);
                break;
            case R.id.post_sc_service_scope:
                break;
            case R.id.post_sc_post_btn:
//                ArrayList<File> files = new ArrayList<>();
//                if (imageItems != null && imageItems.size() > 0) {
//                    for (int i = 0; i < imageItems.size(); i++) {
//                        files.add(new File(imageItems.get(i).path));
//                    }
//                }
//
//                if (checkData()) {
//                    OkGo.post(UrlString.POST_SERVICE_URL)
//                            .tag(this)
//                            .params("username", "")
//                            .params("name", titleEd.getText().toString().trim())
//                            .params("detail", contentEd.getText().toString().trim())
//                            .params("price", priceEd.getText().toString().trim())
//                            .params("type", serviceType.getText().toString().trim())
//                            .params("scope", serviceScope.getText().toString().trim())
//                    .params("imgpath","")
//                    .params("token","")
//                    .params("version","")
//                    ;
//                }
//                finish();
                formUpload();
                break;
            default:
                break;
        }
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.service_img_rv);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ZERO && resultCode == RESULT_SELECTED) {
            if (data.getExtras().getString("serviceType") != null) {
                serviceTypeSelTv.setText(data.getExtras().getString("serviceType").toString());

            }
        }else if (requestCode == REQUEST_ZERO && resultCode == RESULT_NOTHING_SELECT){
            //没有选择服务类型
            Log.e("nnw","kkk");
        }

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(imageItems);
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
    String inputStream2String(InputStream is){
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public void formUpload() {
        ArrayList<File> files = new ArrayList<>();
        File file = null;
        InputStream inputStream = null;
        byte[] data = null;
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
            file = files.get(0);
            String fileString = file.toString();
            Log.e("big",file.length() + "," + fileString);



            try {
                fileInputStream = new FileInputStream(file);
                Log.e("fileInputStream",fileInputStream.toString());
                fileInputStream.available();
                data = new byte[fileInputStream.available()];
                inputStream.read(data);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //拼接参数
        OkGo.post("http://112.74.13.51:8080/carmgr/mappfileupload")//
                .tag(this)//
//                .headers("header1", "headerValue1")//
//                .headers("header2", "headerValue2")//
//                .params("resource_file_name", "paramValue1")//
//                .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .addFileParams("filedata;", files)           // 这种方式为同一个key，上传多个文件
                .params("filedata",fileInputStream.toString())
                .execute(
//                        new JsonCallback<LzyResponse<ServerModel>>() {
//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        super.onBefore(request);
//                        btnFormUpload.setText("正在上传中...");
//                    }
//
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> responseData, Call call, Response response) {
//                        handleResponse(responseData.data, call, response);
//                        Log.e("hhhh", responseData.msg + "," + responseData.data + "," + responseData.code + "");
//                        btnFormUpload.setText("上传完成11");
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        handleError(call, response);
//                        Log.e("eeee", e.toString());
//                        btnFormUpload.setText("上传出错");
//                    }
//
//                    @Override
//                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                        System.out.println("upProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
//
//                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), currentSize);
//                        String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
//                        tvDownloadSize.setText(downloadLength + "/" + totalLength);
//                        String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
//                        tvNetSpeed.setText(netSpeed + "/S");
//                        tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
//                        pbProgress.setMax(100);
//                        pbProgress.setProgress((int) (progress * 100));
//                    }
//                }
                        new MyStringCallback(PostServiceActivity.this,"test") {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("string",s);
                            }

                        }
                );
    }

    private Boolean checkData() {
        if (!TextUtils.isEmpty(titleEd.getText().toString())
                && !TextUtils.isEmpty(contentEd.getText().toString().trim())
                && !TextUtils.isEmpty(priceEd.getText().toString().trim())
                && !TextUtils.isEmpty(serviceTypeSelTv.getText().toString())
                && !TextUtils.isEmpty(serviceTypeSelTv.getText().toString())
                && !TextUtils.isEmpty(serviceScope.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                Log.e("kwq","jwnw");
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
}
