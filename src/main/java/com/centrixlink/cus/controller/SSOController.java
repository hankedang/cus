package com.centrixlink.cus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centrixlink.cus.bean.AppInfo;
import com.centrixlink.cus.service.AppService;
import com.centrixlink.util.Constants;


@Controller
@RequestMapping("/sso")
public class SSOController {
	
	@Autowired
	private AppService ais ;
	
	
	@RequestMapping("/login")
	public String siteLogin(String app_key, String app_secret, String callback, HttpServletRequest req) {
		AppInfo app = ais.authApp(app_key, app_secret);
		if(app != null) {
			HttpSession session = req.getSession();
			session.setAttribute(Constants.APP_ID, app.getId());
			return "redirect:/login.jsp?" + callback;
		}
		return null;
	}
	
	public String siteLogout(String app_key, String app_secret, String callback, HttpServletRequest req){
		return null;
	}
	
	

}
