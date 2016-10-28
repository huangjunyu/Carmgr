package com.yiwucheguanjia.merchantcarmgr.appointment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;

/**
 * Created by Administrator on 2016/10/27.
 */
public class MyAppointmentFragment extends Fragment {
    View homeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = (View)inflater.inflate(R.layout.activity_myappoint_fragment,container,false);
        return homeView;
    }
}
