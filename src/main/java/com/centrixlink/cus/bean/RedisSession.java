package com.centrixlink.cus.bean;


import java.util.List;

import com.centrixlink.cus.base.BaseEntry;


public class RedisSession extends BaseEntry {
	
	private String session_id;
	private int user_id;
	private String user_name;
	private Role role;
	private List<Permission> permissions;

	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
