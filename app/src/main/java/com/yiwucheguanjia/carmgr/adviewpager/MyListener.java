package com.yiwucheguanjia.carmgr.adviewpager;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

//OnPageChangeListener一个滑动时改变状态的回调接口
public class MyListener implements OnPageChangeListener {
	private ArrayList<ImageBean> imageBeans;
	private ImageView[] imageViews;
	/**
	 * 默认起始位置，是content.length 的倍数（从第一个位置开始）
	 */
	public static int num = 301;
	public MyListener(ArrayList<ImageBean> imageBeans, ImageView[] imageViews) {
		// TODO Auto-generated constructor stub
		this.imageBeans = imageBeans;
		this.imageViews = imageViews;
	}
	//当滑动状态改变时调用  
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	//当当前页面被滑动时调用  
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	//当新的页面被选中时调用  
	@Override
	public void onPageSelected(int arg0) {
		// num重新赋值
		num = arg0;
		arg0 = arg0 % imageBeans.size();
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[arg0].setBackgroundResource(R.mipmap.hollow_circle);
			if (arg0 != i) {
				imageViews[i].setBackgroundResource(R.mipmap.solid_circle);
			} 
		}
//		Log.e("--->>", "当前是第"+arg0+"页");
	}

}
