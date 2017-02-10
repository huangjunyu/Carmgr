package com.yiwucheguanjia.carmgr.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.controller.CarBrandAdapter;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarBrandActivity extends AppCompatActivity {
    @BindView(R.id.brand_rv)
    RecyclerView recyclerView;
    private CarBrandAdapter carBrandAdapter;
    private String[] brands;
    private static final int ADD_CAR_BRAND_REQUEST = 5002;
    private static final int ADD_CAR_BRAND_RESULT = 5003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand);
        StatusBarUtil.setColor(this,ContextCompat.getColor(this,R.color.white),50);
        ButterKnife.bind(this);
        brands = getResources().getStringArray(R.array.car_brand);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.HORIZONTAL,1, ContextCompat.getColor(this,R.color.gray_default)));
        recyclerView.setLayoutManager(linearLayoutManager);
        carBrandAdapter = new CarBrandAdapter(this,brands,handler);
        recyclerView.setAdapter(carBrandAdapter);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Intent intent = new Intent();
                    intent.putExtras(msg.getData());
                    setResult(ADD_CAR_BRAND_RESULT,intent);
                    finish();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    };
    @OnClick(R.id.brand_goback_rl)
    void click(){
        finish();
    }
}
