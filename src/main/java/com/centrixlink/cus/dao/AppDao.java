package com.centrixlink.cus.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.cus.bean.AppInfo;


@Repository
public class AppDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public AppInfo authApp(String app_id, String app_secret) {
		String sql = "select id, app_key, app_secret from cus_app_info where app_key = ? and app_secret = ?";
		
		AppInfo app = null;
		
		try {
			app = jdbcTemplate.queryForObject(sql, new Object[]{app_id, app_secret}, 
				new BeanPropertyRowMapper<AppInfo>(AppInfo.class));
		} catch (Exception e) {
			//nothing
		}
		
		return app;
		
	}
	
	public String findAppKey(int id) {
		String sql = "select app_key from cus_app_info where id = ? ";
		String app_key = jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
		return app_key;
	}
	
	public List<Map<String, Object>> query() {
		String sql = "select id, app_key from cus_app_info";
		
		return jdbcTemplate.queryForList(sql);
	}
	

}
