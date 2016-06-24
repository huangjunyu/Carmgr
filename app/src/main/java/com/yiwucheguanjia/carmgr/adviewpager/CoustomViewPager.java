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
		// TODO Auto-generated constructor stub
	}
	public CoustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
    	switch (action) {
		case MotionEvent.ACTION_DOWN:
//			Log.e("MotionEvent", "ACTION_DOWN1111111111");
		case MotionEvent.ACTION_MOVE:
//			Log.e("MotionEvent", "ACTION_MOVE");
			pagerView.setOnTouchMove(true);
			break;
		case MotionEvent.ACTION_UP:
//			Log.e("MotionEvent", "ACTION_UP333333333333");
			pagerView.setOnTouchMove(false);
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
