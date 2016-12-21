package com.yiwucheguanjia.merchantcarmgr.post.controller;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.post.model.ServiceItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 已发布服务适配器.
 */
public class PostedAdapter extends RecyclerView.Adapter<PostedAdapter.HolderView> {

    private Activity activity;
    private ArrayList<ServiceItemBean> serviceItemBeanArrayList;
    private LayoutInflater layoutInflater;

    public PostedAdapter(Activity activity, ArrayList<ServiceItemBean> serviceItemBeanArrayList) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.serviceItemBeanArrayList = serviceItemBeanArrayList;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.post_item, parent, false);
        HolderView holderView = new HolderView(view, this.activity);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        ServiceItemBean serviceItemBean = serviceItemBeanArrayList.get(position);
        Log.e("imgpaht",serviceItemBean.getImg_path());
        Glide.with(activity).load(serviceItemBean.getImg_path()).error(R.mipmap.default_image).centerCrop().into(holder.serviceImg);
        holder.serviceNameTv.setText(serviceItemBean.getService_name());
        holder.stateTv.setText(serviceItemBean.getState());
        holder.postTimeTv.setText(serviceItemBean.getState());
        holder.access.setText(serviceItemBean.getAccess_times() + " 次");
    }

    @Override
    public int getItemCount() {
        return serviceItemBeanArrayList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.post_item_img)
        ImageView serviceImg;
        @BindView(R.id.post_it_servic_name_tv)
        TextView serviceNameTv;
        @BindView(R.id.post_it_state_tv)
        TextView stateTv;
        @BindView(R.id.post_it_time_tv)
        TextView postTimeTv;
        @BindView(R.id.post_it_access)
        TextView access;
        private Activity activity;

        public HolderView(View view, Activity activity) {
            super(view);
            ButterKnife.bind(this, view);
            this.activity = activity;
        }
    }
}
