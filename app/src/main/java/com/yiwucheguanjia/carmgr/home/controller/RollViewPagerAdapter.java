package com.yiwucheguanjia.carmgr.home.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.WaitActivity;
import com.yiwucheguanjia.carmgr.home.model.RollViewPagerBean;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/19.
 */

public class RollViewPagerAdapter extends StaticPagerAdapter {
    private Activity activity;
    private ArrayList<RollViewPagerBean> rollViewPagerBeens;
    private SharedPreferences sharedPreferences;
    public RollViewPagerAdapter(Activity activity,ArrayList<RollViewPagerBean> rollViewPagerBeens){
        this.activity = activity;
        this.rollViewPagerBeens = rollViewPagerBeens;
        sharedPreferences = activity.getSharedPreferences("CARMGR", activity.MODE_PRIVATE);
    }
    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        Picasso.with(container.getContext()).load(rollViewPagerBeens.get(position).getRollViewPagerUrl()).into(view);

//        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(sharedPreferences.getString("ACCOUNT",null),"1000_1",
                        "轮播图",sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
                        UrlString.APP_LOG_USER_OPERATION,1);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return rollViewPagerBeens.size();
    }
    protected void postData(String username,String click_area_id,String detail,String token,
                            String version,String url,int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("click_area_id",click_area_id.toString())
                .addParams("detail",detail)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new RollViewPagerStringCallback());
    }
    protected class RollViewPagerStringCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id)
            {
                case 1:
                    Log.e("logu",response);
                    break;
                default:
                    break;
            }
        }
    }
}
