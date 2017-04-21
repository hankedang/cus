package com.centrixlink.cus.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centrixlink.cus.base.BaseController;
import com.centrixlink.cus.service.UserService;
import com.centrixlink.util.Constants;


@Controller
@RequestMapping("/user/")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	private String login = "redirect:/login.jsp";
	private String main = "redirect:/page/admin/main.do";
	
	@RequestMapping("/login")
	public String login(String user_name, String pwd, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String callback = req.getParameter("callback");
		int app_id = Constants.DEFAULT_APP_ID;
		
		if(callback != null && callback.length() > 0) {
			login = "redirect:/login.jsp?" + callback ;
			main = "redirect:http://" + callback ;
		}
		if(session.getAttribute(Constants.APP_ID) != null) {
			app_id = (int) session.getAttribute(Constants.APP_ID);
		}
		
		if(userService.login(user_name, pwd, app_id, session)) {
			return main + "?session=" + session.getAttribute(Constants.REDIS_SESSION_ID);
		} else {
			return login;
		}
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return login;
	}
	
}
