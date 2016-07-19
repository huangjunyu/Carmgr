package com.yiwucheguanjia.carmgr.progress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.LoginActivity;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.MyListView;
import com.yiwucheguanjia.carmgr.utils.OkhttpManager;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;

/**
 * 进度首页
 */
public class ProgressFragment extends Fragment implements View.OnClickListener{
    private LinearLayout progressView;
    private RelativeLayout personalRl;
    private SharedPreferences sharedPreferences;
    private MyListView myListView;
    private ArrayList<MerchantBean> merchantBeens;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        progressView = (LinearLayout)inflater.inflate(R.layout.activity_progress,null);
        initView();
        appGetProcess(sharedPreferences.getString("ACCOUNT",null),"all",sharedPreferences.getString("TOKEN",null),"1.0",1,2);
        return progressView;
    }
    private void initView(){
        myListView = (MyListView)progressView.findViewById(R.id.progress_item_lv);
        personalRl = (RelativeLayout)progressView.findViewById(R.id.progress_personal_rl);
        personalRl.setOnClickListener(this);
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.e("progressJson",msg.obj.toString());
                    getProgressItenJson();
                    break;
                default:
                    break;
            }
        }
    };
    private void getProgressItenJson(){
        merchantBeens = new ArrayList<>();
        for (int i = 0;i < 5;i++){
        MerchantBean merchantBean = new MerchantBean();
        merchantBean.setOrderNumberStr("8383838");
        merchantBeens.add(merchantBean);

        }
        ProgressAdapter progressAdapter = new ProgressAdapter(getActivity(),merchantBeens);
        Log.e("ksew","a,we");
        myListView.setAdapter(progressAdapter);
    }
    private void appGetProcess(String username,String filter,String token,String version,int success,int fail){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
//        FormBody formBody = new FormBody.Builder()
//                .add("username",username)
//                .add("filter",filter)
//                .add("token",token)
//                .add("version",version)
//                .build();
//        OkhttpManager.getInstance().OKhttpPost(UrlString.APP_GET_PROCESS,handler,formBody,success,fail);
        OkHttpUtils.get()
                .url(UrlString.APP_GET_PROCESS)
                .addParams("username",username)
                .addParams("filter",filter)
                .addParams("token",token)
                .addParams("version",version)
                .id(1)
                .build()
                .execute(new myStringCallback());
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.progress_personal_rl:
                if (sharedPreferences.getString("ACCOUNT",null) != null){
                    Intent intentPersonal = new Intent(getActivity(),personalActivity.class);
                    getActivity().startActivity(intentPersonal);
                }else {
                    Intent personalIntent = new Intent(getActivity(),LoginActivity.class);
                    getActivity().startActivityForResult(personalIntent,1);

                };
                break;
            default:
                break;
        }
    }
    public class myStringCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id){
                case 1:
                    Log.e("responseProgress",response);
                    getProgressItenJson();
                    break;
                default:
                    break;
            }
        }
    }
}
