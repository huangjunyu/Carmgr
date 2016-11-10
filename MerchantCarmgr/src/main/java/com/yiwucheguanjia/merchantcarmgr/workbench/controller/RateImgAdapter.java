package com.yiwucheguanjia.merchantcarmgr.workbench.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.checkpictureutils.ImagePagerActivity;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RateImgBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RateImgAdapter extends RecyclerView.Adapter<RateImgAdapter.HolderView> {
    View view;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<String> rateImgBeens;

    public RateImgAdapter(Activity activity, ArrayList<String> rateImgBeens) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        this.rateImgBeens = rateImgBeens;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        view = layoutInflater.inflate(R.layout.rate_img, parent, false);
        HolderView holderView = new HolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("iwqq", "nki");
                imageBrower(position, rateImgBeens);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.rate_img)
        protected ImageView imageView;

        public HolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    protected void imageBrower(int position, ArrayList<String> urls2) {
        //让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                        (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                        0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        Intent intent = new Intent(this.activity, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        ActivityCompat.startActivity(this.activity, intent, options.toBundle());
    }

}
