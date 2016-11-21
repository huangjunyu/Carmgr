package com.yiwucheguanjia.carmgr.home.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.WaitActivity;
import com.yiwucheguanjia.carmgr.home.model.HotSecondCarBean;
import com.yiwucheguanjia.carmgr.utils.RoundRectImageView;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;

public class HotSecondCarAdapter extends
        RecyclerView.Adapter<HotSecondCarAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private ArrayList<HotSecondCarBean> recyclerBeens;
    private Context context;
    private SharedPreferences sharedPreferences;
    public HotSecondCarAdapter(Context context, ArrayList<HotSecondCarBean> recyclerBeens)
    {
        this.recyclerBeens = recyclerBeens;
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
            imageView= (ImageView)arg0.findViewById(R.id.se_hand_recycler_img);
            arg0.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    postData(sharedPreferences.getString("ACCOUNT",null),"1000_40",
                            "热门二手车",sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
                            UrlString.APP_LOG_USER_OPERATION,1);
                    Intent intent = new Intent(context, WaitActivity.class);
                    context.startActivity(intent);
                }
            });

        }

        RoundRectImageView mImg;
    }

    @Override
    public int getItemCount()
    {

        return recyclerBeens.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.se_hand_recycler_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (RoundRectImageView) view
                .findViewById(R.id.se_hand_recycler_img);
        return viewHolder;
    }
    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        Picasso.with(context).load(recyclerBeens.get(i).getImgUrlStr()).tag(PicassoOnScrollListener.TAG)
                .error(R.mipmap.picture_default).into(viewHolder.mImg);
    }
    protected void postData(String username,String click_area_id,String detail,String token,
                            String version,String url,int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("click_area_id",click_area_id.toString())
                .addParams("detail",detail)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new HotSecondCarStringCallback());
    }
    protected class HotSecondCarStringCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id)
            {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

}