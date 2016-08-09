package com.yiwucheguanjia.carmgr.merchant_detail.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.merchant_detail.controller.DetailServiceTypeAdapter;
import com.yiwucheguanjia.carmgr.merchant_detail.model.ServiceTypeItemBean;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MerchantDetail extends Activity {
    private SharedPreferences sharedPreferences;
    private RecyclerView serviceTypeRView;
    private ArrayList<ServiceTypeItemBean> serviceTypeItemBeens;
    CollapsibleTextView tv;
    String s = "The following layout is an alternative to the layout shown in the previous lesson that shows only one fragment at a time. In order to replace one fragment with another, the activity's layout includes an empty FrameLayout that acts as the fragment container.";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_merchantdetail);
        initView();
        Bundle bundle = getIntent().getExtras();
        String merchantName = bundle.getString("merchantName");
        tv.setDesc(s, TextView.BufferType.NORMAL);
        getMerchants(sharedPreferences.getString("ACCOUNT", null), merchantName,
                sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION, UrlString.APPGETMERCHANTS, 1);
    }

    protected void initView() {
        tv = (CollapsibleTextView) findViewById(R.id.desc_collapse_tv);
        serviceTypeRView = (RecyclerView) findViewById(R.id.commercial_item_lv);
    }

    protected void getMerchants(String username, String merchantName, String token,
                                String version, String url, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("merchant_name", merchantName)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new detailStringCallback());
    }

    //解析JSON数据
    protected void analysisJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("merchants_list");
            serviceTypeItemBeens = new ArrayList<>();
//                serviceTypeItemBeen.setDetailMerchantImgUrl(merchantJson.getString("img_path"));
            for (int i = 0; i < jsonObject.getJSONArray("services_lsit").length(); i++) {
                ServiceTypeItemBean serviceTypeItemBeen = new ServiceTypeItemBean();
                JSONObject merchantJson = jsonObject.getJSONArray("services_lsit").getJSONObject(i);
                serviceTypeItemBeen.setDetailMerchantDistance(merchantJson.getString("service_distance"));
                serviceTypeItemBeen.setDetailMerchantAddr(merchantJson.getString("area"));
                serviceTypeItemBeen.setDetailMerchantIntroduce(merchantJson.getString("service_introduce"));
                serviceTypeItemBeen.setDetailMerchantName(merchantJson.getString("service_name"));
                serviceTypeItemBeen.setDetailMerchantRoad(merchantJson.getString("road"));
                serviceTypeItemBeen.setDetailMerchantServiceItem(merchantJson.getString("service_item"));
                serviceTypeItemBeen.setService_stars(merchantJson.getDouble("stars"));
                serviceTypeItemBeen.setDetailMerchantStarsStr(merchantJson.getString("stars"));
                serviceTypeItemBeens.add(serviceTypeItemBeen);
            }

            DetailServiceTypeAdapter merchantItemAdapter = new DetailServiceTypeAdapter(MerchantDetail.this, serviceTypeItemBeens);
            serviceTypeRView.setAdapter(merchantItemAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            DetailServiceTypeAdapter merchantItemAdapter = new DetailServiceTypeAdapter(MerchantDetail.this, serviceTypeItemBeens);
            serviceTypeRView.setAdapter(merchantItemAdapter);
        }
    }

    protected class detailStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("detail", response);
                    analysisJson(response);
                    break;
                default:
                    break;
            }
        }
    }
}
