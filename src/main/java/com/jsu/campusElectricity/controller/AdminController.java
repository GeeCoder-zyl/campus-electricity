/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsu.campusElectricity.pojo.Admin;
import com.jsu.campusElectricity.service.AdminService;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: AdminController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年12月3日 下午7:00:46
 * @Description: 管理员控制器
 */
/**
 * @ClassName: AdminController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2020年1月9日 下午4:30:55
 * @Description: 该类的功能描述
 */
@RestController
public class AdminController implements FinalConstant {
	@Autowired
	AdminService adminService;

	private static final String nameRegExp = "^.{3,10}$";// 管理员名正则表达式：3-10位字符
	private static final String passRegExp = "^\\w{3,10}$";// 密码正则表达式：3-10位字母数字下划线

	/**
	 * 管理员登录
	 * 
	 * @param session
	 * @param response
	 * @param admin
	 * @param validateCode
	 * @return
	 */
	@PostMapping("/admin-login")
	public Map<Object, Object> submitLogin(HttpSession session, HttpServletResponse response, Admin admin,
			String validateCode) {
		System.out.println("管理员登录Begin...");
		System.out.println(admin + "~~~" + validateCode);

		Map<Object, Object> map = new HashMap<Object, Object>();
		String serverCode = (String) session.getAttribute(session.getId() + SESSION_VALIDATECODE);

		// 管理员登录输入信息验证
		if (!admin.getAdminName().matches(nameRegExp)) {
			System.out.println("管理员名由3-10位字符组成！");
			map.put(REQUEST_ERROR, "管理员名由3-10位字符组成！");
			return map;
		}
		if (!admin.getAdminPassword().matches(passRegExp)) {
			System.out.println("密码由3-10位字母数字下划线组成！");
			map.put(REQUEST_ERROR, "密码由3-10位字母数字下划线组成！");
			return map;
		}
		if (!validateCode.equalsIgnoreCase(serverCode)) {
			System.out.println("验证码不正确！");
			map.put(REQUEST_ERROR, "验证码不正确！");
			return map;
		}
		if (adminService.login(admin) == null) {
			System.out.println("管理员名或密码不正确！");
			map.put(REQUEST_ERROR, "管理员名或密码不正确！");
			return map;
		}

		// 清除session中的验证码
		session.removeAttribute(session.getId() + SESSION_VALIDATECODE);

		// 将登录成功的管理员的信息写入session和cookie
		session.setAttribute(SESSION_ADMIN_ID, adminService.getAdminByName(admin.getAdminName()).getAdminId());
		Cookie cookie = new Cookie(COOKIE_ADMIN_NAME, admin.getAdminName());
		cookie.setMaxAge(60 * 30);// 设置cookie过期时间为30分钟
		cookie.setPath("/");// 设置cookie存放目录为根目录，使多个页面可以获取同一个cookie
		response.addCookie(cookie);
		System.out.println("管理员 " + admin.getAdminName() + " 登录成功！");

		System.out.println("管理员登录End...\n");
		return null;
	}

	/**
	 * 管理员退出登录
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/admin-logOut")
	public Map<Object, Object> adminLogOut(HttpSession session) {
		System.out.println("管理员退出登录Begin...");

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 清除session中的管理员信息
		session.removeAttribute(SESSION_ADMIN_ID);
		if (session.getAttribute(SESSION_ADMIN_ID) != null) {
			map.put(REQUEST_ERROR, "管理员退出登录失败！");
			return map;
		}
		System.out.println("清除session成功！");
		System.out.println("管理员退出登录成功！");
		System.out.println("管理员退出登录End...");
		return null;
	}

}
