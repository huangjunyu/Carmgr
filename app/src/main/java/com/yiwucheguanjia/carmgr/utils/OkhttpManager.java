package com.yiwucheguanjia.carmgr.utils;

import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.util.Log;

import java.io.IOException;
import java.net.HttpRetryException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/5.
 */
public class OkhttpManager {
    public static final int ok_error = -1;
    public static final int ok_success = 1;
    private volatile static OkhttpManager okhttpManager;
    public static OkhttpManager getInstance(){
        OkhttpManager instance = null;
        if (okhttpManager == null){
//            synchronized (OkhttpManager.class){
                if (instance == null){
                    instance = new OkhttpManager();
                    okhttpManager = null;
//                }
            }
        }
        return instance;
    }

    /**
     * Post请求的封装
     */
    public static void OKdoPost(String url, final Handler handler,
                                FormBody formBody) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
//            handler.sendEmptyMessage(ok_error);


            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(ok_error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Message msg = new Message();
                msg.what = ok_success;
                msg.obj = resultStr;
                handler.sendMessage(msg);
            }
//
//            @Override
//            public void onFailure(Request arg0, IOException arg1) {
//                handler.sendEmptyMessage(ok_error);
//            }
//
//            @Override
//            public void onResponse(Response arg0) throws IOException {
//                String resultStr = arg0.body().string();
//                Message msg = new Message();
//                msg.what = ok_success;
//                msg.obj = resultStr;
//                handler.sendMessage(msg);
//            }
        });
    }

    /**
     * Post请求的封装
     */
    public static void OKhttpPost(String url, final Handler handler,
                                        final FormBody formBody, final int success, final int error) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(formBody)
                .build();
//        Log.e("test",formBody.encodedValue(3));
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                Message msg = new Message();
                msg.what = success;
                msg.obj = resultStr;
                handler.sendMessage(msg);
            }

        });
    }

    /**
     * 同步请求
     * @param username
     * @param configKey
     * @param screenSize
     * @param version
     * @param url
     * @return
     * @throws Exception
     */
    public static String postKeyValue(String username, String configKey, String screenSize,String version,String url) throws Exception  {
        String result = null;
        FormBody formBody = new FormBody.Builder()
                .add("username", "15014150833")
                .add("config_key", "zy_0001")
                .add("screen_size", "320x480")
                .add("version","1.0")
                .build();
        Log.e("formBody",formBody.encodedValue(0) + " " + formBody.encodedValue(1) + " " + formBody.encodedValue(2) + " " + formBody.encodedValue(3) );
        Request request = new Request.Builder().url(url).post(formBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()){
            result = response.body().string();
            Log.e("result",result);
        }else {
            throw new IOException("Unexpected code " + response);
        }

        return result;
    }
}
