package com.centrixlink.cus.bean;


import com.centrixlink.cus.base.BaseEntry;
import com.centrixlink.util.Constants;
import com.centrixlink.util.Utils;


public class User extends BaseEntry {
	private int id;
	private String user_name;
	private String pwd;
	private String e_mail;
	private String phone;
	private String status;
	private String create_time;
	private String update_time;
	private int app_id;
	private String app_name;
	private int role_id;
	private String role_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreate_time() {
		return Utils.dateFormat(create_time, Constants.DATE_FORMAT_1);
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return Utils.dateFormat(update_time, Constants.DATE_FORMAT_1);
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	
	

}
