package com.yiwucheguanjia.carmgr.account.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
 * Created by Administrator on 2016/6/25.
 */
public class RegisterActivity extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private String phoneNumString;
    private RippleView gobackImgRpw;
    private static String usernameString = null;
    private static String passwordString = null;
    private RelativeLayout inputPhoneNumRl;
    private RelativeLayout inputCodeRl;
    private RelativeLayout inputPassword;
    private TextView register_number_txt;
    private ImageView register_first_img;
    private TextView register_code_txt;
    private ImageView register_second_img;
    private TextView register_setting_pwd;
    //    private ImageView register_three_img;
    private TextView register_code_sent;
    private EditText register_number_edit;
    private EditText register_pwd_edit;
    private EditText register_pwd_again;
    private TextView userAgreement;//用户协议
    private View register_edit_divide;
    private Button register_button;
    private CheckBox registerCb;
    private int step = 1;//用来统计注册的第几步骤
    private Boolean checkUserService = true;//用户协定
    private String account;
    private String uuidStr;
    private Boolean checkBollean = true;//是否同意易务协议标记
    private DiologLoading diologLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        gobackImgRpw = (RippleView) findViewById(R.id.register_goback_rpw);
        register_number_txt = (TextView) findViewById(R.id.register_number_txt);
        register_first_img = (ImageView) findViewById(R.id.register_first_img);
        register_code_txt = (TextView) findViewById(R.id.register_code_txt);
        register_second_img = (ImageView) findViewById(R.id.register_second_img);
        register_setting_pwd = (TextView) findViewById(R.id.register_setting_pwd);
        register_code_sent = (TextView) findViewById(R.id.register_code_sent);
        register_number_edit = (EditText) findViewById(R.id.register_number_edit);
        register_pwd_edit = (EditText) findViewById(R.id.register_pwd_edit);
        register_pwd_again = (EditText) findViewById(R.id.register_pwd_again);
        register_edit_divide = (View) findViewById(R.id.register_edit_divide);
        userAgreement = (TextView) findViewById(R.id.yiwu_agreement_txt);
        register_button = (Button) findViewById(R.id.register_button);
        registerCb = (CheckBox) findViewById(R.id.register_agree_check);
        register_button.setOnClickListener(this);
        gobackImgRpw.setOnClickListener(this);
        userAgreement.setOnClickListener(this);
        registerCb.setOnCheckedChangeListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private String getPhoneNum() {
        //判断是不是手机号码
        String username = register_number_edit.getText().toString().trim();
        if (!Tools.getInstance().isMobileNO(username) && step == 1) {
            Toast.makeText(this, "手机号码不符合格式", Toast.LENGTH_SHORT).show();
            return null;
        } else if (Tools.getInstance().isMobileNO(username) && step == 1) {//如果是手机号码，并且是第一步骤
            if (checkBollean == true) {
                usernameString = username;
                String uuid = UUID.randomUUID().toString();
                sendverfCode(usernameString, "0", uuid, UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE, 1);
            } else {
                Toast.makeText(RegisterActivity.this, getText(R.string.must_checked_agreetment), Toast.LENGTH_SHORT).show();
                return null;
            }

        } else if (!TextUtils.isEmpty(register_number_edit.getText().toString().trim()) && step == 2) {
            Log.e("222", usernameString);
            checkVerfcode(usernameString, usernameString,
                    register_number_edit.getText().toString().trim(),
                    "0", uuidStr, UrlString.APP_VERSION, UrlString.APP_CHECK_VERFCODE, 2);
            //当两个编辑框的字符串相同时方可提交数据
        } else if (checkString(register_pwd_edit, register_pwd_again) && step == 3) {

            registerAccount(usernameString, register_pwd_edit.getText().toString().trim(),
                    usernameString, UrlString.REGISTER_URL, register_number_edit.getText().toString().trim(), "0", 3);
        }

        // 注册回调监听接口

        return null;
    }

    /**
     * 发送手机号码
     *
     * @param username
     * @param type
     * @param uuid
     * @param version
     * @param url
     * @param id
     */
    protected void sendverfCode(String username, String type, String uuid, String version, String url, int id) {
        diologLoading = new DiologLoading(getResources().getString(R.string.checking));
        diologLoading.show(getSupportFragmentManager(), "register_checking");
        diologLoading.setCancelable(false);
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("type", type)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseRegisterStringCallBack());
    }

    /**
     * 发送接收到的验证码
     *
     * @param username
     * @param mobile
     * @param verfCode
     * @param type
     * @param uuid
     * @param version
     * @param url
     * @param id
     */
    protected void checkVerfcode(String username, String mobile, String verfCode, String type,
                                 String uuid, String version, String url, int id) {
        diologLoading = new DiologLoading(getResources().getString(R.string.checking));
        diologLoading.show(getSupportFragmentManager(), "register_checking");
        diologLoading.setCancelable(false);
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("mobile", mobile)
                .addParams("verf_code", verfCode)
                .addParams("type", type)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseRegisterStringCallBack());
    }

    private void setSharePrefrence(String account, String password, String phoneNum) {
        SharedPreferences p = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("ACCOUNT", account);
//        edit.putString("PASSWORD", password);
        edit.putString("PHONE", phoneNum);
        edit.commit();
    }

    private Boolean checkString(EditText accountEdit, EditText checkPasswordEdit) {
        String accountContain = accountEdit.getText().toString().trim();
        String passwordContain = checkPasswordEdit.getText().toString().trim();
        if (checkUserService && step == 3) {
            if (TextUtils.isEmpty(accountContain)) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_can_not_null), Toast.LENGTH_SHORT).show();
                return false;
            } else if (accountContain.length() < 1) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_length_below), Toast.LENGTH_SHORT).show();
                return false;
            } else if (accountContain.length() > 16) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_length_overtop), Toast.LENGTH_SHORT).show();
                return false;
            } else if (!TextUtils.equals(passwordContain,accountContain)) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_inconsistency), Toast.LENGTH_SHORT).show();
                return false;
            } else {
                passwordString = passwordContain;
                setSharePrefrence(usernameString, passwordString, usernameString);
                return true;
            }
        }

        return false;
    }

    private void registerAccount(String username, String password, String mobile,
                                 String url, String verf_code, String user_type, int id) {
        diologLoading = new DiologLoading(getResources().getString(R.string.checking));
        diologLoading.show(getSupportFragmentManager(), "register_checking");
        diologLoading.setCancelable(false);
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("password", password)
                .addParams("mobile", mobile)
                .addParams("terminal_os", "Android")
                .addParams("terminal_type", Build.MODEL)
                .addParams("verf_code", verf_code)
                .addParams("user_type", user_type)
                .id(id)
                .build()
                .execute(new parseRegisterStringCallBack());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                getPhoneNum();
                break;
            case R.id.register_goback_rpw:
                this.finish();
                break;
            case R.id.yiwu_agreement_txt:
                Intent userAgreementInten = new Intent(RegisterActivity.this, UserAgreement.class);
                startActivity(userAgreementInten);
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    step = 2;
                    register_code_sent.setVisibility(View.VISIBLE);
                    register_code_sent.setText(getResources().getText(R.string.code_sent) + usernameString);
                    register_number_txt.setTextColor(getResources().getColor(R.color.gray_default));
                    register_first_img.setImageResource(R.mipmap.register_right_nor);
                    register_code_txt.setTextColor(getResources().getColor(R.color.orange));//步骤2高亮颜色
                    register_second_img.setImageResource(R.mipmap.register_right_pre);//步骤2高亮图标
                    register_number_edit.setText("");
                    register_number_edit.setHint(R.string.msg_code);
                    register_button.setText(R.string.submit_code);
                    break;
                case 2:
                    step = 3;
                    register_code_sent.setVisibility(View.GONE);
                    register_number_edit.setVisibility(View.GONE);
                    register_pwd_edit.setVisibility(View.VISIBLE);
                    register_pwd_again.setVisibility(View.VISIBLE);
                    register_edit_divide.setVisibility(View.VISIBLE);
                    register_code_txt.setTextColor(getResources().getColor(R.color.gray_default));
                    register_second_img.setImageResource(R.mipmap.register_right_nor);
                    register_setting_pwd.setTextColor(getResources().getColor(R.color.orange));
//                    register_three_img.setImageResource(R.mipmap.register_right_pre);
                    register_button.setText(R.string.register_account);
                    break;
                default:
                    //跳转到个人用户中心
                    break;
            }

        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkBollean = isChecked;
    }

    protected class parseRegisterStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (diologLoading != null) {
                diologLoading.dismiss();
            }
            registerDialog(getText(R.string.request_fail).toString(),
                    getText(R.string.hint).toString(),
                    getText(R.string.ok).toString(),
                    getText(R.string.cancel).toString());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                //发送手机号码得到了响应
                case 1:
                    Log.e("response1", response);
                    //到了此步，说明服务器已经响应请求
                    //解析发送手机号码响应
                    parseSendPhoneNumBack(response,1);

                    break;
                case 2://发送短信验证码的响应
                    //解析发送短信验证码响应
                    Log.e("response2", response);
                    parseSendPhoneNumBack(response,2);

                    break;
                case 3://注册成功
                    Log.e("33", response);
                    parseSendPhoneNumBack(response,3);
                    //交给handler处理注册成功后的事宜
                    handler.sendEmptyMessage(3);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 解析接收到的验证码
     *
     * @param response
     */
    protected void parseSendPhoneNumBack(String response, int num) {
        if (num == 1) {

            try {
                JSONObject jsonObject = new JSONObject(response);

                String opt_state = jsonObject.getString("opt_state");
                if (TextUtils.equals(opt_state, "fail")) {
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
                    registerDialog(getText(R.string.phone_number_registered).toString(),
                            getText(R.string.hint).toString(),
                            getText(R.string.ok).toString(),
                            getText(R.string.cancel).toString());
                } else if (TextUtils.equals(opt_state, "success")) {
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
                    uuidStr = jsonObject.getString("uuid");
                    //更改第一步骤的图标与字体颜色
                    handler.sendEmptyMessage(1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (num == 2){
            try {
                JSONObject jsonObject = new JSONObject(response);

                String opt_state = jsonObject.getString("opt_state");
                if (TextUtils.equals(opt_state, "fail")) {
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
                    registerDialog(getText(R.string.check_fail).toString(),
                            getText(R.string.hint).toString(),
                            getText(R.string.ok).toString(),
                            getText(R.string.cancel).toString());
                } else if (TextUtils.equals(opt_state, "success")) {
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
                    //更改第二步骤的图标与字体颜色
                    handler.sendEmptyMessage(2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if (num == 3){
            try {
                JSONObject jsonObject = new JSONObject(response);

                String opt_state = jsonObject.getString("opt_state");
                if (TextUtils.equals(opt_state, "fail")) {
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
                    registerDialog(getText(R.string.check_fail).toString(),
                            getText(R.string.hint).toString(),
                            getText(R.string.ok).toString(),
                            getText(R.string.cancel).toString());
                } else if (TextUtils.equals(opt_state, "success")) {
                    //13978957403
                    if (diologLoading != null) {
                        diologLoading.dismiss();
                    }
//                    Intent loginInten = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(loginInten);
                    //结束当前界面，退回到登录界面
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void registerDialog(String registered, String title, String positiveTxt,
                                  String negativeTxt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(registered);
        builder.setTitle(title);
        builder.setPositiveButton(positiveTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
