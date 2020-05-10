package com.jsu.campusElectricity.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Admin;
import com.jsu.campusElectricity.pojo.Dormitory;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.pojo.User;
import com.jsu.campusElectricity.service.AdminService;
import com.jsu.campusElectricity.service.DormitoryService;
import com.jsu.campusElectricity.service.PayService;
import com.jsu.campusElectricity.service.UserService;
import com.jsu.campusElectricity.utils.ExportExcel;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: UserController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 上午11:52:59
 * @Description: 用户控制器
 */
@RestController
public class UserController implements FinalConstant {
	@Autowired
	UserService userService;
	@Autowired
	DormitoryService dormitoryService;
	@Autowired
	PayService payService;
	@Autowired
	AdminService adminService;

	long nowPage = 1;// 当前页
	long pageSize = 5;// 每页条数
	long totalPage = 0;// 总页数
	long total = 0;// 总条数

	private static final String nameRegExp = "^\\d{1,16}$";// 用户名正则表达式：1-16位数字
	private static final String passRegExp = "^\\w{8,16}$";// 密码正则表达式：8-16位字母数字下划线

	/**
	 * 用户登录
	 * 
	 * @param session
	 * @param response
	 * @param user
	 * @return
	 */
	@PostMapping("/user/login")
	public Map<Object, Object> login(HttpSession session, HttpServletResponse response, User user) {
		System.out.println("用户登录Begin...");
		System.out.println(user);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 用户登录输入信息验证
		if (!user.getUserName().trim().matches(nameRegExp)) {
			System.out.println("用户名由最多16位数字组成!");
			map.put(REQUEST_ERROR, "用户名由最多16位数字组成!");
			return map;
		}
		if (!user.getUserPassword().trim().matches(passRegExp)) {
			System.out.println("密码由8-16位字母数字下划线组成！");
			map.put(REQUEST_ERROR, "密码由8-16位字母数字下划线组成！");
			return map;
		}

		// 根据用户名查询用户信息
		User user1 = userService.getUserByUserName(user.getUserName());

		// 用户数据库信息验证
		if (user1 == null) {
			System.out.println("用户不存在！");
			map.put(REQUEST_ERROR, "用户不存在！");
			return map;
		}
		if (user1.getUserStatus() == 0) {
			System.out.println("你的账号已被禁用，请联系管理员！");
			map.put(REQUEST_ERROR, "你的账号已被禁用，请联系管理员！");
			return map;
		}
		if (userService.getUserLogin(user.getUserName(), user.getUserPassword()) == null) {
			System.out.println("用户名或密码错误！");
			map.put(REQUEST_ERROR, "用户名或密码错误！");
			return map;
		}

		// 将登录成功的用户的信息写入session和cookie
		session.setAttribute(SESSION_USER_ID, user1.getUserId());
		Cookie cookie = new Cookie(COOKIE_USER_ID, user1.getUserId().toString());
		Cookie cookie2 = new Cookie(COOKIE_USER_NAME, user1.getUserName());
		cookie.setMaxAge(60 * 30);// 设置cookie过期时间为30分钟
		cookie.setPath("/");// 设置cookie存放目录为根目录，使多个页面可以获取同一个cookie
		response.addCookie(cookie);
		cookie2.setMaxAge(60 * 30);
		cookie2.setPath("/");
		response.addCookie(cookie2);
		System.out.println("用户 " + user1.getUserName() + " 登录成功！");

		System.out.println("用户登录End...\n");
		return null;
	}

