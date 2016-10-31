package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateImgBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RateAdapter extends RecyclerView.Adapter<RateAdapter.HolderView> {

    private Activity activity;
    private ArrayList<RateImgBean> rateImgBeens;
    private LayoutInflater layoutInflater;
    public RateAdapter(Activity activity, ArrayList<RateImgBean> rateImgBeens){
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.rateImgBeens = rateImgBeens;
    }
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.rate_item,parent,false);
        HolderView holderView = new HolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HolderView extends RecyclerView.ViewHolder{
        @BindView(R.id.rate_user_img)
        ImageView headerImg;

        public HolderView(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
