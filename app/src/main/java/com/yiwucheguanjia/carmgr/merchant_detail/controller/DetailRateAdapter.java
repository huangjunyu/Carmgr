package com.yiwucheguanjia.carmgr.merchant_detail.controller;

/**
 * Created by Administrator on 2016/8/9.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.home.controller.PicassoOnScrollListener;
import com.yiwucheguanjia.carmgr.merchant_detail.model.RateBean;
import com.yiwucheguanjia.carmgr.utils.RoundRectImageView;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.ArrayList;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/1.
 */
public class DetailRateAdapter extends RecyclerView.Adapter<DetailRateAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Integer> mDatas;
    private ArrayList<RateBean> rateBeens;
    private Context context;
    private SharedPreferences sharedPreferences;

    public DetailRateAdapter(Context context, ArrayList<RateBean> rateBeans) {
        this.rateBeens = rateBeans;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("CARMGR", context.MODE_PRIVATE);
    }


    @Override
    public int getItemCount() {

        return rateBeens.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.rate_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final RateBean rateBean = rateBeens.get(position);
        Log.e("reateTv",rateBean.getRateText() + "wf");
//        myItemViewHolder.merchantStars.setText(rateBean.getMerchantStars() + activity.getResources().getText(R.string.point).toString());
        viewHolder.rateUserTv.setText(rateBean.getRateUser());
        viewHolder.rateTv.setText(rateBean.getRateText());
        viewHolder.rateTimeTv.setText(rateBean.getRateTime());
        selectStar(rateBean.getRateStars(),viewHolder.rateStarsImg);
//        Picasso.with(context).load(rateBeens.get(position).get).tag(PicassoOnScrollListener.TAG)
//                .error(R.mipmap.picture_default).into(viewHolder.merchantImg);
        viewHolder.itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent merchantDetailInten = new Intent(context, MerchantDetail.class);
//                merchantDetailInten.putExtra("merchantName",rateBean.getDetailMerchantName());
//                context.startActivity(merchantDetailInten);
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

        private LinearLayout itemLl;
        private TextView rateUserTv;
        private TextView rateTv;
        private ImageView rateStarsImg;
        private TextView rateTimeTv;


        public ViewHolder(View viewHolder) {
            super(viewHolder);
            itemLl = (LinearLayout)viewHolder.findViewById(R.id.detail_rote_item_ll);
            rateUserTv = (TextView)viewHolder.findViewById(R.id.detail_user_name_tv);
            rateTv = (TextView)viewHolder.findViewById(R.id.detail_rate_tv);
            rateStarsImg = (ImageView)viewHolder.findViewById(R.id.detail_rate_star_img);
            rateTimeTv = (TextView)viewHolder.findViewById(R.id.detail_rate_time);
        }

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

