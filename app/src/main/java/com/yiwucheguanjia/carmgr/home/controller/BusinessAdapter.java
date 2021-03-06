package com.yiwucheguanjia.carmgr.home.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.WaitActivity;
//import com.yiwucheguanjia.carmgr.commercial.view.MerchantListActivity;
import com.yiwucheguanjia.carmgr.commercial.view.MerchantListActivity;
import com.yiwucheguanjia.carmgr.home.model.BusinessBean;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 热门业务ListView的适配器
 */
public class BusinessAdapter extends BaseAdapter{

    private Activity activity;
    private MyViewholder viewHolder;
    private ArrayList<BusinessBean> list;
    private LayoutInflater layoutInflater;
    private SharedPreferences sharedPreferences;
    private int position;
    /**
     * @param activity
     * @param list
     */
    public BusinessAdapter(Activity activity, ArrayList<BusinessBean> list) {
        this.list = list;
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        sharedPreferences = activity.getSharedPreferences("CARMGR", activity.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        if (convertView == null) { // 获取组件布局
            convertView = layoutInflater.inflate(R.layout.home_business_item, null);

            viewHolder = new MyViewholder();
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.business_item_rl);
            viewHolder.businessImg = (ImageView)convertView.findViewById(R.id.business_img);
            viewHolder.businessNameTxt = (TextView)convertView.findViewById(R.id.business_name_txt);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (MyViewholder)convertView.getTag();
        }
        viewHolder.businessNameTxt.setText(list.get(position).getBusinessName());
        Picasso.with(activity).load(list.get(position).getBusinessImgUrl())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.mipmap.picture_default).into(viewHolder.businessImg);
        viewHolder.businessImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("logUser",list.get(position).getBusinessName() + "1000_1" + position);
                postDataOfUserAction(sharedPreferences.getString("ACCOUNT",null),"1000_1" + position,
                        list.get(position).getBusinessName(),
                        sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
                        UrlString.APP_LOG_USER_OPERATION,1);
                Intent intent = new Intent(activity, MerchantListActivity.class);
                intent.putExtra("business",list.get(position).getBusinessName());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    /**
     * 记录用户行为
     * @param username
     * @param click_area_id
     * @param detail
     * @param token
     * @param version
     * @param url
     * @param id
     */
    protected void postDataOfUserAction(String username, String click_area_id, String detail, String token,
                                        String version, String url, int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
            click_area_id.toString();
        }
        Log.e("click_area_id", click_area_id + 1);
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("click_area_id",click_area_id.toString())
                .addParams("detail",detail)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new BusinessStringCallback());
    }
//    postDataOfUserAction(sharedPreferences.getString("ACCOUNT",null),"1000_1" + position,
//            list.get(position).getBusinessName(),
//    sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
//    UrlString.APP_LOG_USER_OPERATION,1);
    class MyViewholder {
        public ImageView businessImg;
        public TextView businessNameTxt;
        public RelativeLayout relativeLayout;
    }
    protected class BusinessStringCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id)
            {
                case 1:
                    Log.e("logu",response);

                    break;
                default:
                    break;
            }
        }
    }
}
