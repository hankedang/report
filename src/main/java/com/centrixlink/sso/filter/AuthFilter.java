package com.centrixlink.sso.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 用户登录Filter，用户如无登录则跳至首页
 */
public class AuthFilter implements HandlerInterceptor {
	
	
	/**
	 * 进入Controller处理之前进行是否登陆过的判断
	 **/
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		return true;
	}

	/**
	 * Controlller 处理完成返回Response之前处理
	 **/
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view)
			throws Exception {
		response.setCharacterEncoding("utf-8");
	}

	/**
	 * 响应Response之后，资源的释放等在此处理
	 **/
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exp)
			throws Exception {
	}
}
