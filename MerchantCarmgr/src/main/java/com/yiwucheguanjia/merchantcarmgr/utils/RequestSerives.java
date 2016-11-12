package com.yiwucheguanjia.merchantcarmgr.utils;

import com.yiwucheguanjia.merchantcarmgr.account.LoginBean;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RequestSerives {

    //此处用Rxjava替代retrofit的call回调
    /*
    * 0：通过账号加密码的方式登录,1:通过账号加登录验证码的方式快捷登录
    * */
    @POST("mapplogin")
    rx.Observable<List<LoginBean>> postData(@Query("username") String username,
                                                      @Query("password") String configKey,
                                                      @Query("type") String type,
                                                      @Query("verf_code") String verfCode,
                                                      @Query("uuid")String uuid,
                                                      @Query("version")String version);
}
