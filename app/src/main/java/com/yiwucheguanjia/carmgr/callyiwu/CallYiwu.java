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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.LoginActivity;
import com.yiwucheguanjia.carmgr.personal.personalActivity;
import com.yiwucheguanjia.carmgr.utils.StringCallback;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

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
        sharedPreferences = getActivity().getSharedPreferences("CARMGR",getActivity().MODE_PRIVATE);
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
        callYiwuTv = (TextView)callYiWuView.findViewById(R.id.call_yiwu);
        appointmentBtn = (Button) callYiWuView.findViewById(R.id.call_appointment);
        callyiwuImg.setImageResource(R.mipmap.call_yiwu_img);
        personalRl = (RelativeLayout)callYiWuView.findViewById(R.id.call_personal_rl);
        personalRl.setOnClickListener(this);
        callYiwuTv.setOnClickListener(this);
        appointmentBtn.setOnClickListener(this);
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
    protected void postData(String username,String click_area_id,String detail,String token,
                            String version,String url,int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("click_area",click_area_id)
                .addParams("detail",detail)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new CallYiwuStringCallback());
    }
    protected void postUerTalk(String username,String advise_text,String token,String version,
                                 String url,int id){
        if (username == null || token == null) {
            username = "username";
            token = "token";
        }
        OkHttpUtils.get().url(url)
                .addParams("username", username)
                .addParams("advise_text",advise_text)
                .addParams("token", token)
                .addParams("version", version)
                .id(id)
                .build()
                .execute(new CallYiwuStringCallback());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_yiwu:
                dialog();
                postData(sharedPreferences.getString("ACCOUNT",null),"4000_2",
                        "拨打电话",sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
                        UrlString.APPRESETPASSWORD,2);
                break;
            case R.id.call_appointment:
                postData(sharedPreferences.getString("ACCOUNT",null),"4000_1",
                        "预约服务",sharedPreferences.getString("TOKEN",null), UrlString.APP_VERSION,
                        UrlString.APPRESETPASSWORD,1);
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
          default:
              break;
      }
    }

    protected class CallYiwuStringCallback extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            switch (id)
            {
                case 1:
                    Log.e("appoi",response);
                    break;
                case 2:
                    Log.e("call",response);
                    break;
                default:
                    break;
            }
        }
    }
}
