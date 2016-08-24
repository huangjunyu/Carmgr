package com.yiwucheguanjia.carmgr.callyiwu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.view.LoginActivity;
import com.yiwucheguanjia.carmgr.city.CityActivity;
import com.yiwucheguanjia.carmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.carmgr.home.model.FavorabledRecommendBean;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.scanner.CaptureActivity;
import com.yiwucheguanjia.carmgr.utils.JsonModel;
import com.yiwucheguanjia.carmgr.utils.RequestSerives;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.Tools;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;


/**
 * Created by Administrator on 2016/6/20.
 */
public class CallYiwu extends Fragment implements View.OnClickListener {
    private LinearLayout callYiWuView;
    private RelativeLayout callYiWuRl;
    private RelativeLayout scanRl;
    private LinearLayout callBackground;
    private ImageView callyiwuImg;
    private TextView callYiwuTv;
    private TextView positionTv;
    private Button submitBtn;
    private EditText suggestEdit;
    private SharedPreferences sharedPreferences;
    private Button appointmentBtn;
    private RelativeLayout personalRl;
    private RelativeLayout positionRl;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 1004;
    final private static int REQUEST_CODE_ASK_CAMERA = 1005;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR", getActivity().MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        callYiWuView = (LinearLayout) inflater.inflate(R.layout.activity_callyiwu, null);
        initView();
        return callYiWuView;
    }

    private void initView() {
        callBackground = (LinearLayout) callYiWuView.findViewById(R.id.callyiwu_background);
        scanRl = (RelativeLayout) callYiWuView.findViewById(R.id.yiwu_scan_rl);
        positionRl = (RelativeLayout) callYiWuView.findViewById(R.id.yiwu_position_rl);
        callyiwuImg = (ImageView) callYiWuView.findViewById(R.id.callyiwu_img);
        callYiwuTv = (TextView) callYiWuView.findViewById(R.id.call_yiwu);
        appointmentBtn = (Button) callYiWuView.findViewById(R.id.call_appointment);
        callyiwuImg.setImageResource(R.mipmap.call_yiwu_img);
        personalRl = (RelativeLayout) callYiWuView.findViewById(R.id.call_personal_rl);
        submitBtn = (Button) callYiWuView.findViewById(R.id.callyiwu_submit);
        suggestEdit = (EditText) callYiWuView.findViewById(R.id.callyiwu_suggest_edit);
        positionTv = (TextView) callYiWuView.findViewById(R.id.yiwu_position_Tv);
        positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        personalRl.setOnClickListener(this);
        positionRl.setOnClickListener(this);
        callYiwuTv.setOnClickListener(this);
        appointmentBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        scanRl.setOnClickListener(this);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认拨打易务宝客服？");

        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertDummyContactWrapper();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    protected void postData(String username, String click_area_id, String detail, String token,
                            String version, String url, int id) {
        if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(token)) {

            OkHttpUtils.get().url(url)
                    .addParams("username", username)
                    .addParams("click_area_id", click_area_id.toString())
                    .addParams("detail", detail)
                    .addParams("token", token)
                    .addParams("version", version)
                    .id(id)
                    .build()
                    .execute(new CallYiwuStringCallback());
        }
    }

    //相当于设置一个标识符,在回调函数中可以对应操作

    /**
     * 判断当前权限是否允许,弹出提示框来选择
     */
    private void insertDummyContactWrapper() {
//         需要验证的权限
        int hasWriteContactsPermission = checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            // 弹窗询问 ，让用户自己判断
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CODE_ASK_CALL_PHONE);
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "4001119665"));
        startActivity(intent);

    }

    private void openCamera() {

        int hasWriteContactsPermission = checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            // 弹窗询问 ，让用户自己判断
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_ASK_CAMERA);
            return;
        } else {
            Intent capterIntent = new Intent(getActivity(), CaptureActivity.class);
            capterIntent.setAction(Intent.ACTION_CAMERA_BUTTON);
            startActivityForResult(capterIntent, 4);
        }
    }

    /**
     * 用户进行权限设置后的回调函数 , 来响应用户的操作，无论用户是否同意权限，Activity都会
     * 执行此回调方法，所以我们可以把具体操作写在这里
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里写你需要相关权限的操作
                    Intent capterIntent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(capterIntent, 4);
                } else {
                    Toast.makeText(getActivity(), "权限没有开启", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "4001119665"));
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    protected void postUerSuggest(String username, String advise_text, String token,
                                  String version, String url, int id) {
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(getActivity(), getResources().getText(R.string.login_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(advise_text)) {
            OkHttpUtils.get().url(url)
                    .addParams("username", username)
                    .addParams("advise_text", advise_text)
                    .addParams("token", token)
                    .addParams("version", version)
                    .id(id)
                    .build()
                    .execute(new CallYiwuStringCallback());
        } else {
            Toast.makeText(getActivity(), getResources().getText(R.string.suggest_submit_hint),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == 5) {
            positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        } else if (requestCode == 4 && resultCode == 10) {//地区选择返回
            positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_yiwu:
                dialog();
                postData(sharedPreferences.getString("ACCOUNT", null), "4000_2",
                        "拨打电话", sharedPreferences.getString("TOKEN", null),
                        UrlString.APP_VERSION, UrlString.APP_LOG_USER_OPERATION, 2);
                break;
            case R.id.call_appointment:
                postData(sharedPreferences.getString("ACCOUNT", null), "4000_1",
                        "预约服务", sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION,
                        UrlString.APP_LOG_USER_OPERATION, 1);
                init2();
                break;
            case R.id.call_personal_rl:
                if (sharedPreferences.getString("ACCOUNT", null) != null) {
                    Intent intentPersonal = new Intent(getActivity(), personalActivity.class);
                    getActivity().startActivity(intentPersonal);
                } else {
                    Intent personalIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(personalIntent);
                }
                break;
            case R.id.callyiwu_submit:
                String suggestStr = suggestEdit.getText().toString().trim();
                postUerSuggest(sharedPreferences.getString("ACCOUNT", null), suggestStr,
                        sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION,
                        UrlString.APPADVISE, 3);
                break;
            case R.id.yiwu_scan_rl:
                openCamera();
                break;
            case R.id.yiwu_position_rl:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                startActivityForResult(cityIntent, 4);
            default:
                break;
        }
    }
    private void init2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://112.74.13.51:8080/carmgr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestSerives requestSerives = retrofit.create(RequestSerives.class);
        retrofit2.Call<JsonModel> call = requestSerives.getString("18617376560","ZY_0001",sharedPreferences.getString("TOKEN", null),"1.0");
        call.enqueue(new Callback<JsonModel>() {
            @Override
            public void onResponse(retrofit2.Call<JsonModel> call, Response<JsonModel> response) {
                Log.e("成功",response.body().getUsername());
            }

            @Override
            public void onFailure(retrofit2.Call<JsonModel> call, Throwable t) {
                Log.e("失败",t.getMessage());
            }
        });
    }

    protected class CallYiwuStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        protected void parseSuggestResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String opt_state = jsonObject.getString("opt_state");
                if (opt_state.equals("success")) {
                    Toast toast = Toast.makeText(getActivity(), "提交建议成功，谢谢您!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    suggestEdit.setText("");
                } else {
                    Toast toast = Toast.makeText(getActivity(), "提交建议失败，请重试！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    Log.e("appoi", response);
                    break;
                case 2:
                    Log.e("call", response);
                    break;
                case 3:
                    Log.e("advise", response);
                    parseSuggestResponse(response);
                    break;
                default:
                    break;
            }
        }
    }
}
