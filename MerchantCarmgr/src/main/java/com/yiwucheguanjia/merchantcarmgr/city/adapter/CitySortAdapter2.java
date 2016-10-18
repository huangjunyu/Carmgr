package com.yiwucheguanjia.merchantcarmgr.city.adapter;

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
 * Created by qibin on 2015/11/7.
 */
public class CitySortAdapter2 extends BaseRecyclerAdapter<List<CityModel>> implements SectionIndexer {
    private List<CityModel> cityModels;
    private CityActivity cityActivity;
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_selecter, parent, false);
        return new MyHolder(layout);
    }
    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView，动态刷新listview的数据源
     */
    public void updateListView(List<CityModel> list){
        this.cityModels = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, List<CityModel> data) {
        Log.e("data",data.size() + ";");
        final CityModel mContent = cityModels.get(RealPosition);
        final CityModel merchantItemBean = cityModels.get(RealPosition);
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(RealPosition);

        if(viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).cityNameTv.setText(data.get(RealPosition).getName());
            ((MyHolder) viewHolder).cityNameTv.setOnClickListener(new View.OnClickListener() {
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
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (RealPosition == getPositionForSection(section)) {
            ((MyHolder)viewHolder).cityLetterTv.setVisibility(View.VISIBLE);
            ((MyHolder)viewHolder).cityLetterTv.setText(mContent.getFirstName());
        } else {
            ((MyHolder)viewHolder).cityLetterTv.setVisibility(View.GONE);
        }
        }
    }

    public CitySortAdapter2(CityActivity cityActivity,List<CityModel> cityModels){
        Log.e("size",cityModels.size() + ";");
        this.cityActivity = cityActivity;
        this.cityModels = cityModels;
    }
    @Override
    public Object[] getSections() {
        return new Object[0];
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
    public int getCount() {
        return this.cityModels.size();
    }
    class MyHolder extends BaseRecyclerAdapter.Holder {
        private TextView cityNameTv;
        private TextView cityLetterTv;
        public MyHolder(View itemView) {
            super(itemView);
            cityNameTv = (TextView) itemView.findViewById(R.id.selecter_city_name);
            cityLetterTv = (TextView) itemView.findViewById(R.id.catalog);
        }
    }
}


