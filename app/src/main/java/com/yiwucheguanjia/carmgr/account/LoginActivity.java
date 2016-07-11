package com.yiwucheguanjia.carmgr.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.animation.DiologLoading;
import com.yiwucheguanjia.carmgr.home.HomeFragment;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.OkhttpManager;
import com.yiwucheguanjia.carmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener, LoaderCallbacks<Cursor> {

    private String usernameString;
    private String passwordString;
    //登录状态提示
    private String loginState;
    private Button loginBtn;
    private ImageButton login_gobackImgBtn;
    private EditText loginUsernameEdit;
    private EditText loginPasswordEdit;
    private TextView loginRegisterTxtBtn;
    private TextView getPassword;
    private OkHttpClient okHttpClient;
    private DiologLoading diologLoading;
    private int LOGIN_SUSSESS = 3;
    private int LOGIN_ERROR = 4;
    private RequestQueue mQueue;

    JSONObject jsonObject = new JSONObject();
    private static final MediaType JSON = MediaType.parse("application/json;charset:utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_login);
        initView();
        diologLoading = new DiologLoading(getResources().getString(R.string.logging));
    }

    private void initView() {
        loginUsernameEdit = (EditText) findViewById(R.id.login_account_edit);
        loginPasswordEdit = (EditText) findViewById(R.id.login_password_edit);
        loginRegisterTxtBtn = (TextView) findViewById(R.id.login_register_btn);
        loginBtn = (Button) findViewById(R.id.login_button);
        login_gobackImgBtn = (ImageButton) findViewById(R.id.login_goback_img_btn);
        getPassword = (TextView)findViewById(R.id.login_get_password);
        okHttpClient = new OkHttpClient();
        getPassword.setOnClickListener(this);
        loginRegisterTxtBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        login_gobackImgBtn.setOnClickListener(this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(LoginActivity.this, loginState, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //关闭动画
                    Intent homeFragmentIntent = new Intent(LoginActivity.this, HomeFragment.class);
//                    startActivityForResult(homeFragmentIntent,1);
                    setResult(1);
                    getCallingActivity();
                    Display display = getWindowManager().getDefaultDisplay(); //Activity#getWindowManager()
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int height = size.y;
                    Log.e("usernameString",usernameString);
//                    appGetConfig(usernameString, "ZY_0001", width + "x" + height,"1.0");
                    finish();
                    break;
                case 3://请求成功
                    if (msg.obj.toString() != null) {
                        try {
                            String msgObj = msg.obj.toString();
                            JSONObject jsonAll = new JSONObject(msgObj);
                            if (jsonAll.getString("opt_state").equals("fail")) {
                            Log.e("jsonAll", jsonAll + "");
                            loginState = jsonAll.getString("opt_info");
                            handler.sendEmptyMessage(1);
                            } else if (jsonAll.getString("opt_state").equals("success")) {
                                Log.e("token",jsonAll.getString("token"));
                            setSharePrefrence(usernameString, passwordString,jsonAll.getString("token"));
                                Log.e("login","登录成功" + msgObj);
                                handler.sendEmptyMessage(2);
                            }
                            ;

                        } catch (JSONException e) {
                            Log.e("ooeo", "kaeee");
                            e.printStackTrace();
                        }
                    }
                    break;
                case 4://请求失败
                    break;
                case 5://appGetConfig方法获取配置请求成功
                    Log.e("config", msg.obj.toString());
                    break;
                case 6://appGetConfig方法获取配置请求失败
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Log.e("kdke", "kskskk");
                if (checkString(loginUsernameEdit, loginPasswordEdit)) {
                    usernameString = loginUsernameEdit.getText().toString().trim();
                    passwordString = loginPasswordEdit.getText().toString().trim();
                    loginAccount(this.usernameString, this.passwordString, "1.0", UrlString.LOGIN_URL);
                }
                break;
            case R.id.login_register_btn:
                Log.e("kdke", "kskskk");
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.login_goback_img_btn:
                this.finish();
                break;
            case R.id.login_get_password:
                appGetConfig(usernameString, "ZY_0001", "1080x1920","1.0");
                break;
            default:
                break;
        }
    }

    /**
     * 核审登录资料
     *
     * @param usernameEdit 用户名
     * @param passwordEdit 登录密码
     * @author huangjunyu
     */
    private Boolean checkString(EditText usernameEdit, EditText passwordEdit) {
        String usernameString = usernameEdit.getText().toString().trim();
        String passwordString = passwordEdit.getText().toString().trim();
        if (usernameString.equals("")) {
            Toast.makeText(LoginActivity.this, getResources().getText(R.string.acc_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordString.equals("")) {
            Toast.makeText(LoginActivity.this, getResources().getText(R.string.pwd_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            this.usernameString = usernameString;
            this.passwordString = passwordString;
            return true;
        }

    }

    /**
     * APP获取配置请求
     *
     * @param username   用户名
     * @param configKey  该参数指定用户需要获取的图片或者配置资源的编号
     * @param screenSize 终端屏幕尺寸
     */
    private void appGetConfig(final String username, final  String configKey, final String screenSize, final String version) {
        FormBody formBody = new FormBody.Builder()
                .add("username", "15014150833")
                .add("config_key", configKey)
                .add("screen_size", screenSize)
                .add("version",version)
                .build();
        Log.e("config",username + configKey + screenSize + version);
        OkhttpManager.getInstance().OKhttpPost(UrlString.APP_GET_CONFIG, handler, formBody, 5, 6);
//        OkhttpManager.OKhttpPost(UrlString.APP_GET_CONFIG, handler, formBody, 5, 6);
    }

    private void loginAccount(final String username, final String password, String version, final String url) {
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("version", version)
                .build();
        OkhttpManager.OKhttpPost(url, handler, formBody, 3, 4);
    }

    private void setSharePrefrence(String account, String password,String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("ACCOUNT", account);
        edit.putString("PASSWORD", password);
        edit.putString("TOKEN",token);
        edit.commit();
    }
    /**
     * Use an AsyncTask to fetch the user's email addresses on a background thread, and update
     * the email text field with results on the main UI thread.
     */

}

