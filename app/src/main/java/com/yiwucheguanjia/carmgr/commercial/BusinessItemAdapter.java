package com.yiwucheguanjia.carmgr.commercial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 */
public class BusinessItemAdapter extends BaseAdapter{
    private LayoutInflater myInflater;
    Activity activity;
    private ArrayList<BusinessSelectItemBean> businessSelectItems;
    public BusinessItemAdapter(Activity activity, ArrayList<BusinessSelectItemBean> businessSelectItemBeen){
        myInflater = LayoutInflater.from(activity);
        this.businessSelectItems = businessSelectItemBeen;
    }
    @Override
    public int getCount() {
        return businessSelectItems.size();
    }

    @Override
    public Object getItem(int position) {
        return businessSelectItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyItemViewHolder myItemViewHolder = new MyItemViewHolder();
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.spinner_item,null);
            myItemViewHolder.businessItem = (TextView)convertView.findViewById(R.id.spinner_item);
            convertView.setTag(myItemViewHolder);
        }else {
            myItemViewHolder = (MyItemViewHolder)convertView.getTag();
        }
        BusinessSelectItemBean businessSelectItemBean = businessSelectItems.get(position);
        myItemViewHolder.businessItem.setText(businessSelectItemBean.getBusinessItemStr());
        return convertView;
    }
    class MyItemViewHolder{
        private TextView businessItem;
    }
}
