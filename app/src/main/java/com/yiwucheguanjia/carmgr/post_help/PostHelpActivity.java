package com.yiwucheguanjia.carmgr.post_help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.controller.ImagePickerAdapter;
import com.yiwucheguanjia.carmgr.utils.GlideImageLoader;
import com.yiwucheguanjia.carmgr.utils.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostHelpActivity extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{
    @BindView(R.id.post_help_img_rv)
    RecyclerView recyclerView;
    @BindView(R.id.post_help_goback_rl)
    RelativeLayout gobackRl;
    @BindView(R.id.post_help_want_et)
    EditText iWantEt;
    @BindView(R.id.post_help_descr_et)
    EditText descripEt;
    @BindView(R.id.post_help_reward_et)
    EditText rewardEt;
    @BindView(R.id.post_help_position_rl)
    RelativeLayout positionRl;
    @BindView(R.id.post_help_position_tv)
    TextView positionTv;
    @BindView(R.id.post_help_type_rl)
    RelativeLayout helpTypeRl;
    @BindView(R.id.post_help_type_tv)
    TextView helpTypeTv;
    @BindView(R.id.post_help_timeup_rl)
    RelativeLayout timeupRl;
    @BindView(R.id.post_help_timeup_tv)
    TextView timeupTv;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private ArrayList<ImageItem> imageItems;
    private final static int maxImgCount = 8;               //允许选择图片最大数
    private final static int RESULT_SELECTED = 0;
    private final static int RESULT_NOTHING_SELECT = 1;
    private final static int REQUEST_ZERO = 0;
    private final static int REQUEST_ONE = 1;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<String> textViewArrayList;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private Boolean checkData(){
        textViewArrayList.clear();
        textViewArrayList.add(iWantEt.getText().toString().trim());
        textViewArrayList.add(descripEt.getText().toString().trim());
        textViewArrayList.add(rewardEt.getText().toString().trim());
        textViewArrayList.add(positionTv.getText().toString().trim());
        textViewArrayList.add(helpTypeTv.getText().toString().trim());
        textViewArrayList.add(timeupTv.getText().toString().trim());
        for (int i = 0;i < textViewArrayList.size();i++){
            if (TextUtils.isEmpty(textViewArrayList.get(i))){
                dialog(getResources().getText(R.string.hint).toString(),getResources().getText(R.string.finish_post_data).toString(),getResources().getText(R.string.ok).toString());
                return false;
            };
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_post_help);
        ButterKnife.bind(this);
        initImagePicker();
        initData();
        initLocation();
        startLocation();
    }
    private void initData(){
        selImageList = new ArrayList<>();
        textViewArrayList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }
    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                positionTv.setText(loc.getAddress());
            } else {
                positionTv.setText(getResources().getString(R.string.position_fail));
            }
        }
    };
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1000);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1000);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setMultiMode(true);
    }
    public void dialog(String title, String content, String checked){
        AlertDialog.Builder builder = new AlertDialog.Builder(PostHelpActivity.this);
        builder.setTitle(title).setMessage(content).setNegativeButton(checked, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == REQUEST_ZERO && resultCode == RESULT_NOTHING_SELECT) {
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
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

    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                break;
        }
    }
    @OnClick({R.id.post_help_goback_rl,R.id.post_help_post_bn,R.id.post_help_type_rl})
    void click(View view){
        switch (view.getId()){
            case R.id.post_help_goback_rl:
                finish();
                break;
            case R.id.post_help_post_bn:
                checkData();
                break;
            case R.id.post_help_type_rl:
                Intent helpTypeIntent = new Intent(PostHelpActivity.this,HelpTypeActivity.class);
                startActivity(helpTypeIntent);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }
    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        locationOption.setOnceLocation(true);
        locationOption.setOnceLocationLatest(true);
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        locationOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
        //根据控件的选择，重新设置定位参数
//        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
