package com.yiwucheguanjia.merchantcarmgr.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yiwucheguanjia.merchantcarmgr.BaseActivity;
import com.yiwucheguanjia.merchantcarmgr.MainActivity;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.NewActivityUtil;
import com.yiwucheguanjia.merchantcarmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/11.
 */
public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 50);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", this.MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @BindView(R.id.login_account_edit)
    EditText accountEdit;
    @BindView(R.id.login_password_edit)
    EditText passwordEdit;
    private String usernameString;

    @OnClick({R.id.login_button,R.id.login_goback_rl})
    void click(View view){
        switch (view.getId()){
            case R.id.login_button:
                loginAccount();
                break;
            case R.id.login_goback_rl:
                finish();
                break;
            default:
                break;
        }
    }
    void loginAccount() {
        if (checkString(accountEdit, passwordEdit)) {
            OkGo.post(UrlString.LOGIN_URL)
                    .tag(this)
                    .params("username", accountEdit.getText().toString().trim())
                    .params("password", passwordEdit.getText().toString().trim())
                    .params("type", "0")
                    .params("verf_code", "")
                    .params("uuid", UUID.randomUUID().toString())
                    .params("version", "1.0")
                    .execute(
                            new MyStringCallback(this, getResources().getString(R.string.loading)) {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    //保存账号与token
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                            Intent intent = new Intent();
                                            intent.setAction("action.login_success");
                                            sendBroadcast(intent);
                                            setSharePrefrence(jsonObject.getString("username"), jsonObject.getString("token"), jsonObject.getString("merchant_id"));
                                            checkParkState();
                                        } else if (TextUtils.equals(jsonObject.getString("opt_state"), "fail")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle(LoginActivity.this.getString(R.string.hint))
                                                    .setMessage(LoginActivity.this.getString(R.string.login_fail))
                                                    .setPositiveButton(LoginActivity.this.getString(R.string.i_know),
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                        ;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
        }
    }

    private void checkParkState() {
        OkGo.post(UrlString.MAPP_CHECKPARKSTATE)
                .params("username", SharedPreferencesUtil.getInstance(LoginActivity.this).usernameSharedPreferences())
                .params("merchant_id", SharedPreferencesUtil.getInstance(LoginActivity.this).merchantIdSharedPreferences())
                .params("token", SharedPreferencesUtil.getInstance(LoginActivity.this).tokenSharedPreference())
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(LoginActivity.this, getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("ssewww", s);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.getString("opt_state"), "success")) {
                                NewActivityUtil newActivityUtil = new NewActivityUtil(LoginActivity.this, MainActivity.class);
                                newActivityUtil.newActivity();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle(LoginActivity.this.getString(R.string.hint))
                                        .setMessage(LoginActivity.this.getString(R.string.do_not_entry))
                                        .setPositiveButton(LoginActivity.this.getString(R.string.i_know),
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
            Toast.makeText(this, getResources().getText(R.string.account_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordString.equals("")) {
            Toast.makeText(this, getResources().getText(R.string.password_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            this.usernameString = usernameString;
            return true;
        }

    }

    private void setSharePrefrence(String account, String token, String merchantId) {
        SharedPreferences p = getSharedPreferences("CARMGR_MERCHANT", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = p.edit();
        Log.e("acount", account + token);
        edit.putString("ACCOUNT", account);
        edit.putString("TOKEN", token);
        edit.putString("VERSION", UrlString.APP_VERSION);//登录过这个APP之后的标记
        edit.putString("MERCHANTID", merchantId);
        edit.commit();
    }
}
