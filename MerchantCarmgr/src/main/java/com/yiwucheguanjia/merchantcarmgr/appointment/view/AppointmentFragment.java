package com.yiwucheguanjia.merchantcarmgr.appointment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiwucheguanjia.merchantcarmgr.R;
import com.yiwucheguanjia.merchantcarmgr.appointment.HeadTab;
import com.yiwucheguanjia.merchantcarmgr.appointment.HeadTabContent;
import com.yiwucheguanjia.merchantcarmgr.appointment.controller.OneFmAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AppointmentFragment extends Fragment implements OnPageChangeListener {

    private View view;
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private HorizontalScrollView horizontalScrollView;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private OneFmAdapter adapter;
    //当前显示的Fragment
    private Fragment currentFragment;
    private Fragment allFragment;
//    private Fragment myFragment;
//    private Fragment underwayFragment;
//    private Fragment finishFragment;
//    android.support.v4.app.FragmentManager fragmentManager = getActivity().getChildFragmentManager();
//    FragmentManager fragmentManager = getChildFragmentManager();

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            //初始化view
            view = inflater.inflate(R.layout.activity_appointment, container,false);
            radioGroup = (RadioGroup) view.findViewById(R.id.appoint_radiogroup);
            viewPager = (ViewPager) view.findViewById(R.id.one_view);
            horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.appoint_hscrollview);
            //设置RadioGroup点击事件
            radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int id) {
                    viewPager.setCurrentItem(id);
                }
            });
            //初始化顶部导航栏
            initTab(inflater);
            //初始化viewpager
            initView();
            initFraagment();
        }
		/*
		 * 底部导航栏切换后 由于没有销毁顶部设置导致如果没有重新设置view
		 * 导致底部切换后切回顶部页面数据会消失等bug
		 * 以下设置每次重新创建view即可
		*/
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initFraagment(){
        if (allFragment == null){
            allFragment = new AllAppointmentFragment();
            Log.e("fragment","fragment2");
        }
        if (!allFragment.isAdded()){
            getChildFragmentManager().beginTransaction().add(R.id.one_view, allFragment).commit();
            currentFragment = allFragment;
        }
    }

    /***
     * 初始化viewpager
     */
    private void initView() {
        List<HeadTab> headTabs = HeadTabContent.getSelected();

        AllAppointmentFragment allAppointmentFragment = new AllAppointmentFragment();
        MyAppointmentFragment myAppointmentFragment = new MyAppointmentFragment();
        UnderwayAppointFragment underwayAppointFragment = new UnderwayAppointFragment();
        FinishAppointFragment finishAppointFragment = new FinishAppointFragment();
        fragmentList.add(allAppointmentFragment);
        fragmentList.add(myAppointmentFragment);
        fragmentList.add(underwayAppointFragment);
        fragmentList.add(finishAppointFragment);
        //设置viewpager适配器
        adapter = new OneFmAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        //两个viewpager切换不重新加载
        viewPager.setOffscreenPageLimit(2);
        //设置默认fragment
        viewPager.setCurrentItem(0);
        //设置viewpager监听事件
        viewPager.setOnPageChangeListener(this);

    }
    /***
     * 初始化头部导航栏
     * @param inflater
     */
    private void initTab(LayoutInflater inflater) {
        List<HeadTab> headTabs = HeadTabContent.getSelected();
        for (int i = 0; i < 4; i++) {
            //设置头部项布局初始化数据
            RadioGroup.LayoutParams radioGroupLayoutParams = new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1f);
            RadioButton rbButton  = (RadioButton) inflater.inflate(R.layout.tab_rb, null);
            rbButton.setId(i);
            rbButton.setText(headTabs.get(i).getName());
            if (i == 0){
                rbButton.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
            }
            //加入RadioGroup
            radioGroup.addView(rbButton,radioGroupLayoutParams);
        }
        //默认点击
        radioGroup.check(0);
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
    @Override
    public void onPageSelected(int id) {
        setTab(id);
    }
    /***
     * 页面跳转切换头部偏移设置
     * @param id
     */
    private void setTab(int id) {
        RadioButton rbButton = (RadioButton) radioGroup.getChildAt(id);
        //设置标题被点击
        rbButton.setChecked(true);

        for (int i = 0; i < 4; i++){
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setTextColor(ContextCompat.getColor(getActivity(),R.color.buseness_black));

        }
        rbButton.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
        //偏移设置
        int left = rbButton.getLeft();
        int width = rbButton.getMeasuredWidth();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        //移动距离= 左边的位置 + button宽度的一半 - 屏幕宽度的一半
        int len = left + width / 2 - screenWidth / 2;
        //移动
        horizontalScrollView.smoothScrollTo(len, 0);
    }
}

