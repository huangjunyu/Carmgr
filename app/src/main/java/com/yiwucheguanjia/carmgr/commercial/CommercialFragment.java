package com.yiwucheguanjia.carmgr.commercial;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CommercialFragment extends Fragment implements View.OnClickListener{
    private LinearLayout commercialView;
    private RelativeLayout businessSelectRl;
    private Spinner mySpinner;
    private ArrayList<BusinessSelectItemBean> businessSelectItemBeans;
    private BusinessSelectItemBean businessSelectItemBean;
    private BusinessItemAdapter itemAdapter;
    private PopupWindow popupWindow;
    private LayoutInflater popupwindowinflater;
    private ListView popupwindowListView;
    private PopupWindowSimpleAdapter popupWindowSimpleAdapter;
    //盛装所有弹出项的item
    private List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        commercialView = (LinearLayout)inflater.inflate(R.layout.activity_commercial,null);
//        mySpinner = (Spinner)commercialView.findViewById(R.id.business_select_sp);
        testPopup();
        initView();
        popupwindowinflater = LayoutInflater.from(getActivity());
        return commercialView;
    }
    private void initView(){
        businessSelectItemBeans = new ArrayList<BusinessSelectItemBean>();
        businessSelectItemBeans.add(new BusinessSelectItemBean("kjaja"));
        businessSelectItemBeans.add(new BusinessSelectItemBean("kj44"));
        businessSelectRl = (RelativeLayout)commercialView.findViewById(R.id.business_select_rl);
        businessSelectRl.setOnClickListener(this);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1);
//        arrayAdapter.add("你好");
//        arrayAdapter.add("很好");
//        arrayAdapter.setDropDownViewResource(R.layout.drop_down_item);

//        itemAdapter = new BusinessItemAdapter(getActivity(),businessSelectItemBeans);
//        itemAdapter.set
/*        mySpinner.setAdapter(arrayAdapter);
        mySpinner.setGravity(Gravity.CENTER);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = parent.getItemAtPosition(position).toString();
                String ss = businessSelectItemBeans.get(position).getBusinessItemStr().toString();
                Log.e("kkkkkksajfkd",string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }
    public void testPopup(){
        Map<String,String> listItem = new HashMap<>();
        listItem.put("1","ha");
        listItem.put("2","wo");
        listItems.add(listItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.business_select_rl:
                View view = popupwindowinflater.inflate(R.layout.commercial_select_list, null);
                popupwindowListView = (ListView) view.findViewById(R.id.mylist);
                // 创建一个SimpleAdapter
                popupWindowSimpleAdapter = new PopupWindowSimpleAdapter(getActivity(), listItems, R.layout.commercial_sort,
                        new String[] { "1" }, new int[] { R.id.typeTopic });
                popupwindowListView.setAdapter(popupWindowSimpleAdapter);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.dr));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.update();
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);

                backgroundAlpha(0.5f);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });

                popupWindow.showAsDropDown(businessSelectRl, 0, 0);
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;// 0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
