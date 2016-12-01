package com.yiwucheguanjia.carmgr.account.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.callback.MyStringCallback;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/1.
 */
public class PhoneLoginFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private EditText phoneNumEdit;
    private Button sendCodeTv;
    private Button submitBtn;
    private EditText msgCodeEdit;
    private String phoneNumStr;
    private TimeCount timeCount;
    private String uuidStr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeCount = new TimeCount(60000, 1000);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_phone_login, container, false);
        ButterKnife.bind(this, homeView);
        initView(homeView);
        return homeView;
    }

    protected void initView(View homeView) {
        phoneNumEdit = (EditText) homeView.findViewById(R.id.phone_num_et);
        msgCodeEdit = (EditText) homeView.findViewById(R.id.phone_code_et);
        sendCodeTv = (Button) homeView.findViewById(R.id.phone_send_code);
        submitBtn = (Button) homeView.findViewById(R.id.phone_submit_btn);
        submitBtn.setOnClickListener(this);
        sendCodeTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_send_code:
                getPhoneNum();
                break;
            case R.id.phone_submit_btn:
                checkLogin(phoneNumStr, "1", msgCodeEdit.getText().toString().trim(), uuidStr,
                        UrlString.APP_VERSION, UrlString.LOGIN_URL);
                break;
            default:
                break;
        }
    }

    //获取手机号码，并且判断格式、发送手机号码
    protected void getPhoneNum() {
        uuidStr = UUID.randomUUID().toString();
        phoneNumStr = phoneNumEdit.getText().toString().trim();
        if (Tools.getInstance().isMobileNO(phoneNumStr)) {
            sendCode(phoneNumStr, "1",
                    uuidStr,
                    UrlString.APP_VERSION, UrlString.APP_SENDVERF_CODE);
        } else {
            Toast.makeText(getActivity(), getResources().getText(R.string.phone_num_format), Toast.LENGTH_SHORT).show();
            phoneNumEdit.setText("");
        }
    }

    protected void sendCode(String username, String type, String uuid, String version, String url) {
        OkGo.post(url)
                .params("username", username)
                .params("type", type)
                .params("uuid", uuid)
                .params("version", version)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //发送请求成功后开始倒计时
                        timeCount.start();

                    }
                });
    }

    protected void checkLogin(final String username, String type, String verf_code, String uuid, String version, String url) {
        Log.e("user", username + type + " " + verf_code + " " + uuid + version + url);
        if (TextUtils.isEmpty(verf_code)) {
            Toast.makeText(getActivity(), getResources().getText(R.string.input_msg_code), Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.post(url)
                .tag(this)
                .params("username", username)
                .params("type", type)
                .params("verf_code", verf_code)
                .params("uuid", uuid)
                .params("version", version)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonAll = new JSONObject(s);
                            if (TextUtils.equals(jsonAll.getString("opt_state"), "fail")) {
                                Log.e("nnwekk", s);
                                //密码或账号不对的提示
                            } else if (TextUtils.equals(jsonAll.getString("opt_state"), "success")) {
                                Log.e("can", "gothat");
                                setSharePrefrence(username, jsonAll.getString("token"));
//                                Intent intent = new Intent();
//                                intent.setAction("action.loginfresh");
//                                getActivity().sendBroadcast(intent);
                                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                startActivity(mainIntent);
                                timeCount.cancel();
                                getActivity().finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("eeeee", e.toString());
                        }
                    }
                });

    }

    private void setSharePrefrence(String account, String token) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("ACCOUNT", account);
        edit.putString("TOKEN", token);
        edit.commit();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sendCodeTv.setClickable(false);
            sendCodeTv.setText(millisUntilFinished / 1000 + "秒后可重新发送");
                sendCodeTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray_default));
        }

        @Override
        public void onFinish() {
            sendCodeTv.setText("重新获取验证码");
            sendCodeTv.setClickable(true);
                sendCodeTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange));
        }

    }
}
