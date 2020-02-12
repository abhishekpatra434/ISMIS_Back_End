package com.ISMIS.applicationConfiguration;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ISMIS.service.UserApplicationDetails;

@Component
public class LogInInterceptor implements HandlerInterceptor {

	static Logger logger = LogManager.getLogger(LogInInterceptor.class);
	Set<String> urlList = new HashSet<String>() {
		{
			add("logOut");
			add("login");
			add("getHomeList");
			add("invalidSession");

		}
	};

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
	}

	// PROD

	/*
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object object) throws Exception {
	 * 
	 * String uri = request.getRequestURI(); boolean isOpenUrl =
	 * urlList.parallelStream().anyMatch(url -> uri.endsWith(url));
	 * 
	 * if (!isOpenUrl) {
	 * 
	 * String usesionId = request.getHeader("usessionId"); String methodType =
	 * request.getMethod();
	 * 
	 * if (!(usesionId == null && methodType.equalsIgnoreCase("OPTIONS"))) {
	 * 
	 * if (usesionId == null || UserApplicationDetails.APPDET.get(usesionId) ==
	 * null) { response.sendRedirect("/invalidSession"); return false; } } }
	 * 
	 * return true; }
	 */

	// Testing

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		String uri = request.getRequestURI();

		if (!uri.endsWith("logOut") && !uri.endsWith("login")) {

			// String usessionId = request.getHeader("usessionId"); //
			System.out.println();
		}

		return true;
	}

	/*
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object object) throws Exception {
	 * 
	 * String uri = request.getRequestURI();
	 * 
	 * if (!uri.endsWith("logOut") && !uri.endsWith("login")) {
	 * 
	 * String usessionId = request.getHeader("usessionId");
	 * 
	 * UserBean UserBean = (UserBean)
	 * request.getSession().getAttribute("loggedUser"); if (UserBean == null) {
	 * 
	 * if (null != request.getSession()) { //System.out.println("uri-->"
	 * +uri+"-->"+request.getSession().getId());
	 * //request.getSession().invalidate(); }
	 * 
	 * System.out.println(request.getRemoteAddr());
	 * //response.encodeRedirectUrl("/logOut"); //response.sendRedirect("/logOut");
	 * //response.containsHeader("sorry"); //return false;
	 * 
	 * } }
	 * 
	 * return true; }
	 */

}
