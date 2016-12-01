package com.yiwucheguanjia.merchantcarmgr.workbench.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/24.
 */
public abstract class BaseFragment extends Fragment {

    private boolean isFirstLoad = true;         //是否第一次加载
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("jjjj","gogoog");
        isFirstLoad = true;
        isPrepared = true;
        View view = initView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad();
    }

    /** 如果是与ViewPager一起使用，调用的是setUserVisibleHint */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
        if (getUserVisibleHint()) {
            Log.e("eeaa","qqq66q");
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            Log.e("eeaa","qqqq");
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        Log.e("true",isFirstLoad+ ""  + isVisible + isPrepared );
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        Log.e("eeaa","n,,,ll");
        isFirstLoad = false;
        initData();
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();
}
