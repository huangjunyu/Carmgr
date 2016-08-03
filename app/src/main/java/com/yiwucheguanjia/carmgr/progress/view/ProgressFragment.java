package com.yiwucheguanjia.carmgr.progress.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginActivity;
import com.yiwucheguanjia.carmgr.city.CityActivity;
import com.yiwucheguanjia.carmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.progress.model.MerchantBean;
import com.yiwucheguanjia.carmgr.progress.controller.ProgressAdapter;
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
    private RelativeLayout waitUseRl;
    private RelativeLayout goingRl;
    private RelativeLayout doneRl;
    private RelativeLayout waitAssessRl;
    private RelativeLayout afterSaleRl;
    private RelativeLayout positionRl;
    private SharedPreferences sharedPreferences;
    private MyListView myListView;
    private ArrayList<MerchantBean> merchantBeens;
    private ImageView allImg;
    private ImageView waitPayImg;
    private ImageView waitUseImg;
    private ImageView goingImg;
    private ImageView doneImg;
    private ImageView waitAssessImg;
    private ImageView afterSale;
    private TextView allTv;
    private TextView waitPayTv;
    private TextView waitUseTv;
    private TextView gogingTv;
    private TextView doneTv;
    private TextView waitAssessTv;
    private TextView afterSaleTv;
    private TextView positionTv;
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
    private void initView() {
        myListView = (MyListView) progressView.findViewById(R.id.progress_item_lv);
        personalRl = (RelativeLayout) progressView.findViewById(R.id.progress_personal_rl);
        allRl = (RelativeLayout) progressView.findViewById(R.id.progress_all_rl);
        waitPayRl = (RelativeLayout) progressView.findViewById(R.id.pro_wait_pay_rl);
        waitUseRl = (RelativeLayout) progressView.findViewById(R.id.pro_wait_use_rl);
        goingRl = (RelativeLayout) progressView.findViewById(R.id.pro_going_rl);
        doneRl = (RelativeLayout) progressView.findViewById(R.id.pro_done_rl);
        waitAssessRl = (RelativeLayout) progressView.findViewById(R.id.pro_wait_assess_rl);
        positionRl = (RelativeLayout) progressView.findViewById(R.id.progress_position_rl);
        afterSaleRl = (RelativeLayout) progressView.findViewById(R.id.pro_after_sale__rl);
        allImg = (ImageView)progressView.findViewById(R.id.progress_all_img);
        waitPayImg = (ImageView)progressView.findViewById(R.id.pro_wait_pay_img);
        waitUseImg = (ImageView)progressView.findViewById(R.id.pro_wait_use_img);
        goingImg = (ImageView)progressView.findViewById(R.id.pro_going_img);
        doneImg = (ImageView)progressView.findViewById(R.id.pro_done_img);
        waitAssessImg = (ImageView)progressView.findViewById(R.id.pro_wait_assess_img);
        afterSale = (ImageView)progressView.findViewById(R.id.pro_after_sale_img);
        allTv = (TextView)progressView.findViewById(R.id.progress_all_tv);
        waitPayTv = (TextView)progressView.findViewById(R.id.pro_wait_pay_txt);
        waitUseTv = (TextView)progressView.findViewById(R.id.pro_wait_use_txt);
        gogingTv = (TextView)progressView.findViewById(R.id.pro_going_txt);
        doneTv = (TextView)progressView.findViewById(R.id.pro_done_xt);
        waitAssessTv = (TextView)progressView.findViewById(R.id.pro_wait_assess_txt);
        afterSaleTv = (TextView)progressView.findViewById(R.id.pro_after_sale_txt);
        positionTv = (TextView)progressView.findViewById(R.id.progress_position_Tv);
        positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        positionRl.setOnClickListener(this);
        personalRl.setOnClickListener(this);
        allRl.setOnClickListener(this);
        waitPayRl.setOnClickListener(this);
        waitUseRl.setOnClickListener(this);
        goingRl.setOnClickListener(this);
        doneRl.setOnClickListener(this);
        waitAssessRl.setOnClickListener(this);
        afterSaleRl.setOnClickListener(this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == 5){
            positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        }
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
                allImg.setImageResource(R.mipmap.all_pre);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                waitPayImg.setImageResource(R.mipmap.wait_pay_nor);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitUseImg.setImageResource(R.mipmap.wait_use_nor);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                goingImg.setImageResource(R.mipmap.going_nor);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                doneImg.setImageResource(R.mipmap.done_nor);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_nor);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                afterSale.setImageResource(R.mipmap.refund_nor);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                break;
            case R.id.pro_wait_pay_rl:
                allImg.setImageResource(R.mipmap.all_nor);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitPayImg.setImageResource(R.mipmap.wait_pay_pre);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                waitUseImg.setImageResource(R.mipmap.wait_use_nor);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                goingImg.setImageResource(R.mipmap.going_nor);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                doneImg.setImageResource(R.mipmap.done_nor);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_nor);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                afterSale.setImageResource(R.mipmap.refund_nor);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                break;
            case R.id.pro_wait_use_rl:
                allImg.setImageResource(R.mipmap.all_nor);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitPayImg.setImageResource(R.mipmap.wait_pay_nor);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitUseImg.setImageResource(R.mipmap.wait_use_pre);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                goingImg.setImageResource(R.mipmap.going_nor);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                doneImg.setImageResource(R.mipmap.done_nor);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_nor);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                afterSale.setImageResource(R.mipmap.refund_nor);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                break;
            case R.id.pro_going_rl:
                allImg.setImageResource(R.mipmap.all_nor);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitPayImg.setImageResource(R.mipmap.wait_pay_nor);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitUseImg.setImageResource(R.mipmap.wait_use_nor);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                goingImg.setImageResource(R.mipmap.going_pre);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                doneImg.setImageResource(R.mipmap.done_nor);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_nor);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                afterSale.setImageResource(R.mipmap.refund_nor);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                break;
            case R.id.pro_done_rl:
                allImg.setImageResource(R.mipmap.all_nor);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitPayImg.setImageResource(R.mipmap.wait_pay_nor);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitUseImg.setImageResource(R.mipmap.wait_use_nor);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                goingImg.setImageResource(R.mipmap.going_nor);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                doneImg.setImageResource(R.mipmap.done_pre);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_nor);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                afterSale.setImageResource(R.mipmap.refund_nor);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                break;
            case R.id.pro_wait_assess_rl:
                allImg.setImageResource(R.mipmap.all_nor);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitPayImg.setImageResource(R.mipmap.wait_pay_nor);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitUseImg.setImageResource(R.mipmap.wait_use_nor);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                goingImg.setImageResource(R.mipmap.going_nor);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                doneImg.setImageResource(R.mipmap.done_nor);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_pre);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                afterSale.setImageResource(R.mipmap.refund_nor);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                break;
            case R.id.pro_after_sale__rl:
                allImg.setImageResource(R.mipmap.all_nor);
                allTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitPayImg.setImageResource(R.mipmap.wait_pay_nor);
                waitPayTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitUseImg.setImageResource(R.mipmap.wait_use_nor);
                waitUseTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                goingImg.setImageResource(R.mipmap.going_nor);
                gogingTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                doneImg.setImageResource(R.mipmap.done_nor);
                doneTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                waitAssessImg.setImageResource(R.mipmap.wait_assess_nor);
                waitAssessTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.gray_default));
                afterSale.setImageResource(R.mipmap.refund_pre);
                afterSaleTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                break;
            case R.id.progress_position_rl:
                Intent intent=new Intent(getActivity(), CityActivity.class);
                startActivityForResult(intent,3);
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
