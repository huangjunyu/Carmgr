package com.yiwucheguanjia.carmgr.utils;

import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/17.
 */

public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        return response.body().string();
    }
}
