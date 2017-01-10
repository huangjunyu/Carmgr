package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.animation.AlertDialog;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.Tools;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/12.
 */
public class EnterRegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.enter_goback_rl)
    RelativeLayout gobackRl;
    @BindView(R.id.enter_account_edit)
    EditText accountEdit;
    @BindView(R.id.enter_pwd_edit)
    EditText passwordEdit;
    @BindView(R.id.getpwd_phone_num_edit)
    EditText phoneNumEdit;
    @BindView(R.id.getpwd_code_edit)
    EditText msgCode;
    @BindView(R.id.enter_enter_btn)
    Button enterBtn;
    private String phoneNumStr;
    @BindView(R.id.enter_send_code)
    TextView sendCodeTv;
    @BindView(R.id.register_agree_check)
    CheckBox checkBox;

    private TimeCount timeCount;
    private Boolean checkBool = true;
    private String serviceUUid;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", MODE_PRIVATE);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.activity_enter);
        timeCount = new TimeCount(60000, 1000);
        ButterKnife.bind(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.enter_enter_btn, R.id.enter_goback_rl, R.id.enter_send_code})
    void enterNext(View view) {
        switch (view.getId()) {
            case R.id.enter_enter_btn:
                checkData();
                break;
            case R.id.enter_goback_rl:
                finish();
                break;
            case R.id.enter_send_code:
                getPhoneNum();
                break;
            default:
                break;
        }
//        Intent intent = new Intent(EnterRegisterActivity.this, MerchantEnterFragmentActivity.class);
//        startActivity(intent);
    }

//    @OnCheckedChanged(R.id.register_agree_check) void onChecked(){
//        Log.e("jj","nwkwn");
//    }

    /**
     * 获取手机号码，并且判断格式、发送手机号码
     */
    protected void getPhoneNum() {
        if (Tools.getInstance().isMobileNO(phoneNumEdit.getText().toString().trim())) {
            phoneNumStr = phoneNumEdit.getText().toString().trim();
            Log.e("phone",phoneNumStr);
            final String uuid = UUID.randomUUID().toString();
            OkGo.post(UrlString.SEND_CODE_URL)
                    .tag(this)
                    .params("username", phoneNumStr)
                    .params("type", "0")
                    .params("uuid", uuid)
                    .params("version", UrlString.APP_VERSION)
                    .params("usertype", "1")
                    .execute(new MyStringCallback(this, getResources().getString(R.string.sending)) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            //获取返回的相关注册需要信息
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(s);
                                Log.e("uiuu", uuid);
                                Log.e("ssss", s);
                                if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                    timeCount.start();
                                    serviceUUid = uuid;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } else {
//            Toast.makeText(GetPassword.this, getResources().getText(R.string.phone_num_format), Toast.LENGTH_SHORT).show();
            phoneNumEdit.setText("");
        }

    }

    private void checkData(){
        //如果输入的账号与验证的手机号码相同
        if (TextUtils.equals(accountEdit.getText().toString(),phoneNumStr)){
            //如果密码里面不包含空格
            if (formalRegex(passwordEdit.getText().toString()) && !TextUtils.isEmpty(msgCode.getText().toString())){
                OkGo.post(UrlString.REGISTER_URL)
                        .tag(this)
                        .params("username",phoneNumStr)
                        .params("password",passwordEdit.getText().toString().trim())
                        .params("mobile",phoneNumStr)
                        .params("terminal_os","Android")
                        .params("terminal_type", Build.MODEL)
                        .params("verf_code",msgCode.getText().toString().trim())
                        .params("user_type","1")
                        .execute(new MyStringCallback(this,getResources().getString(R.string.loading)) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("sussess",s);
                                Intent intent = new Intent();
                                intent.setClass(EnterRegisterActivity.this, MerchantEnterFragmentActivity.class);//从一个activity跳转到另一个activity
                                intent.putExtra("password", passwordEdit.getText().toString().trim());//给intent添加额外数据，key为“str”,key值为"Intent Demo"
                                startActivity(intent);
                                setSharePrefrence(phoneNumStr);
                                EnterRegisterActivity.this.finish();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.e("eee",e.toString());
                            }
                        });
            }else {
                Log.e("hwhw","nnn");
                //密码格式不对或验证码为空
                AlertDialog alertDialog = new AlertDialog(EnterRegisterActivity.this,"密码格式不对或验证码为空");
                alertDialog.instance().showAlertDialog();
            }
        }else {
            //输入的账号与验证的手机号不同
            AlertDialog alertDialog = new AlertDialog(EnterRegisterActivity.this,"输入的账号与验证的手机号不同");
            alertDialog.instance().showAlertDialog();
        }
    }

    public Boolean formalRegex(String passwordStr) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z\u4E00-\u9FA5]+");
        Matcher matcher = pattern.matcher(passwordStr);
        if (!matcher.matches() ) {
            Log.e("nnn","no");
            return false;
        } else {
            Log.e("nnn","no4");
            return true;
        }
    }

    private void setSharePrefrence(String account) {
        SharedPreferences p = getSharedPreferences("CARMGR_MERCHANT", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("ACCOUNT", account);
        edit.putString("VERSION",UrlString.APP_VERSION);//登录过这个APP之后的标记
        edit.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkBool = isChecked;
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
            sendCodeTv.setTextColor(ContextCompat.getColor(EnterRegisterActivity.this, R.color.orange));

        }
    }
}
