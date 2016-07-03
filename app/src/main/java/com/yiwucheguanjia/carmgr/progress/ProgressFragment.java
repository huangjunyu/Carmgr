package com.yiwucheguanjia.carmgr.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ProgressFragment extends Fragment {
    private LinearLayout progressView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        progressView = (LinearLayout)inflater.inflate(R.layout.activity_progress,null);
        return progressView;
    }
}
