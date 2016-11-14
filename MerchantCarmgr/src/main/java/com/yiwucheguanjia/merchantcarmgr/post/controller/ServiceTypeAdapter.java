package com.yiwucheguanjia.merchantcarmgr.post.controller;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<String> typeList;
    private Handler handler;
    public ServiceTypeAdapter(Activity activity,ArrayList<String> typeList,Handler handler){
        this.activity = activity;
        this.typeList = typeList;
        this.handler = handler;
        layoutInflater = LayoutInflater.from(this.activity);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_service_type,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemTv.setText(typeList.get(position));
        holder.itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.service_type_item)
        TextView itemTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
