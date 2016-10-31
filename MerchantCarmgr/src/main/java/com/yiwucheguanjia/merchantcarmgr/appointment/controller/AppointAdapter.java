package com.yiwucheguanjia.merchantcarmgr.appointment.controller;

import android.app.Activity;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
