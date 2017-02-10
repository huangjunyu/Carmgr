package com.yiwucheguanjia.carmgr.progress.controller;

/**
 * 系统消息适配器.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/1.
 */
public class ServiceTypeOnMapAdapter extends RecyclerView.Adapter<ServiceTypeOnMapAdapter.ViewHolder> {
    Vector<Boolean> vector = new Vector<>();
    private LayoutInflater layoutInflater;
    private Activity activity;
    private String[] serviceTypes;
    private Handler handler;

    private OnItemClickListener mOnItemClickListener = null;

    public ServiceTypeOnMapAdapter(Activity activity, String[] serviceTypes, Handler handler){
        this.activity = activity;
        layoutInflater = LayoutInflater.from(this.activity);
        this.serviceTypes = serviceTypes;
        this.handler = handler;
        for (String s : serviceTypes){
            vector.add(false);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_service_type,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(vector.get(position)){
            holder.brandNameTv.setTextColor(ContextCompat.getColor(activity,R.color.orange));
        }else{
            holder.brandNameTv.setTextColor(ContextCompat.getColor(activity,R.color.buseness_black));
        }
        holder.brandNameTv.setText(serviceTypes[position]);
        holder.itemView.setTag(holder.brandNameTv);
//        holder.brandNameTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("carBrand", serviceTypes[position]);
//                Message message = Message.obtain();
//                message.what = 0;
//                message.setData(bundle);
//                handler.sendMessage(message);
//            }
//        });

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i <vector.size();i++){
                        vector.set(i,false);
                    }
                    vector.set(position,true);
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(holder.itemView,holder.brandNameTv,position);
                }
            });
        }
    }
    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public int getItemCount() {
        return serviceTypes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.it_service_type_name)
        TextView brandNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, TextView textView,int position);
    }
}

