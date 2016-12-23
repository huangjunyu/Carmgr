package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/2.
 */
public class MerchantGradeActivity extends AppCompatActivity {

    @BindView(R.id.grade_progress)
    protected CBProgressBar progressBar;
    private int levelInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_merchant_grade);
        ButterKnife.bind(this);


        Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        levelInt = bundle.getInt("grade");//getString()返回指定key的值
        try {
            levelInt = getIntent().getExtras().getInt("grade");

        }catch (Exception e){
            levelInt = 1;
        }
        Log.e("grade", levelInt + "");

        progressBar.setMax(5);
        progressBar.setProgress(levelInt);
    }

    @OnClick({R.id.grade_goback_rl})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.grade_goback_rl:
                finish();
                break;
            default:
                break;
        }
    }
}
