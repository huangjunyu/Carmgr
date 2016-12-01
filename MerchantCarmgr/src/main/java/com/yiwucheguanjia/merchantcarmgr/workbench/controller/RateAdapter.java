package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.checkpictureutils.ImagePagerActivity;
import com.yiwucheguanjia.merchantcarmgr.checkpictureutils.ItemEntity;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RateAdapter extends RecyclerView.Adapter<RateAdapter.HolderView> {

    private Activity activity;
    private ArrayList<ItemEntity> itemEntities;
    private LayoutInflater layoutInflater;

    /*
    * @param starNum 评价的星数
    * */
    public RateAdapter(Activity activity, ArrayList<ItemEntity> itemEntities,int starNum) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.itemEntities = itemEntities;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.rate_item, parent, false);
        HolderView holderView = new HolderView(view, this.activity);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position) {
        Log.e("itementi", itemEntities.size() + "");
        ItemEntity itemEntity = itemEntities.get(position);
        Glide.with(activity).load(R.mipmap.defualt_header).error(R.mipmap.default_image).into(holder.headerImg);
        holder.rateContent.setText(itemEntity.getContent());
        holder.nickNameTv.setText(itemEntity.getTime());
        final ArrayList<String> imageUrls = itemEntity.getImageUrls();
        if (imageUrls == null || imageUrls.size() == 0) {
            holder.rateImgRv.setVisibility(View.GONE);
        } else {
            for (int i = 0 ;i < imageUrls.size();i++){

                Log.e("item", imageUrls.get(i) + "n");

            }
            holder.rateImgRv.setAdapter(new RateImgAdapter(activity, imageUrls));
        }
    }

    @Override
    public int getItemCount() {
        return itemEntities == null ? 0 : itemEntities.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.rate_user_img)
        ImageView headerImg;
        @BindView(R.id.rate_item_img_rv)
        RecyclerView rateImgRv;
        @BindView(R.id.rate_rate_tv)
        TextView rateContent;
        @BindView(R.id.rate_user_name_tv)
        TextView nickNameTv;
        private Activity activity;

        public HolderView(View view, Activity activity) {
            super(view);
            this.activity = activity;
            ButterKnife.bind(this, view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            RateImgAdapter rateImgAdapter = new RateImgAdapter(this.activity, null);
            rateImgRv.setLayoutManager(linearLayoutManager);
            rateImgRv.setAdapter(rateImgAdapter);
        }

    }

}
