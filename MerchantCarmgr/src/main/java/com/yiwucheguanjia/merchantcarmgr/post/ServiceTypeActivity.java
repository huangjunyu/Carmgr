package com.yiwucheguanjia.merchantcarmgr.post;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.my.controller.SystemMsgAdapter;
import com.yiwucheguanjia.merchantcarmgr.post.controller.ServiceTypeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ServiceTypeActivity extends Activity{
    private ServiceTypeAdapter serviceTypeAdapter;
    private ArrayList<String> typeList;
    @BindView(R.id.service_type_rv)
    protected RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service_type);
        ButterKnife.bind(this);
        typeList = new ArrayList<>();
        typeList.add("上牌");
        typeList.add("驾考");
        typeList.add("车检");
        typeList.add("维修");
        typeList.add("租车");
        typeList.add("保养");
        typeList.add("二手车");
        typeList.add("车贷");
        typeList.add("新车");
        typeList.add("急救");
        typeList.add("用品");
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceTypeActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        serviceTypeAdapter = new ServiceTypeAdapter(ServiceTypeActivity.this,typeList);
        recyclerView.setAdapter(serviceTypeAdapter);

    }
}
