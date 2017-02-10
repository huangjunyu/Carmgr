package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
//import com.squareup.picasso.Picasso;
//import com.yiwucheguanjia.carmgr.WaitActivity;
//import com.yiwucheguanjia.carmgr.utils.StringCallback;
//import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RollViewPagerBean;
//import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/19.
 */

public class RollViewPagerAdapter extends LoopPagerAdapter {
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private ArrayList<RollViewPagerBean> rollViewPagerBeanArrayList;
    RollViewPagerBean rollViewPagerBean;
    private int[] imgs = {
            R.mipmap.default_image,
    };

    public RollViewPagerAdapter(RollPagerView viewPager, ArrayList<RollViewPagerBean> rollViewPagerBeanArrayList, Activity activity) {
        super(viewPager);
        this.rollViewPagerBeanArrayList = rollViewPagerBeanArrayList;
        this.activity = activity;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        rollViewPagerBean = rollViewPagerBeanArrayList.get(position);
        Glide.with(container.getContext()).load(rollViewPagerBean.getRollViewPagerUrl()).into(view);

        /*
        *         Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
        * */
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    @Override
    protected int getRealCount() {
        return rollViewPagerBeanArrayList.size();
    }
}
