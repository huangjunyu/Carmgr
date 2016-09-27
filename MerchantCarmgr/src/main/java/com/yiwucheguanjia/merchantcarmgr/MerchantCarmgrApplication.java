package com.yiwucheguanjia.merchantcarmgr;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.yiwucheguanjia.merchantcarmgr.city.db.DBManager;
import com.yiwucheguanjia.merchantcarmgr.city.utils.GrideImageLoader;

/**
 * Created by Administrator on 2016/9/11.
 */
public class MerchantCarmgrApplication extends Application {
    private DBManager dbManager;
    //定位相关

    @Override
    public void onCreate() {
        super.onCreate();
        dbManager = new DBManager(getApplicationContext());
        dbManager.openDatabase();
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GrideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.closeDatabase();
    }


}
