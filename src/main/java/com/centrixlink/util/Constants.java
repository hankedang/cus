package com.centrixlink.util;

import net.sf.json.JSONObject;

public class Constants {
	
	// 默认APP_ID 
	public static String APP_ID = "app_id";
	public static int DEFAULT_APP_ID = 1;
	public static String USER = "user";
	// redis session id
	public static String REDIS_SESSION_ID = "redis_session_key";
	public static String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	// return result 
	public static JSONObject SUCC = new JSONObject();
	public static JSONObject FAIL = new JSONObject();
	static{
		SUCC.put("result", "ok");
		FAIL.put("result", "fail");
	}
	//字符集
	public static String CHARACTER_ENCODING = "UTF-8";
	//redis 默认超时时间， 秒
	public static long REDIS_TIMEOUT = 30 * 60;

}
