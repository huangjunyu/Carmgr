package com.yiwucheguanjia.merchantcarmgr.callback;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/13.
 */
public abstract class MyTCallback<T> extends AbsCallback<T>{
    @Override
    public T convertSuccess(Response response) throws Exception {

        T t = (T) MyStringConvert.create().convertSuccess(response);
        return t;
    }
}
