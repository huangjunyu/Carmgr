package com.yiwucheguanjia.carmgr.adviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

class TestFragmentAdapter extends FragmentStatePagerAdapter {
//	private int [] content;
	ArrayList<ImageBean> imageBeans;
    public TestFragmentAdapter(FragmentManager fm, ArrayList<ImageBean> beans) {
        super(fm);
        this.imageBeans = beans;
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance(imageBeans.get(position % imageBeans.size()));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    
//    @Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		int realPosition = position % getCount();
//		this.destroyItem(container, realPosition, object);
//	}
    @Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
        //positionδ����getCountʱ��position����������
    	int realPosition = position % getCount();
		super.destroyItem(container, realPosition, object);
	}
//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		int realPosition = position % getCount();
//		return this.instantiateItem(container, realPosition);
//	}
}