package com.yiwucheguanjia.carmgr.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/8/25.
 */
public class HttpInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().removeHeader("Pragma")
                .header("Cache-Control", String.format("max-age=%d", 10)).build();//设置了缓存时间为10秒
//        return null;
    }
}
