package com.yiwucheguanjia.carmgr.utils;

import android.os.Handler;
import android.os.Looper;
import android.service.carrier.CarrierMessagingService;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/15.
 */
public class OkhttpTools {
//    private Handler mDelivery;
//    private OkHttpClient mOkHttpClient;
//    private static OkhttpTools mInstance;
//
//    /** 单例模式 */
//    public static OkhttpTools getInstance() {
//        if (mInstance == null) {
//            synchronized (OkhttpTools.class) {
//                if (mInstance == null) {
//                    mInstance = new OkhttpTools();
//                }
//            }
//        }
//        return mInstance;
//    }
//    public static void postAsyn(Request.Builder requestBuilder,
//                                final CarrierMessagingService.ResultCallback callback, Map<String, String> params) {
//        getInstance()._postAsyn(requestBuilder, callback, params);
//    }
//    private OkhttpTools() {
//        mOkHttpClient = new OkHttpClient();
//        mDelivery = new Handler(Looper.getMainLooper());
//        //mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
//        /*
//        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
//        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);*/
//
//        mOkHttpClient.connectTimeoutMillis();
//        mOkHttpClient.writeTimeoutMillis();
//        mOkHttpClient.readTimeoutMillis();
//        // cookie enabled
////		mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
////        mDelivery =  new Handler(Looper.getMainLooper());
////        mGson = new Gson();
//    }
//    /**
//     * 异步的post请求
//     *
//     * @param callback
//     * @param params
//     */
//    private void _postAsyn(Request.Builder requestBuilder,
//                           final CarrierMessagingService.ResultCallback callback, Map<String, String> params) {
//        Param[] paramsArr = map2Params(params);
//        Request request = buildPostRequest(requestBuilder, paramsArr);
//        deliveryResult((ResultCallback) callback, request);
//    }
//    public static class Param {
//        public Param() {
//        }
//
//        public Param(String key, String value) {
//            this.key = key;
//            this.value = value;
//        }
//
//        String key;
//        String value;
//    }
//
//    private Param[] map2Params(Map<String, String> params) {
//        if (params == null)
//            return new Param[0];
//        int size = params.size();
//        Param[] res = new Param[size];
//        Set<Map.Entry<String, String>> entries = params.entrySet();
//        int i = 0;
//        for (Map.Entry<String, String> entry : entries) {
//            res[i++] = new Param(entry.getKey(), entry.getValue());
//        }
//        return res;
//    }
//    private Request buildPostRequest(Request.Builder requestBuilder,
//                                     Param[] params) {
//        if (params == null) {
//            params = new Param[0];
//        }
////        FormEncodingBuilder builder = new FormEncodingBuilder();
//        FormBody.Builder formBody = new FormBody.Builder();
//        for (Param param : params) {
//
//            formBody.add(param.key, param.value);
//
//        }
//        RequestBody requestBody = formBody.build();
//        return requestBuilder.post(requestBody).build();
//    }
//    private void sendFailedStringCallback(final Call request,
//                                          final Exception e, final ResultCallback callback) {
//        mDelivery.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callback != null)
//                    callback.onError(request, e);
//            }
//        });
//    }
//    public static abstract class ResultCallback<T> {
//        Type mType;
//
//        public ResultCallback() {
//            mType = getSuperclassTypeParameter(getClass());
//        }
//
//        static Type getSuperclassTypeParameter(Class<?> subclass) {
//            Type superclass = subclass.getGenericSuperclass();
//            if (superclass instanceof Class) {
//                throw new RuntimeException("Missing type parameter.");
//            }
//            ParameterizedType parameterized = (ParameterizedType) superclass;
//            return $Gson$Types.canonicalize(parameterized
//                    .getActualTypeArguments()[0]);
//        }
//
//        public abstract void onError(Call request, Exception e);
//
//        public abstract void onResponse(T response);
//    }
//    private void sendSuccessResultCallback(final Object object,
//                                           final ResultCallback callback) {
//        mDelivery.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callback != null) {
//                    callback.onResponse(object);
//                }
//            }
//        });
//    }
//    private void deliveryResult(final ResultCallback callback, Request request) {
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                sendFailedStringCallback(call, e, callback);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    final String string = response.body().string();
//                    if (callback.mType == String.class) {
//                        sendSuccessResultCallback(string, callback);
//                    } else {
////                        Object o = mGson.fromJson(string, callback.mType);
////                        sendSuccessResultCallback(o, callback);
//                    }
//
//                } catch (IOException e) {
////                    sendFailedStringCallback(response.request(), e, callback);
//                }// catch (com.google.gson.JsonParseException e)// Json解析的错误
////                {
////                    sendFailedStringCallback(response.request(), e, callback);
////                }
//
//            }
//
///*            @Override
//            public void onFailure(final Request request, final IOException e) {
//                sendFailedStringCallback(request, e, callback);
//            }
//
//            @Override
//            public void onResponse(final Response response) {
//                try {
//                    final String string = response.body().string();
//                    if (callback.mType == String.class) {
//                        sendSuccessResultCallback(string, callback);
//                    } else {
////                        Object o = mGson.fromJson(string, callback.mType);
////                        sendSuccessResultCallback(o, callback);
//                    }
//
//                } catch (IOException e) {
//                    sendFailedStringCallback(response.request(), e, callback);
//                } catch (com.google.gson.JsonParseException e)// Json解析的错误
//                {
//                    sendFailedStringCallback(response.request(), e, callback);
//                }
//
//            }*/
//        });
//    }
}
