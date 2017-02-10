package com.yiwucheguanjia.carmgr.merchant_detail.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.callback.MyStringCallback;
import com.yiwucheguanjia.carmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.carmgr.utils.UrlString;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class GoodsDetailActivity extends AppCompatActivity {
    private String merchantName;
    private String merchantId;
    private String serviceName;
    private String serviceId;
    @BindView(R.id.goods_detail_qppoint_btn)
    Button appointSeriveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(GoodsDetailActivity.this, ContextCompat.getColor(GoodsDetailActivity.this,R.color.white),50);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        merchantName = bundle.getString("merchantName","");
        serviceName = bundle.getString("serviceName","");
        serviceId = bundle.getString("serviceId","");
        merchantId = bundle.getString("merchantId","");
        getGoodsData();
    }
    @OnClick({R.id.goods_detail_qppoint_btn,R.id.goods_detail_goback_rl})
    void clickView(View view){
        switch (view.getId()){
            case R.id.goods_detail_qppoint_btn:
                appointmentService();
                break;
            case R.id.goods_detail_goback_rl:
                finish();
            default:
                break;
        }
    }
    private void getGoodsData(){
        OkGo.post(UrlString.APP_GETMERCHANTS_SERVICE)
                .params("username", SharedPreferencesUtil.getInstance(GoodsDetailActivity.this).usernameSharedPreferences())
                .params("merchant_name",merchantName)
                .params("service_name",serviceName)
                .params("token",SharedPreferencesUtil.getInstance(GoodsDetailActivity.this).usernameSharedPreferences())
                .params("version",UrlString.APP_VERSION)
                .execute(new MyStringCallback(GoodsDetailActivity.this,getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("scr",s);
                    }
                });

    }
    private void appointmentService(){
        OkGo.post(UrlString.APP_APPOINTMENT_SERVICE)
                .params("username", SharedPreferencesUtil.getInstance(GoodsDetailActivity.this).usernameSharedPreferences())
                .params("merchant_id",merchantId)
                .params("service_id",serviceId)
                .params("opt_type","1")
                .params("type","")
                .params("version","")
                .execute(new MyStringCallback(GoodsDetailActivity.this,getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("ssa",s);
                    }
                });
    }
}
