package com.yiwucheguanjia.carmgr.utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestSerives {

    @POST("appadvise")
    Call<JsonModel> getString(@Query("username") String username,
                           @Query("advise_text") String advise_text,
                           @Query("token") String token,
                           @Query("version") String version);

    @POST("appgetconfig")
    Call<ConfiModel> postData(@Query("username") String username,
                              @Query("config_key") String configKey,
                              @Query("screen_size") String screenSize,
                              @Query("token") String token,
                              @Query("version")String version);
}
