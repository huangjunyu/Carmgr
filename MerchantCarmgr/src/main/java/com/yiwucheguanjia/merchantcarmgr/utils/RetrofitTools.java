package com.yiwucheguanjia.merchantcarmgr.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/11.
 */
public class RetrofitTools {
    public volatile static RetrofitTools tools;
    public static RetrofitTools getInstance(){
        RetrofitTools instance = null;
        if (tools == null){
            if (instance == null){
                instance = new RetrofitTools();
                tools = instance;
            }
        }
        return instance;
    }
    public Retrofit retrofit(){

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://112.74.13.51:8080/carmgr/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
        return retrofit;
    }
}
