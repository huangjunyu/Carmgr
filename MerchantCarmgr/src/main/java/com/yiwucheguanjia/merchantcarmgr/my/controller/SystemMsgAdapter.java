package com.yiwucheguanjia.merchantcarmgr.my.controller;

/**
 * Created by Administrator on 2016/10/21.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/1.
 */
public class SystemMsgAdapter extends RecyclerView.Adapter<SystemMsgAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Activity activity;

    public SystemMsgAdapter(Activity activity){
        this.activity = activity;
        layoutInflater = LayoutInflater.from(this.activity);
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.system_msg_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("gogo","gogo");
        holder.titleTv.setText("nnnnn");
    }

    @Override
    public int getItemCount() {
        Log.e("3","3");
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.system_msg_title_tv)
        TextView titleTv;
        @BindView(R.id.system_msg_time_tv)
        TextView timeTv;
        @BindView(R.id.system_msg_img)
        ImageView msgImg;
        @BindView(R.id.system_msg_some_tv)
        TextView someTv;
        @BindView(R.id.system_msg_detail_rv)
        RelativeLayout detailRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

