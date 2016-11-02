package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateImgBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RateImgAdapter extends RecyclerView.Adapter<RateImgAdapter.HolderView> {

    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<RateImgBean> rateImgBeens;
    public RateImgAdapter(Activity activity, ArrayList<RateImgBean> rateImgBeens){
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        this.rateImgBeens = rateImgBeens;
    }
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rate_img,parent,false);
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
        public HolderView(View view){
            super(view);
        }
    }
}
