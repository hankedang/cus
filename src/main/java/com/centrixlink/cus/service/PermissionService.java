package com.centrixlink.cus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.cus.base.BaseService;
import com.centrixlink.cus.bean.Permission;
import com.centrixlink.cus.dao.PermissionDao;
import com.centrixlink.log.Logging;
import com.centrixlink.util.PageEntry;

@Service
public class PermissionService extends BaseService{

	@Autowired
	private PermissionDao permissionDao;
	
	public List<Permission> findPermissionByRole(int role_id) {
		List<Permission> qs = permissionDao.findByRole(role_id);
		Logging.debug(qs.toString());
		// 目录
		List<Permission> root = new ArrayList<Permission>();
		//取出所有一级菜单
		for(Permission p : qs) {
			if(p.getType() == 0 && p.getParent_id() == 0) {
				root.add(p);
			}
		}
		convert(qs, root);
		return root;
	}
	
	private void convert(List<Permission> qs, List<Permission> root) {
		// 查询目录下面的子目录
		for(Permission p : root) {
			List<Permission> children = new ArrayList<Permission>();
			for(Permission chd : qs) {
				if(chd.getParent_id() == p.getId()) {
					children.add(chd);
				}
			}
			if(children.size() > 0) {
				convert(qs, children);
				p.setChildren(children);
			}
		}
	}
	
	public PageEntry<Permission> queryAll(int pageNumber, int pageSize, Map<Object, Object> where) {
		PageEntry<Permission> pe = new PageEntry<Permission>();
		int start = (pageNumber - 1) * pageSize;
		int count = permissionDao.count();
		where.put("start", start);
		where.put("pageSize", pageSize);
		List<Permission> records = permissionDao.queryAll(where);
		pe.setPageNumber(pageNumber);
		pe.setPageSize(pageSize);
		pe.setTotal(count);
		pe.setRecords(records);
		return pe;
	}
	
	public Permission findById(int id) {
		Map<Object, Object> where = new HashMap<Object, Object>();
		where.put("id", id);
		where.put("start", 0);
		where.put("pageSize", 2);
		List<Permission> records = permissionDao.queryAll(where);
		return records.size() > 0 ? records.get(0) : null;
	}
	
	public boolean insertPermission(Map<Object, Object> where){
		return permissionDao.insertPermission(where) < 0 ? false : true;
	}
	
	public boolean delPermission(Map<Object, Object> where) {
		return permissionDao.delPermission(where) < 0 ? false : true;
	}
	
	public boolean editPermission(Map<Object, Object> where) {
		return permissionDao.editPermission(where) < 0 ? false : true;
	}
	
	public int roleAddPms(String pids, int role_id) {
		return permissionDao.roleAddPms(pids, role_id);
	}
	
}
