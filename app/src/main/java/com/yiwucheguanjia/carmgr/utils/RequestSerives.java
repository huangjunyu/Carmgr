package com.yiwucheguanjia.carmgr.utils;

import java.io.IOException;
import java.util.Observable;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.observers.Observers;


public interface RequestSerives {

//    @POST("appadvise")
//    Call<JsonModel> getString(@Query("username") String username,
//                           @Query("advise_text") String advise_text,
//                           @Query("token") String token,
//                           @Query("version") String version);
//
//    @POST("appgetconfig")
//    Call<ConfiModel> postData(@Query("username") String username,
//                              @Query("config_key") String configKey,
//                              @Query("screen_size") String screenSize,
//                              @Query("token") String token,
//                              @Query("version")String version);

    //此处用Rxjava替代retrofit的call回调
    @POST("appgetconfig")
    rx.Observable<ConfiModel> postData(@Query("username") String username,
                                       @Query("config_key") String configKey,
                                       @Query("screen_size") String screenSize,
                                       @Query("token") String token,
                                       @Query("version")String version);
}
