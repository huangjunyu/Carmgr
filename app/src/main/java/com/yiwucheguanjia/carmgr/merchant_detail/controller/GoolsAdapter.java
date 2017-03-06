package com.yiwucheguanjia.carmgr.merchant_detail.controller;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.merchant_detail.GoolsBean;
import com.yiwucheguanjia.carmgr.utils.RoundRectImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
public class GoolsAdapter extends BaseQuickAdapter<GoolsBean, BaseViewHolder> {
    Activity activity;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public GoolsAdapter(Activity activity,List<GoolsBean> data) {
        super(R.layout.item_gools, data);
        this.activity = activity;
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param goolsBean   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, GoolsBean goolsBean) {
        Glide.with(activity).load(goolsBean.getPicUrl()).error(R.mipmap.yangmin)
                .into((RoundRectImageView)helper.getView(R.id.item_gools_pic));
        helper.setText(R.id.item_gools_title,goolsBean.getServiceTitle());
        helper.setText(R.id.item_gools_privilege_tv,goolsBean.getPrivilegePrice());
        helper.setText(R.id.item_gools_retail_tv,goolsBean.getRetailPrice());
        helper.setText(R.id.item_gools_saled_tv,goolsBean.getSaled());
    }
}
