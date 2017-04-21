package com.centrixlink.cus.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.centrixlink.cus.base.BaseController;
import com.centrixlink.cus.bean.Permission;
import com.centrixlink.cus.bean.Role;
import com.centrixlink.cus.bean.User;
import com.centrixlink.cus.service.AppService;
import com.centrixlink.cus.service.PermissionService;
import com.centrixlink.cus.service.RoleService;
import com.centrixlink.cus.service.UserService;
import com.centrixlink.log.Logging;
import com.centrixlink.util.Constants;
import com.centrixlink.util.PageEntry;

@Controller
@RequestMapping("/page/admin")
public class AdminController extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AppService appService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String redisSessionKey = (String) session.getAttribute(Constants.REDIS_SESSION_ID);
		JSONArray arr = userService.queryPermission(redisSessionKey);
		
		Map<String, Object> map = new HashMap<>();
		map.put("menu", arr);
		
		return new ModelAndView("/page/admin/main", map);
	}
	
	@RequestMapping("/user/allUser")
	public void allUser(int pageNumber, int pageSize, 
			HttpServletRequest req, HttpServletResponse resp) {
		
		PageEntry<User> pe = userService.queryAll(pageNumber, pageSize, newWhere(req));
		
		try {
			this.writeJSON(resp, pe);
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/user/appCombo")
	public void appCombo(HttpServletRequest req, HttpServletResponse resp) {
		List<Map<String, Object>> result = appService.combApp();
		try {
			this.writeJSON(resp, result);
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/user/roleCombo")
	public void roleCombo(HttpServletRequest req, HttpServletResponse resp) {
		List<Map<String, Object>> result = roleService.combQuery();
		try {
			this.writeJSON(resp, result);
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/user/edit")
	public void editUser(HttpServletRequest req, HttpServletResponse resp) {
		Map<Object, Object> where = newWhere(req);
		boolean isSucc = userService.updateUser(where);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/role/allRole")
	public void allRole(int pageNumber, int pageSize, 
			HttpServletRequest req, HttpServletResponse resp) {
		
		PageEntry<Role> pe = roleService.query(pageNumber, pageSize, newWhere(req));
		
		try {
			this.writeJSON(resp, pe);
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/role/edit")
	public void editRole(HttpServletRequest req, HttpServletResponse resp) {
		Map<Object, Object> where = newWhere(req);
		boolean isSucc = roleService.updateRole(where);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/role/add")
	public void addRole(HttpServletRequest req, HttpServletResponse resp) {
		Map<Object, Object> where = newWhere(req);
		boolean isSucc = roleService.insertRole(where);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/role/del")
	public void delRole(int role_id, HttpServletResponse resp) {
		boolean isSucc = roleService.delRole(role_id);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/role/delPermission")
	public void delPermission(int role_id, int permission_id, HttpServletResponse resp) {
		boolean isSucc = roleService.delPermission(role_id, permission_id);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/role/currentPermission")
	public void currentPermission(int role_id, HttpServletResponse resp) {

		List<Permission> result = permissionService.findPermissionByRole(role_id);
		
		try {
			this.writeJSON(resp, result);
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/permission/allPermission")
	public void allPermission(int pageNumber, int pageSize, 
			HttpServletRequest req, HttpServletResponse resp) {
		
		PageEntry<Permission> pe = permissionService.queryAll(pageNumber, pageSize, newWhere(req));
		
		try {
			this.writeJSON(resp, pe);
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	} 
	
	@RequestMapping("/permission/add")
	public void addPermission(HttpServletRequest req, HttpServletResponse resp) {
		Map<Object, Object> where = newWhere(req);
		boolean isSucc = permissionService.insertPermission(where);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/permission/permission")
	public void permissionById(int id, HttpServletResponse resp) {
		Permission permission = permissionService.findById(id);
		try {
		
			this.writeJSON(resp, permission);
			
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/permission/del")
	public void delPermission(HttpServletRequest req, HttpServletResponse resp) {
		Map<Object, Object> where = newWhere(req);
		boolean isSucc = permissionService.delPermission(where);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	@RequestMapping("/permission/edit")
	public void editPermission(HttpServletRequest req, HttpServletResponse resp) {
		Map<Object, Object> where = newWhere(req);
		boolean isSucc = permissionService.editPermission(where);
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
	}
	
	
	@RequestMapping("/permission/roleAddPms")
	public void roleAddPms(String pids, int role_id, HttpServletResponse resp) {
		
		int ret = permissionService.roleAddPms(pids, role_id);
		boolean isSucc = ret < 0 ? false : true;
		try {
			if(isSucc) {
				this.writeJSON(resp, Constants.SUCC);
			} else {
				this.writeJSON(resp, Constants.FAIL);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logging.error(e.getMessage());
		}
		
	}
	
}
