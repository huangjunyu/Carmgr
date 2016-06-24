package com.yiwucheguanjia.carmgr.adviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;

public class SamplePagerView extends RelativeLayout {
	public SamplePagerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.viewpager_in, this);
	}
	public SamplePagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.viewpager_in, this);
	}
	public SamplePagerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = (FragmentActivity) context;
		LayoutInflater.from(context).inflate(R.layout.viewpager_in, this);
	}

	private FragmentActivity mContext;
	public FragmentActivity getmContext() {
		return mContext;
	}
	public void setmContext(FragmentActivity mContext) {
		this.mContext = mContext;
	}

	private CoustomViewPager viewPager;
	private ImageView[] imageViews;
	private TestFragmentAdapter mAdapter;
	private ArrayList<ImageBean> imageBeans = new ArrayList<ImageBean>();
	public ArrayList<ImageBean> getImageBeans() {
		return imageBeans;
	}
	public void setImageBeans(ArrayList<ImageBean> imageBeans) {
		this.imageBeans = imageBeans;
	}

	/**
	 * 每隔多长时间执行一次
	 */
	private int interval_time = 5000;
	public int getInterval_time() {
		return interval_time;
	}
	/**
	 * 设置轮播间隔时间
	 * @param interval_time
	 */
	public void setInterval_time(int interval_time) {
		this.interval_time = interval_time;
	}

	/**
	 * 判断是否正在手势滑动
	 */
	private boolean isOnTouchMove = false;
	public boolean isOnTouchMove() {
		return isOnTouchMove;
	}
	public void setOnTouchMove(boolean isOnTouchMove) {
		this.isOnTouchMove = isOnTouchMove;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	/**
	 * 控制开启和关闭自动轮播,默认关闭
	 */
	private boolean isOpen = false;
	public void init(){
		initArrayList();
		initCircle();
		initData();
		mHandler.postDelayed(mRunnable, interval_time);
	}
	/**
	 * 初始化viewpager数据
	 */
	private void initData() {
		mAdapter = new TestFragmentAdapter(mContext.getSupportFragmentManager(), imageBeans);
		viewPager = (CoustomViewPager)findViewById(R.id.viewPager);
		viewPager.setPagerView(this);
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(new MyListener(imageBeans, imageViews));
		viewPager.setCurrentItem(MyListener.num);
	}
	private void initArrayList(){
		ImageBean imageBean2 = new ImageBean("2", "http://pic2.ooopic.com/11/75/33/20bOOOPIC41.jpg", R.mipmap.p1);
		ImageBean imageBean3 = new ImageBean("3", "http://pic.58pic.com/58pic/10/95/82/54q58PICeI2.jpg", R.mipmap.p1);
		ImageBean imageBean6 = new ImageBean("6", "http://pic.58pic.com/58pic/11/15/98/26T58PICCa3.jpg", R.mipmap.p1);
		ImageBean imageBean7 = new ImageBean("7", "http://pic.58pic.com/01/05/43/43bOOOPIC1e.jpg", R.mipmap.p1);
		ImageBean imageBean8 = new ImageBean("8", "http://pic.58pic.com/58pic/11/15/47/91u58PICpYF.jpg", R.mipmap.p1);
		ImageBean imageBean9 = new ImageBean("9", "http://pic.58pic.com/10/19/41/49bOOOPIC03.jpg", R.mipmap.p1);
		ImageBean imageBean10 = new ImageBean("10", "http://pic.58pic.com/58pic/11/12/92/87k58PICVwj.jpg", R.mipmap.p1);
		imageBeans.add(imageBean2);
		imageBeans.add(imageBean3);
		imageBeans.add(imageBean6);
		imageBeans.add(imageBean7);
		imageBeans.add(imageBean8);
		imageBeans.add(imageBean9);
		imageBeans.add(imageBean10);
	}
	/**
	 * 初始化底部圆点
	 */
	private void initCircle() {
		int imageSize = imageBeans.size();
		imageViews = new ImageView[imageSize];
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viewGroup);
		for (int i = 0; i < imageSize; i++) {
			imageViews[i] = new ImageView(mContext);
			imageViews[i].setLayoutParams(new LayoutParams(12,12));
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.mipmap.hollow_circle);
			} else {
				imageViews[i].setBackgroundResource(R.mipmap.solid_circle);
			}
			linearLayout.addView(imageViews[i]);
		}
	}
	private Handler mHandler =  new Handler() {

		@Override
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};
	private Runnable mRunnable = new Runnable() {
		public void run() {
			//每隔多长时间执行一次
			mHandler.postDelayed(mRunnable, interval_time);
			int length = imageBeans.size();
			//如果手指没有滑动并且集合长度>1,同时开关是开的开启自动轮播
			if(!isOnTouchMove && isOpen && length > 1){
				MyListener.num++;
				mHandler.sendEmptyMessage(MyListener.num);
			}
		}
	};

}
