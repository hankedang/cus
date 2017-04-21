package com.centrixlink.cus.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.cus.base.BaseService;
import com.centrixlink.cus.bean.Permission;
import com.centrixlink.cus.bean.RedisSession;
import com.centrixlink.cus.bean.Role;
import com.centrixlink.cus.bean.User;
import com.centrixlink.cus.dao.SessionDao;
import com.centrixlink.cus.dao.UserDao;
import com.centrixlink.log.Logging;
import com.centrixlink.util.Constants;
import com.centrixlink.util.PageEntry;
import com.centrixlink.util.Utils;

@Service
public class UserService extends BaseService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private AppService ssoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	public boolean login(String user_name, String pwd, int app_id,
			HttpSession session) {
		User user = userDao.login(user_name, pwd, app_id);
		if (user == null) {
			Logging.info("user: " + user_name + " login fail!");
			return false;
		} else {
			Logging.info("user: " + user_name + " login success!");
			// 存储session
			session.setAttribute(Constants.USER, user);
			if (app_id == Constants.DEFAULT_APP_ID) {
				session.setAttribute(Constants.APP_ID, app_id);
			}
			sessionDao.saveSession(this.buildSession(user, session));
			return true;
		}
	}

	public void saveSession(RedisSession session) {
		sessionDao.saveSession(session);
	}

	public JSONObject querySession(String session_id) {
		return sessionDao.querySession(session_id);
	}
	
	public long getSessionTTL(String session_id) {
		return sessionDao.getExpire(session_id);
	}
	
	public void delSession(String session_id) {
		sessionDao.delSession(session_id);
	}
	
	public void updateTTL(String session_id) {
		sessionDao.updateExpire(session_id);
	}
	
	public JSONArray queryPermission(String session_id) {
		return (JSONArray) querySession(session_id).get("permission");
	}
	
	public PageEntry<User> queryAll(int pageNumber, int pageSize, Map<Object, Object> where) {
		PageEntry<User> pe = new PageEntry<User>();
		int start = (pageNumber - 1) * pageSize;
		int count = userDao.count();
		where.put("start", start);
		where.put("pageSize", pageSize);
		List<User> records = userDao.queryAll(where);
		pe.setPageNumber(pageNumber);
		pe.setPageSize(pageSize);
		pe.setTotal(count);
		pe.setRecords(records);
		return pe;
	}
	
	public boolean updateUser(Map<Object, Object> where) {
		int ret = userDao.updateUser(where);
		return ret < 0 ? false : true;
	}

	private RedisSession buildSession(User user, HttpSession session) {
		RedisSession redisSession = new RedisSession();
		int app_id = (int) session.getAttribute(Constants.APP_ID);
		String app_key = ssoService.appKey(app_id);
		String session_id = app_key + "_" + Utils.randomString();
		//保存 redissessionid
		session.setAttribute(Constants.REDIS_SESSION_ID, session_id);
		// redissessiono 赋值
		redisSession.setSession_id(session_id);
		redisSession.setUser_id(user.getId());
		redisSession.setUser_name(user.getUser_name());
		// 根据用户查询角色
		Role role = roleService.findRoleByUser(user.getId());
		// 根据角色获取权限
		if(role != null){
			List<Permission> permissions = permissionService.findPermissionByRole(role.getId());
			redisSession.setRole(role);
			redisSession.setPermissions(permissions);
		}
		Logging.info(redisSession.toString());
		return redisSession;
	}
	

}
