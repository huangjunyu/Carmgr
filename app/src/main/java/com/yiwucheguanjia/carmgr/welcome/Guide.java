package com.yiwucheguanjia.carmgr.welcome;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginBaseFragmentActivity;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class Guide extends Activity implements OnViewChangeListener {

    private static final String TAG = "Guide";
    private ScrollLayout mScrollLayout;
    private ImageView[] imgs;
    private int count;
    private int currentItem;
    private Button startBtn;
    private RelativeLayout mainRLayout;
    private LinearLayout pointLLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingGuide();
    }

    protected void loadingGuide() {
        if (sharedPreferences.getString("VERSION", null) == null) {
            setContentView(R.layout.guide);
            init();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("VERSION", UrlString.APP_VERSION);
            editor.commit();
        } else if (sharedPreferences.getString("TOKEN", null) == null) {
            Intent intent = new Intent(
                    Guide.this,
                    LoginBaseFragmentActivity.class);
            Guide.this.startActivity(intent);
            Guide.this.finish();
        } else {
            appGetConfig(sharedPreferences.getString("ACCOUNT", null),
                    sharedPreferences.getString("TOKEN", null),
                    UrlString.APP_VERSION, UrlString.APP_GETPRIVATE, 1);
        }
    }

    private void appGetConfig(String username, String token,
                              String version, String url, int id) {

        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new PrivateStringCallback());
    }

    /**
     * 初始化
     */
    private void init() {
        mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayout);
        pointLLayout = (LinearLayout) findViewById(R.id.llayout);
        mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(onClick);
        count = mScrollLayout.getChildCount();
        imgs = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = (ImageView) pointLLayout.getChildAt(i);
            imgs[i].setEnabled(true);
            imgs[i].setTag(i);
        }
        currentItem = 0;
        imgs[currentItem].setEnabled(false);
        mScrollLayout.SetOnViewChangeListener(this);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startBtn:
                    Intent intent = new Intent(
                            Guide.this,
                            LoginBaseFragmentActivity.class);
                    Guide.this.startActivity(intent);
                    Guide.this.finish();
            }
        }
    };

    @Override
    public void OnViewChange(int position) {
        setcurrentPoint(position);
    }

    private void setcurrentPoint(int position) {
        if (position < 0 || position > count - 1 || currentItem == position) {
            return;
        }
        imgs[currentItem].setEnabled(true);
        imgs[position].setEnabled(false);
        currentItem = position;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected class PrivateStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {//此处可以实现无网络下的缓存读取操作
            Exit();
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("mer", response);
                    parseJson(response);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 退出事件
     */
    public void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Guide.this);
        builder.setTitle("友情提示!").setMessage(getString(R.string.nekwork_error)).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Guide.this.finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void parseJson(String response) {
        Log.e("response", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                Intent intent = new Intent(
                        Guide.this,
                        MainActivity.class);
                Guide.this.startActivity(intent);
                Guide.this.finish();
            } else {//跳到登录界面
                Intent intent = new Intent(
                        Guide.this,
                        LoginBaseFragmentActivity.class);
                Guide.this.startActivity(intent);
                Guide.this.finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Intent intent = new Intent(
                    Guide.this,
                    LoginBaseFragmentActivity.class);
            Guide.this.startActivity(intent);
            Guide.this.finish();
        }
    }
}
