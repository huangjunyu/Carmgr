package com.yiwucheguanjia.carmgr.account.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/22.
 */
public class GetPassword extends Activity implements View.OnClickListener{
    private ImageView goback;
    private EditText phoneNumEdit;
    private EditText msgCodeEdit;
    private TextView sendCodeTv;
    private Button checkBtn;
    private SharedPreferences sharedPreferences;
    private String phoneNumStr;
    private String uuidStr;
    private String msgCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", MODE_PRIVATE);
        setContentView(R.layout.activity_get_password);
        uuidStr = UUID.randomUUID().toString();
        initView();
    }
    private void initView(){
        goback = (ImageView)findViewById(R.id.getpwd_goback_imgbtn);
        phoneNumEdit = (EditText)findViewById(R.id.getpwd_phone_num_edit);
        msgCodeEdit = (EditText)findViewById(R.id.getpwd_code_edit);
        sendCodeTv = (TextView)findViewById(R.id.getpwd_send_code);
        checkBtn = (Button)findViewById(R.id.getpwd_check_code);
        goback.setOnClickListener(this);
        sendCodeTv.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getpwd_goback_imgbtn:
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
                checkCode(phoneNumStr,phoneNumStr,uuidStr,msgCode,"2",UrlString.APP_VERSION,
                        UrlString.APP_CHECK_VERFCODE,2);
                break;
            default:
                break;
        }
    }

    /**
     * 验证手机号与短信验证码
     * @param username
     * @param mobile
     * @param uuid
     * @param verf_code
     * @param type
     * @param version
     * @param url
     * @param id
     */
    protected void checkCode(String username, String mobile,String uuid, String verf_code,String type,String version, String url,
                              int id) {
        if (verf_code.equals("")){
            Toast.makeText(GetPassword.this,getResources().getText(R.string.input_msg_code),Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("mobile",mobile)
                .addParams("verf_code", verf_code)
                .addParams("type",type)
                .addParams("uuid", uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
    }

    /**
     * 获取手机号码，并且判断格式、发送手机号码
     */
    protected void getPhoneNum(){
        Log.e("response", "owiewq");
        if (Tools.getInstance().isMobileNO(phoneNumEdit.getText().toString().trim())){
        phoneNumStr = phoneNumEdit.getText().toString().trim();
            sendCode(phoneNumStr,"2",
                    UUID.randomUUID().toString(),
                    UrlString.APP_VERSION,UrlString.APP_SENDVERF_CODE,1);
        }else {
            Toast.makeText(GetPassword.this,getResources().getText(R.string.phone_num_format),Toast.LENGTH_SHORT).show();
            phoneNumEdit.setText("");
        }

    }

    /**
     * 发送手机号
     * @param username
     * @param type
     * @param uuid
     * @param version
     * @param url
     * @param id
     */
    protected void sendCode(String username,String type,String uuid,String version,String url,
                            int id){
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("type", type)
                .addParams("uuid",uuid)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new parseStringCallBack());
    }

    private class parseStringCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id){
                case 1:
                    Log.e("sme",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        uuidStr = jsonObject.getString("uuid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Log.e("get2",response);
                    Intent setPwdIntent = new Intent(GetPassword.this,SetPasswordActivity.class);
                    setPwdIntent.putExtra("UUID",uuidStr);
                    setPwdIntent.putExtra("PHONENUMBER",phoneNumStr);
                    setPwdIntent.putExtra("MSGCODE",msgCode);
                    startActivity(setPwdIntent);
                    finish();
                default:
                    break;
            }
        }
    }
}
