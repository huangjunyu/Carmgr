package com.yiwucheguanjia.carmgr.progress;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.CircularImage;
import com.yiwucheguanjia.carmgr.utils.RoundRectImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 */
public class ProgressAdapter extends BaseAdapter {
    private ArrayList<MerchantBean> merchantBeenList;
    private LayoutInflater mInflater;
    private Activity activity;
    public ProgressAdapter(Activity activity,ArrayList<MerchantBean> merchantBeenList){
        mInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.merchantBeenList = merchantBeenList;
    }
    @Override
    public int getCount() {
        return merchantBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return merchantBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyadapterHolder myadapterHolder = new MyadapterHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.progress_merchant_item, null,false);
            myadapterHolder.merchantNameTxt = (TextView)convertView.findViewById(R.id.merchant_provider_txt);
            myadapterHolder.payStatusTxt = (TextView)convertView.findViewById(R.id.merchant_pay_status);
            myadapterHolder.serviceTypeTxt = (TextView)convertView.findViewById(R.id.merchant_service_type);
            myadapterHolder.orderNumberTxt = (TextView)convertView.findViewById(R.id.merchant_order_number);
            myadapterHolder.orderTimeText = (TextView)convertView.findViewById(R.id.merchant_order_time);
            myadapterHolder.orderOperate = (TextView)convertView.findViewById(R.id.merchant_operate_order);
            myadapterHolder.urgingProgress = (TextView)convertView.findViewById(R.id.merchat_urgingProgress);
            myadapterHolder.merchantImg = (RoundRectImageView) convertView.findViewById(R.id.merchant_img);
            convertView.setTag(myadapterHolder);
        }else {
            myadapterHolder = (MyadapterHolder)convertView.getTag();
        }
        MerchantBean merchantBean = merchantBeenList.get(position);
/*        myadapterHolder.merchantNameTxt.setText(merchantBean.getMerchantStr());
        myadapterHolder.payStatusTxt.setText(merchantBean.getPayStatusStr());
        myadapterHolder.serviceTypeTxt.setText(merchantBean.getServicTypeStr());
        myadapterHolder.orderNumberTxt.setText(merchantBean.getOrderNumberStr());
        myadapterHolder.urgingProgress.setText(merchantBean.getUrgingOrder());*/

        return convertView;
    }
    class MyadapterHolder{
        private TextView merchantNameTxt;//服务商名称
        private TextView payStatusTxt;//支付状态
        private TextView serviceTypeTxt;//服务类型
        private TextView orderNumberTxt;//订单号
        private TextView orderTimeText;//下单时间
        private TextView orderOperate;//订单操作
        private TextView urgingProgress;//催进度
//        talkContent,replies,hits,lastpostUsername,lastpostTime;
        private RoundRectImageView merchantImg;
        public LinearLayout layout;
    }
}
