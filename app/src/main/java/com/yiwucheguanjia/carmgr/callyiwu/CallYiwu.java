package com.yiwucheguanjia.carmgr.callyiwu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.account.LoginActivity;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CallYiwu extends Fragment implements View.OnClickListener {
    private LinearLayout callYiWuView;
    private RelativeLayout callYiWuRl;
    private LinearLayout callBackground;
    private ImageView callyiwuImg;
    private TextView callYiwuTv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
        callyiwuImg.setImageResource(R.mipmap.call_yiwu_img);
        callYiwuTv.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_yiwu:
                dialog();
                break;
          default:
              break;
      }
    }
}
