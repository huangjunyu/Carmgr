package com.yiwucheguanjia.merchantcarmgr.appointment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.appointment.controller.AppointAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/17.
 */
public abstract class BaseFragment extends Fragment {
    View homeView;
    @BindView(R.id.allappoint_item_rv)
    RecyclerView itemRv;
    AppointAdapter appointAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = (View)inflater.inflate(R.layout.activity_allapoint_fragment,container,false);
        ButterKnife.bind(this,homeView);
        appointAdapter = new AppointAdapter(getActivity());
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemRv.setLayoutManager(linearLayoutManager);
        itemRv.setAdapter(appointAdapter);
        return homeView;
    }
}
