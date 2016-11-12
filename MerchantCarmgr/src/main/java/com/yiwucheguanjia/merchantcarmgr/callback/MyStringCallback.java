package com.yiwucheguanjia.merchantcarmgr.callback;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.yiwucheguanjia.merchantcarmgr.animation.DialogLoading;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/12.
 */
public abstract class MyStringCallback extends StringCallback {
    private ProgressDialog progressDialog;
    private String loadingToast;
    private Activity activity;
    public MyStringCallback(Activity activity,String loadingToast){
        this.activity = activity;
        this.loadingToast = loadingToast;
        initDialog(this.activity,this.loadingToast);
    }

    /*
    * @params activity
    * @params loadingToast 加载动画要显示的提示内容
    * */
    private void initDialog(Activity activity,String loadingToast) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(loadingToast);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        //网络请求结束后关闭对话框
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
