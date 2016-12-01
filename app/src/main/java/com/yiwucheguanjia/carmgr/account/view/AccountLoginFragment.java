package com.yiwucheguanjia.carmgr.account.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.carmgr.MainActivity;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.callback.MyStringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 已处理的投诉
 */
public class AccountLoginFragment extends Fragment implements View.OnClickListener{
    private String usernameString;
    private EditText loginUsernameEdit;
    private EditText loginPasswordEdit;
    private TextView forgetPwdTv;
    private Button accountLoginBtn;
    //登录状态提示
    private String loginState;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.account_login_fragment, container, false);
//        ButterKnife.bind(this, homeView);
        homeView.findViewById(R.id.account_loging_btn).setOnClickListener(this);
        homeView.findViewById(R.id.account_forget_pwd).setOnClickListener(this);
        loginUsernameEdit = (EditText)homeView.findViewById(R.id.account_account_et);
        loginPasswordEdit = (EditText)homeView.findViewById(R.id.account_password_et);
        return homeView;
    }


    @Override
    public void onClick(View v) {
        Log.e("n,,,,w","btn");
        switch (v.getId()){
            case R.id.account_loging_btn:
                Log.e("acoooo","aaew");
                if (checkString(loginUsernameEdit, loginPasswordEdit)) {
                    usernameString = loginUsernameEdit.getText().toString().trim();
                    loginAccount(loginUsernameEdit.getText().toString().trim(),
                            loginPasswordEdit.getText().toString().trim(),
                            "0","", UUID.randomUUID().toString(),"1.0", UrlString.LOGIN_URL);
                }
                break;
            case R.id.account_forget_pwd:
                Intent getPasswordIntent = new Intent(getActivity(), GetPassword.class);
                startActivity(getPasswordIntent);
                break;
            default:
                break;
        }
    }

    private void loginAccount(final String username, final String password,
                              String type, String verf_code, String uuid, String version, final String url) {
        OkGo.post(url)
                .tag(this)
                .params("username",username)
                .params("password",password)
                .params("type",type)
                .params("verf_code",verf_code)
                .params("uuid",uuid)
                .params("version",version)
                .execute(new MyStringCallback(getActivity(),"加载中。。。") {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonAll = new JSONObject(s);
                            //如果密码或账号不对
                            if (TextUtils.equals(jsonAll.getString("opt_state"),"fail")) {
                                Toast.makeText(getActivity(),"账号或密码不正确",Toast.LENGTH_SHORT).show();
                                loginState = jsonAll.getString("opt_info");
                            } else if (jsonAll.getString("opt_state").equals("success") && TextUtils.equals(jsonAll.getString("opt_state"),"success")) {

                                //登录成功后保存相关数据
                                setSharePrefrence(usernameString,jsonAll.getString("token"));
                                Intent intent = new Intent(
                                        getActivity(),
                                        MainActivity.class);
                                getActivity().startActivity(intent);
                                getActivity().finish();
                            };
                        } catch (JSONException e) {
                            e.printStackTrace();
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
            Toast.makeText(getActivity(), getResources().getText(R.string.acc_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordString.equals("")) {
            Toast.makeText(getActivity(), getResources().getText(R.string.pwd_can_not_null), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            this.usernameString = usernameString;
            return true;
        }

    }
}
