package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.my.controller.SystemMsgAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/21.
 */
public class SystemMsgActivity extends AppCompatActivity {
    private SystemMsgAdapter systemMsgAdapter;
    @BindView(R.id.system_msg_rv)
    protected RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.system_msg_activity);
        ButterKnife.bind(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SystemMsgActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        systemMsgAdapter = new SystemMsgAdapter(SystemMsgActivity.this);
        recyclerView.setAdapter(systemMsgAdapter);

    }
}
