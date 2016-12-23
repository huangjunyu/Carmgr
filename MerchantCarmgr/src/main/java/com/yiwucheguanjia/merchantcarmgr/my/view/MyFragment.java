package com.yiwucheguanjia.merchantcarmgr.my.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MyFragment extends Fragment {
    LinearLayout myFragmentView;
    @BindView(R.id.myft_user_header_rl)
    RelativeLayout userHeaderRl;
    @BindView(R.id.myft_order_week_rl)
    RelativeLayout orderWeekRl;
    @BindView(R.id.myft_income_rl)
    RelativeLayout incomeRl;
    @BindView(R.id.myft_assess_rl)
    RelativeLayout assessRl;
    @BindView(R.id.myft_grade_rl)
    RelativeLayout gradeRl;
    @BindView(R.id.myft_balance_rl)
    RelativeLayout balanceRl;//余额
    @BindView(R.id.myft_cash_deposit_rl)
    RelativeLayout cashDepositRl;
    @BindView(R.id.myft_merchant_profile_rl)
    RelativeLayout profileRl;//评价
    @BindView(R.id.myft_merchant_photo_rl)
    RelativeLayout photoRl;
    @BindView(R.id.myft_system_msg_rl)
    RelativeLayout systemMsgRl;
    @BindView(R.id.myft_setting_rl)
    RelativeLayout settingRl;
    @BindView(R.id.myft_order_week)
    TextView orderWeekTv;//本周订单
    @BindView(R.id.myft_income)
    TextView incomeTv;
    @BindView(R.id.mytf_rate_star_img)
    ImageView rateStarImg;
    @BindView(R.id.my_shopname_tv)
    TextView shopNameTv;
    @BindView(R.id.my_header_img)
    ImageView shopHeaderImg;
    private int merchantLevelInt;//商家等级
    private String merchantBalanceStr;//余额
    private String deposit;
    private String introduce;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT",getActivity().MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = (LinearLayout)inflater.inflate(R.layout.activity_my_fragment,container,false);
        ButterKnife.bind(this,myFragmentView);
        getData();
        return myFragmentView;
    }

    @OnClick({R.id.myft_setting_rl,R.id.myft_system_msg_rl,R.id.myft_merchant_photo_rl,
    R.id.myft_merchant_profile_rl,R.id.myft_cash_deposit_rl,R.id.myft_balance_rl,R.id.myft_grade_rl,
    R.id.myft_assess_rl,R.id.myft_income_rl,R.id.myft_order_week_rl})
    void onClickView(View view){
        switch (view.getId()){
            case R.id.myft_setting_rl:
                Intent settingIntent = new Intent(getActivity(),SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.myft_system_msg_rl:
                Intent systemMsgIntent = new Intent(getActivity(),SystemMsgActivity.class);
                startActivity(systemMsgIntent);
                break;
            case R.id.myft_merchant_photo_rl:
                Intent merchantPhoIntent = new Intent(getActivity(),MerchantPhotoActivity.class);
                startActivity(merchantPhoIntent);
                break;
            case R.id.myft_merchant_profile_rl:
                Intent introIntent = new Intent(getActivity(), MerchantIntroActivity.class);
                introIntent.putExtra("introduce",introduce);
                startActivity(introIntent);
                break;
            case R.id.myft_cash_deposit_rl:
                Intent depositIntent = new Intent(getActivity(),CashDepositActivity.class);
                depositIntent.putExtra("deposit",deposit);
                startActivity(depositIntent);
                break;
            case R.id.myft_balance_rl:
                Intent balanceIntent = new Intent(getActivity(),AccountBalanceActivity.class);
                balanceIntent.putExtra("balance",merchantBalanceStr);
                startActivity(balanceIntent);
                break;
            case R.id.myft_grade_rl:
                Intent gradeIntent = new Intent(getActivity(),MerchantGradeActivity.class);
                gradeIntent.putExtra("grade",merchantLevelInt);
                startActivity(gradeIntent);
                break;
            default:
                break;
        }
    }
    private void getData(){
        OkGo.post(UrlString.APP_GETSHOPINFO)
                .tag(this)
                .params("username",sharedPreferences.getString("ACCOUNT",null))
                .params("token",sharedPreferences.getString("TOKEN",null))
                .params("version",UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(),getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("calllll",s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            orderWeekTv.setText(jsonObject.getString("week_total_orders").toString());
                            incomeTv.setText(jsonObject.getString("week_total_income").toString());
                            Log.e("shop",jsonObject.getString("shop_name".toString()));
                            shopNameTv.setText(jsonObject.getString("shop_name".toString()));
                            drawScore(jsonObject.getString("merchants_score"));
                            Log.e("merchants",jsonObject.getInt("merchants_level") + "");
                            merchantLevelInt = jsonObject.getInt("merchants_level");
                            merchantBalanceStr = jsonObject.getString("account_balance");
                            deposit = jsonObject.getString("deposit");
                            introduce = jsonObject.getString("merchants_introduce");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    private void drawScore(String score){
        switch (score){
            case "0.5":
                Glide.with(getActivity()).load(R.mipmap.star_half).into(rateStarImg);
                break;
            case "1":
                Glide.with(getActivity()).load(R.mipmap.star).into(rateStarImg);
                break;
            case "1.5":
                Glide.with(getActivity()).load(R.mipmap.star_one_hafl).into(rateStarImg);
                break;
            case "2":
                Glide.with(getActivity()).load(R.mipmap.star_two).into(rateStarImg);
                break;
            case "2.5":
                Glide.with(getActivity()).load(R.mipmap.star_two_half).into(rateStarImg);
                break;
            case "3":
                Glide.with(getActivity()).load(R.mipmap.star_three).into(rateStarImg);
                break;
            case "3.5":
                Glide.with(getActivity()).load(R.mipmap.star_three_half).into(rateStarImg);
                break;
            case "4":
                Glide.with(getActivity()).load(R.mipmap.star_four).into(rateStarImg);
                break;
            case "4.5":
                Glide.with(getActivity()).load(R.mipmap.star_four_half).into(rateStarImg);
                break;
            case "5":
                Glide.with(getActivity()).load(R.mipmap.star_five).into(rateStarImg);
                break;
            default:
                break;
        }
    }
}
