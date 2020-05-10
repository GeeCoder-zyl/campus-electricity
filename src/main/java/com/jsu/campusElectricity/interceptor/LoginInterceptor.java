/**
 * 
 */
package com.jsu.campusElectricity.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jsu.campusElectricity.service.AdminService;
import com.jsu.campusElectricity.service.UserService;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: UserLoginInterceptor.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月18日 下午4:51:56
 * @Description: 登录拦截器类
 */
@Component
public class LoginInterceptor implements HandlerInterceptor, FinalConstant {
	@Autowired
	UserService userService;
	@Autowired
	AdminService adminService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle被调用");
		System.out.println("请求的完整 URL：" + request.getRequestURL());
		String req = request.getServletPath();// 请求的方法
		System.out.println("请求的方法：" + req);
		String reqObject = null;// 发起请求的对象
		if (req.lastIndexOf("/") > 0) {
			reqObject = req.substring(req.indexOf("/") + 1, req.lastIndexOf("/"));
			System.out.println("发起请求的对象：" + reqObject);
		}
		if (reqObject != null && reqObject != "") {
			HttpSession session = request.getSession();// 获取session
			String xReq = request.getHeader("x-requested-with");// 获取请求头
			if (reqObject.equals("user") && session.getAttribute("userId") == null) {
				System.out.println("用户未登录，跳转到用户登录页面！");
				// 判断是否是Ajax或Axios请求
				if (("XMLHttpRequest").equalsIgnoreCase(xReq)) {
					// 设置响应消息
					response.getWriter().write("sessionIsNull");
					return false;
				} else {
					response.sendRedirect("http://localhost:8080/user/user-login.html");
					return false;
				}
			} else if (reqObject.equals("admin") && session.getAttribute("adminId") == null) {
				System.out.println("管理员未登录，跳转到管理员登录页面！");
				// 判断是否是Ajax或Axios请求
				if (("XMLHttpRequest").equalsIgnoreCase(xReq)) {
					// 设置响应消息
					response.getWriter().write("sessionIsNull");
					return false;
				} else {
					response.sendRedirect("http://localhost:8080/admin/admin-login.html");
					return false;
				}
			} else if (reqObject.equals("user")
					&& userService.getUserById((int) session.getAttribute(SESSION_USER_ID)).getUserStatus() == 0) {
				System.out.println("用户已被禁用，跳转到用户登录页面！");
				// 清除session中的用户信息
				session.removeAttribute(SESSION_USER_ID);
				System.out.println("清除用户登录信息完成！");
				// 判断是否是Ajax或Axios请求
				if (("XMLHttpRequest").equalsIgnoreCase(xReq)) {
					// 设置响应消息
					response.getWriter().write("sessionIsNull");
					return false;
				} else {
					response.sendRedirect("http://localhost:8080/user/user-login.html");
					return false;
				}
			} else if (reqObject.equals("admin")
					&& adminService.getAdminById((int) session.getAttribute(SESSION_ADMIN_ID)).getAdminStatus() == 0) {
				System.out.println("管理员已被禁用，跳转到管理员登录页面！");
				// 清除session中的用户信息
				session.removeAttribute(SESSION_ADMIN_ID);
				System.out.println("清除管理员登录信息完成！");
				// 判断是否是Ajax或Axios请求
				if (("XMLHttpRequest").equalsIgnoreCase(xReq)) {
					// 设置响应消息
					response.getWriter().write("sessionIsNull");
					return false;
				} else {
					response.sendRedirect("http://localhost:8080/admin/admin-login.html");
					return false;
				}
			} else {
				return true;
			}
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle被调用");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		System.out.println("afterCompletion被调用\n");
	}
}
