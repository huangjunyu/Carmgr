package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.yiwucheguanjia.merchantcarmgr.MainActivity;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.account.LoginActivity;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.utils.NewActivityUtil;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;
import com.yiwucheguanjia.merchantcarmgr.workbench.view.HistogramView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/25.
 */
public class DataStatisticsActivity extends AppCompatActivity {

    @BindView(R.id.data_order_chart)
    HistogramView orderHistogramView;
    @BindView(R.id.data_assess_chart)
    HistogramView assessHistogram;
    @BindView(R.id.data_total_appoin)
    TextView totalAppointTv;
    @BindView(R.id.data_total_read)
    TextView totalReadTv;
    @BindView(R.id.data_today_point)
    TextView todayPointTvTv;
    @BindView(R.id.data_month_point)
    TextView monthPointTv;
    @BindView(R.id.data_today_order)
    TextView todayOrderTv;
    @BindView(R.id.data_month_order)
    TextView monthOderTv;
    @BindView(R.id.data_withdraw_total)
    TextView witdrawTotalTv;
    @BindView(R.id.data_account_balance)
    TextView accountBalanceTv;
    @BindView(R.id.data_five_star)
    TextView fiveStarTv;
    @BindView(R.id.data_four_star)
    TextView fourStarTv;
    @BindView(R.id.data_three_star)
    TextView threeStarTv;
    @BindView(R.id.data_two_star)
    TextView twoStarTv;
    @BindView(R.id.data_one_star)
    TextView oneStarTv;
    @BindView(R.id.data_total_contrack)
    TextView totalContrackTv;
    @BindView(R.id.statistics_dat_goback_rl)
    RelativeLayout gobackRl;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("CARMGR_MERCHANT", this.MODE_PRIVATE);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
        setContentView(R.layout.data_statistics_activity);
        ButterKnife.bind(this);
        getData();
    }

    public void initChartView(float totalAppoint,float totalRead,float totalContract,float oneStarFloat,float twoStarFloat,float threeStarFloat,float fourStarFloat,float fiveStarFloat) {
        ArrayList<HistogramView.Bar> orderBarList = new ArrayList<HistogramView.Bar>();
        HistogramView.Bar bar1 = orderHistogramView.new Bar(1, totalAppoint, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.appointment_total), "");
        HistogramView.Bar bar2 = orderHistogramView.new Bar(2, totalRead, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.read_total), "");
        HistogramView.Bar bar3 = orderHistogramView.new Bar(3, totalContract, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.contract_total), "");
        orderBarList.add(bar1);
        orderBarList.add(bar2);
        orderBarList.add(bar3);
        orderHistogramView.setBarLists(orderBarList);


        ArrayList<HistogramView.Bar> assessBarList = new ArrayList<HistogramView.Bar>();
        HistogramView.Bar assessBar1 = assessHistogram.new Bar(1, oneStarFloat, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.one_star), "");
        HistogramView.Bar assessBar2 = assessHistogram.new Bar(2, twoStarFloat, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.two_star), "");
        HistogramView.Bar assessBar3 = assessHistogram.new Bar(3, threeStarFloat, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.three_star), "");
        HistogramView.Bar assessBar4 = assessHistogram.new Bar(4, fourStarFloat, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.four_star), "");
        HistogramView.Bar assessBar5 = assessHistogram.new Bar(5, fiveStarFloat, ContextCompat.getColor(this, R.color.orange), getResources().getString(R.string.five_star), "");
        assessBarList.add(assessBar1);
        assessBarList.add(assessBar2);
        assessBarList.add(assessBar3);
        assessBarList.add(assessBar4);
        assessBarList.add(assessBar5);
        assessHistogram.setBarLists(assessBarList);
    }

    private void getData() {
        OkGo.post(UrlString.DATA_STATISTICS_URL)
                .tag(this)
                .params("username", sharedPreferences.getString("ACCOUNT",null))
                .params("data_time", "952788")
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", "1.0")
                .execute(
                        new MyStringCallback(this, getResources().getString(R.string.loading)) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("re", s);
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int totalAppointInt = Integer.parseInt(jsonObject.getString("total_subscribe"));
                                    int totalReadInt = Integer.parseInt(jsonObject.getString("total_access"));
                                    int totalContrackInt = Integer.parseInt(jsonObject.getString("total_communicate"));
                                    int totalInt = totalAppointInt + totalReadInt + totalContrackInt;
                                    int oneStarInt = Integer.parseInt(jsonObject.getString("evaluate_start_1"));
                                    int twoStarInt = Integer.parseInt(jsonObject.getString("evaluate_start_2"));
                                    int threeStarInt = Integer.parseInt(jsonObject.getString("evaluate_start_3"));
                                    int fourStarInt = Integer.parseInt(jsonObject.getString("evaluate_start_4"));
                                    int fiveStarInt = Integer.parseInt(jsonObject.getString("evaluate_start_1"));
                                    int totalStarInt = oneStarInt + twoStarInt + threeStarInt;
                                    float oneStarFloat = (float)oneStarInt / totalStarInt;
                                    float twoStarFloat = (float)twoStarInt / totalStarInt;
                                    float threeStartFloat = (float)threeStarInt / totalStarInt;
                                    float fourStarFloat = (float)fourStarInt / totalStarInt;
                                    float fiveStarFloat = (float)fiveStarInt / totalStarInt;
                                    float appointParentFloat = (float)totalAppointInt / totalInt;
                                    float readParentFloat = (float)totalReadInt / totalInt;
                                    float contrackParentFloat = (float)totalContrackInt / totalInt;
                                    initChartView(appointParentFloat,readParentFloat,contrackParentFloat,oneStarFloat,
                                            twoStarFloat,threeStartFloat,fourStarFloat,fiveStarFloat);

                                    oneStarTv.setText(jsonObject.getString("evaluate_start_1"));
                                    twoStarTv.setText(jsonObject.getString("evaluate_start_2"));
                                    threeStarTv.setText(jsonObject.getString("evaluate_start_3"));
                                    fourStarTv.setText(jsonObject.getString("evaluate_start_4"));
                                    fiveStarTv.setText(jsonObject.getString("evaluate_start_5"));
                                    //总预约量
                                    totalAppointTv.setText(jsonObject.getString("total_subscribe"));
                                    //总浏览量
                                    totalReadTv.setText(jsonObject.getString("total_access"));
                                    totalContrackTv.setText(jsonObject.getString("total_communicate"));
                                    todayPointTvTv.setText(jsonObject.getString("day_total_subscribe"));
                                    monthPointTv.setText(jsonObject.getString("month_total_subscribe"));
                                    todayOrderTv.setText(jsonObject.getString("day_total_order"));
                                    monthOderTv.setText(jsonObject.getString("month_total_order"));
                                    witdrawTotalTv.setText(jsonObject.getString("fatch_cash_total"));
                                    accountBalanceTv.setText(jsonObject.getString("account_balance"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
    }
    @OnClick(R.id.statistics_dat_goback_rl)
    void click(){
        finish();
    }
}
