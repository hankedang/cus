package com.centrixlink.cus.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.cus.bean.Role;
import com.centrixlink.cus.dao.RoleDao;
import com.centrixlink.util.PageEntry;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao ;
	
	public Role findRoleByUser(int user_id) {
		
		return roleDao.findByUser(user_id);
	}
	
	public List<Map<String, Object>> combQuery() {
		return roleDao.comboQuery();
	}
	
	public PageEntry<Role> query(int pageNumber, int pageSize, Map<Object, Object> where) {
		PageEntry<Role> pe = new PageEntry<Role>();
		int start = (pageNumber - 1) * pageSize;
		where.put("start", start);
		int count = roleDao.count();
		List<Role> records = roleDao.query(where);
		pe.setPageNumber(pageNumber);
		pe.setPageSize(pageSize);
		pe.setTotal(count);
		pe.setRecords(records);
		return pe;
	}
	
	public boolean updateRole(Map<Object, Object> where) {
		return roleDao.updateRole(where) < 0 ? false : true;
	}
	
	public boolean insertRole(Map<Object, Object> where) {
		return roleDao.insertRole(where) < 0 ? false : true;
	}
	
	public boolean delRole(int role_id) {
		return roleDao.delRole(role_id) < 0 ? false : true;
	}
	
	public boolean delPermission(int role_id, int permission_id) {
		return roleDao.delPermissison(role_id, permission_id) < 0 ? false : true;
	}

}
