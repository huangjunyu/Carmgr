package com.yiwucheguanjia.carmgr.account.view;

import android.app.AlertDialog;
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
 * Created by Administrator on 2016/7/22.
 */
public class GetPassword extends FragmentActivity implements View.OnClickListener {
    private RippleView gobackRpw;
    private EditText phoneNumEdit;
    private EditText msgCodeEdit;
    private Button sendCodeTv;
    private RippleView checkRpw;
    private TimeCount timeCount;
    private SharedPreferences sharedPreferences;
    private DiologLoading diologLoading;
    private String phoneNumStr;
    private String uuidStr;
    private String msgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        timeCount = new TimeCount(60000, 1000);
        setContentView(R.layout.activity_get_password);
        uuidStr = UUID.randomUUID().toString();
        initView();
    }

    private void initView() {
        gobackRpw = (RippleView) findViewById(R.id.setpwd_goback_rpw);
        phoneNumEdit = (EditText) findViewById(R.id.getpwd_phone_num_edit);
        msgCodeEdit = (EditText) findViewById(R.id.getpwd_code_edit);
        sendCodeTv = (Button) findViewById(R.id.getpwd_send_code);
        checkRpw = (RippleView) findViewById(R.id.getpwd_check_code_rpw);
        gobackRpw.setOnClickListener(this);
        sendCodeTv.setOnClickListener(this);
        checkRpw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setpwd_goback_rpw:
                this.finish();
                break;
            case R.id.getpwd_phone_num_edit:
                break;
            case R.id.getpwd_code_edit://验证码编辑框
                break;
            case R.id.getpwd_send_code://发送验证码
                getPhoneNum();
                break;
            case R.id.getpwd_check_code://验证的按钮
                msgCode = msgCodeEdit.getText().toString().trim();
                phoneNumStr = phoneNumEdit.getText().toString().trim();
                checkCode(phoneNumStr, phoneNumStr, uuidStr, msgCode, "2", UrlString.APP_VERSION,
                        UrlString.APP_CHECK_VERFCODE, 2);
                break;
            default:
                break;
        }
    }

    /**
     * 验证手机号与短信验证码
     *
     * @param username
     * @param mobile
     * @param uuid
     * @param verf_code
     * @param type
     * @param version
     * @param url
     * @param id
     */
    protected void checkCode(String username, String mobile, String uuid, String verf_code, String type, String version, String url,
                             int id) {
        if (TextUtils.isEmpty(verf_code)) {
            Toast.makeText(GetPassword.this, getResources().getText(R.string.input_msg_code), Toast.LENGTH_SHORT).show();
            return;
        }
        diologLoading = new DiologLoading(getResources().getString(R.string.checking));
        diologLoading.show(getSupportFragmentManager(), "checking");
        diologLoading.setCancelable(false);

        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("mobile", mobile)
                .addParams("verf_code", verf_code)
                .addParams("type", type)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
    }

    /**
     * 获取手机号码，并且判断格式、发送手机号码
     */
    protected void getPhoneNum() {
        if (Tools.getInstance().isMobileNO(phoneNumEdit.getText().toString().trim())) {
            phoneNumStr = phoneNumEdit.getText().toString().trim();
            sendCode(phoneNumStr, "2",
                    UUID.randomUUID().toString(),
                    UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE, 1);
        } else {
            Toast.makeText(GetPassword.this, getResources().getText(R.string.phone_num_format), Toast.LENGTH_SHORT).show();
            phoneNumEdit.setText("");
        }

    }

    /**
     * 发送手机号
     *
     * @param username
     * @param type
     * @param uuid
     * @param version
     * @param url
     * @param id
     */
    protected void sendCode(String username, String type, String uuid, String version, String url,
                            int id) {
        diologLoading = new DiologLoading(getResources().getString(R.string.geting_code));
        diologLoading.show(getSupportFragmentManager(), "login");
        diologLoading.setCancelable(false);
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("type", type)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
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
            sendCodeTv.setTextColor(ContextCompat.getColor(GetPassword.this, R.color.orange));

        }
    }

    public void exceptionDialog(String detail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GetPassword.this);
        builder.setTitle(R.string.hint).setMessage(detail)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
                    timeCount.start();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {

                            uuidStr = jsonObject.getString("uuid");
                            if (diologLoading != null) {
                                diologLoading.dismiss();
                            }
                        } else if (TextUtils.equals(jsonObject.getString("opt_state"), "fail")) {
                            if (diologLoading != null) {
                                diologLoading.dismiss();
                            }
                            exceptionDialog(getText(R.string.login_fail).toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Log.e("get2", response);
                    if (response != null) {
                        try {
                            JSONObject jsonAll = new JSONObject(response);
                            if (TextUtils.equals(jsonAll.getString("opt_state"), "fail")) {
                                if (diologLoading != null) {
                                    diologLoading.dismiss();
                                }
                                exceptionDialog(getText(R.string.check_fail).toString());
                            } else if (TextUtils.equals(jsonAll.getString("opt_state"), "success")) {
                                if (diologLoading != null) {
                                    diologLoading.dismiss();
                                }
                                Intent setPwdIntent = new Intent(GetPassword.this, SetPasswordActivity.class);
                                setPwdIntent.putExtra("UUID", uuidStr);
                                setPwdIntent.putExtra("PHONENUMBER", phoneNumStr);
                                setPwdIntent.putExtra("MSGCODE", msgCode);
                                startActivity(setPwdIntent);
                                finish();
                            }
                            ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
