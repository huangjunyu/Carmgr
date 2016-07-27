package com.yiwucheguanjia.carmgr.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/6.
 */
public class FavorabledRecommendAdapter extends BaseAdapter{

    private Activity activity;
    private MyViewholder viewHolder;
    private ArrayList<FavorabledRecommendBean> secondHandBeans;
    private LayoutInflater layoutInflater;
    /**
     *
     * @param activity
     * @param list
     */
    public FavorabledRecommendAdapter(Activity activity, ArrayList<FavorabledRecommendBean> list) {
        this.secondHandBeans = list;
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return secondHandBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return secondHandBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) { // 获取组件布局
            convertView = layoutInflater.inflate(R.layout.second_hand_item, null);

            viewHolder = new MyViewholder();
            viewHolder.secondHandImg = (ImageView)convertView.findViewById(R.id.second_hand_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (MyViewholder)convertView.getTag();
        }
        Picasso.with(activity).load(secondHandBeans.get(position).getImgUrl()).error(R.mipmap.picture_default).into(viewHolder.secondHandImg);
        return convertView;
    }

    class MyViewholder {
        public ImageView secondHandImg;
    }
}
