package com.yiwucheguanjia.carmgr.commercial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 */
public class MerchantItemAdapter extends BaseAdapter{
    private LayoutInflater myInflater;
    private Activity activity;
    private ArrayList<MerchantItemBean> businessItemBeans;
    public MerchantItemAdapter(Activity activity, ArrayList<MerchantItemBean> businessSelectItemBeen){
        myInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.businessItemBeans = businessSelectItemBeen;
    }
    @Override
    public int getCount() {
        return businessItemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return businessItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyItemViewHolder myItemViewHolder = new MyItemViewHolder();
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.merchant_item,null);
            myItemViewHolder.merchantName = (TextView)convertView.findViewById(R.id.merchant_name_txt);
            myItemViewHolder.merchantImg = (ImageView)convertView.findViewById(R.id.merchant_img);
            myItemViewHolder.merchantStars = (TextView) convertView.findViewById(R.id.merchant_stars_txt);
            convertView.setTag(myItemViewHolder);
        }else {
            myItemViewHolder = (MyItemViewHolder)convertView.getTag();
        }
        MerchantItemBean merchantItemBean = businessItemBeans.get(position);
        myItemViewHolder.merchantName.setText(merchantItemBean.getMerchantName());
        myItemViewHolder.merchantStars.setText(merchantItemBean.getMerchantStars());
        Picasso.with(this.activity).load(merchantItemBean.getMerchantImgUrl())
                .placeholder(R.mipmap.picture_default)
                .error(R.mipmap.picture_default).into(myItemViewHolder.merchantImg);
        return convertView;
    }
    class MyItemViewHolder{
        private TextView merchantName;
        private TextView merchantStars;
        private TextView merchantAddress;
        private TextView merchantMobile;
        private ImageView merchantImg;
    }
}
