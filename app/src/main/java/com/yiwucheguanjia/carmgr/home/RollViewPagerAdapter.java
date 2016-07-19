package com.yiwucheguanjia.carmgr.home;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/19.
 */

public class RollViewPagerAdapter extends StaticPagerAdapter {
    private Activity activity;
    private ArrayList<RollViewPagerBean> rollViewPagerBeens;
//    private int[] imgs = {
//            R.mipmap.testimg,
//            R.mipmap.testimg,
//            R.mipmap.testimg,
//            R.mipmap.testimg,
//    };
    public RollViewPagerAdapter(Activity activity,ArrayList<RollViewPagerBean> rollViewPagerBeens){
        this.activity = activity;
        this.rollViewPagerBeens = rollViewPagerBeens;
    }
    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        Picasso.with(container.getContext()).load(rollViewPagerBeens.get(position).getRollViewPagerUrl()).into(view);
//        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("posiio",rollViewPagerBeens.get(position).getRollViewPagerClickUrl() + "");
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return rollViewPagerBeens.size();
    }
}
