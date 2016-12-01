package com.yiwucheguanjia.merchantcarmgr.checkpictureutils;

import java.util.ArrayList;

public class ItemEntity {
	private String avatar; // 用户头像URL
	private String title; // 标题
	private String content; // 内容


	private String usernaem;//用户名
	private String time;
	private ArrayList<String> imageUrls; // 九宫格图片的URL集合

	public ItemEntity(ArrayList<String> imageUrls,String content,String username,String time) {
		super();
		this.imageUrls = imageUrls;
		this.content = content;
		this.usernaem = username;
		this.time = time;
	}

	public String getUsernaem() {
		return usernaem;
	}

	public void setUsernaem(String usernaem) {
		this.usernaem = usernaem;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(ArrayList<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	@Override
	public String toString() {
		return "ItemEntity [avatar=" + avatar + ", title=" + title + ", content=" + content + ", imageUrls=" + imageUrls + "]";
	}

}
