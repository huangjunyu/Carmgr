package com.yiwucheguanjia.carmgr.callyiwu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.yiwucheguanjia.carmgr.city.utils.SharedPreferencesUtils;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CallYiwu extends Fragment implements View.OnClickListener {
    private LinearLayout callYiWuView;
    private RelativeLayout callYiWuRl;
    private LinearLayout callBackground;
    private ImageView callyiwuImg;
    private TextView callYiwuTv;
    private TextView positionTv;
    private Button submitBtn;
    private EditText suggestEdit;
    private SharedPreferences sharedPreferences;
    private Button appointmentBtn;
    private RelativeLayout personalRl;

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
        callyiwuImg = (ImageView) callYiWuView.findViewById(R.id.callyiwu_img);
        callYiwuTv = (TextView) callYiWuView.findViewById(R.id.call_yiwu);
        appointmentBtn = (Button) callYiWuView.findViewById(R.id.call_appointment);
        callyiwuImg.setImageResource(R.mipmap.call_yiwu_img);
        personalRl = (RelativeLayout) callYiWuView.findViewById(R.id.call_personal_rl);
        submitBtn = (Button) callYiWuView.findViewById(R.id.callyiwu_submit);
        suggestEdit = (EditText) callYiWuView.findViewById(R.id.callyiwu_suggest_edit);
        positionTv = (TextView) callYiWuView.findViewById(R.id.progress_position_Tv);
        positionTv.setText(SharedPreferencesUtils.getCityName(getActivity()));
        personalRl.setOnClickListener(this);
        callYiwuTv.setOnClickListener(this);
        appointmentBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认拨打易务宝客服？");

        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4001119665"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                getActivity().startActivity(intent);
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
                    .addParams("click_area", click_area_id)
                    .addParams("detail", detail)
                    .addParams("token", token)
                    .addParams("version", version)
                    .id(id)
                    .build()
                    .execute(new CallYiwuStringCallback());
        }
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
        if (requestCode == 5 && resultCode == 5){
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
                postUerSuggest(sharedPreferences.getString("ACCOUNT", null),
                        suggestStr,
                        sharedPreferences.getString("TOKEN", null), UrlString.APP_VERSION,
                        UrlString.APPADVISE, 3);
                break;
            default:
                break;
        }
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
