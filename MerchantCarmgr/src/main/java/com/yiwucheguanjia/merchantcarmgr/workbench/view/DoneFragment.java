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
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.ComplaintAdapter;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.RateAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 已处理的投诉
 */
public class DoneFragment extends Fragment {
    @BindView(R.id.complain_item_rv)
    RecyclerView recyclerView;
    ComplaintAdapter complaintAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_complain,container,false);
        ButterKnife.bind(this,homeView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        complaintAdapter = new ComplaintAdapter(getActivity(),null);
        recyclerView.setAdapter(complaintAdapter);
        return homeView;
    }
}
