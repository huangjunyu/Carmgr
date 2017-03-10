package com.yiwucheguanjia.carmgr.home.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.home.model.HotRecommendBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/6.
 */
public class HotRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<HotRecommendBean.ServicesListBean> hotRecommendBeens;
    private LayoutInflater layoutInflater;
    public HotRecommendAdapter(Activity activity,ArrayList<HotRecommendBean.ServicesListBean> hotRecommendBeens){
        this.activity = activity;
        this.hotRecommendBeens = hotRecommendBeens;
        layoutInflater = LayoutInflater.from(activity);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.hot_recommend_img);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.hot_recommend,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //设置图片
        Picasso.with(activity).load(hotRecommendBeens.get(position).getImg_path()).error(R.mipmap.picture_default).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return hotRecommendBeens == null ? 0 : hotRecommendBeens.size();
    }

}
