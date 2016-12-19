package com.yiwucheguanjia.merchantcarmgr.city.adapter;

/**
 * Created by Administrator on 2016/9/23.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.CityActivity;
import com.yiwucheguanjia.merchantcarmgr.city.bean.RegionInfo;
import com.yiwucheguanjia.merchantcarmgr.city.utils.KeyBoard;
import com.yiwucheguanjia.merchantcarmgr.city.utils.SharedPreferencesUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 */
public class HotCityRecyclerAdapter extends RecyclerView.Adapter<HotCityRecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Integer> mDatas;
    private ArrayList<RegionInfo> regionInfos;
    private CityActivity cityActivity;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private static final int HOT_CITY_CALLBACK = 1;
    public HotCityRecyclerAdapter(CityActivity cityActivity, ArrayList<RegionInfo> hotCitys, Handler handler) {
        Log.e("seze", hotCitys.size() + "789");
        this.cityActivity = cityActivity;
        this.regionInfos = hotCitys;
        mInflater = LayoutInflater.from(cityActivity);
        sharedPreferences = cityActivity.getSharedPreferences("CARMGR", cityActivity.MODE_PRIVATE);
        this.handler = handler;
    }


    @Override
    public int getItemCount() {
        return regionInfos.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_hot_city,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RegionInfo merchantItemBean = regionInfos.get(position);
        holder.cityNameTv.setText(merchantItemBean.getName());
        holder.cityNameTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("hotv",merchantItemBean.getName());
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String cityName = merchantItemBean.getName();

                Bundle cityBundle = new Bundle();
                cityBundle.putString("hot_city",merchantItemBean.getName());
                Message message = new Message();
                message.what = HOT_CITY_CALLBACK;
                message.setData(cityBundle);
                handler.sendMessage(message);

                if (cityName != null && cityName.length() > 0) {
                    KeyBoard.closeSoftKeyboard(cityActivity);
                    SharedPreferencesUtils.saveCityName(cityActivity, cityName);
//                    cityActivity.filterData(cityName);
                    cityActivity.searchKey(cityName);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cityNameTv;

        public ViewHolder(View viewHolder) {
            super(viewHolder);
            cityNameTv = (TextView) viewHolder.findViewById(R.id.hot_city_name_tv);
        }

    }

}

