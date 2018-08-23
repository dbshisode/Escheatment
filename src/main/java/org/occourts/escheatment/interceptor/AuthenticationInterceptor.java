package org.occourts.escheatment.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.occourts.escheatment.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {

	// AuthenticationInterceptor.java
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Avoid a redirect loop for some urls
		if (!request.getRequestURI().equals("/Escheatment/") && !request.getRequestURI().contains("/resources/")) {
			User userData = (User) request.getSession().getAttribute("user");
			// userData should contain user info once logged in, but will be null until
			// successfully authenticated and /review
			// is displayed; therefore, do not redirect if data is posted from login form
			if (userData == null && request.getParameter("userName") == null) {
				response.sendRedirect("/Escheatment/");
				return false;
			}
		}
		return true;
	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
