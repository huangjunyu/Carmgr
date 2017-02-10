package com.yiwucheguanjia.merchantcarmgr.account;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/15.
 */
public class UserAgreement extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
    }
    @OnClick(R.id.agreement_goback_rl)
    void click(){
        finish();
    }
}
