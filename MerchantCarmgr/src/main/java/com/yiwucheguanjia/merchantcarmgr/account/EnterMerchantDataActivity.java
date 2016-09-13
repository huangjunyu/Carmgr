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

/**
 * Created by Administrator on 2016/9/13.
 */
public class EnterMerchantDataActivity extends AppCompatActivity {
    @BindView(R.id.merchant_goback_rl)
    RelativeLayout gobackRl;
    @BindView(R.id.merchant_name_edit)
    EditText merchantNameEd;
    @BindView(R.id.merchant_area_ed)
    TextView merchantAreAEd;
    @BindView(R.id.merchant_detail_addr_ed)
    EditText merchantDetailAdrrEd;
    @BindView(R.id.merchant_service_pho_ed)
    EditText merchantServicePhoEd;
    @BindView(R.id.merchant_license_img)
    ImageView licenseImg;
    @BindView(R.id.merchant_updown_btn)
    Button updownBtn;
    @BindView(R.id.merchant_next_btn)
    Button nextBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_merchant_data);
        ButterKnife.bind(this);
    }
}
