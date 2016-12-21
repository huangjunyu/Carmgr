package com.yiwucheguanjia.carmgr.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yiwucheguanjia.carmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCollectActivity extends AppCompatActivity {
    @BindView(R.id.collect_rv)
    RecyclerView recyclerView;
    private MyCollectAdapter myCollectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myCollectAdapter = new MyCollectAdapter(MyCollectActivity.this,null);
        recyclerView.setAdapter(myCollectAdapter);
    }
}
