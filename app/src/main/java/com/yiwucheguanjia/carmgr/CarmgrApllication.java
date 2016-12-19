package com.yiwucheguanjia.carmgr;

import android.app.Activity;
import android.app.Application;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.umeng.socialize.PlatformConfig;
import com.yiwucheguanjia.carmgr.city.db.DBManager;
import com.yiwucheguanjia.carmgr.utils.PicassoImageLoader;

/**
 * Created by Administrator on 2016/7/31.
 */
public class CarmgrApllication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        dbManager = new DBManager(getApplicationContext());
        dbManager.openDatabase();
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
//        OkHttpUtils.getInstance().getOkHttpClient().cache();



        HttpHeaders headers = new HttpHeaders();

        headers.put("Authorization", "APPCODE 043d0512fe5e4e04a328a9d6552e5923");    //header不支持中文
        OkGo.init(this);



        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数

        //好处是全局参数统一,特定请求可以特别定制参数

        try {

            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了

            OkGo.getInstance()



                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行

                    .debug("OkGo")



                    //如果使用默认的 60秒,以下三行也不需要传

                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间

                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间

                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间



                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 [url]https://github.com/jeasonlzy/[/url]

                    .setCacheMode(CacheMode.NO_CACHE)



                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍

                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)



                    //如果不想让框架管理cookie,以下不需要

//                .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）

                    .setCookieStore(new PersistentCookieStore())          //cookie持久化存储，如果cookie不过期，则一直有效



                    //可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置

//                    .setCertificates()                                  //方法一：信任所有证书

//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书

//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密



                    //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行

//                .addInterceptor(new Interceptor() {

//                    @Override

//                    public Response intercept(Chain chain) throws IOException {

//                        return chain.proceed(chain.request());

//                    }

//                })



                    //这两行同上,不需要就不要传

                    .addCommonHeaders(headers);                                   //设置全局公共头

//                    .addCommonParams(params);                                          //设置全局公共参数

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    {

        PlatformConfig.setQQZone("1105629466", "fYspq5Wgo6FnrWgW");
    }

    private DBManager dbManager;

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.closeDatabase();
    }


}
