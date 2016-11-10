package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.app.Activity;
import android.os.Bundle;
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
