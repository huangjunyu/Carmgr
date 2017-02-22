package com.yiwucheguanjia.carmgr.post_help;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jaeger.library.StatusBarUtil;
import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpTypeActivity extends AppCompatActivity {
    @BindView(R.id.help_type_rc)
    RecyclerView recyclerView;
    ArrayList<HelpBean> helpBeanArrayList;
    private String[] type;
    HelpTypeAdapter helpTypeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(HelpTypeActivity.this,ContextCompat.getColor(HelpTypeActivity.this,R.color.white),50);
        setContentView(R.layout.activity_help_type);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.HORIZONTAL,1, ContextCompat.getColor(this,R.color.rc_divide)));
        recyclerView.setLayoutManager(linearLayoutManager);
        type = getResources().getStringArray(R.array.service_type);
        helpBeanArrayList = new ArrayList<>();
        for (int i = 0;i < type.length;i++){
            HelpBean helpBean = new HelpBean();
            helpBean.setHelpType(type[i]);
            helpBeanArrayList.add(helpBean);
        }
        helpTypeAdapter = new HelpTypeAdapter(helpBeanArrayList);
        recyclerView.setAdapter(helpTypeAdapter);
    }
}
