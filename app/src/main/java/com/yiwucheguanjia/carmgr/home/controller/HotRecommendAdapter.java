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
//    private MyViewholder viewHolder;
    private ArrayList<HotRecommendBean> hotRecommendBeens;
    private LayoutInflater layoutInflater;
    public HotRecommendAdapter(Activity activity,ArrayList<HotRecommendBean> hotRecommendBeens){
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
        Picasso.with(activity).load(hotRecommendBeens.get(position).getHotRecommendUrlImg()).error(R.mipmap.picture_default).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return hotRecommendBeens == null ? 0 : hotRecommendBeens.size();
    }


//    private Activity activity;
//    private MyViewholder viewHolder;
//    private ArrayList<HotRecommendBean> list;
//    private LayoutInflater layoutInflater;
//    /**
//     *
//     * @param activity
//     * @param list
//     */
//    public HotRecommendAdapter(Activity activity, ArrayList<HotRecommendBean> list) {
//        this.list = list;
//        this.activity = activity;
//        this.layoutInflater = LayoutInflater.from(activity);
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) { // 获取组件布局
//            convertView = layoutInflater.inflate(R.layout.hot_recommend, null);
//
//            viewHolder = new MyViewholder();
//            viewHolder.hotRecommendImg = (ImageView)convertView.findViewById(R.id.hot_recommend_img);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (MyViewholder)convertView.getTag();
//        }
//        viewHolder.hotRecommendImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, WaitActivity.class);
//                activity.startActivity(intent);
//            }
//        });
//
//        return convertView;
//    }
//
//    class MyViewholder {
//        public ImageView hotRecommendImg;
//    }
}
