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
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.city.CityActivity;
import com.yiwucheguanjia.merchantcarmgr.city.bean.CityModel;
import com.yiwucheguanjia.merchantcarmgr.city.utils.KeyBoard;
import com.yiwucheguanjia.merchantcarmgr.city.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class CitySortAdapter extends RecyclerView.Adapter<CitySortAdapter.ViewHolder> implements SectionIndexer {
    private LayoutInflater mInflater;
    private List<CityModel> cityModels;
    private Context context;
    private SharedPreferences sharedPreferences;
private CityActivity cityActivity;
    public CitySortAdapter(CityActivity cityActivity, List<CityModel> cityModels) {
        Log.e("seze", cityModels.size() + "kkl");
        this.cityModels = cityModels;
        this.cityActivity = cityActivity;
        mInflater = LayoutInflater.from(cityActivity);
        sharedPreferences = cityActivity.getSharedPreferences("CARMGR", cityActivity.MODE_PRIVATE);
    }


    @Override
    public int getItemCount() {
        return cityModels.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_city_selecter,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }
    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView，动态刷新listview的数据源
     */
    public void updateListView(List<CityModel> list){
        this.cityModels = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CityModel merchantItemBean = cityModels.get(position);
        Log.e("cityn", "=--ew");
        final CityModel mContent = cityModels.get(position);
//        holder.cityNameTv.setText(merchantItemBean.getName());
//        holder.cityNameTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.cityLetterTv.setVisibility(View.VISIBLE);
            holder.cityLetterTv.setText(mContent.getFirstName());
        } else {
            holder.cityLetterTv.setVisibility(View.GONE);
        }

        Log.e("tvtitle", "kjwiia;;;");
        holder.cityNameTv.setText(this.cityModels.get(position).getName());

        holder.cityNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                    这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String cityName = merchantItemBean.getName();
                Log.e("citynnn","knwkek");
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

    public int getCount() {
        return this.cityModels.size();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = cityModels.get(i).getFirstName();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return cityModels.get(position).getFirstName().charAt(0);
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cityNameTv;
        private TextView cityLetterTv;

        public ViewHolder(View viewHolder) {
            super(viewHolder);
            cityNameTv = (TextView) viewHolder.findViewById(R.id.selecter_city_name);
            cityLetterTv = (TextView) viewHolder.findViewById(R.id.catalog);

        }

    }

}

