package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateImgBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户投诉适配器.
 */
public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.HolderView> {

    private Activity activity;
    private ArrayList<RateImgBean> rateImgBeens;
    private LayoutInflater layoutInflater;

    public ComplaintAdapter(Activity activity, ArrayList<RateImgBean> rateImgBeens) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.rateImgBeens = rateImgBeens;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.complain_item, parent, false);
        HolderView holderView = new HolderView(view, this.activity);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.complain_user_img)
        ImageView headerImg;
        private Activity activity;

        public HolderView(View view, Activity activity) {
            super(view);
            ButterKnife.bind(this, view);
            this.activity = activity;
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            RateImgAdapter rateImgAdapter = new RateImgAdapter(this.activity, null);
//            complainImgRv.setLayoutManager(linearLayoutManager);
//            complainImgRv.setAdapter(rateImgAdapter);
        }
    }
}
