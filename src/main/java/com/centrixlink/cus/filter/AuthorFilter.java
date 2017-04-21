package com.centrixlink.cus.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.centrixlink.cus.service.UserService;
import com.centrixlink.log.Logging;
import com.centrixlink.util.Constants;

/**
 * Servlet Filter implementation class AuthorFilter
 */

public class AuthorFilter implements Filter {
	
	private UserService uService = null;
	
    /**
     * Default constructor. 
     */
    public AuthorFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		req.setCharacterEncoding(Constants.CHARACTER_ENCODING);
		HttpSession session = req.getSession();
		Object user = session.getAttribute(Constants.USER);
		if(null == user ) {
			Logging.info("user not login.");
			rep.sendRedirect("/cus/login.jsp");
			return;
		}
		chain.doFilter(request, response);
		

		/**
		 *  更新session时间
		 */
		if(uService != null) {
			String key = (String) session.getAttribute(Constants.REDIS_SESSION_ID);
			uService.updateTTL(key);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext sc = fConfig.getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
		
		if(cxt != null ) {
			uService = (UserService) cxt.getBean("uService");
		} else {
			Logging.error("cxt must be not null.");
			System.exit(-1);
		}
	}

}
