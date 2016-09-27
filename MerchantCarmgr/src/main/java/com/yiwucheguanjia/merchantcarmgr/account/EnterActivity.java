package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.animation.DiologLoading;
import com.yiwucheguanjia.merchantcarmgr.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/12.
 */
public class EnterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
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


    private DiologLoading diologLoading;
    private Boolean checkBool = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),0);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_enter);

        ButterKnife.bind(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.enter_goback_rl)void goback(){
        finish();
    }
    @OnClick(R.id.enter_send_code)void sendCode(){
        diologLoading = new DiologLoading(getResources().getString(R.string.geting_code));
        diologLoading.show(getSupportFragmentManager(), "login");
        diologLoading.setCancelable(false);
    }
    @OnClick(R.id.enter_enter_btn)void enterNext(){
        Intent intent = new Intent(EnterActivity.this,MerchantEnter.class);
        startActivity(intent);
    }

//    @OnCheckedChanged(R.id.register_agree_check) void onChecked(){
//        Log.e("jj","nwkwn");
//    }
    /**
     * 获取手机号码，并且判断格式、发送手机号码
     */
    protected void getPhoneNum() {
        if (Tools.getInstance().isMobileNO(phoneNumEdit.getText().toString().trim())) {
//            phoneNumStr = phoneNumEdit.getText().toString().trim();
//            sendCode(phoneNumStr, "2",
//                    UUID.randomUUID().toString(),
//                    UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE, 1);
        } else {
//            Toast.makeText(GetPassword.this, getResources().getText(R.string.phone_num_format), Toast.LENGTH_SHORT).show();
            phoneNumEdit.setText("");
        }

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
            sendCodeTv.setTextColor(ContextCompat.getColor(EnterActivity.this, R.color.orange));

        }
    }
}
