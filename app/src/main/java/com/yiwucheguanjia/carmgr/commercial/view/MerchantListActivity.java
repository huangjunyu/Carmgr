package com.yiwucheguanjia.carmgr.commercial.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.city.utils.SharedPreferencesUtils;
//import com.yiwucheguanjia.carmgr.commercial.controller.MerchantItemAdapter;
//import com.yiwucheguanjia.carmgr.commercial.model.MerchantItemBean;
import com.yiwucheguanjia.carmgr.commercial.controller.MerchantItemAdapter;
import com.yiwucheguanjia.carmgr.commercial.model.MerchantItemBean;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/5.
 */
public class MerchantListActivity extends Activity implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private ArrayList<MerchantItemBean> merchantItemBeens;
    private RecyclerView recyclerView;
    private RippleView gobackRpw;
    private String business;
    private TextView titleTv;
    private TextView nothingTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_merchantlist);
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("business",null) != null) {
            business = bundle.getString("business", null);
            titleTv.setText(business);
        }
        sharedPreferences = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        getMerchantsList(sharedPreferences.getString("ACCOUNT", null),
                SharedPreferencesUtils.getCityName(MerchantListActivity.this),
                business,
                sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION,
                UrlString.APP_GET_MERCHANTS_LIST, 1);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MerchantListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.merchant_list_lv);
        gobackRpw = (RippleView) findViewById(R.id.merchant_list_goback_rpw);
        titleTv = (TextView) findViewById(R.id.merchant_list_title);
        nothingTv = (TextView) findViewById(R.id.merchantlist_nothing_tv);
        gobackRpw.setOnClickListener(this);
    }

    //一个Okhttputils封装类的示例
    private void getMerchantsList(String username, String city_filter, String service_filter, String token,
                                  String version, String url, int id) {
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(MerchantListActivity.this, getResources().getText(R.string.login_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("city_filter", city_filter)
                .addParams("service_filter", service_filter)
                .addParams("token", token)
                .addParams("version", version)
                .id(1)
                .build()
                .execute(new CommercialStringCallback());
    }

    //解析JSON数据
    protected void analysisJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            merchantItemBeens = new ArrayList<>();
            if (jsonObject.getInt("list_size") <= 0){
                nothingTv.setVisibility(View.VISIBLE);
                return;
            }
            nothingTv.setVisibility(View.GONE);
            for (int i = 0; i < jsonObject.getJSONArray("merchants_list").length(); i++) {
                MerchantItemBean merchantItemBean = new MerchantItemBean();
                JSONObject merchantJson = jsonObject.getJSONArray("merchants_list").getJSONObject(i);
                merchantItemBean.setMerchantImgUrl(merchantJson.getString("img_path"));
                merchantItemBean.setMerchantDistance(merchantJson.getString("distance"));
                merchantItemBean.setMerchantArea(merchantJson.getString("area"));
                merchantItemBean.setMerchantIntroduce(merchantJson.getString("merchant_introduce"));
                merchantItemBean.setMerchantName(merchantJson.getString("merchant_name"));
                merchantItemBean.setMerchantRoad(merchantJson.getString("road"));
                merchantItemBean.setMerchantServiceItem(merchantJson.getString("service_item"));
                merchantItemBean.setMerchantStars(merchantJson.getDouble("stars"));
                merchantItemBean.setMerchantStarsStr(merchantJson.getString("stars"));
                merchantItemBeens.add(merchantItemBean);
            }
            MerchantItemAdapter merchantItemAdapter = new MerchantItemAdapter(MerchantListActivity.this, merchantItemBeens);
            recyclerView.setAdapter(merchantItemAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            MerchantItemAdapter merchantItemAdapter = new MerchantItemAdapter(MerchantListActivity.this, merchantItemBeens);
            recyclerView.setAdapter(merchantItemAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.merchant_list_goback_rpw:
                finish();
                break;
            default:
                break;
        }
    }

    protected class CommercialStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("mer", response);
                    analysisJson(response);
                    break;
                default:
                    break;
            }
        }
    }
}
