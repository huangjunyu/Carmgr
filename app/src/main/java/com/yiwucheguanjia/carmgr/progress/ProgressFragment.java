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
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 进度首页
 */
public class ProgressFragment extends Fragment implements View.OnClickListener {
    private LinearLayout progressView;
    private RelativeLayout personalRl;
    private RelativeLayout allRl;
    private RelativeLayout waitPayRl;
    private RelativeLayout waitUseRrl;
    private RelativeLayout goingRl;
    private RelativeLayout doneRl;
    private RelativeLayout waitAssessRl;
    private RelativeLayout afterSale;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        progressView = (LinearLayout) inflater.inflate(R.layout.activity_progress, null);
        initView();
        appGetProcess(sharedPreferences.getString("ACCOUNT", null), "all",
                sharedPreferences.getString("TOKEN", null), "1.0", 1);
        return progressView;
    }



    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    private void getProgressItemJson(String response) {
        merchantBeens = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray ordersList = jsonObject.getJSONArray("orders_list");
            int listSize = jsonObject.getInt("list_size");
            for (int i = 0;i < listSize;i++){
                JSONObject listItem = ordersList.getJSONObject(i);
                MerchantBean merchantBean = new MerchantBean();
                Log.e("merch",listItem.getString("merchant_name"));
                merchantBean.setMerchantNameStr(listItem.getString("merchant_name"));
                merchantBean.setMerchantImgUrl(listItem.getString("img_path"));
                merchantBean.setServicTypeStr(listItem.getString("service_name"));
                merchantBean.setOrderNumberStr(listItem.getString("order_id"));
                merchantBean.setTimeStr(listItem.getString("order_time"));
                merchantBeens.add(merchantBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ProgressAdapter progressAdapter = new ProgressAdapter(getActivity(), merchantBeens);
        myListView.setAdapter(progressAdapter);
    }

    private void appGetProcess(String username, String filter, String token, String version, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get()
                .url(UrlString.APP_GET_PROCESS)
                .addParams("username", username)
                .addParams("filter", filter)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new myStringCallback());
    }
    private void initView() {
        myListView = (MyListView) progressView.findViewById(R.id.progress_item_lv);
        personalRl = (RelativeLayout) progressView.findViewById(R.id.progress_personal_rl);
        allRl = (RelativeLayout) progressView.findViewById(R.id.progress_all_rl);
        waitPayRl = (RelativeLayout) progressView.findViewById(R.id.pro_wait_pay_rl);
        waitUseRrl = (RelativeLayout) progressView.findViewById(R.id.pro_wait_use_rl);
        goingRl = (RelativeLayout) progressView.findViewById(R.id.pro_going_rl);
        doneRl = (RelativeLayout) progressView.findViewById(R.id.pro_done_rl);
        waitAssessRl = (RelativeLayout) progressView.findViewById(R.id.pro_wait_assess_rl);
        personalRl.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progress_personal_rl:
                if (sharedPreferences.getString("ACCOUNT", null) != null) {
                    Intent intentPersonal = new Intent(getActivity(), personalActivity.class);
                    getActivity().startActivity(intentPersonal);
                } else {
                    Intent personalIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(personalIntent, 1);
                } ;
                break;
            case R.id.progress_all_rl://全部
                break;
            case R.id.pro_wait_pay_rl:
                break;
            case R.id.pro_wait_use_rl:
                break;
            case R.id.pro_going_rl:
                break;
            case R.id.pro_done_rl:
                break;
            case R.id.pro_wait_assess_rl:
                break;
            default:
                break;
        }
    }

    public class myStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }
        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("responseProgress", response);
                    getProgressItemJson(response);
                    break;
                default:
                    break;
            }
        }
    }
}
