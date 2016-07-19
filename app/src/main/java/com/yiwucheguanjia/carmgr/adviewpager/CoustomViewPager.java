package com.yiwucheguanjia.carmgr.adviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CoustomViewPager extends ViewPager {
	private SamplePagerView pagerView;

	public SamplePagerView getPagerView() {
		return pagerView;
	}
	public void setPagerView(SamplePagerView pagerView) {
		this.pagerView = pagerView;
	}
	public CoustomViewPager(Context context) {
		super(context);
	}
	public CoustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
    	switch (action) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			pagerView.setOnTouchMove(true);
			break;
		case MotionEvent.ACTION_UP:
			pagerView.setOnTouchMove(false);
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
