package com.yiwucheguanjia.carmgr.order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.carmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseOrderFragment extends Fragment {

    public BaseOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = initView(inflater, container, savedInstanceState);
        initData();
        return view;

    }
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void initData();
}
