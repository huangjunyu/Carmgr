package com.yiwucheguanjia.merchantcarmgr.utils;

import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RequestSerives {


    //此处用Rxjava替代retrofit的call回调
//    @POST("appgetconfig")
//    rx.Observable<ConfiModel> postData(@Query("username") String username,
//                                       @Query("config_key") String configKey,
//                                       @Query("screen_size") String screenSize,
//                                       @Query("token") String token,
//                                       @Query("version") String version);
}
