package com.yiwucheguanjia.carmgr.account.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.animation.DiologLoading;
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
public class PhoneLoginActivity extends FragmentActivity implements View.OnClickListener {
    private RippleView gobackRpw;
    private EditText phoneNumEdit;
    private EditText msgCodeEdit;
    private Button sendCodeTv;
    private TimeCount timeCount;
    private RippleView loginRpw;
    private SharedPreferences sharedPreferences;
    private String phoneNumStr;
    private String uuidStr;
    private String usernameString;
    private String passwordString;
    private DiologLoading diologLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_phone_login);
        initView();
        timeCount = new TimeCount(60000, 1000);

    }

    protected void initView() {
        gobackRpw = (RippleView) findViewById(R.id.phone_login_goback_rpw);
        phoneNumEdit = (EditText) findViewById(R.id.phone_num_edit);
        msgCodeEdit = (EditText) findViewById(R.id.phone_code_edit);
        sendCodeTv = (Button) findViewById(R.id.phone_send_code);
        loginRpw = (RippleView) findViewById(R.id.phone_check_login_rpw);
        gobackRpw.setOnClickListener(this);
        sendCodeTv.setOnClickListener(this);
        loginRpw.setOnClickListener(this);
    }

    //获取手机号码，并且判断格式、发送手机号码
    protected void getPhoneNum() {
        Log.e("response", "owiewq");
        diologLoading = new DiologLoading(getResources().getString(R.string.geting_code));
        diologLoading.show(getSupportFragmentManager(), "login");
        phoneNumStr = phoneNumEdit.getText().toString().trim();
        if (Tools.getInstance().isMobileNO(phoneNumStr)) {
            uuidStr = UUID.randomUUID().toString();
            sendCode(phoneNumStr, "1",
                    uuidStr,
                    UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE, 1);
        } else {
            diologLoading.dismiss();
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
        Log.e("user", username + type + " " + verf_code + " " + uuid + version + url);
        if (TextUtils.isEmpty(verf_code)) {
            Toast.makeText(PhoneLoginActivity.this, getResources().getText(R.string.input_msg_code), Toast.LENGTH_SHORT).show();
            return;
        }
        diologLoading = new DiologLoading(getResources().getString(R.string.logging));
        diologLoading.show(getSupportFragmentManager(), "login");
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
            case R.id.phone_login_goback_rpw:
                this.finish();
                break;
            case R.id.phone_code_edit://验证码编辑框
                break;
            case R.id.phone_num_edit:
                break;
            case R.id.phone_send_code://发送验证码
                getPhoneNum();
                break;
            case R.id.phone_check_login_rpw://验证并登录的按钮
                String msgCode = msgCodeEdit.getText().toString().trim();
                checkLogin(phoneNumStr, "1", msgCode, uuidStr,
                        UrlString.APP_VERSION, UrlString.LOGIN_URL, 2);
                break;
            default:
                break;
        }
    }

    private class parseStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("kwkw","wknql");
            if (diologLoading != null) {
                diologLoading.dismiss();
            }
            exceptionDialog(PhoneLoginActivity.this,getText(R.string.request_fail).toString());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    //此处需要加入发送验证码失败提示
                    Log.e("sme", response);
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
                    timeCount.start();
                    break;
                case 2:
                    Log.e("login", response);
                    if (response != null) {
                        try {
                            JSONObject jsonAll = new JSONObject(response);
                            if (TextUtils.equals(jsonAll.getString("opt_state"),"fail")) {
                                diologLoading.dismiss();
                                Log.e("login", "aewl");
                                exceptionDialog(PhoneLoginActivity.this,getText(R.string.login_fail).toString());
                            } else if (TextUtils.equals(jsonAll.getString("opt_state"),"success")) {
                                setSharePrefrence(phoneNumStr, jsonAll.getString("token"));
                                Log.e("login", "登录成功" + response);
                                Intent intent = new Intent();
                                intent.setAction("action.loginfresh");
                                sendBroadcast(intent);
                                Intent mainIntent = new Intent(PhoneLoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }
                            ;

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
    public void exceptionDialog(Activity activity, String detail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.hint).setMessage(detail)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            sendCodeTv.setBackgroundColor(getResources().getColor(R.color.gray_default));
            sendCodeTv.setClickable(false);
            sendCodeTv.setText(millisUntilFinished / 1000 + "秒后可重新发送");
            sendCodeTv.setTextColor(getResources().getColor(R.color.gray_default));
        }

        @Override
        public void onFinish() {
            sendCodeTv.setText("重新获取验证码");
            sendCodeTv.setClickable(true);
//            sendCodeTv.setBackgroundColor(getResources().getColor(R.color.orange));
            sendCodeTv.setTextColor(ContextCompat.getColor(PhoneLoginActivity.this, R.color.orange));

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
