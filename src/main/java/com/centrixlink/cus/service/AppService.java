package com.centrixlink.cus.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.cus.base.BaseService;
import com.centrixlink.cus.bean.AppInfo;
import com.centrixlink.cus.dao.AppDao;
import com.centrixlink.log.Logging;

@Service
public class AppService extends BaseService {

	@Autowired
	private AppDao appDao ;
	
	public AppInfo authApp(String app_key, String app_secret) {
		AppInfo info = appDao.authApp(app_key, app_secret);
		if(info != null) {
			return info;
		}
		Logging.info("app_key: " + app_key + ", not permission use this sys.");
		return null;
	}
	
	public String appKey(int id) {
		return appDao.findAppKey(id);
	}
	
	public List<Map<String, Object>> combApp(){
		return appDao.query();
	}

	
}
