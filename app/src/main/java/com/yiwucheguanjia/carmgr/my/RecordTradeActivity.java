package com.yiwucheguanjia.carmgr.my;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.controller.OrderAdapter;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordTradeActivity extends AppCompatActivity {
    OrderAdapter orderAdapter;
    @BindView(R.id.record_trade_rv)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,ContextCompat.getColor(this,R.color.white),50);
        setContentView(R.layout.activity_record_trade);
        ButterKnife.bind(this);
        orderAdapter = new OrderAdapter(RecordTradeActivity.this,null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecyclerViewDivider(RecordTradeActivity.this,LinearLayoutManager.HORIZONTAL,1, ContextCompat.getColor(RecordTradeActivity.this,R.color.gray_divide)));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderAdapter);
    }
}
