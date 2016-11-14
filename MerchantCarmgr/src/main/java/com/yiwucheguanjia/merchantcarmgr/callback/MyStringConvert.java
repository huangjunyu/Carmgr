package com.yiwucheguanjia.merchantcarmgr.callback;

import com.lzy.okgo.convert.Converter;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/13.
 */
public class MyStringConvert<T> implements Converter<T> {

    public static MyStringConvert create() {
        return ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static MyStringConvert convert = new MyStringConvert();
    }

    @Override
    public T convertSuccess(Response value) throws Exception {
        return (T)value.body().string();
    }
}
