package com.yiwucheguanjia.merchantcarmgr.city.adapter;

/**
 * Created by Administrator on 2016/9/23.
 */

import android.content.Context;
import android.content.SharedPreferences;
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

    public HotCityRecyclerAdapter(CityActivity cityActivity, ArrayList<RegionInfo> hotCitys) {
        Log.e("seze", hotCitys.size() + "kkl");
        this.cityActivity = cityActivity;
        this.regionInfos = hotCitys;
        mInflater = LayoutInflater.from(cityActivity);
        sharedPreferences = cityActivity.getSharedPreferences("CARMGR", cityActivity.MODE_PRIVATE);
    }


    @Override
    public int getItemCount() {
//        Log.e("zi", regionInfos.size() + "");
        return regionInfos.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        Log.e("zi"," regionInfos.size()");
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
                //                                    这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String cityName = merchantItemBean.getName();
                if (cityName != null && cityName.length() > 0) {
//                    Toast.makeText(CityActivity.this, cityName, Toast.LENGTH_SHORT).show();
                    KeyBoard.closeSoftKeyboard(cityActivity);
                    SharedPreferencesUtils.saveCityName(cityActivity, cityName);
                    Log.e("cityn", cityName + "ew");
//                    cityActivity.filterData(cityName);
                    cityActivity.searchKey(cityName);

//                    Intent intent = new Intent();
//                    setResult(10);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                            finish();
//                            Log.e("htise","iwiiwiiw");
//                            setPopupwindow();
//                            //此处选择地区后进入其他界面
//
//
//                        }
//                    }, 500);
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

