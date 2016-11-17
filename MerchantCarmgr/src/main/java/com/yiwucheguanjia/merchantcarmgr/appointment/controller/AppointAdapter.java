package com.yiwucheguanjia.merchantcarmgr.appointment.controller;

import android.app.Activity;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;

/**
 * Created by Administrator on 2016/10/28.
 */
public class AppointAdapter extends RecyclerView.Adapter<AppointAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Activity activity;
    public AppointAdapter(Activity activity){
        this.activity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_appointment,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
