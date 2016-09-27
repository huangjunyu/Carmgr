package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.utils.RequestSerives;
import com.yiwucheguanjia.merchantcarmgr.utils.RetrofitTools;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/11.
 */
public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    @BindView(R.id.login_account_edit)
    EditText accountEdit;
    @BindView(R.id.login_password_edit)
    EditText passwordEdit;
    @BindView(R.id.login_goback_img_btn)
    ImageView gobackImg;
    private String usernameString;
    private String passwordString;

    @OnClick(R.id.login_button) void loginAccount(){
        RetrofitTools.getInstance().retrofit().create(RequestSerives.class);
        checkString(accountEdit,passwordEdit);
    }
    @OnClick(R.id.login_goback_img_btn) void finishActivity(){
        finish();
    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            //登录
//            case R.id.login_button:
////                checkString()
////                if (checkString(loginUsernameEdit, loginPasswordEdit)) {
////                    usernameString = loginUsernameEdit.getText().toString().trim();
////                    passwordString = loginPasswordEdit.getText().toString().trim();
////                    loginAccount(this.usernameString, this.passwordString, "0", "",
////                            UUID.randomUUID().toString(), "1.0", UrlString.LOGIN_URL, 1);
////                }
//                break;
////            case R.id.login_back_rl:
////                this.finish();
////                break;
////            case R.id.login_get_pwd_tv:
////                Intent getPasswordIntent = new Intent(LoginActivity.this, GetPassword.class);
////                startActivity(getPasswordIntent);
////                break;
//            default:
//                break;
//        }
//    }

//    private void loginAccount(){
//        //
//        RetrofitTools.getInstance().retrofit().create(RequestSerives.class);
//    }

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
            Toast.makeText(this, getResources().getText(R.string.account_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordString.equals("")) {
            Toast.makeText(this, getResources().getText(R.string.password_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            this.usernameString = usernameString;
            this.passwordString = passwordString;
            return true;
        }

    }
}
