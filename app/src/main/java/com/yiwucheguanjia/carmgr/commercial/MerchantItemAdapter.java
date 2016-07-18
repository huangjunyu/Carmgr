package com.yiwucheguanjia.carmgr.commercial;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.Tools;

import java.lang.reflect.Array;
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
            myItemViewHolder.merchantStarsLL = (RelativeLayout)convertView.findViewById(R.id.merchant_stars_rl);
            myItemViewHolder.merchantNameRl = (RelativeLayout)convertView.findViewById(R.id.merchant_name_rl);
            myItemViewHolder.merchantAddress = (TextView)convertView.findViewById(R.id.merchant_address);
            convertView.setTag(myItemViewHolder);
        }else {
            myItemViewHolder = (MyItemViewHolder)convertView.getTag();
        }
        MerchantItemBean merchantItemBean = businessItemBeans.get(position);
        myItemViewHolder.merchantName.setText(merchantItemBean.getMerchantName());
        myItemViewHolder.merchantAddress.setText(merchantItemBean.getMerchantAddress());
        myItemViewHolder.merchantStars.setText(merchantItemBean.getMerchantStars() + "");
        String numberStr = merchantItemBean.getMerchantStarsStr();
        if (Tools.isInteger(merchantItemBean.getMerchantStarsStr())){
            for (int i = 0;i < Integer.parseInt(numberStr);i++){
                myItemViewHolder.merchantStarsLL.addView(Tools.getInstance().createImageview(activity,i));
            }
        }else {
            int starNumber = (int) Math.floor(merchantItemBean.getMerchantStars());

            for (int i = 0;i < starNumber;i++){
                myItemViewHolder.merchantStarsLL.addView(Tools.getInstance().createImageview(activity,i));
            }
            myItemViewHolder.merchantStarsLL.addView(Tools.getInstance().createImageview2(activity,starNumber));
        }
        //动态显示商家提供的服务项目
        if ((extractString(merchantItemBean.getMerchantTag()).length) >= 1){
            Log.e("size",extractString(merchantItemBean.getMerchantTag()).length + "");
            int serviceNum = (extractString(merchantItemBean.getMerchantTag()).length);
            for (int k = 0;k < serviceNum;k++){
//                Tools.getInstance().createImageView3(activity,extractString(merchantItemBean.getMerchantTag()),k);
                myItemViewHolder.merchantNameRl.addView(Tools.getInstance().createImageView3(activity,extractString(merchantItemBean.getMerchantTag()),k));
            }
        }
//        extractString(merchantItemBean.getMerchantTag());

        Picasso.with(this.activity).load(merchantItemBean.getMerchantImgUrl())
                .placeholder(R.mipmap.picture_default)
                .error(R.mipmap.picture_default).into(myItemViewHolder.merchantImg);
        return convertView;
    }

    public String[] extractString(String tags){
        String string = tags;
        String[] b = string.split("\\|");  //注意这里用两个 \\，而不是一个\
        for (int j = 0;j < b.length;j++){
        System.out.println("处理结果: "+b[j] + j);   //输出的是: 处理结果: 5678,XYZ
        }
        return b;
    }
    class MyItemViewHolder{
        private RelativeLayout merchantStarsLL;
        private RelativeLayout merchantNameRl;
        private TextView merchantName;
        private TextView merchantStars;
        private TextView merchantAddress;
        private TextView merchantMobile;
        private ImageView merchantImg;
    }
}
