package com.centrixlink.cus.filter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.centrixlink.log.Logging;
import com.centrixlink.schduler.SessionCheck;
import com.centrixlink.util.Constants;


public class SessionMgr implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		SessionCheck.addSession(session);
		
		Logging.info("new session, session count: " + SessionCheck.size());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		SessionCheck.remove(session);
		
		Logging.info("session : " + session.getAttribute(Constants.REDIS_SESSION_ID) + " timeout .");
	}

   

}
