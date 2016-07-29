package com.yiwucheguanjia.carmgr.account.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/7/15.
 */
public class UserAgreement extends Activity {
    private TextView agreementTv;
    private ImageView gobackImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        gobackImg = (ImageView)findViewById(R.id.agreement_goback_imgbtn);
        agreementTv = (TextView)findViewById(R.id.agreement_tv);
        gobackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
