package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.my.controller.SystemMsgAdapter;
import com.yiwucheguanjia.merchantcarmgr.utils.RecyclerViewDivider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        setContentView(R.layout.activity_system_msg);
        ButterKnife.bind(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SystemMsgActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(SystemMsgActivity.this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(SystemMsgActivity.this, R.color.gray_divide)));
        systemMsgAdapter = new SystemMsgAdapter(SystemMsgActivity.this);
        recyclerView.setAdapter(systemMsgAdapter);

    }
    @OnClick(R.id.setting_goback_rl)
    void click(View view){
        switch (view.getId()){
            case R.id.setting_goback_rl:
                finish();
                break;
            default:
                break;
        }

    }
}
