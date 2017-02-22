package com.yiwucheguanjia.carmgr.order_detail;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchantDetailAmapActivity extends AppCompatActivity {
    @BindView(R.id.order_detail_album_rv)
    RecyclerView albumRv;//相册
    @BindView(R.id.order_detail_addview_ll)
    LinearLayout addViewLl;
    private AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_help);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.orange), 0);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        albumRv.setLayoutManager(linearLayoutManager);
        albumAdapter = new AlbumAdapter(this, null);
        albumRv.setAdapter(albumAdapter);
        createImagview(4);
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
