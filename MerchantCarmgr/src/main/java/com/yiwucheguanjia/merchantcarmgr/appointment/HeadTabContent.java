package com.yiwucheguanjia.merchantcarmgr.appointment;

import java.util.ArrayList;
import java.util.List;

public class HeadTabContent {
	private static final List<HeadTab> Selected = new ArrayList<HeadTab>();
	static{
		Selected.add(new HeadTab("全部"));
		Selected.add(new HeadTab("我的预约"));
		Selected.add(new HeadTab("进行中"));
		Selected.add(new HeadTab("已完成"));
	}
	/***
	 * 获得头部tab的所有项
	 */
	public static List<HeadTab> getSelected() {
		return Selected;
	}

}
