package com.yiwucheguanjia.merchantcarmgr.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/19.
 */
public class PostServic extends Activity {

    @BindView(R.id.post_sc_goback)
    RelativeLayout postRl;
    @BindView(R.id.post_sc_title)
    EditText titleEd;
    @BindView(R.id.post_sc_content)
    EditText contentEd;
    @BindView(R.id.post_sc_price_ed)
    EditText priceEd;
    @BindView(R.id.post_sc_service_type)
    TextView serviceType;
    @BindView(R.id.post_sc_service_limits)
    TextView serviceLimits;
    @BindView(R.id.post_sc_post_btn)
    Button submitBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_service);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.post_sc_goback,R.id.post_sc_post_btn})
    void onClickView(View view){

        switch (view.getId()){
            case R.id.post_sc_goback:
                finish();
                break;
            case R.id.post_sc_title:
                break;
            case R.id.post_sc_content:
                break;
            case R.id.post_sc_price_ed:
                break;
            case R.id.post_sc_service_type:
                break;
            case R.id.post_sc_service_limits:
                break;
            case R.id.post_sc_post_btn:
//                Intent postedMgIntent = new Intent(PostServic.this,PostedManage.class);
//                startActivity(postedMgIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
