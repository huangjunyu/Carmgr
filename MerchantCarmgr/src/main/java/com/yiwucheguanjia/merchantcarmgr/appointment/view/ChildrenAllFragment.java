package com.yiwucheguanjia.merchantcarmgr.appointment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yiwucheguanjia.merchantcarmgr.R;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ChildrenAllFragment extends Fragment {

    LinearLayout allFragmentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        allFragmentView = (LinearLayout)inflater.inflate(R.layout.activity_home_main,container,false);
        return allFragmentView;
    }
}
