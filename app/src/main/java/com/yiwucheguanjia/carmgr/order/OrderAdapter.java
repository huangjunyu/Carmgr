package com.yiwucheguanjia.carmgr.order;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.HolderView> {

    private Activity activity;
    private LayoutInflater layoutInflater;

    /*
    * @param starNum 评价的星数
    * */
    public OrderAdapter(Activity activity) {
        layoutInflater = LayoutInflater.from(activity);
        this.activity = activity;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_order, parent, false);
        HolderView holderView = new HolderView(view, this.activity);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position) {
//        Glide.with(activity).load(R.mipmap.defualt_header).error(R.mipmap.default_image).into(holder.headerImg);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class HolderView extends RecyclerView.ViewHolder {
        private Activity activity;

        public HolderView(View view, Activity activity) {
            super(view);
            this.activity = activity;
            ButterKnife.bind(this, view);
        }

    }

}
