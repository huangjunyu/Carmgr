package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
//import com.squareup.picasso.Picasso;
//import com.yiwucheguanjia.carmgr.WaitActivity;
//import com.yiwucheguanjia.carmgr.utils.StringCallback;
//import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RollViewPagerBean;
//import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/19.
 */

public class RollViewPagerAdapter extends LoopPagerAdapter {
    private Activity activity;
    private ArrayList<RollViewPagerBean> rollViewPagerBeens;
    private SharedPreferences sharedPreferences;
//    public RollViewPagerAdapter(Activity activity, ArrayList<RollViewPagerBean> rollViewPagerBeens){
//        this.activity = activity;
//        this.rollViewPagerBeens = rollViewPagerBeens;
//        sharedPreferences = activity.getSharedPreferences("CARMGR", activity.MODE_PRIVATE);
//    }
//    public RollViewPagerAdapter(Activity activity){
//        this.activity = activity;
//    }

    private int[] imgs = {
            R.mipmap.appoint_manage_nor,
            R.mipmap.appoint_manage_pre,
            R.mipmap.goback,
            R.mipmap.default_image,
    };

    public RollViewPagerAdapter(RollPagerView viewPager,Activity activity) {
        super(viewPager);
        this.activity = activity;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
//        Picasso.with(container.getContext()).load(rollViewPagerBeens.get(position).getRollViewPagerUrl()).into(view);
//        view.setImageResource(imgs[position]);
        Glide.with(activity).load(imgs[position]).error(R.mipmap.default_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);

        /*
        *         Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
        * */
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postData(sharedPreferences.getString("ACCOUNT",null),"1000_1",
//                        "轮播图",sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
//                        UrlString.APP_LOG_USER_OPERATION,1);
            }
        });
        return view;
    }

//    @Override
//    public int getCount() {
////        return rollViewPagerBeens.size();
//        return 4;
//    }

    @Override
    protected int getRealCount() {
        return 4;
    }
/*    protected void postData(String username,String click_area_id,String detail,String token,
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
    }*/
/*    protected class RollViewPagerStringCallback extends StringCallback{

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
    }*/
}
