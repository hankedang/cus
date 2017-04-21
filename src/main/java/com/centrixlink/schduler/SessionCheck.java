package com.centrixlink.schduler;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.centrixlink.cus.service.UserService;
import com.centrixlink.log.Logging;
import com.centrixlink.util.Constants;

public class SessionCheck {
	
	private static CopyOnWriteArrayList<HttpSession> list = new CopyOnWriteArrayList<HttpSession>();
	private static Timer timer = new Timer();
	
	static {
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try {
					Iterator<HttpSession> it = list.iterator();
					while(it.hasNext()) {
						HttpSession session = it.next();
						UserService uService = getUService(session);
						String key = (String) session.getAttribute(Constants.REDIS_SESSION_ID);
						if(key == null || "".equals(key) || "null".equals(key)) {
							continue;
						}
						long ttl = uService.getSessionTTL(key);
						if(ttl < 0) {
							session.invalidate();
							continue;
						}
						session.setMaxInactiveInterval((int) ttl);
					}
				} catch (Exception e) {
					Logging.error("session check timer exception.");
				}
			}
		}, 1000, 10 * 1000);
		
	}
	
	public static void addSession(HttpSession sesison) {
		list.add(sesison);
	}
	
	public static void remove(int index) {
		list.remove(index);
	}
	
	public static void remove(HttpSession o) {
		list.remove(o);
	}
	
	public static int size() {
		return list.size();
	}

	private static UserService getUService(HttpSession session) {
		UserService uService = null;
		ServletContext sc = session.getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
		
		if(cxt != null ) {
			uService = (UserService) cxt.getBean("uService");
		} else {
			Logging.error("cxt must be not null.");
		}
		return uService;
	}

}
