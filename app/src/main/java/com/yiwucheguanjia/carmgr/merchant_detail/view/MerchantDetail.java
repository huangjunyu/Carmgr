package com.yiwucheguanjia.carmgr.merchant_detail.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.merchant_detail.controller.DetailRateAdapter;
import com.yiwucheguanjia.carmgr.merchant_detail.controller.DetailServiceTypeAdapter;
import com.yiwucheguanjia.carmgr.merchant_detail.controller.myScrollView;
import com.yiwucheguanjia.carmgr.merchant_detail.model.RateBean;
import com.yiwucheguanjia.carmgr.merchant_detail.model.ServiceTypeItemBean;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MerchantDetail extends Activity implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private RecyclerView serviceTypeRView;
    private RecyclerView rateRView;
    //    private RelativeLayout merchantdetail;
    private RelativeLayout merchantdetailTitle;
    private myScrollView scrollview;
    private ImageView merchantBg;
    private ArrayList<ServiceTypeItemBean> serviceTypeItemBeens;
    private ArrayList<RateBean> rateBeens;
    private CollapsibleTextView merchantDetailTv;
    private TextView readMoreDetail;
    private TextView readMoredRate;
    private ImageView readMoredDetailImg;
    private ImageView readMoredRateImg;
    private TextView merchantNameTv;
    private Drawable drawable;
    private static final int START_ALPHA = 0;
    private static final int END_ALPHA = 255;
    private int fadingHeight = 98;   //当ScrollView滑动到什么位置时渐变消失（根据需要进行调整）
    private RelativeLayout detail_goback_rl;
    private ImageView starImg;
    private int REQUEST_TIMES = 0;
    private int TOTAL_TIMES = 3;
    private String merchantNameStr;
    private String merchantStarStr;
    private String merchantAddress;
    private TextView positionTv;
    private TextView appoinBusTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sharedPreferences = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_merchantdetail);
        initView();
        Log.e("px",Tools.getInstance().pxTodip(MerchantDetail.this,504) + "");
//        drawable = getDrawable(R.drawable.orange);
        drawable = getResources().getDrawable(R.drawable.orange);
        drawable.setAlpha(0);
        merchantdetailTitle.setBackground(drawable);
        scrollview.setOnScrollChangedListener(scrollChangedListener);
        Bundle bundle = getIntent().getExtras();
        merchantNameStr = bundle.getString("merchantName");
        merchantStarStr = bundle.getString("merchantStar","5");
        merchantAddress = bundle.getString("merchantAddress","");
        getMerchants(sharedPreferences.getString("ACCOUNT", null), merchantNameStr,
                sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION, UrlString.APPGETMERCHANTS, 1);

    }
    protected void selectStar(String starNum,ImageView merchantStarImg){
        switch (starNum){
            case "0.5":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_half).error(R.mipmap.star_five).into(merchantStarImg);

                break;
            case "1":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "1.5":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_one_hafl).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "2":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_two).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "2.5":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_two_half).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "3":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_three).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "3.5":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_three_half).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "4":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_four).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "4.5":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_four_half).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "5":
                Picasso.with(MerchantDetail.this).load(R.mipmap.star_five).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected void initView() {
        scrollview = (myScrollView) findViewById(R.id.merchantdetail_scroll);
        merchantdetailTitle = (RelativeLayout) findViewById(R.id.merchantdetail_title);
        merchantBg = (ImageView) findViewById(R.id.detail_title_bg);
        merchantDetailTv = (CollapsibleTextView) findViewById(R.id.desc_collapse_tv);
        serviceTypeRView = (RecyclerView) findViewById(R.id.detail_serv_typ_rv);
        rateRView = (RecyclerView) findViewById(R.id.detail_rate_rv);
        readMoreDetail = (TextView) findViewById(R.id.detail_more_tv);
        readMoredRate = (TextView) findViewById(R.id.detail_more_rate_tv);
        readMoredDetailImg = (ImageView) findViewById(R.id.detail_more_img);
        readMoredRateImg = (ImageView) findViewById(R.id.detail_more_rate_img);
        detail_goback_rl = (RelativeLayout) findViewById(R.id.detail_goback_rl);
        merchantNameTv = (TextView) findViewById(R.id.detail_merchant_name);
        starImg = (ImageView)findViewById(R.id.detail_star);
        positionTv = (TextView)findViewById(R.id.detail_position_tv);
        appoinBusTv = (TextView)findViewById(R.id.detail_appoin_buse_tv);
        //发送广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.appointment");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        serviceTypeRView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rateRView.setLayoutManager(linearLayoutManager2);
        detail_goback_rl.setOnClickListener(this);
        appoinBusTv.setOnClickListener(this);
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
            //jsonObject.getString("merchant_introduce") + e
            merchantNameTv.setText(jsonObject.getString("merchant_name"));
            positionTv.setText(merchantAddress);
            selectStar(merchantStarStr,starImg);
            merchantDetailTv.setDesc(jsonObject.getString("merchant_introduce"), TextView.BufferType.NORMAL);
            handler.sendEmptyMessage(1);
            Picasso.with(this).load(jsonObject.getString("img_path")).error(R.mipmap.picture_default).into(
                    new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                            merchantBg.setBackground(new BitmapDrawable(bitmap));
                        }

                        @Override
                        public void onBitmapFailed(Drawable drawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable drawable) {

                        }
                    }
            );
            readMoreDetail.setText(getText(R.string.read_more));
            readMoredDetailImg.setImageResource(R.mipmap.pull_down_black);
            serviceTypeItemBeens = new ArrayList<>();
