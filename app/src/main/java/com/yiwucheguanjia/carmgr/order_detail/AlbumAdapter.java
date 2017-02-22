package com.yiwucheguanjia.carmgr.order_detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.WaitActivity;

import java.util.ArrayList;

public class AlbumAdapter extends
        RecyclerView.Adapter<AlbumAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private ArrayList<MerchantAlbumCarBean> merchantAlbumCarBeanArrayList;
    private Context context;
    private SharedPreferences sharedPreferences;
    public AlbumAdapter(Context context, ArrayList<MerchantAlbumCarBean> merchantAlbumCarBeanArrayList)
    {
        this.merchantAlbumCarBeanArrayList = merchantAlbumCarBeanArrayList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("CARMGR", context.MODE_PRIVATE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public ViewHolder(View arg0)
        {
            super(arg0);
            imageView= (ImageView)arg0.findViewById(R.id.album_photo_img);
            arg0.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WaitActivity.class);
                    context.startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount()
    {

        return 5;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.item_album,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.imageView = (ImageView) view
                .findViewById(R.id.album_photo_img);
        return viewHolder;
    }
    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
    }
}