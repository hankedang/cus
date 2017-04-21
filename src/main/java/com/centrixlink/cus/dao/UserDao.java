package com.centrixlink.cus.dao;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.cus.base.BaseDao;
import com.centrixlink.cus.bean.User;

@Repository
public class UserDao extends BaseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User login(String user_name, String pwd, int app_id) {
		
		String sql = "select id, user_name, pwd, e_mail, phone, status, create_time, update_time, app_id "
				+ "from cus_user_list where user_name = ? and pwd = ? and app_id = ?";
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, new Object[]{user_name, pwd, app_id}, 
					new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException e) {
			//nothing
		}
			
		return user;
		
	}
	
	public int count() {
		return jdbcTemplate.queryForObject("select count(id) from cus_user_list", Integer.class);
	}
	
	public List<User> queryAll(Map<Object, Object> where) {
		String sql = 
				"select a.id, user_name, e_mail, phone, status, create_time, update_time, app_id, app_key as app_name, c.id as role_id, role_name from "
				+ "(select * from cus_user_list) a "
				+ "left outer join "
				+ "(select * from cus_app_info) b "
				+ "on(a.app_id = b.id) "
				+ "left outer join "
				+ "(select * from cus_user_role_ref) c  "
				+ "on(a.id = c.user_id) "
				+ "left outer join "
				+ "(select * from cus_sys_role) d "
				+ "on(c.role_id = d.id) "
				+ "#if(${user_name} && ${user_name} != '') and user_name = '${user_name}' #end "
				+ "#if(${app_name} && ${app_name} != '') and b.app_key like '%${app_name}%' #end "
				+ "#if(${role_name} && ${role_name} != '') and c.role_name like '%${role_name}%' #end "
				+ "limit ${start}, ${pageSize}";
		
		String asql = buildSql(sql, where);
		
		List<User> list = jdbcTemplate.query(asql, 
				new BeanPropertyRowMapper<User>(User.class));
		
		return list;
		
	}
	
	public int updateUser(Map<Object, Object> where) {
		String sql = 
				"update cus_user_list a, cus_user_role_ref b "
				+ "set a.app_id = '${app_id}', a.phone = '${phone}', a.e_mail = '${e_mail}', "
				+ "a.status='${status}', a.update_time = CURRENT_TIME(), "
				+ "b.role_id = '${role_id}' "
				+ "where b.id = ${id} "
				+ "and b.user_id = ${id} ";
		
		int ret = jdbcTemplate.update(buildSql(sql, where));
		return ret;
	}
	
	
	
}
