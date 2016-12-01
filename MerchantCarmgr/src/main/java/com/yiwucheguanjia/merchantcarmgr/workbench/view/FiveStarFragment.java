package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.checkpictureutils.ItemEntity;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.RateAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class FiveStarFragment extends AssessBaseFragment {
    @Override
    protected int getStar() {
        return 4;
    }
//    @BindView(R.id.star_rv)
//    RecyclerView recyclerView;
//    RateAdapter rateAdapter;
//
//    /** Item数据实体集合 */
//    private ArrayList<ItemEntity> itemEntities;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View homeView = inflater.inflate(R.layout.fragment_star,container,false);
//        ButterKnife.bind(this,homeView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        initData();
//        rateAdapter = new RateAdapter(getActivity(),itemEntities);
//        recyclerView.setAdapter(rateAdapter);
//        return homeView;
//    }
//
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        itemEntities = new ArrayList<ItemEntity>();
////        // 1.无图片
////        ItemEntity entity1 = new ItemEntity( null);
////        itemEntities.add(entity1);
//        // 2.1张图片
//        ArrayList<String> urls_1 = new ArrayList<String>();
//        urls_1.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
//        ItemEntity entity2 = new ItemEntity(urls_1);
//        itemEntities.add(entity2);
//        // 3.3张图片
//        ArrayList<String> urls_2 = new ArrayList<String>();
//        urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
//        urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
//        urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
//        ItemEntity entity3 = new ItemEntity(urls_2);
//        itemEntities.add(entity3);
//        // 4.6张图片
//        ArrayList<String> urls_3 = new ArrayList<String>();
//        urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_7507.jpg");
//        urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
//        urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
//        urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
//        urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
//        urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698839_2302.jpg");
//        ItemEntity entity4 = new ItemEntity(urls_3);
//        itemEntities.add(entity4);
//    }
}
