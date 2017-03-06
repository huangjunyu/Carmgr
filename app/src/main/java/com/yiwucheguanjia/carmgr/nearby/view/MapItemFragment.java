package com.yiwucheguanjia.carmgr.nearby.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.merchant_detail.MerchantDetailAmapActivity;
import com.yiwucheguanjia.carmgr.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/14.
 */
public class MapItemFragment extends Fragment {
    @BindView(R.id.fragment_pager_item_title)
    TextView titleTv;
    @BindView(R.id.fragment_pager_item_price)
    TextView priceTv;
    @BindView(R.id.fragment_pager_item_content)
    TextView contentTv;
    @BindView(R.id.fragment_pager_it_star)
    LinearLayout linearLayout;
    private String serviceTitle;
    private Bundle bundle;
    public static MapItemFragment newInstance(Bundle bundle) {

        MapItemFragment f = new MapItemFragment();
        f.setArguments(bundle);
        return f;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_item, container,false);
        ButterKnife.bind(this,view);
        titleTv.setText(bundle.getString("service_title",""));
        priceTv.setText(bundle.getString("service_price","") + "元/次");
        contentTv.setText(bundle.getString("service_content"));
        createImagview(3);
        return view;
    }
    @OnClick()
    void click(){
        Intent intent = new Intent(getActivity(), MerchantDetailAmapActivity.class);
        startActivity(intent);
    }
    private void createImagview(int length) {
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(getActivity());
            //下面必须使用LinearLayout，如果使用ViewGroup的LayoutParams，则会报空指针异常。
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    Tools.getInstance().dipTopx(getActivity(),10), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Tools.getInstance().dipTopx(getActivity(),0),Tools.getInstance().dipTopx(getActivity(),3),Tools.getInstance().dipTopx(getActivity(),3),Tools.getInstance().dipTopx(getActivity(),3));
            imageView.setLayoutParams(layoutParams);
            imageView.setMaxWidth(Tools.getInstance().dipTopx(getActivity(),10));
            imageView.setMaxHeight(Tools.getInstance().dipTopx(getActivity(),10));
            Glide.with(this).load(R.mipmap.star).into(imageView);
            linearLayout.addView(imageView);
        }
        for (int j = 0;j < 5 - length;j++){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    Tools.getInstance().dipTopx(getActivity(),10), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Tools.getInstance().dipTopx(getActivity(),0),Tools.getInstance().dipTopx(getActivity(),3),Tools.getInstance().dipTopx(getActivity(),3),Tools.getInstance().dipTopx(getActivity(),3));
            imageView.setLayoutParams(layoutParams);
            imageView.setMaxWidth(Tools.getInstance().dipTopx(getActivity(),10));
            imageView.setMaxHeight(Tools.getInstance().dipTopx(getActivity(),10));
            Glide.with(this).load(R.mipmap.star).into(imageView);
            linearLayout.addView(imageView);
        }
    }
}