	/**
	 * 用户退出登录
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@DeleteMapping("/user/logOut")
	public Map<Object, Object> logOut(HttpSession session) {
		System.out.println("用户退出登录Begin...");

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 清除session中的用户信息
		session.removeAttribute(SESSION_USER_ID);
		if (session.getAttribute(SESSION_USER_ID) != null) {
			map.put(REQUEST_ERROR, "用户退出登录失败！");
			return map;
		}
		System.out.println("用户退出登录成功！");
		System.out.println("用户退出登录End...");
		return null;
	}

	/**
	 * 用户主页信息查询
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/user/index")
	public Map<Object, Object> index(HttpSession session) {
		System.out.println("用户主页信息查询Begin...");

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 从session中获取已登录用户的ID
		int userId = (int) session.getAttribute(SESSION_USER_ID);

		// 根据用户ID查询用户信息
		User user = userService.getUserById(userId);
		System.out.println(user);

		// 根据宿舍ID查询宿舍信息
		if (user.getDormitoryId() == null) {
			map.put(REQUEST_ERROR, "未绑定宿舍！");
			return map;
		}
		Dormitory dormitory = dormitoryService.getDormitoryById(user.getDormitoryId());
		if (dormitory == null) {
			map.put(REQUEST_ERROR, "未找到宿舍信息！");
			return map;
		}
		System.out.println(dormitory);
		map.put("dormitory", dormitory);

		// 根据宿舍ID查询宿舍最新的电费充值记录
		Pay pay = payService.getLatestPay(dormitory.getDormitoryId());
		if (pay == null) {
			map.put(REQUEST_ERROR, "未找到充值记录！");
			return map;
		}
		System.out.println(pay);
		map.put("pay", pay);

		// 根据充值方式查询充值人名称
		String payName = null;// 充值人名称
		if (pay.getPayManner() == 0) {// 当充值人为管理员时，获取管理员的名称
			Admin admin = adminService.getAdminById(pay.getPayPid());
			if (admin == null) {
				map.put(REQUEST_ERROR, "未找到充值人名称！");
				return map;
			}
			payName = admin.getAdminName();
		} else if (pay.getPayManner() == 1) {// 当充值人为用户时，获取用户的名称
			User user2 = userService.getUserById(pay.getPayPid());
			if (user2 == null) {
				map.put(REQUEST_ERROR, "未找到充值人名称！");
				return map;
			}
			payName = user2.getUserName();
		}
		System.out.println("payName：" + payName);
		map.put("payName", payName);

		System.out.println("用户主页信息查询End...");
		return map;
	}

	/**
	 * 用户个人信息查询
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/user/personalInfo")
	public Map<Object, Object> personalInfo(HttpSession session) {
		System.out.println("个人信息查询Begin...");

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 从session中获取已登录用户的ID
		int userId = (int) session.getAttribute(SESSION_USER_ID);

		// 根据用户ID查询用户信息
		User user = userService.getUserById(userId);
		System.out.println(user);
		map.put("user", user);

		// 根据宿舍ID查询宿舍信息
		if (user.getDormitoryId() == null) {
			map.put(REQUEST_ERROR, "未绑定宿舍！");
			return map;
		}
		Dormitory dormitory = new Dormitory();
		dormitory = dormitoryService.getDormitoryById(user.getDormitoryId());
		if (dormitory == null) {
			map.put(REQUEST_ERROR, "未找到宿舍信息！");
			return map;
		}
		System.out.println(dormitory);
		map.put("dormitory", dormitory);

		System.out.println("个人信息查询End...");
		return map;
	}

	/**
	 * 用户修改个人信息
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@PutMapping("/user/updateUser")
	public int updateUser(HttpSession session, HttpServletRequest request, HttpServletResponse response, User user) {
		System.out.println("用户修改个人信息Begin...");
		System.out.println(user);

		// 从session中获取已登录用户的ID
		int userId = (int) session.getAttribute(SESSION_USER_ID);

		// 修改个人信息
		user.setUserId(userId);
		int num = userService.updateUser(user);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条用户信息修改成功！");

//		// 将修改后的用户名写入cookie中
//		String userName = userService.getUserById(userId).getUserName();
//		Cookie[] cookie = request.getCookies();
//		for (Cookie co : cookie) {
//			if (co.getName().equals(COOKIE_USER_NAME)) {
//				co.setValue(userName);
//				co.setMaxAge(60 * 30);
//				co.setPath("/");
//				response.addCookie(co);
//
//				System.out.println("用户信息修改成功！");
//				return num;
//			}
//		}

		System.out.println("用户修改个人信息End...");
		return num;
	}

	/**
	 * 管理员修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping("admin/updateUserByAdmin")
	public int updateUserByAdmin(User user, String editDormitoryId) {
		System.out.println("管理员修改用户信息Begin...");
		System.out.println(user + "~" + editDormitoryId);

		if (editDormitoryId != null && editDormitoryId.length() != 0) {
			user.setDormitoryId(Integer.parseInt(editDormitoryId));
		} else {
			user.setDormitoryId(null);
		}
		System.out.println(user);

		int num = userService.updateUser(user);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条用户信息修改成功！");

		System.out.println("管理员修改用户信息End...");
		return num;
	}

	/**
	 * 管理员新增用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/admin/addUser")
	public int addUser(User user) {
		System.out.println("管理员新增用户Begin...");
		System.out.println(user);

		int num = userService.insertUser(user);
		if (num == 0) {
			return 0;
		}
		System.out.println("新增" + num + "个用户成功！");

		System.out.println("管理员新增用户End...");
		return num;
	}

	/**
	 * 管理员根据用户ID删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@DeleteMapping("admin/delUserById")
	public int delUserById(Integer userId) {
		System.out.println("管理员根据用户ID删除用户Begin...");
		System.out.println(userId);

		int num = userService.deleteUserById(userId);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条用户信息删除成功！");

		System.out.println("管理员根据用户ID删除用户End...");
		return num;
	}

	/**
	 * 分页模糊查询用户信息
	 * 
	 * @param userStatus
	 * @param content
	 * @param fields
	 * @param nowPage
	 * @param pageSize
	 * @return
	 */
	@GetMapping("admin/findUsersPage")
	public Map<Object, Object> findUsersPage(Integer userStatus, String content, String fields, long nowPage,
			long pageSize) {
		System.out.println("分页模糊查询用户信息Begin...");
		System.out.println(userStatus + "~" + content + "~" + fields);

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

		// 根据选择的用户字段设置查询的用户字段内容
		User user = new User();
		if (content != null && content != "") {
			if ("用户名".equals(fields)) {
				user.setUserName(content);
			} else if ("真实姓名".equals(fields)) {
				user.setUserRealName(content);
			} else if ("联系电话".equals(fields)) {
				user.setUserTel(content);
			} else if ("绑定的宿舍号".equals(fields)) {
				if (dormitoryService.getDormitoryByNo(Integer.parseInt(content)) == null) {
					map.put(REQUEST_ERROR, "未找到宿舍信息，请确认输入正确的宿舍号！");
					return map;
				}
				Integer dormitoryId = dormitoryService.getDormitoryByNo(Integer.parseInt(content)).getDormitoryId();
				if (dormitoryId == null) {
					map.put(REQUEST_ERROR, "未找到宿舍信息！");
					return map;
				}
				user.setDormitoryId(dormitoryId);
			}
		}
		user.setUserStatus(userStatus);
		System.out.println(user);

		// 分页模糊查询用户信息
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		IPage<User> iPage = userService.listUsersPage(new Page<User>(nowPage, pageSize), user);
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
		List<User> userList = iPage.getRecords();
		if (userList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到用户信息！");
			return map;
		}
		System.out.println(userList);
		map.put("userList", userList);

		// 根据用户绑定的宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(userList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);
		map.put("dormitoryNoList", dormitoryNoList);

		System.out.println("分页模糊查询用户信息End...");
		return map;
	}

