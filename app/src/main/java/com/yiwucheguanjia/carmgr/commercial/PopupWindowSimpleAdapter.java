package com.yiwucheguanjia.carmgr.commercial;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/2.
 */
public class PopupWindowSimpleAdapter extends SimpleAdapter {

    Context context;
    List<Map<String, String>> data;
    public PopupWindowSimpleAdapter(Context context, List<Map<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        TextView textView = (TextView) v.findViewById(R.id.typeTopic);
        final int p = position;
        Log.d("Position", Integer.toString(position));
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handler.sendMessage(handler.obtainMessage(10, p));
//            }
//        });
        return v;
    }
}
