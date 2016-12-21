package com.yiwucheguanjia.merchantcarmgr.appointment.controller;

import android.app.Activity;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.appointment.model.AppointmentBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28.
 */
public class AppointAdapter extends RecyclerView.Adapter<AppointAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Activity activity;
    private ArrayList<AppointmentBean> appointmentBeanArrayList;
    public AppointAdapter(Activity activity, ArrayList<AppointmentBean> appointmentBeanArrayList){
        this.activity = activity;
        this.appointmentBeanArrayList = appointmentBeanArrayList;
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
        AppointmentBean appointmentBean = appointmentBeanArrayList.get(position);
        Glide.with(activity).load(appointmentBean.getSubscribe_service_img()).error(R.mipmap.default_image).centerCrop().into(holder.goodsImg);
        holder.orderNumTv.setText(appointmentBean.getSubscribe_order());
//        holder.customerTv.setText(appointmentBean.get);
//        holder.operateTv.setText(appointmentBean.get;
        holder.phoneTv.setText(appointmentBean.getSubscribe_user_mobile());
        holder.goodsTv.setText(appointmentBean.getSubscribe_service_name());
        holder.goodsNumTv.setText(appointmentBean.getSubscribe_service_total());
        holder.priceTv.setText(appointmentBean.getSubscribe_price());
        holder.customerTv.setText(appointmentBean.getSubscribe_user_mobile());
    }

    @Override
    public int getItemCount() {
        return appointmentBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.allappoint_order_num_tv)
        TextView orderNumTv;
        @BindView(R.id.allappoint_customer_tv)
        TextView customerTv;
        @BindView(R.id.allappoint_operate)
        TextView operateTv;
        @BindView(R.id.allappoint_phone_tv)
        TextView phoneTv;
        @BindView(R.id.allapoint_goods_img)
        ImageView goodsImg;
        @BindView(R.id.allappoint_goods_name_tv)
        TextView goodsTv;
        @BindView(R.id.appoin_goods_num_tv)
        TextView goodsNumTv;
        @BindView(R.id.appoint_price_tv)
        TextView priceTv;
        @BindView(R.id.it_appoint_refuse_tv)
        TextView refuseTv;
        @BindView(R.id.it_appoin_edit_tv)
        TextView editTv;
        @BindView(R.id.it_appoin_accept_tv)
        TextView acceptTv;
        Activity activity;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
//            this.activity = activity;
        }
    }
}