	/**
	 * 导出用户信息
	 * 
	 * @param userStatus
	 * @param content
	 * @param fields
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@GetMapping("admin/exportUsersToExcel")
	public Map<Object, Object> exportUsersToExcel(HttpServletResponse response, Integer userStatus, String content,
			String fields, long nowPage, long pageSize) throws Exception {
		System.out.println("导出用户信息Begin...");
		System.out.println(nowPage + "~" + pageSize + "~" + userStatus + "~" + content + "~" + fields);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 根据选择的用户字段设置查询的用户字段内容
		User user = new User();
		if (content != null && content != "") {
			if ("用户名".equals(fields)) {
				user.setUserName(content);
			} else if ("真实姓名".equals(fields)) {
				user.setUserRealName(content);
			} else if ("联系电话".equals(fields)) {
				user.setUserTel(content);
			} else if ("绑定的宿舍号".equals(fields)) {
				if (dormitoryService.getDormitoryByNo(Integer.parseInt(content)) == null) {
					map.put(REQUEST_ERROR, "未找到宿舍信息，请确认输入完整的宿舍号！");
					return map;
				}
				Integer dormitoryId = dormitoryService.getDormitoryByNo(Integer.parseInt(content)).getDormitoryId();
				if (dormitoryId == null) {
					map.put(REQUEST_ERROR, "未找到宿舍信息！");
					return map;
				}
				user.setDormitoryId(dormitoryId);
			}
		}
		user.setUserStatus(userStatus);
		System.out.println(user);

		// 分页模糊查询用户信息
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		IPage<User> iPage = userService.listUsersPage(new Page<User>(nowPage, pageSize), user);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		List<User> userList = iPage.getRecords();
		if (userList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到用户信息！");
			return map;
		}
		System.out.println(userList);

		// 根据用户绑定的宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(userList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);

		// 遍历用户信息并填入表格
		String title = "用户列表";
		String[] rowsName = new String[] { "序号", "用户ID", "用户名", "真实姓名", "联系电话", "绑定的宿舍号", "状态" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		for (int i = 0; i < userList.size(); i++) {
			User user2 = userList.get(i);
			Object[] objs = new Object[rowsName.length];
			objs[0] = i;
			objs[1] = user2.getUserId();
			objs[2] = user2.getUserName();
			objs[3] = user2.getUserRealName();
			objs[4] = user2.getUserTel();
			objs[5] = dormitoryNoList.get(i);
			if (user2.getUserStatus() == 1) {
				objs[6] = "启用";
			} else if (user2.getUserStatus() == 0) {
				objs[6] = "停用";
			}
			for (int j = 0; j < objs.length; j++) {
				if (objs[j] == null || objs[j].equals("")) {
					objs[j] = " ";
				}
			}
			dataList.add(objs);
			System.out.println(objs[0] + "~" + objs[1] + "~" + objs[2] + "~" + objs[3] + "~" + objs[4] + "~" + objs[5]
					+ "~" + objs[6]);
		}
		System.out.println(dataList.size() + "行记录已写入表格！");
		if (dataList.size() == 0) {
			map.put(REQUEST_ERROR, "表格数据写入失败！");
			return map;
		}

		// 导出表格
		String fileName = ExportExcel.export(title, rowsName, dataList, response);
		if (fileName == null) {
			map.put(REQUEST_ERROR, "导出表格失败！");
			return map;
		}

		System.out.println("导出用户信息End...");
		return null;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@GetMapping("/findUserByUserName")
	public int findUserByUserName(String userName) {
		System.out.println("根据用户名查询用户信息Begin...");
		System.out.println(userName);

		User user = userService.getUserByUserName(userName);
		if (user == null) {
			return 0;
		}
		System.out.println(user);

		System.out.println("根据用户名查询用户信息End...");
		return 1;
	}

	/**
	 * 上传Excel到服务器
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/admin/fileUpload")
	public static String fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 允许上传的文件类型
		String fileType = "xls,xlsx";
		// 允许上传的文件最大大小(100M,单位为byte)
		int maxSize = 1024 * 1024 * 100;
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 文件要保存的路径
		@SuppressWarnings("deprecation")
		String savePath = request.getRealPath("/") + "excelFiles/";
		response.setContentType("text/html; charset=UTF-8");
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		if (!uploadDir.canWrite()) {
			return "上传目录没有写权限！";
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置缓冲区大小，这里是1M
		factory.setSizeThreshold(1024 * 1024);
		// 设置缓冲区目录
		factory.setRepository(uploadDir);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> it = items.iterator();
		FileItem item = null;
		String fileName = "";
		String name = "";
		String extName = "";
		String newFileName = "";
		while (it.hasNext()) {
			item = (FileItem) it.next();
			fileName = item.getName();
			if (null == fileName || "".equals(fileName)) {
				continue;
			}
			// 判断文件大小是否超限
			if (item.getSize() > maxSize) {
				item.delete();
				JOptionPane.showMessageDialog(null, "文件大小超过限制！应小于" + maxSize / 1024 / 1024 + "M");
				return "文件大小超过限制！应小于" + maxSize;
			}
			// 获取文件名称
			name = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.lastIndexOf("."));
			// 获取文件后缀名
			extName = fileName.substring(fileName.indexOf(".") + 1).toLowerCase().trim();
			// 判断是否为允许上传的文件类型
			if (!Arrays.<String>asList(fileType.split(",")).contains(extName)) {
				item.delete();
				return "文件类型不正确，必须为" + fileType + "的文件！";
			}
			newFileName = name + "." + extName;
			File uploadedFile = new File(savePath, newFileName);
			item.write(uploadedFile);
			// 在这里添加读取Excel的方法来根据路径读取Excel内容
			uploadedFile.delete();
		}
		return "success";
	}

	// 读取Excel，获取Excel的文本和数字格式的内容，可根据需要修改
	@SuppressWarnings("resource")
	public static List<String> readExcel(String filePath) {
		List<String> list = new ArrayList<String>();
		// 判断是否是excel2007格式
		boolean isE2007 = false;
		if (filePath.endsWith("xlsx"))
			isE2007 = true;
		try {
			// 建立输入流
			InputStream input = new FileInputStream(filePath);
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			// 获得第一个表单
			Sheet sheet = wb.getSheetAt(0);
			// 获得第一个表单的迭代器
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				// 获得行数据
				Row row = rows.next();
				// 获得第一行的迭代器
				Iterator<Cell> cells = row.cellIterator();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					// 根据cell中的类型来输出数据
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						list.add(String.valueOf(new BigDecimal(cell.getNumericCellValue())));
						break;
					case HSSFCell.CELL_TYPE_STRING:
						list.add(cell.getStringCellValue());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					}
				}
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("解析的数据：" + list);
		return list;
	}

//	/**
//	 * 用户注册
//	 * 
//	 * @param user
//	 * @param confirmPass
//	 * @return
//	 */
//	@PostMapping("/user/register")
//	public Map<Object, Object> register(User user, String confirmPass) {
//		System.out.println("用户注册Begin...");
//		System.out.println(user + "~~~" + confirmPass);
//
//		Map<Object, Object> map = new HashMap<Object, Object>();
//
//		// 用户注册输入信息验证
//		if (!user.getUserName().trim().matches(nameRegExp)) {
//			System.out.println("用户名由3-10位字符组成!");
//			map.put(REQUEST_ERROR, "用户名由3-10位字符组成!");
//			return map;
//		}
//		if (!user.getUserPassword().trim().matches(passRegExp)) {
//			System.out.println("密码由3-10位字母数字下划线组成！");
//			map.put(REQUEST_ERROR, "密码由3-10位字母数字下划线组成！");
//			return map;
//		}
//		if (!confirmPass.trim().equals(user.getUserPassword().trim())) {
//			System.out.println("两次输入的密码不一致！");
//			map.put(REQUEST_ERROR, "两次输入的密码不一致！");
//			return map;
//		}
//
//		// 用户数据库信息验证
//		if (userService.getUserByUserName(user.getUserName()) != null) {
//			System.out.println("用户名已被注册！");
//			map.put(REQUEST_ERROR, "用户名已被注册！");
//			return map;
//		}
//
//		// 将用户注册信息写入数据库
//		User user1 = new User();
//		user1.setUserName(user.getUserName()).setUserPassword(user.getUserPassword()).setUserStatus(1);
//		int num = userService.insertUser(user1);
//		System.out.println("新增" + num + "个用户成功！");
//
//		System.out.println("用户注册End...\n");
//		return null;
//	}

}
