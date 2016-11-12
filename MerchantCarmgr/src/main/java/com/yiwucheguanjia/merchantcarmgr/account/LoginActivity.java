package com.yiwucheguanjia.merchantcarmgr.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yiwucheguanjia.merchantcarmgr.BaseActivity;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/11.
 */
public class LoginActivity extends BaseActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR", this.MODE_PRIVATE);
        setContentView(R.layout.activity_login);
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

    @OnClick(R.id.login_button)
    void loginAccount() {
//        RetrofitTools.getInstance().retrofit().create(RequestSerives.class);
//        checkString(accountEdit, passwordEdit);
        OkGo.post(UrlString.LOGIN_URL)
                .tag(this)
                .params("username","13560102795")
                .params("password","952788")
                .params("type","0")
                .params("verf_code","")
                .params("uuid",UUID.randomUUID().toString())
                .params("version","1.0")
                .execute(new MyStringCallback(this,"nn") {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("re",s);
                    }
                });
    }

    @OnClick(R.id.login_goback_img_btn)
    void finishActivity() {
        finish();
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
            this.passwordString = passwordString;
            return true;
        }

    }

//    private void init2() {
//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(UrlString.LOGIN_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        final RequestSerives requestSerives = retrofit.create(RequestSerives.class);
//
//
//        retrofit2.Call<LoginBean> call = (retrofit2.Call<LoginBean>) requestSerives.postData("13560102795", "952788",
//                "0",
//                "",
//                UUID.randomUUID().toString(),
//                UrlString.APP_VERSION);
//
//        Observable<LoginBean> confiModel = requestSerives.postData("13560102795", "952788",
//                "0",
//                "",
//                UUID.randomUUID().toString(),
//                UrlString.APP_VERSION);
//        confiModel.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        new Subscriber<LoginBean>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(LoginBean loginBean) {
//
//                            }
//                        }
//                );
//        call.enqueue(new Callback<LoginBean>() {
//            @Override
//            public void onResponse(retrofit2.Call<LoginBean> call, Response<LoginBean> response) {
//                Log.e("成功7",response.body().getUsername());
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<LoginBean> call, Throwable t) {
//                Log.e("失败",t.getMessage());
//            }
//
//
//        });
//        call.enqueue(new retrofitCallback<LoginBean>(2));
//
//    }

     private void setSharePrefrence(String account, String password, String phoneNum) {
        SharedPreferences p = getSharedPreferences("CARMGR", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("ACCOUNT", account);
        edit.putString("PHONE", phoneNum);
        edit.commit();
    }
}
