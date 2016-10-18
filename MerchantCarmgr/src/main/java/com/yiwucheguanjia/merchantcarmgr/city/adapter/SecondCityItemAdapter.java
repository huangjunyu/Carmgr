package com.yiwucheguanjia.merchantcarmgr.city.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.MainActivity;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.bean.SecondCityModel;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/1.
 */
public class SecondCityItemAdapter extends RecyclerView.Adapter<SecondCityItemAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Integer> mDatas;
    private ArrayList<SecondCityModel> recyclerBeens;
    private Context context;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    public SecondCityItemAdapter(Context context, ArrayList<SecondCityModel> recyclerBeens, Handler handler) {
        Log.e("seze", "kkl");
        this.recyclerBeens = recyclerBeens;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("CARMGR", context.MODE_PRIVATE);
        this.handler = handler;
    }


    @Override
    public int getItemCount() {
        return recyclerBeens.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_second_city_name,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final SecondCityModel merchantItemBean = recyclerBeens.get(i);
        viewHolder.cityNameTv.setText(merchantItemBean.getSecondCityName().toString());
        viewHolder.cityNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("re",merchantItemBean.getSecondCityName().toString());
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                handler.sendEmptyMessage(0);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cityNameTv;
        public ViewHolder(View viewHolder) {
            super(viewHolder);
            cityNameTv = (TextView) viewHolder.findViewById(R.id.city_name_name);
        }

    }
}
