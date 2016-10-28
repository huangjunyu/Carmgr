package com.yiwucheguanjia.merchantcarmgr.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.workbench.HistogramView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/20.
 */
public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        HistogramView dcv7 = (HistogramView) findViewById(R.id.data_order_chart);
        ArrayList<HistogramView.Bar> bar7Lists = new ArrayList<HistogramView.Bar>();
        HistogramView.Bar bar1 = dcv7.new Bar(1, 0.8f, ContextCompat.getColor(this,R.color.orange), "one", "");
        HistogramView.Bar bar2 = dcv7.new Bar(2, 0.65f, ContextCompat.getColor(this,R.color.orange), "two", "");
        HistogramView.Bar bar3 = dcv7.new Bar(3, 0.8f, ContextCompat.getColor(this,R.color.orange), "three", "");
        HistogramView.Bar bar4 = dcv7.new Bar(3, 0.8f, ContextCompat.getColor(this,R.color.orange), "three", "");
        bar7Lists.add(bar1);
        bar7Lists.add(bar2);
        bar7Lists.add(bar3);
        bar7Lists.add(bar4);
        dcv7.setBarLists(bar7Lists);
    }
}
