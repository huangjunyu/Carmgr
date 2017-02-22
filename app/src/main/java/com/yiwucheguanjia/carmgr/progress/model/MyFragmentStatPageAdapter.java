package com.yiwucheguanjia.carmgr.progress.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.yiwucheguanjia.carmgr.progress.view.MapItemFragment;

import java.util.ArrayList;

/**
 * 这里继承的是FragmentStatePagerAdapter， 根据官方API的介绍，当项目中遇到使用大量的列表页面时，使用该适配器是个更好的选择。
 * （This version of the pager is more useful when there are a large number of
 * pages, working more like a list view.）
 */
public class MyFragmentStatPageAdapter extends FragmentStatePagerAdapter {
ArrayList<MapItemFragmentBean> fragmentArrayList = new ArrayList<>();
	public MyFragmentStatPageAdapter(FragmentManager fm, ArrayList<MapItemFragmentBean> fragmentArrayList) {
		super(fm);
		this.fragmentArrayList = fragmentArrayList;
	}
	/**
	 * 只需要实现下面两个方法即可。
	 */
	@Override
	public MapItemFragment getItem(int position) {
		Bundle bundle = new Bundle();
		bundle.putString("service_title",fragmentArrayList.get(position).getServiceTitle());
		bundle.putString("service_price",fragmentArrayList.get(position).getServicePrice());
		bundle.putString("service_content",fragmentArrayList.get(position).getContent());
		bundle.putInt("service_star",fragmentArrayList.get(position).getStar());
		return MapItemFragment.newInstance(bundle);
	}

	@Override
	public int getCount() {
		return fragmentArrayList.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}

}
