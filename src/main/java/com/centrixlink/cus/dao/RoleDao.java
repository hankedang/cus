package com.centrixlink.cus.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.cus.base.BaseDao;
import com.centrixlink.cus.bean.Role;

@Repository
public class RoleDao extends BaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Role findByUser(int user_id) {

		String sql = "select id, role_name, descr from cus_sys_role "
				+ "where id = (select role_id from cus_user_role_ref where user_id = ? limit 1)";

		Role role = null;
		try {
			role = jdbcTemplate.queryForObject(sql, new Object[] { user_id }, new BeanPropertyRowMapper<Role>(Role.class));
		} catch (EmptyResultDataAccessException e) {
			//nothing
		}
		return role;
	}
	
	public List<Map<String, Object>> comboQuery() {
		String sql = "select id, role_name from cus_sys_role";
		return jdbcTemplate.queryForList(sql);
	}
	
	public int count() {
		return jdbcTemplate.queryForObject("select count(id) from cus_sys_role", Integer.class);
	}
	
	public List<Role> query(Map<Object, Object> where) {
		String sql = "select id, role_name, descr from cus_sys_role "
				+ "#if(${role_name} && ${role_name} != '') where role_name like '%${role_name}%' #end "
				+ "limit ${start}, ${pageSize}";
		
		String asql = buildSql(sql, where);
		
		List<Role> list = jdbcTemplate.query(asql, 
				new BeanPropertyRowMapper<Role>(Role.class));
		
		return list;
	}
	
	public int updateRole(Map<Object, Object> where) {
		String sql = "update cus_sys_role set role_name = '${role_name}', descr ='${descr}' where id = ${id} ";
		int ret = jdbcTemplate.update(buildSql(sql, where));
		return ret;
	}
	
	public int insertRole(Map<Object, Object> where) {
		String sql = "insert into cus_sys_role (role_name, descr) values ('${role_name}', '${descr}')";
		int ret = jdbcTemplate.update(buildSql(sql, where));
		return ret;
	}
	
	public int delRole(int role_id) {
		String sql = "delete from cus_sys_role where id=?";
		int ret = jdbcTemplate.update(sql, new Object[]{role_id});
		return ret;
	}
	
	public int delPermissison(int role_id, int permission_id) {
		String sql = "delete from cus_role_permission_ref where role_id = ? and permission_id = ?";
		int ret = jdbcTemplate.update(sql, new Object[]{role_id, permission_id});
		return ret;
	}
	
}
