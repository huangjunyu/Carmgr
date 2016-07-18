package com.yiwucheguanjia.carmgr.callyiwu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CallYiwu extends Fragment implements View.OnClickListener {
    private LinearLayout callYiWuView;
    private RelativeLayout callYiWuRl;

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
        callYiWuRl = (RelativeLayout) callYiWuView.findViewById(R.id.callyiwu_call_rl);
        callYiWuRl.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callyiwu_call_rl:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "10010"));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                getActivity().startActivity(intent);
              break;
          default:
              break;
      }
    }
}
