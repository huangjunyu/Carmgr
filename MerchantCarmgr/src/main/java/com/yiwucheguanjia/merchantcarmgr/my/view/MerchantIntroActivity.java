package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.post.PostServiceActivity1;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MerchantIntroActivity extends AppCompatActivity {
    @BindView(R.id.intro_goback_rl)
    RelativeLayout gobackRl;
    @BindView(R.id.intro_merchant_intro_ed)
    EditText introEdit;
    private SharedPreferences sharedPreferences;
    private String introduce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        setContentView(R.layout.activity_merchant_intro);
        ButterKnife.bind(this);
        try {
            introduce = getIntent().getExtras().getString("introduce");
        } catch (Exception e) {
            e.printStackTrace();
            introduce = "";
        }
        introEdit.setText(introduce);
    }

    @OnClick({R.id.intro_goback_rl, R.id.intro_submit_bt})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_goback_rl:
                finish();
                break;
            case R.id.intro_submit_bt:
                if (!TextUtils.isEmpty(introEdit.getText().toString().trim())) {
                    submit(introEdit);
                }
                break;
            default:
                break;
        }
    }

    private void submit(EditText editText) {
        OkGo.post(UrlString.UPLOAD_INTRODUCE)
                .tag(this)
                .params("username", sharedPreferences.getString("ACCOUNT", null))
                .params("introduce", editText.getText().toString().trim())
                .params("token", sharedPreferences.getString("TOKEN", null))
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(MerchantIntroActivity.this, getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.getString("opt_state").toString(), "success")) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                });
    }


}
