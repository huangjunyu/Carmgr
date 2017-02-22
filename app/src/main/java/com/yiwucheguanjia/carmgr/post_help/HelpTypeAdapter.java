package com.yiwucheguanjia.carmgr.post_help;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yiwucheguanjia.carmgr.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
public class HelpTypeAdapter extends BaseQuickAdapter<HelpBean, BaseViewHolder> {
    Activity activity;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public HelpTypeAdapter( List<HelpBean> data) {
        super(R.layout.item_help_type, data);
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param helpBean   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, HelpBean helpBean) {
        helper.setText(R.id.help_type,helpBean.getHelpType());
    }
}
