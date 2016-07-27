package com.yiwucheguanjia.carmgr.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SetPasswordActivity extends Activity implements View.OnClickListener {
    private ImageView goback;
    private EditText newPwdEdit;
    private EditText newPwdCheckEdit;
    private Button submit;
    private String getUuid;
    private String phoneNum;
    private String msgCode;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        initView();
    }

    protected void initView() {
        goback = (ImageView) findViewById(R.id.setpwd_goback_imgbtn);
        newPwdEdit = (EditText) findViewById(R.id.setpwd_password_edit);
        newPwdCheckEdit = (EditText) findViewById(R.id.setpwd_password2_edit);
        submit = (Button) findViewById(R.id.setpwd_submit);
        submit.setOnClickListener(this);
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
            case R.id.setpwd_goback_imgbtn:
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

    protected class parseStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("setp", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("opt_state").equals("success")){
                            Intent personalActivityIntent = new Intent(SetPasswordActivity.this,personalActivity.class);
                            startActivity(personalActivityIntent);
                            finish();
                        }else if (jsonObject.getString("opt_state").equals("fail")){
                            Toast.makeText(SetPasswordActivity.this,"reset password fail",Toast.LENGTH_SHORT).show();
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
