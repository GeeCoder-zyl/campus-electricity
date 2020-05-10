/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

	long nowPage = 1;// 当前页
	long pageSize = 5;// 每页条数
	long totalPage = 0;// 总页数
	long total = 0;// 总条数

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
	@PostMapping("/admin/login")
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
	@DeleteMapping("/admin/logOut")
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

	/**
	 * 分页模糊查询管理员信息
	 * 
	 * @param adminStatus
	 * @param content
	 * @param fields
	 * @param nowPage
	 * @param pageSize
	 * @return
	 */
	@GetMapping("admin/findAdminsPage")
	public Map<Object, Object> findAdminsPage(Integer adminStatus, String content, String fields, long nowPage,
			long pageSize) {
		System.out.println("分页模糊查询管理员信息Begin...");
		System.out.println(adminStatus + "~" + content + "~" + fields);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 返回初始数据
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		map.put("nowPage", nowPage);
		map.put("pageSize", pageSize);
		map.put("totalPage", totalPage);
		map.put("total", total);

		// 根据选择的管理员字段设置查询的管理员字段内容
		Admin admin = new Admin();
		if (content != null && content != "") {
			if ("管理员名".equals(fields)) {
				admin.setAdminName(content);
			} else if ("真实姓名".equals(fields)) {
				admin.setAdminRealName(content);
			} else if ("联系电话".equals(fields)) {
				admin.setAdminTel(content);
			}
		}
		admin.setAdminStatus(adminStatus);
		System.out.println(admin);

		// 分页模糊查询管理员信息
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		IPage<Admin> iPage = adminService.listAdminsPage(new Page<Admin>(nowPage, pageSize), admin);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		map.put("pageSize", pageSize);
		map.put("nowPage", nowPage);
		map.put("totalPage", totalPage);
		map.put("total", total);
		List<Admin> adminList = iPage.getRecords();
		if (adminList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到管理员信息！");
			return map;
		}
		System.out.println(adminList);
		map.put("adminList", adminList);

		System.out.println("分页模糊查询管理员信息End...");
		return map;
	}

	/**
	 * 修改管理员信息
	 * 
	 * @param admin
	 * @return
	 */
	@PutMapping("admin/updateAdmin")
	public int updateAdmin(Admin admin) {
		System.out.println("修改管理员信息Begin...");
		System.out.println(admin);

		int num = adminService.updateAdmin(admin);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条管理员信息修改成功！");

		System.out.println("修改管理员信息End...");
		return num;
	}

	/**
	 * 查询当前已登录管理员的信息
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/admin/findAdminInfo")
	public Map<Object, Object> findAdminInfo(HttpSession session) {
		System.out.println("查询当前已登录管理员的等级Begin...");

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 从session中获取已登录管理员的ID
		int adminId = (int) session.getAttribute(SESSION_ADMIN_ID);
		map.put("adminId", adminId);
		System.out.println(adminId);

		// 根据管理员ID查询管理员等级
		int adminLevel = adminService.getAdminById(adminId).getAdminLevel();
		map.put("adminLevel", adminLevel);
		System.out.println(adminLevel);

		System.out.println("查询当前已登录管理员的等级End...");
		return map;
	}

	/**
	 * 新增管理员
	 * 
	 * @param admin
	 * @return
	 */
	@PostMapping("/admin/addAdmin")
	public int addAdmin(Admin admin) {
		System.out.println("新增管理员Begin...");
		System.out.println(admin);

		int num = adminService.insertAdmin(admin);
		if (num == 0) {
			return 0;
		}
		System.out.println("新增" + num + "个管理员成功！");

		System.out.println("新增管理员End...");
		return num;
	}

	/**
	 * 根据管理员名查询管理员信息
	 * 
	 * @param adminName
	 * @return
	 */
	@GetMapping("findAdminByAdminName")
	public int findAdminByAdminName(String adminName) {
		System.out.println("根据管理员名查询管理员信息Begin...");
		System.out.println(adminName);

		Admin admin = adminService.getAdminByName(adminName);
		if (admin == null) {
			return 0;
		}
		System.out.println(admin);

		System.out.println("根据管理员名查询管理员信息End...");
		return 1;
	}

	/**
	 * 根据ID删除管理员
	 * 
	 * @param adminId
	 * @return
	 */
	@DeleteMapping("/admin/delAdminById")
	public int delAdminById(Integer adminId) {
		System.out.println("根据ID删除管理员Begin...");
		System.out.println(adminId);

		int num = adminService.deleteAdminById(adminId);
		if (num == 0) {
			return 0;
		}
		System.out.println("删除" + num + "条管理员信息成功！");

		System.out.println("根据ID删除管理员End...");
		return num;
	}

}
