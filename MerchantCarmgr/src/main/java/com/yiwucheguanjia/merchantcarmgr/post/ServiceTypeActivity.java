package com.yiwucheguanjia.merchantcarmgr.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.post.controller.ServiceTypeAdapter;
import com.yiwucheguanjia.merchantcarmgr.utils.RecyclerViewDivider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/14.
 */
public class ServiceTypeActivity extends AppCompatActivity{
    private ServiceTypeAdapter serviceTypeAdapter;
    private ArrayList<String> serviceTypeArrList;
    private final static int SELECTED = 0;
    private final static int NOTHING_SELECT = 1;
    private String[] serviceTypeArrStr;
    @BindView(R.id.service_type_rv)
    protected RecyclerView recyclerView;
    @BindView(R.id.type_goback_rl)
    RelativeLayout gobackRl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceTypeArrStr = getResources().getStringArray(R.array.service_type);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_post_service_type);
        serviceTypeArrList = new ArrayList<>();
        ButterKnife.bind(this);
        for (int i = 0; i < serviceTypeArrStr.length; i++) {
            serviceTypeArrList.add(serviceTypeArrStr[i]);
        }
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceTypeActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(ServiceTypeActivity.this,LinearLayoutManager.HORIZONTAL,
                1,ContextCompat.getColor(ServiceTypeActivity.this,R.color.gray_divide)));
        serviceTypeAdapter = new ServiceTypeAdapter(ServiceTypeActivity.this,serviceTypeArrList,handler);
        recyclerView.setAdapter(serviceTypeAdapter);

    }
    @OnClick(R.id.type_goback_rl)void onClick(){
        Intent intent = new Intent();
        setResult(NOTHING_SELECT,intent);
        this.finish();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String serviceType = serviceTypeArrList.get(msg.what);
            Intent intent = new Intent();
            intent.putExtra("serviceType",serviceType);
            setResult(SELECTED,intent);
            Log.e("intent","intent");
            finish();
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            setResult(NOTHING_SELECT,intent);
            this.finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
