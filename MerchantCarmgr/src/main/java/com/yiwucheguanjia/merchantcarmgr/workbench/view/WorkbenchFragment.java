package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.post.PostServic;
import com.yiwucheguanjia.merchantcarmgr.workbench.controller.RollViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        workbenchHomeViewLl = (LinearLayout)inflater.inflate(R.layout.activity_workbench,container,false);
        ButterKnife.bind(this,workbenchHomeViewLl);
        initView();
        return workbenchHomeViewLl;
    }
    @OnClick({R.id.workbench_post_rl,R.id.workbench_data_stat_Rl,R.id.user_assess_rl,R.id.workbench_complaint_rl})
    void onClickView(View view){
        switch (view.getId()){
            case R.id.workbench_post_rl:
                Intent postIntent = new Intent(getActivity(), PostServic.class);
                startActivity(postIntent);
                break;
            case R.id.workbench_data_stat_Rl:
                Intent dataStatisticsIntent = new Intent(getActivity(), DataStatisticsActivity.class);
                startActivity(dataStatisticsIntent);
                break;
            case R.id.user_assess_rl:
                Intent assessIntent = new Intent(getActivity(),CustomerAssessActivity.class);
                startActivity(assessIntent);
                break;
            case R.id.workbench_complaint_rl:
                Intent complaintIntent = new Intent(getActivity(),ComplaintDealActivity.class);
                startActivity(complaintIntent);
                break;
            default:
                break;
        }
    }
    private void initView(){
        //设置播放时间间隔
        rollPagerView.setPlayDelay(5000);
        rollPagerView.setAnimationDurtion(500);
        //设置适配器
        rollPagerView.setAdapter(new RollViewPagerAdapter(rollPagerView,getActivity()));
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        rollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.TRANSPARENT, Color.WHITE));
    }
}
