package com.yiwucheguanjia.carmgr.adviewpager;

import java.io.Serializable;

public class ImageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8891345594773717622L;
	private String id;
	private String url;
	private int ids;
	public ImageBean(String id, String url,int ids) {
		super();
		this.id = id;
		this.url = url;
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIds() {
		return ids;
	}
	public void setIds(int ids) {
		this.ids = ids;
	}
	
}
