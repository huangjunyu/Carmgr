package com.yiwucheguanjia.merchantcarmgr.city.utils;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by Administrator on 2016/9/27.
 */
public class LocationPosition {
//    public volatile static Tools tools;
//    public static Tools getInstance(){
//        Tools instance = null;
//        if (tools == null){
//            if (instance == null){
//                instance = new Tools();
//                tools = instance;
//            }
//        }
//        return instance;
//    }
    public volatile static LocationPosition locationPosition;
    public static LocationPosition getInstance(){
        LocationPosition instance = null;
        if (locationPosition == null){
            if (instance == null){
                instance = new LocationPosition();
            }
        return instance;
        }else
            return locationPosition;
    }
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.e("citylo",bdLocation.getCity());
        }
    }
}
