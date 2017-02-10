package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yiwucheguanjia.merchantcarmgr.MainActivity;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用于判断是否首次使用APP
 */
public class JudgeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        setSharedPreferences();

    }

    private void setSharedPreferences() {
        //如果是首次使用该APP,后期可以加入欢迎页
        if (sharedPreferences.getString("VERSION", null) == null) {
            Intent homeIntent = new Intent(JudgeActivity.this, HomeActivty.class);
            startActivity(homeIntent);
            finish();
        } else if (sharedPreferences.getString("VERSION", null) != null && sharedPreferences.getString("TOKEN", null) == null) {
            Intent homeIntent = new Intent(JudgeActivity.this, HomeActivty.class);
            startActivity(homeIntent);
            finish();
        } else {
            OkGo.post(UrlString.DATA_STATISTICS_URL)
                    .tag(this)
                    .params("username", sharedPreferences.getString("ACCOUNT", null))
                    .params("data_time", "")
                    .params("token", sharedPreferences.getString("TOKEN", null))
                    .params("version", UrlString.APP_VERSION)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                    Intent mainIntent = new Intent(JudgeActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                } else {
                                    Intent loginIntent = new Intent(JudgeActivity.this, LoginActivity.class);
                                    startActivity(loginIntent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            Log.e("onerror", call.request().toString());
                            super.onError(call, response, e);

                        }

                        @Override
                        public void onAfter(@Nullable String s, @Nullable Exception e) {
                            super.onAfter(s, e);
                        }
                    });
        }
    }
}

