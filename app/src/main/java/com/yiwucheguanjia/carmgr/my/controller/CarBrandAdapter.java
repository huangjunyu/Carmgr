package com.yiwucheguanjia.carmgr.my.controller;

/**
 * 系统消息适配器.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/1.
 */
public class CarBrandAdapter extends RecyclerView.Adapter<CarBrandAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Activity activity;
    private String[] brands;
    private Handler handler;
    public CarBrandAdapter(Activity activity, String[] brands, Handler handler){
        this.activity = activity;
        layoutInflater = LayoutInflater.from(this.activity);
        this.brands = brands;
        this.handler = handler;
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_car_brand,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.brandNameTv.setText(brands[position]);
        holder.brandNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("carBrand",brands[position]);
                Message message = Message.obtain();
                message.what = 0;
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brands.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.brand_name)
        TextView brandNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

