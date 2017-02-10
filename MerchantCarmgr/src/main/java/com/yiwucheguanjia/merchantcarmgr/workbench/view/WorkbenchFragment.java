package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.lzy.okgo.OkGo;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.callback.MyStringCallback;
import com.yiwucheguanjia.merchantcarmgr.post.PostServiceActivity;
import com.yiwucheguanjia.merchantcarmgr.utils.UrlString;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.RollViewPagerAdapter;
import com.yiwucheguanjia.merchantcarmgr.workbench.model.RollViewPagerBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/17.
 */
public class WorkbenchFragment extends Fragment {

    LinearLayout workbenchHomeViewLl;
    @BindView(R.id.workbench_rollpagerview)
    RollPagerView rollPagerView;
    @BindView(R.id.workbench_post_rl)
    RelativeLayout postRl;
    @BindView(R.id.workbench_data_stat_Rl)
    RelativeLayout dataStatRl;
    @BindView(R.id.user_assess_rl)
    RelativeLayout userAssessRl;
    @BindView(R.id.workbench_complaint_rl)
    RelativeLayout compliantRl;
    @BindView(R.id.workbench_read_num_tv)
    TextView readNumTv;
    @BindView(R.id.workbench_appoint_num_tv)
    TextView appointNumTv;
    @BindView(R.id.workbench_talk_num_tv)
    TextView talkNumTv;
    private SharedPreferences sharedPreferences;
    private String imgPaths[];
    private ArrayList<RollViewPagerBean> rollViewPagerBeanArrayList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        workbenchHomeViewLl = (LinearLayout) inflater.inflate(R.layout.activity_workbench, container, false);
        ButterKnife.bind(this, workbenchHomeViewLl);
        sharedPreferences = getActivity().getSharedPreferences("CARMGR_MERCHANT", getActivity().MODE_PRIVATE);
        getData();
        getMerchantData();
        return workbenchHomeViewLl;
    }

    private void getMerchantData() {
        OkGo.post(UrlString.DATA_STATISTICS_URL)
                .tag(this)
                .params("username", sharedPreferences.getString("ACCOUNT",null))
                .params("data_time", "")
                .params("token", sharedPreferences.getString("TOKEN", "null"))
                .params("version", UrlString.APP_VERSION)
                .execute(
                        new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.e("re", s);
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    int totalAppointInt = Integer.parseInt(jsonObject.getString("total_subscribe"));
                                    int totalReadInt = Integer.parseInt(jsonObject.getString("total_access"));
                                    int totalContrackInt = Integer.parseInt(jsonObject.getString("total_communicate"));
                                    //总预约量
                                    appointNumTv.setText(jsonObject.getString("total_subscribe"));
                                    //总浏览量
                                    readNumTv.setText(jsonObject.getString("total_access"));
                                    talkNumTv.setText(jsonObject.getString("total_communicate"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
    }

    @OnClick({R.id.workbench_post_rl, R.id.workbench_data_stat_Rl, R.id.user_assess_rl, R.id.workbench_complaint_rl})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.workbench_post_rl:
                Intent postIntent = new Intent(getActivity(), PostServiceActivity.class);
                postIntent.putExtra("postType","post");//来自于哪里的标识，首发或者编辑修改
                startActivity(postIntent);
                break;
            case R.id.workbench_data_stat_Rl:
                Intent dataStatisticsIntent = new Intent(getActivity(), DataStatisticsActivity.class);
                startActivity(dataStatisticsIntent);
                break;
            case R.id.user_assess_rl:
                Intent assessIntent = new Intent(getActivity(), CustomerAssessActivity.class);
                startActivity(assessIntent);
                break;
            case R.id.workbench_complaint_rl:
                Intent complaintIntent = new Intent(getActivity(), ComplaintDealActivity.class);
                startActivity(complaintIntent);
                break;
            default:
                break;
        }
    }

    private void getData() {

        OkGo.post(UrlString.APP_GETSHOPINFO)
                .tag(this)
                .params("username", sharedPreferences.getString("ACCOUNT", null))
                .params("token", sharedPreferences.getString("TOKEN", null))
                .params("version", UrlString.APP_VERSION)
                .execute(new MyStringCallback(getActivity(), getResources().getString(R.string.loading)) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        rollViewPagerBeanArrayList = new ArrayList<RollViewPagerBean>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.equals(jsonObject.optString("opt_state", "path"), "success")) {
                                if (TextUtils.isEmpty(jsonObject.optString("merchants_imgs", "path"))) {
                                    //如果空的话，调用默认图片
                                } else {
                                    String path = jsonObject.optString("merchants_imgs", "path");
                                    imgPaths = path.split("\\^");
                                    for (int i = 0;i < imgPaths.length;i++){
                                        RollViewPagerBean rollViewPagerBean = new RollViewPagerBean();
                                        rollViewPagerBean.setRollViewPagerUrl(imgPaths[i]);
                                        rollViewPagerBeanArrayList.add(rollViewPagerBean);
                                    }
                                    //设置播放时间间隔
                                    rollPagerView.setPlayDelay(5000);
                                    rollPagerView.setAnimationDurtion(500);
                                    //设置指示器（顺序依次）
                                    //自定义指示器图片
                                    //设置圆点指示器颜色
                                    //设置文字指示器
                                    //隐藏指示器
                                    rollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.TRANSPARENT, Color.WHITE));
                                    //设置适配器
                                    rollPagerView.setAdapter(new RollViewPagerAdapter(rollPagerView, rollViewPagerBeanArrayList, getActivity()));
                                    initView(rollViewPagerBeanArrayList);
                                }
                                Log.e("loging",s);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    private void initView(ArrayList<RollViewPagerBean> rollViewPagerBeanArrayList) {
        //设置播放时间间隔
        rollPagerView.setPlayDelay(5000);
        rollPagerView.setAnimationDurtion(500);
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        rollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.TRANSPARENT, Color.WHITE));
        //设置适配器
        rollPagerView.setAdapter(new RollViewPagerAdapter(rollPagerView, rollViewPagerBeanArrayList, getActivity()));
    }
}
