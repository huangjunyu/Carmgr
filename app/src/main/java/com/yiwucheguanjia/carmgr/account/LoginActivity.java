package com.yiwucheguanjia.carmgr.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.animation.DiologLoading;
import com.yiwucheguanjia.carmgr.home.HomeFragment;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
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
public class LoginActivity extends FragmentActivity implements View.OnClickListener,LoaderCallbacks<Cursor> {

    private String usernameString;
    private String passwordString;
    //登录状态提示
    private String loginState;
    private Button loginBtn;
    private EditText loginUsernameEdit;
    private EditText loginPasswordEdit;
    private TextView loginRegisterTxtBtn;
    private OkHttpClient okHttpClient;
    private DiologLoading diologLoading;
    JSONObject jsonObject = new JSONObject();
    private static final MediaType JSON = MediaType.parse("application/json;charset:utf-8");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        diologLoading = new DiologLoading(getResources().getString(R.string.logging));
    }
    private void initView(){
        loginUsernameEdit = (EditText)findViewById(R.id.login_account_edit);
        loginPasswordEdit = (EditText)findViewById(R.id.login_password_edit);
        loginRegisterTxtBtn = (TextView)findViewById(R.id.login_register_btn);
        loginBtn = (Button)findViewById(R.id.login_button);
        okHttpClient = new OkHttpClient();
        loginRegisterTxtBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
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
    Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(LoginActivity.this,loginState,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //关闭动画

//                    Intent homeFragmentIntent = new Intent(LoginActivity.this, HomeFragment.class);
//                    startActivityForResult(homeFragmentIntent,1);
//                    setResult(1);
//                    getCallingActivity();

                    finish();

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                Log.e("kdke","kskskk");
                usernameString = loginUsernameEdit.getText().toString();
                passwordString = loginPasswordEdit.getText().toString();
                if (checkString(loginUsernameEdit, loginPasswordEdit)){
                    loginAccount(this.usernameString,this.passwordString,"1.0" ,UrlString.LOGIN_URL);
                }
                break;
            case R.id.login_register_btn:
                Log.e("kdke","kskskk");
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }
    }

    /**
     * @author huangjunyu
     * @param usernameEdit 用户名
     * @param passwordEdit 登录密码
     */
    private Boolean checkString(EditText usernameEdit,EditText passwordEdit){
        String usernameString = usernameEdit.getText().toString().trim();
        String passwordString = passwordEdit.getText().toString().trim();
        if (usernameString.equals("")){
            Toast.makeText(LoginActivity.this,getResources().getText(R.string.acc_can_not_null),Toast.LENGTH_SHORT).show();
            return false;
        }else if (passwordString.equals("")){
            Toast.makeText(LoginActivity.this,getResources().getText(R.string.pwd_can_not_null),Toast.LENGTH_SHORT).show();
            return false;
        }else {
            this.usernameString = usernameString;
            this.passwordString = passwordString;
            return true;
        }

    }
    private void loginAccount(final String username, final String password, String version,final String url){
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("version",version)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final  Response response) throws IOException {
                if (response.isSuccessful()) {

                    try {
                        JSONObject jsonAll = new JSONObject(response.body().string());
                        if (jsonAll.getString("opt_state").equals("fail")){
                            Log.e("jsonAll",jsonAll + "");
                            loginState = jsonAll.getString("opt_info");
                            Log.e("json111", loginState);
                            handler.sendEmptyMessage(1);
                        }else if (jsonAll.getString("opt_state").equals("success")){
                            setSharePrefrence(username,password);
                            handler.sendEmptyMessage(2);
                        };

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
    private void setSharePrefrence(String account, String password) {
        SharedPreferences p = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("ACCOUNT", account);
        edit.putString("PASSWORD", password);
        edit.commit();
    }
    /**
     * Use an AsyncTask to fetch the user's email addresses on a background thread, and update
     * the email text field with results on the main UI thread.
     */

}

