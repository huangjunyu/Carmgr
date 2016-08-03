package com.yiwucheguanjia.carmgr.account.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cn.smssdk.EventHandler;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/6/25.
 */
public class RegisterActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private String phoneNumString;
    private ImageButton gobackImgBtn;
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
    private ImageView register_three_img;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        gobackImgBtn = (ImageButton) findViewById(R.id.register_goback_imgbtn);
        register_number_txt = (TextView) findViewById(R.id.register_number_txt);
        register_first_img = (ImageView) findViewById(R.id.register_first_img);
        register_code_txt = (TextView) findViewById(R.id.register_code_txt);
        register_second_img = (ImageView) findViewById(R.id.register_second_img);
        register_setting_pwd = (TextView) findViewById(R.id.register_setting_pwd);
        register_three_img = (ImageView) findViewById(R.id.register_three_img);
        register_code_sent = (TextView) findViewById(R.id.register_code_sent);
        register_number_edit = (EditText) findViewById(R.id.register_number_edit);
        register_pwd_edit = (EditText) findViewById(R.id.register_pwd_edit);
        register_pwd_again = (EditText) findViewById(R.id.register_pwd_again);
        register_edit_divide = (View) findViewById(R.id.register_edit_divide);
        userAgreement = (TextView) findViewById(R.id.yiwu_agreement_txt);
        register_button = (Button) findViewById(R.id.register_button);
        registerCb = (CheckBox) findViewById(R.id.register_agree_check);
        register_button.setOnClickListener(this);
        gobackImgBtn.setOnClickListener(this);
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
                Log.e("uuid", uuid);
                sendverfCode(usernameString, "0", uuid, UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE, 1);
            }else {
                Toast.makeText(RegisterActivity.this,getText(R.string.must_checked_agreetment),Toast.LENGTH_SHORT).show();
                return null;
            }

        } else if (!register_number_edit.getText().toString().trim().equals("") && step == 2) {
            Log.e("222", usernameString);
            checkVerfcode(usernameString, usernameString,
                    register_number_edit.getText().toString().trim(),
                    "1", uuidStr, UrlString.APP_VERSION, UrlString.APP_CHECK_VERFCODE, 2);
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
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("mobile", mobile)
                .addParams("verfCode", verfCode)
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
        edit.putString("PASSWORD", password);
        edit.putString("PHONE", phoneNum);
        edit.commit();
    }

    private Boolean checkString(EditText accountEdit, EditText checkPasswordEdit) {
        String accountContain = accountEdit.getText().toString().trim();
        String passwordContain = checkPasswordEdit.getText().toString().trim();
        if (checkUserService && step == 3) {
            if (accountContain.equals("")) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_can_not_null), Toast.LENGTH_SHORT).show();
                return false;
            } else if (accountContain.length() < 1) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_length_below), Toast.LENGTH_SHORT).show();
                return false;
            } else if (accountContain.length() > 8) {
                Toast.makeText(RegisterActivity.this, getResources().getText(R.string.pwd_length_overtop), Toast.LENGTH_SHORT).show();
                return false;
            } else if (!passwordContain.equals(accountContain)) {
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
            case R.id.register_goback_imgbtn:
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
                    register_three_img.setImageResource(R.mipmap.register_right_pre);
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

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                //发送手机号码得到了响应
                case 1:
                    Log.e("response1", response);
                    //到了此步，说明服务器已经响应请求
                    //解析发送手机号码响应
                    parseSendPhoneNumBack(response);
                    //更改第一步骤的图标与字体颜色
                    handler.sendEmptyMessage(1);
                    break;
                case 2://发送短信验证码的响应
                    //解析发送短信验证码响应
                    Log.e("response2", response);
                    //更改第二步骤的图标与字体颜色
                    handler.sendEmptyMessage(2);
                    break;
                case 3://注册成功
                    Log.e("33", response);
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
    protected void parseSendPhoneNumBack(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            uuidStr = jsonObject.getString("uuid");
//            usernameString = jsonObject.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
