package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.view.HistogramView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/25.
 */
public class DataStatisticsActivity extends Activity {

    @BindView(R.id.data_order_chart)
    HistogramView orderHistogramView;
    @BindView(R.id.data_assess_chart)
    HistogramView assessHistogram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_statistics_activity);
        ButterKnife.bind(this);
        initChartView();
    }
    public void initChartView(){
        ArrayList<HistogramView.Bar> orderBarList = new ArrayList<HistogramView.Bar>();
        HistogramView.Bar bar1 = orderHistogramView.new Bar(1, 0.8f, ContextCompat.getColor(this,R.color.orange), "one", "");
        HistogramView.Bar bar2 = orderHistogramView.new Bar(2, 0.65f, ContextCompat.getColor(this,R.color.orange), "two", "");
        HistogramView.Bar bar3 = orderHistogramView.new Bar(3, 0.8f, ContextCompat.getColor(this,R.color.orange), "three", "");
        HistogramView.Bar bar4 = orderHistogramView.new Bar(4, 0.8f, ContextCompat.getColor(this,R.color.orange), "four", "");
        orderBarList.add(bar1);
        orderBarList.add(bar2);
        orderBarList.add(bar3);
        orderBarList.add(bar4);
        orderHistogramView.setBarLists(orderBarList);


        ArrayList<HistogramView.Bar> assessBarList = new ArrayList<HistogramView.Bar>();
        HistogramView.Bar assessBar1 = assessHistogram.new Bar(1, 0.8f, ContextCompat.getColor(this,R.color.orange), "one", "");
        HistogramView.Bar assessBar2 = assessHistogram.new Bar(2, 0.65f, ContextCompat.getColor(this,R.color.orange), "two", "");
        HistogramView.Bar assessBar3 = assessHistogram.new Bar(3, 0.8f, ContextCompat.getColor(this,R.color.orange), "three", "");
        HistogramView.Bar assessBar4 = assessHistogram.new Bar(4, 0.8f, ContextCompat.getColor(this,R.color.orange), "four", "");
        assessBarList.add(assessBar1);
        assessBarList.add(assessBar2);
        assessBarList.add(assessBar3);
        assessBarList.add(assessBar4);
        assessHistogram.setBarLists(assessBarList);
    }
}
