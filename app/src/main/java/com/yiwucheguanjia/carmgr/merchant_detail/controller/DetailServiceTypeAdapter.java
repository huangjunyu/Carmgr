package com.yiwucheguanjia.carmgr.merchant_detail.controller;

/**
 * Created by Administrator on 2016/8/9.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.home.controller.PicassoOnScrollListener;
import com.yiwucheguanjia.carmgr.merchant_detail.model.ServiceTypeItemBean;
import com.yiwucheguanjia.carmgr.merchant_detail.view.MerchantDetail;
import com.yiwucheguanjia.carmgr.utils.RoundRectImageView;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.ArrayList;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/1.
 */
public class DetailServiceTypeAdapter extends RecyclerView.Adapter<DetailServiceTypeAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Integer> mDatas;
    private ArrayList<ServiceTypeItemBean> recyclerBeens;
    private Context context;
    private SharedPreferences sharedPreferences;

    public DetailServiceTypeAdapter(Context context, ArrayList<ServiceTypeItemBean> recyclerBeens) {
        this.recyclerBeens = recyclerBeens;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("CARMGR", context.MODE_PRIVATE);
    }


    @Override
    public int getItemCount() {

        return recyclerBeens.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.merchant_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ServiceTypeItemBean merchantItemBean = recyclerBeens.get(position);
        viewHolder.merchantDistance.setText(merchantItemBean.getDetailMerchantDistance());
        viewHolder.merchantArea.setText(merchantItemBean.getDetailMerchantAddr());
        viewHolder.merchantInstroduce.setText(merchantItemBean.getDetailMerchantIntroduce());
//        myItemViewHolder.merchantStars.setText(merchantItemBean.getMerchantStars() + activity.getResources().getText(R.string.point).toString());
        viewHolder.merchantName.setText(merchantItemBean.getDetailMerchantName());
        viewHolder.merchantRoad.setText(merchantItemBean.getDetailMerchantRoad());
        String numberStr = merchantItemBean.getDetailMerchantStarsStr();
        selectStar(numberStr,viewHolder.merchantStarImg);
        Picasso.with(context).load(recyclerBeens.get(position).getDetailMerchantImgUrl()).tag(PicassoOnScrollListener.TAG)
                .error(R.mipmap.picture_default).into(viewHolder.merchantImg);
        viewHolder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent merchantDetailInten = new Intent(context, MerchantDetail.class);
                merchantDetailInten.putExtra("merchantName",merchantItemBean.getDetailMerchantName());
                context.startActivity(merchantDetailInten);
            }
        });
    }

    protected void postData(String username, String click_area_id, String detail, String token,
                            String version, String url, int id) {
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("click_area_id", click_area_id.toString())
                .addParams("detail", detail)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new HotSecondCarStringCallback());
    }
    protected void selectStar(String starNum,ImageView merchantStarImg){
        switch (starNum){
            case "0.5":
                Picasso.with(this.context).load(R.mipmap.star_half).error(R.mipmap.star_five).into(merchantStarImg);

                break;
            case "1":
                Picasso.with(this.context).load(R.mipmap.star).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "1.5":
                Picasso.with(this.context).load(R.mipmap.star_one_hafl).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "2":
                Picasso.with(this.context).load(R.mipmap.star_two).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "2.5":
                Picasso.with(this.context).load(R.mipmap.star_two_half).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "3":
                Picasso.with(this.context).load(R.mipmap.star_three).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "3.5":
                Picasso.with(this.context).load(R.mipmap.star_three_half).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "4":
                Picasso.with(this.context).load(R.mipmap.star_four).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "4.5":
                Picasso.with(this.context).load(R.mipmap.star_four_half).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            case "5":
                Picasso.with(this.context).load(R.mipmap.star_five).error(R.mipmap.star_five).into(merchantStarImg);
                break;
            default:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        private RelativeLayout merchantStarsLL;
        private RelativeLayout merchantNameRl;
        private LinearLayout itemLl;
        private TextView merchantDistance;
        private TextView merchantArea;
        private TextView merchantInstroduce;
        private TextView merchantName;
        private TextView merchantRoad;
        private TextView merchantMobile;
        private ImageView merchantImg;
        private ImageView merchantStarImg;

        public ViewHolder(View viewHolder) {
            super(viewHolder);
            itemLl = (LinearLayout)viewHolder.findViewById(R.id.merchant_item_ll);
            merchantStarsLL = (RelativeLayout) viewHolder.findViewById(R.id.merchant_stars_rl);
            merchantNameRl = (RelativeLayout) viewHolder.findViewById(R.id.merchant_name_rl);
            merchantDistance = (TextView) viewHolder.findViewById(R.id.merchant_distance);
            merchantArea = (TextView) viewHolder.findViewById(R.id.merchant_area);
            merchantInstroduce = (TextView) viewHolder.findViewById(R.id.merchant_introduce_txt);
            merchantName = (TextView) viewHolder.findViewById(R.id.merchant_name_txt);
            merchantRoad = (TextView) viewHolder.findViewById(R.id.merchant_road);
            merchantImg = (ImageView) viewHolder.findViewById(R.id.merchant_img);
            merchantStarImg = (ImageView) viewHolder.findViewById(R.id.merchant_star_img);
//            viewHolder.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    postData(sharedPreferences.getString("ACCOUNT",null),"1000_40",
//                            "热门二手车",sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
//                            UrlString.APP_LOG_USER_OPERATION,1);
//                    Intent intent = new Intent(context, WaitActivity.class);
//                    context.startActivity(intent);
//                }
//            });

        }

        RoundRectImageView mImg;
    }

    protected class HotSecondCarStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("hotse", response);
                    break;
                default:
                    break;
            }
        }
    }
}

