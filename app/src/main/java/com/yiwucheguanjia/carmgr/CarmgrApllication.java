package com.yiwucheguanjia.carmgr;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.socialize.PlatformConfig;
import com.yiwucheguanjia.carmgr.city.db.DBManager;
import com.yiwucheguanjia.carmgr.utils.PicassoImageLoader;
import com.yiwucheguanjia.carmgr.welcome.WelcomActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/31.
 */
public class CarmgrApllication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        dbManager = new DBManager(getApplicationContext());
        dbManager.openDatabase();
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
//        OkHttpUtils.getInstance().getOkHttpClient().cache();
    }
    {

        PlatformConfig.setQQZone("1105629466", "fYspq5Wgo6FnrWgW");
    }

    private DBManager dbManager;

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.closeDatabase();
    }

}
