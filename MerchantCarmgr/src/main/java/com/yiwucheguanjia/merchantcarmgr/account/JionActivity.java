package com.yiwucheguanjia.merchantcarmgr.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/13.
 */
public class JionActivity extends AppCompatActivity {
    @BindView(R.id.jion_area_ed)
    TextView areaEdit;
    @BindView(R.id.jion_intro_ed)
    EditText introEd;
    @BindView(R.id.jion_stor_img1)
    ImageView img1;
    @BindView(R.id.jion_stor_img2)
    ImageView img2;
    @BindView(R.id.jion_stor_img3)
    ImageView img3;
    @BindView(R.id.join_updown_btn)
    Button updownBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jion);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.jion_stor_img1)void setImg1(){

    }
    @OnClick(R.id.jion_stor_img2) void setImg2(){

    }
    @OnClick(R.id.jion_stor_img3) void setImg3(){

    }
    @OnClick(R.id.join_updown_btn) void setUpdownBtn(){

    }
}
