package com.yiwucheguanjia.carmgr.city.bean;

/**
 * 城市列表model  拼音
 */
public class CityModel {

	private String name;   //显示的数据
	private String firstName;  //显示数据拼音的首字母
	private int parent;

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