//                serviceTypeItemBeen.setDetailMerchantImgUrl(merchantJson.getString("img_path"));
            for (int i = 0; i < jsonObject.getJSONArray("services_list").length(); i++) {
                ServiceTypeItemBean serviceTypeItemBeen = new ServiceTypeItemBean();
                JSONObject merchantJson = jsonObject.getJSONArray("services_list").getJSONObject(i);
                serviceTypeItemBeen.setServicePrice(merchantJson.getString("service_price"));
                serviceTypeItemBeen.setService_stars(merchantJson.getDouble("service_stars"));
                serviceTypeItemBeen.setDetailMerchantStarsStr(merchantJson.getString("service_stars"));
                serviceTypeItemBeen.setDetailMerchantImgUrl(merchantJson.getString("service_img"));
                serviceTypeItemBeen.setDetailMerchantAddr(merchantJson.getString("service_address"));
                serviceTypeItemBeen.setDetailMerchantIntroduce(merchantJson.getString("service_introduce"));
                serviceTypeItemBeen.setDetailMerchantName(merchantJson.getString("service_name"));
                serviceTypeItemBeen.setDetailMerchantDistance(merchantJson.getString("service_distance"));
                Log.e("ewqvv", merchantJson.getString("service_distance"));

                serviceTypeItemBeens.add(serviceTypeItemBeen);
            }
            DetailServiceTypeAdapter merchantItemAdapter = new DetailServiceTypeAdapter(MerchantDetail.this, serviceTypeItemBeens);
            serviceTypeRView.setAdapter(merchantItemAdapter);

            readMoredRate.setText(getText(R.string.read_more));
            readMoredRateImg.setImageResource(R.mipmap.pull_down_black);
            rateBeens = new ArrayList<>();
            for (int j = 0; j < jsonObject.getJSONArray("rate_list").length(); j++) {
                RateBean rateBean = new RateBean();
                JSONObject rateJson = jsonObject.getJSONArray("rate_list").getJSONObject(j);
                Log.e("user", rateJson.getString("rate_user"));
                rateBean.setRateUser(rateJson.getString("rate_user"));
                rateBean.setRateText(rateJson.getString("rate_text"));
                rateBean.setRateStars(rateJson.getString("rate_stars"));
                rateBean.setRateTime(rateJson.getString("rate_time"));
                rateBeens.add(rateBean);
            }
            DetailRateAdapter rateAdapter = new DetailRateAdapter(MerchantDetail.this, rateBeens);
            rateRView.setAdapter(rateAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_goback_rl:
                finish();
                break;
            case R.id.detail_appoin_buse_tv:

                Intent intent = new Intent();
                intent.setAction("action.appointment");
                sendBroadcast(intent);
                finish();
                break;
            default:
                break;
        }
    }

    protected class detailStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (REQUEST_TIMES <= TOTAL_TIMES) {
                REQUEST_TIMES++;
                getMerchants(sharedPreferences.getString("ACCOUNT", null), merchantNameStr,
                        sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION, UrlString.APPGETMERCHANTS, id);

            }
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
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.appointment")) {
                Log.e("ooeo", "ka");
                MerchantDetail.this.finish();
            }
        }
    };
    @Override
    protected void onDestroy() { // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

    /**
     * 公共接口：ScrollView的滚动监听
     */
    private myScrollView.OnScrollChangedListener scrollChangedListener = new myScrollView.OnScrollChangedListener() {

        @Override
        public void onScrollChanged(ScrollView who, int x, int y, int oldx, int oldy) {
            if (y > Tools.dipTopx(MerchantDetail.this, fadingHeight)) {
                y = Tools.dipTopx(MerchantDetail.this, fadingHeight);   //当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可
            }
            drawable.setAlpha(y * (END_ALPHA - START_ALPHA) / Tools.dipTopx(MerchantDetail.this, fadingHeight) + START_ALPHA);
        }
    };
}
