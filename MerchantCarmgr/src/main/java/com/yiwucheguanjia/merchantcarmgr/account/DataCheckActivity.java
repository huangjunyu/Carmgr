package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.MainActivity;
import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataCheckActivity extends AppCompatActivity {
    @BindView(R.id.data_check_check_img)
    ImageView checkImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_data_check);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.type_checkbox_goback_rl})
    void click(View view){
        switch (view.getId()){
            case R.id.type_checkbox_goback_rl:
                Intent mainIntent = new Intent(DataCheckActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
