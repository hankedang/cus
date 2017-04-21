package com.centrixlink.cus.bean;

import com.centrixlink.cus.base.BaseEntry;


public class AppInfo extends BaseEntry {
	
	private int id;
	private String app_key;
	private String app_secret;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}

}
