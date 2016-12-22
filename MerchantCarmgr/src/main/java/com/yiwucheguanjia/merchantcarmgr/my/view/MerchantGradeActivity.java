package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/2.
 */
public class MerchantGradeActivity extends Activity {

    @BindView(R.id.grade_progress)
    protected CBProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_grade);
        ButterKnife.bind(this);


        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
       int pwdString = bundle.getInt("grade");//getString()返回指定key的值
        Log.e("grade",pwdString + "");

        progressBar.setMax(100);
        progressBar.setProgress(80);
    }

    @OnClick({R.id.grade_goback_rl})void onClick(View view){
        switch (view.getId()){
            case R.id.grade_goback_rl:
                finish();
                break;
            default:
                break;
        }
    }
}
