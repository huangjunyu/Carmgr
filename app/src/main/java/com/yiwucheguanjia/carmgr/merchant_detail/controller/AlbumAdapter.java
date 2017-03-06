package com.yiwucheguanjia.carmgr.merchant_detail.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.WaitActivity;
import com.yiwucheguanjia.carmgr.merchant_detail.MerchantAlbumCarBean;

import java.util.ArrayList;

public class AlbumAdapter extends
        RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<MerchantAlbumCarBean> merchantAlbumCarBeanArrayList;
    private Context context;
    private SharedPreferences sharedPreferences;

    public AlbumAdapter(Context context, ArrayList<MerchantAlbumCarBean> merchantAlbumCarBeanArrayList) {
        this.merchantAlbumCarBeanArrayList = merchantAlbumCarBeanArrayList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("CARMGR", context.MODE_PRIVATE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public ViewHolder(View arg0) {
            super(arg0);
            imageView = (ImageView) arg0.findViewById(R.id.album_photo_img);
            arg0.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WaitActivity.class);
                    context.startActivity(intent);
                }
            });

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

        }
    }

    class ViewHolderTwo extends ViewHolder {
        TextView textView;

        public ViewHolderTwo(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.item_album_two_tv);
        }
    }

    @Override
    public int getItemCount() {

        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;//布局A
        } else if (position == 1) {
            return 1;//布局B
        }
        return 2;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = mInflater.inflate(R.layout.item_album_two, viewGroup, false);
            ViewHolderTwo viewHolder = new ViewHolderTwo(view);
            viewHolder.imageView = (ImageView) view
                    .findViewById(R.id.album_photo_img);
            viewHolder.textView = (TextView) view.findViewById(R.id.item_album_two_tv);
            return viewHolder;
        } else if (i == 1) {
            View view = mInflater.inflate(R.layout.item_album_two, viewGroup, false);

            ViewHolderTwo viewHolder = new ViewHolderTwo(view);
            viewHolder.imageView = (ImageView) view
                    .findViewById(R.id.album_photo_img);
            return viewHolder;
        } else {
            View view = mInflater.inflate(R.layout.item_album,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.imageView = (ImageView) view
                    .findViewById(R.id.album_photo_img);
            return viewHolder;
        }
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ViewHolderTwo) {
            ((ViewHolderTwo) viewHolder).imageView.setImageResource(R.mipmap.yangmin);
            ((ViewHolderTwo) viewHolder).textView.setText("ooooo");
            if (i == 0){
                ((ViewHolderTwo) viewHolder).imageView.setImageResource(R.mipmap.yangmin_test);
                ((ViewHolderTwo) viewHolder).textView.setText(context.getResources().getString(R.string.brand_story));
            }else if (i == 1){
                ((ViewHolderTwo) viewHolder).imageView.setImageResource(R.mipmap.yangmin);
                ((ViewHolderTwo) viewHolder).textView.setText(context.getResources().getString(R.string.picture));
            }
        } else {
            ((ViewHolder) viewHolder).imageView.setImageResource(R.mipmap.yangmin);
        }
    }
}