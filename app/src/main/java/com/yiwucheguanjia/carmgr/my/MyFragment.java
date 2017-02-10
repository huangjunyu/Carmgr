package com.yiwucheguanjia.carmgr.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.callback.MyStringCallback;
import com.yiwucheguanjia.carmgr.order.OrderActivity;
import com.yiwucheguanjia.carmgr.utils.SharedPreferencesUtil;
import com.yiwucheguanjia.carmgr.utils.UrlString;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MyFragment extends Fragment {

    private View homeView;

    @BindView(R.id.my_setting_img)
    ImageView setingImg;
    @BindView(R.id.my_msg_img)
    ImageView msgImg;
    @BindView(R.id.my_account_rl)
    RelativeLayout accountRl;
    @BindView(R.id.my_header_img)
    ImageView headerImg;
    @BindView(R.id.my_header_tv)
    TextView userNameTv;
    @BindView(R.id.my_mycar_rl)
    RelativeLayout myCarRl;
    @BindView(R.id.my_addcar_img)
    ImageView addCarImg;
    @BindView(R.id.pro_wait_use_rl)
    RelativeLayout waitUseRl;
    @BindView(R.id.pro_going_rl)
    RelativeLayout gingRl;
    @BindView(R.id.my_account_balance_rl)
    RelativeLayout balanceRl;
    @BindView(R.id.my_car_rl)
    RelativeLayout carRl;
    @BindView(R.id.my_order_service_ll)
    LinearLayout orderServiceLl;
    private static final int ADD_CAR_REQUEST = 6000;
    private static final int ADD_CAR_RESULT = 6001;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, homeView);
        appGetConfig();
        return homeView;
    }

    @OnClick({R.id.my_setting_img, R.id.my_msg_img, R.id.my_account_rl, R.id.my_header_img,
            R.id.my_header_tv, R.id.my_mycar_rl, R.id.my_addcar_img,
            R.id.progress_all_rl, R.id.pro_wait_pay_rl, R.id.pro_wait_use_rl, R.id.pro_going_rl,
            R.id.pro_done_rl, R.id.pro_wait_assess_rl, R.id.pro_after_sale__rl, R.id.my_records_rl,
            R.id.my_account_balance_rl, R.id.my_post_rl, R.id.my_data_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_setting_img:
                Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.my_msg_img:
                break;
            case R.id.my_account_rl:
                break;
            case R.id.my_header_img:
                break;
            case R.id.my_header_tv:
                break;
            case R.id.my_mycar_rl:
                break;
            case R.id.my_addcar_img:
                Intent addCarIntent = new Intent(getActivity(), AddCarActivity.class);
                startActivityForResult(addCarIntent, ADD_CAR_REQUEST);
                break;
            case R.id.progress_all_rl://全部
//                Intent allIntent = new Intent(getActivity(), OrderActivity.class);
//                startActivity(allIntent);
                break;
            case R.id.pro_wait_pay_rl://待付款
                break;
            case R.id.pro_wait_use_rl://待使用
                break;
            case R.id.pro_going_rl://进行中
                break;
            case R.id.pro_done_rl://已完成
                break;
            case R.id.pro_wait_assess_rl://待评价
                break;
            case R.id.pro_after_sale__rl://退款/售后
                break;
            case R.id.my_account_balance_rl://账户余额
                Intent accountBalanceIntent = new Intent(getActivity(), AccountBalanceActivity.class);
                startActivity(accountBalanceIntent);
                break;
            case R.id.my_data_rl://个人资料
                Intent personalDataIntent = new Intent(getActivity(), PersonalDataActivity.class);
                startActivity(personalDataIntent);
                break;
            case R.id.my_records_rl://历史业务
                Intent recordTradeIntent = new Intent(getActivity(), RecordTradeActivity.class);
                startActivity(recordTradeIntent);
                break;
            case R.id.my_post_rl://邮寄地址
//                Intent postAddrIntent = new Intent(getActivity(), PostAddressListActivity.class);
//                startActivity(postAddrIntent);
                break;
            default:
                break;
        }
    }

    private void appGetConfig() {

        OkGo.post(UrlString.APP_GETPRIVATE)
                .params("username", SharedPreferencesUtil.getInstance(getActivity()).usernameSharedPreferences())
                .params("token", SharedPreferencesUtil.getInstance(getActivity()).tokenSharedPreference())
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("config", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            userNameTv.setText(jsonObject.optString("username", "未知用户"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        OkGo.post(UrlString.APP_GET_CAR_INFO)
                .params("username", SharedPreferencesUtil.getInstance(getActivity()).usernameSharedPreferences())
                .params("token", SharedPreferencesUtil.getInstance(getActivity()).tokenSharedPreference())
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("carinfo", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.optString("vehicle_number", "nothing"), "nothing")) {
                                //隐藏车辆信息，显示添加车辆按钮
                                Log.e("kkk", "kkkw");
                                carRl.setVisibility(View.VISIBLE);
                                orderServiceLl.setVisibility(View.GONE);
                            } else {
                                carRl.setVisibility(View.GONE);
                                orderServiceLl.setVisibility(View.VISIBLE);
                                Log.e("qqqq11", "kkkw");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CAR_REQUEST && resultCode == ADD_CAR_RESULT) {
            Log.e("some", "some");
            carRl.setVisibility(View.GONE);
            orderServiceLl.setVisibility(View.VISIBLE);
        }
    }
}
