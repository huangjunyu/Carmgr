package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.utils.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceTypeActivity extends AppCompatActivity {
    ServiceTypeCheckBoxAdapter serviceTypeAdapter;
    private String[] serviceTypeArrStr;
    private ArrayList<String> serviceTypeArrList;
    @BindView(R.id.service_type_rv)
    protected RecyclerView recyclerView;
    private static final int CHECKBOX_RESULT = 301;
    private String allType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceTypeArrStr = getResources().getStringArray(R.array.service_type);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_service_type);
        serviceTypeArrList = new ArrayList<>();
        for (int i = 0; i < serviceTypeArrStr.length; i++) {
            serviceTypeArrList.add(serviceTypeArrStr[i]);
        }
        ButterKnife.bind(this);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceTypeActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(ServiceTypeActivity.this, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(ServiceTypeActivity.this, R.color.gray_divide)));
        serviceTypeAdapter = new ServiceTypeCheckBoxAdapter(ServiceTypeActivity.this, serviceTypeArrList, handler);
        recyclerView.setAdapter(serviceTypeAdapter);
        serviceTypeAdapter.setRecyclerViewOnItemClickListener(new ServiceTypeCheckBoxAdapter.RecyclerViewOnItemClickListener() {

            @Override
            public void onItemClickListener(View view, int position) {
                serviceTypeAdapter.setShowBox();
                serviceTypeAdapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                return true;
            }
        });
    }

    @OnClick({R.id.type_checkbox_goback_rl,R.id.type_submit})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.type_checkbox_goback_rl:
                finish();
                break;
            case R.id.type_submit:
                //获取你选中的item
                Map<Integer, Boolean> map = serviceTypeAdapter.getMap();

        /*        Set keySet = map.keySet(); // key的set集合
        Iterator it = keySet.iterator();
        while (it.hasNext()){
            Log.e("ks",map.get(0).toString() + " " + it.next());
        }*/
                Set keySet = map.keySet();
                Iterator iterator = keySet.iterator();
                while (iterator.hasNext()){
                    Object next = iterator.next();
                    Log.e("kks",map.get(next).toString() + next);
                    if (map.get(next)){
                        allType = allType + serviceTypeArrStr[(int)next] + " ";
                    }
                }
                Log.e("allType",allType);
                Intent intent = new Intent();
                intent.putExtra("serviceCheckBoxType", allType);
                setResult(CHECKBOX_RESULT, intent);
                finish();
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String serviceType = serviceTypeArrList.get(msg.what);
            Intent intent = new Intent();
            intent.putExtra("serviceCheckBoxType", serviceType);
            setResult(CHECKBOX_RESULT, intent);
            finish();
        }
    };

}
