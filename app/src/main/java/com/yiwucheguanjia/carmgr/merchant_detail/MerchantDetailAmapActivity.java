package com.yiwucheguanjia.carmgr.merchant_detail;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.merchant_detail.controller.AlbumAdapter;
import com.yiwucheguanjia.carmgr.merchant_detail.controller.GoolsAdapter;
import com.yiwucheguanjia.carmgr.merchant_detail.view.GoodsDetailActivity;
import com.yiwucheguanjia.carmgr.utils.MyRecyclerView;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;
import com.yiwucheguanjia.carmgr.utils.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
* 从地图的服务贴进来的商家详情界面
* */
public class MerchantDetailAmapActivity extends AppCompatActivity {
    @BindView(R.id.order_detail_album_rv)
    RecyclerView albumRv;//相册
    @BindView(R.id.merchantdetail_gools_rv)
    MyRecyclerView goolsRv;
    @BindView(R.id.order_detail_addview_ll)
    LinearLayout addViewLl;
    @BindView(R.id.order_detail_position_tv)
    TextView positionTv;//商家地址
    @BindView(R.id.merchantdetail_title_tv)
    TextView merchantNameTv;
    private AlbumAdapter albumAdapter;
    private GoolsAdapter goolsAdapter;
    private ArrayList<GoolsBean> goolsBeanArrayList;
    private String merchantNameStr;
    private String merchantStarStr;
    private String merchantAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchantdetail_amap);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.orange), 0);
        ButterKnife.bind(this);
        goolsBeanArrayList = new ArrayList<>();
        getData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        goolsRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(this, R.color.rc_divide)));
//        albumRv.setNestedScrollingEnabled(false);
        goolsRv.setNestedScrollingEnabled(false);
        albumRv.setLayoutManager(linearLayoutManager);
        albumAdapter = new AlbumAdapter(this, null);
        albumRv.setAdapter(albumAdapter);
        goolsRv.setLayoutManager(linearLayoutManager2);
        Log.e("losg",goolsBeanArrayList.size() + "");
        goolsAdapter = new GoolsAdapter(this,goolsBeanArrayList);
        goolsRv.setAdapter(goolsAdapter);
        goolsRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent goodsDetailIntent = new Intent(MerchantDetailAmapActivity.this, GoodsDetailActivity.class);

                goodsDetailIntent.putExtra("serviceId","2");
                goodsDetailIntent.putExtra("merchantId","33");
                goodsDetailIntent.putExtra("merchantName",merchantNameStr);
                goodsDetailIntent.putExtra("serviceName","上牌");

                startActivity(goodsDetailIntent);
            }
        });
        createImagview(4);
    }
    private void getData(){
        Bundle bundle = getIntent().getExtras();
        merchantNameStr = bundle.getString("merchantName");
        merchantStarStr = bundle.getString("merchantStar","5");
        merchantAddress = bundle.getString("merchantAddress","");
        merchantNameTv.setText(bundle.getString("merchantName"));
        positionTv.setText(bundle.getString("merchantAddress",""));
        for (int i = 0;i < 10;i++){
            GoolsBean goolsBean = new GoolsBean();
            goolsBean.setPicUrl("");
            goolsBean.setPrivilegePrice("53");
            goolsBean.setRetailPrice("77");
            goolsBean.setSaled("526");
            goolsBean.setServiceTitle("年审");
            goolsBeanArrayList.add(goolsBean);
        }
    }

    private void createImagview(int length) {
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(this);
            //下面必须使用LinearLayout，如果使用ViewGroup的LayoutParams，则会报空指针异常。
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    Tools.getInstance().dipTopx(this,10), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Tools.getInstance().dipTopx(this,0),Tools.getInstance().dipTopx(this,3),Tools.getInstance().dipTopx(this,3),Tools.getInstance().dipTopx(this,3));
            imageView.setLayoutParams(layoutParams);
            imageView.setMaxWidth(Tools.getInstance().dipTopx(this,10));
            imageView.setMaxHeight(Tools.getInstance().dipTopx(this,10));
            Glide.with(this).load(R.mipmap.star).into(imageView);
            addViewLl.addView(imageView);
        }
        for (int j = 0;j < 5 - length;j++){
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    Tools.getInstance().dipTopx(this,10), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Tools.getInstance().dipTopx(this,0),Tools.getInstance().dipTopx(this,3),Tools.getInstance().dipTopx(this,3),Tools.getInstance().dipTopx(this,3));
            imageView.setLayoutParams(layoutParams);
            imageView.setMaxWidth(Tools.getInstance().dipTopx(this,10));
            imageView.setMaxHeight(Tools.getInstance().dipTopx(this,10));
            Glide.with(this).load(R.mipmap.star).into(imageView);
            addViewLl.addView(imageView);
        }
    }
}
