package com.centrixlink.cus.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.centrixlink.cus.base.BaseDao;
import com.centrixlink.cus.bean.Permission;
import com.centrixlink.log.Logging;

@Repository
public class PermissionDao extends BaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Permission> findByRole(int role_id) {
		String sql = "select id, parent_id, text, url, type, descr, icon as iconCls from cus_sys_permission "
				+ "where id in (select permission_id from cus_role_permission_ref where role_id = ?)";

		List<Permission> permissions = jdbcTemplate.query(sql,
				new Object[] { role_id },
				new BeanPropertyRowMapper<Permission>(Permission.class));

		return permissions;
	}

	public int count() {
		return jdbcTemplate.queryForObject(
				"select count(id) from cus_sys_permission", Integer.class);
	}

	public List<Permission> queryAll(Map<Object, Object> where) {
		String sql = "select id, parent_id, text, url, type, descr, icon as iconCls from cus_sys_permission "
				+ "where 1 = 1"
				+ "#if(${id} && ${id} != '') and id = '${id}' #end "
				+ "#if(${parent_id} && ${parent_id} != '') and parent_id = '${parent_id}' #end "
				+ "#if(${text} && ${text} != '') and text like '%${text}%' #end "
				+ "limit ${start}, ${pageSize}";

		String asql = buildSql(sql, where);
		List<Permission> list = jdbcTemplate.query(asql,
				new BeanPropertyRowMapper<Permission>(Permission.class));

		return list;
	}

	public int insertPermission(Map<Object, Object> where) {
		String sql = "insert into cus_sys_permission (parent_id, text, url, type, descr, icon) "
				+ "values "
				+ "('${parent_id}', '${text}', '${url}', '${type}', '${descr}', '${icon}')";

		int ret = jdbcTemplate.update(buildSql(sql, where));
		return ret;
	}

	public int delPermission(Map<Object, Object> where) {
		String sql = "delete from cus_sys_permission where id in (${ids})";

		int ret = jdbcTemplate.update(buildSql(sql, where));
		return ret;
	}
	
	public int editPermission(Map<Object, Object> where) {
		String sql = "update cus_sys_permission set parent_id = ${parent_id}, "
				+ "text = '${text}', url = '${url}', type = '${type}', descr = '${descr}', icon = '${icon}' "
				+ "where id = ${id}";

		int ret = jdbcTemplate.update(buildSql(sql, where));
		return ret;
	}

	public int roleAddPms(String pids, int role_id) {
		String sql = "insert into cus_role_permission_ref (role_id, permission_id) values (?, ?)";

		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String pid : pids.split(",")) {

			int count = jdbcTemplate.queryForObject(
							"select count(1) from cus_role_permission_ref where role_id = ? and permission_id = ?",
							new Object[] { role_id, pid }, Integer.class);

			if(count == 0) {
				map.put("role_id", role_id);
				map.put("permission_id", pid);
				list.add(map);
			}
		}

		int[] rets = jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						Map<String, Object> map = list.get(i);
						ps.setString(1, map.get("role_id").toString());
						ps.setString(2, map.get("permission_id").toString());
					}

					@Override
					public int getBatchSize() {
						return list.size();
					}
				});
		
		Logging.info("The role " + role_id + " succeed add " + rets.length + " permissions. there have been : " + (pids.split(",").length - rets.length));

		return rets.length;
	}

}
