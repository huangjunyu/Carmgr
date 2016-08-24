package com.yiwucheguanjia.carmgr.account.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.animation.DiologLoading;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SetPasswordActivity extends FragmentActivity implements View.OnClickListener {
    private RippleView gobackRpw;
    private EditText newPwdEdit;
    private EditText newPwdCheckEdit;
    private Button submit;
    private String getUuid;
    private String phoneNum;
    private String msgCode;
    private String password;
    private DiologLoading diologLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
    }

    protected void initView() {
        gobackRpw = (RippleView) findViewById(R.id.setpwd_goback_rpw);
        newPwdEdit = (EditText) findViewById(R.id.setpwd_password_edit);
        newPwdCheckEdit = (EditText) findViewById(R.id.setpwd_password2_edit);
        submit = (Button) findViewById(R.id.setpwd_submit);
        submit.setOnClickListener(this);
        gobackRpw.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if (!bundle.getString("UUID").isEmpty() && !bundle.getString("PHONENUMBER").isEmpty()) {
            getUuid = bundle.getString("UUID");
            phoneNum = bundle.getString("PHONENUMBER");
            msgCode = bundle.getString("MSGCODE");
        }
    }

    protected Boolean getPassword() {
        password = newPwdEdit.getText().toString();
        if (!password.equals("")) {
            if (password.equals(newPwdCheckEdit.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    protected void resetPassword(String username, String new_password, String uuid,
                                 String verf_code, String type, String version, String url, int id) {
        diologLoading = new DiologLoading(getResources().getString(R.string.geting_code));
        diologLoading.show(getSupportFragmentManager(), "resetpassword");
        diologLoading.setCancelable(false);
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("new_password", new_password)
                .addParams("uuid", uuid)
                .addParams("verf_code", verf_code)
                .addParams("type", type)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setpwd_goback_rpw:
                finish();
                break;
            case R.id.setpwd_password_edit:
                break;
            case R.id.setpwd_password2_edit:
                break;
            case R.id.setpwd_submit:
                if (getPassword()) {
                    resetPassword(phoneNum, password, getUuid, msgCode, "2", UrlString.APP_VERSION,
                            UrlString.APPRESETPASSWORD, 1);
                }else {
                    Toast.makeText(this,getResources().getText(R.string.pwd_inconsistency),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    public void exceptionDialog(String detail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetPasswordActivity.this);
        builder.setTitle(R.string.hint).setMessage(detail)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected class parseStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("opt_state").equals("success")){
                            if (diologLoading != null) {
                                diologLoading.dismiss();
                            }
                            finish();
                        }else if (jsonObject.getString("opt_state").equals("fail")){
                            if (diologLoading != null) {
                                diologLoading.dismiss();
                            }
                            exceptionDialog(getText(R.string.reset_password).toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
