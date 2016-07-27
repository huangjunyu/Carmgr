package com.yiwucheguanjia.carmgr.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/24.
 */
public class PhoneLoginActivity extends Activity implements View.OnClickListener {
    private ImageView goback;
    private EditText phoneNumEdit;
    private EditText msgCodeEdit;
    private TextView sendCodeTv;
    private Button loginBtn;
    private SharedPreferences sharedPreferences;
    private String phoneNumStr;
    private String uuidStr;
    private String usernameString;
    private String passwordString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        initView();
    }

    protected void initView() {
        goback = (ImageView) findViewById(R.id.phone_login_goback_img);
        phoneNumEdit = (EditText) findViewById(R.id.phone_num_edit);
        msgCodeEdit = (EditText) findViewById(R.id.phone_code_edit);
        sendCodeTv = (TextView) findViewById(R.id.phone_send_code);
        loginBtn = (Button) findViewById(R.id.phone_check_login);
        goback.setOnClickListener(this);
        sendCodeTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    //获取手机号码，并且判断格式、发送手机号码
    protected void getPhoneNum() {
        Log.e("response", "owiewq");
        phoneNumStr = phoneNumEdit.getText().toString().trim();
        if (Tools.getInstance().isMobileNO(phoneNumStr)) {
            uuidStr = UUID.randomUUID().toString();
            sendCode(phoneNumStr, "1",
                    uuidStr,
                    UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE, 1);
        } else {
            Toast.makeText(PhoneLoginActivity.this, getResources().getText(R.string.phone_num_format), Toast.LENGTH_SHORT).show();
            phoneNumEdit.setText("");
        }
    }

    protected void sendCode(String username, String type, String uuid, String version, String url,
                            int id) {
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("type", type)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
    }

    protected void checkLogin(String username, String type, String verf_code, String uuid, String version, String url,
                              int id) {
//        Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
//        startActivityForResult(intent,2);
        Log.e("user",username + type +" " + verf_code + " " + uuid + version + url);
        if (verf_code.equals("")){
            Toast.makeText(PhoneLoginActivity.this,getResources().getText(R.string.input_msg_code),Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("password", "")
                .addParams("type", type)
                .addParams("verf_code", verf_code)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_login_goback_img:
                this.finish();
                break;
            case R.id.phone_code_edit://验证码编辑框
                break;
            case R.id.phone_num_edit:
                break;
            case R.id.phone_send_code://发送验证码
                getPhoneNum();
                break;
            case R.id.phone_check_login://验证并登录的按钮
//                Intent intent = new Intent()
                String msgCode = msgCodeEdit.getText().toString().trim();
                checkLogin(phoneNumStr,"1",msgCode,uuidStr,
                        UrlString.APP_VERSION,UrlString.LOGIN_URL,2);

                break;
            default:
                break;
        }
    }

    private class parseStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("sme", response);
                    break;
                case 2:
                    Log.e("login",response);
                    if (response != null) {
                        try {
                            JSONObject jsonAll = new JSONObject(response);
                            if (jsonAll.getString("opt_state").equals("fail")) {
                                Log.e("jsonAll", jsonAll + "");
//                                loginState = jsonAll.getString("opt_info");
//                                handler.sendEmptyMessage(1);
                            } else if (jsonAll.getString("opt_state").equals("success")) {
                                Log.e("token", jsonAll.getString("token"));
                                setSharePrefrence(phoneNumStr, jsonAll.getString("token"));
                                Log.e("login", "登录成功" + response);
                                MainActivity activity = new MainActivity();
                                activity.onActivityResult(2,2,null);
                                finish();
                            };

                        } catch (JSONException e) {
                            Log.e("ooeo", "kaeee");
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
    private void setSharePrefrence(String account, String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("ACCOUNT", account);
        edit.putString("TOKEN", token);
        edit.commit();
    }
}
