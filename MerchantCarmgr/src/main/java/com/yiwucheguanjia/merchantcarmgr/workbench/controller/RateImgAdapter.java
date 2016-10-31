package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateImgBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RateImgAdapter extends RecyclerView.Adapter<RateImgAdapter.HolderView> {

    private Activity activity;
    private ArrayList<RateImgBean> rateImgBeens;
    public RateImgAdapter(Activity activity, ArrayList<RateImgBean> rateImgBeens){
        this.activity = activity;
        this.rateImgBeens = rateImgBeens;
    }
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return rateImgBeens.size();
    }

    public class HolderView extends RecyclerView.ViewHolder{
        public HolderView(View view){
            super(view);
        }
    }
}
