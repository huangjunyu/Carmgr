package com.yiwucheguanjia.merchantcarmgr.appointment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

/**
 * Created by Administrator on 2016/10/20.
 */
public class ContentFragment extends Fragment {

    private View viewContent;
    private int mType = 0;
    private String mTitle;


    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //布局文件中只有一个居中的TextView
        viewContent = inflater.inflate(R.layout.activity_my_fragment,container,false);
        TextView textView = (TextView) viewContent.findViewById(R.id.personal_header_txt);
        textView.setText(this.mTitle);

        return viewContent;
    }

}
