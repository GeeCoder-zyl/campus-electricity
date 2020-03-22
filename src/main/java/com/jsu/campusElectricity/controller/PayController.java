/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
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
import com.jsu.campusElectricity.utils.AlipayConfig;
import com.jsu.campusElectricity.utils.ExportExcel;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: PayController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月18日 下午2:23:15
 * @Description: 充值记录控制器
 */
@RestController
public class PayController implements FinalConstant {
	@Autowired
	UserService userService;
	@Autowired
	PayService payService;
	@Autowired
	AdminService adminService;
	@Autowired
	DormitoryService dormitoryService;

	long nowPage = 1;// 当前页
	long pageSize = 5;// 每页条数
	long totalPage = 0;// 总页数
	long total = 0;// 总条数

	/**
	 * 根据宿舍ID分页查询充值记录
	 * 
	 * @param session
	 * @param nowPage
	 * @return
	 */
	@GetMapping("/user-findPaysPageByDormitoryId")
	public Map<Object, Object> findPaysPageByDormitoryId(HttpSession session, long nowPage, long pageSize) {
		System.out.println("根据宿舍ID分页查询充值记录Begin...");
		System.out.println(nowPage);

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

		// 从session中获取已登录用户的ID
		int userId = (int) session.getAttribute(SESSION_USER_ID);

		// 根据用户ID查询用户信息
		User user = userService.getUserById(userId);
		if (user.getDormitoryId() == null) {
			map.put(REQUEST_ERROR, "未绑定宿舍！");
			return map;
		}

		// 根据宿舍ID分页查询充值记录
		IPage<Pay> iPage = payService.listPaysPageByDormitoryId(new Page<Pay>(nowPage, pageSize),
				user.getDormitoryId());
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		map.put("nowPage", nowPage);
		map.put("pageSize", pageSize);
		map.put("totalPage", totalPage);
		map.put("total", total);
		List<Pay> payList = iPage.getRecords();
		if (payList == null || payList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到充值记录！");
			return map;
		}
		System.out.println(payList);
		map.put("payList", payList);

		// 根据充值方式查询充值人名称
		List<String> payNameList = new ArrayList<String>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getPayManner() == 0) {
				Admin admin = adminService.getAdminById(payList.get(i).getPayPid());
				if (admin == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, admin.getAdminName());
			} else if (payList.get(i).getPayManner() == 1) {
				User user2 = userService.getUserById(payList.get(i).getPayPid());
				if (user2 == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, user2.getUserName());
			}
		}
		System.out.println("payNameList：" + payNameList);
		map.put("payNameList", payNameList);

		System.out.println("根据宿舍ID分页查询充值记录End...");
		return map;
	}

	/**
	 * 用户充值电费（支付宝沙箱支付）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/user-payElectricity")
	public String pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("用户充值电费（支付宝沙箱支付）Begin...");

		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);
		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = request.getParameter("orderId");
		// 付款金额，必填
		String total_amount = request.getParameter("payAmount");
		// 订单名称，必填
		String sName = request.getParameter("orderName");
		String subject = sName;
		// 商品描述，可空
		String body = "";

		System.out.println("~~~~~~~~~~~~~~" + out_trade_no + "~" + total_amount + "~" + subject);

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
				+ "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		// 请求
		String result;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
			response.setContentType("text/html;charset=" + AlipayConfig.charset);
			response.getWriter().write(result);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (AlipayApiException e) {
			e.printStackTrace();
			response.getWriter().write("捕获异常出错");
			response.getWriter().flush();
			response.getWriter().close();
		}

		System.out.println("用户充值电费（支付宝沙箱支付）End...");
		return null;
	}

	/**
	 * 充值成功异步处理
	 * 
	 * @param session
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException
	 */
	@RequestMapping("/user-successPay")
	public String successPay(HttpSession session, HttpServletRequest request)
			throws UnsupportedEncodingException, AlipayApiException {
		System.out.println("充值成功异步处理Begin...");

		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
				AlipayConfig.sign_type); // 调用SDK验证签名

		// ——请在这里编写您的程序（以下代码仅作参考）——
		if (signVerified) {
			// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

			System.out.println(
					"trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);

			System.out.println("支付成功！");

			// 从session中获取已登录用户的ID
			int userId = (int) session.getAttribute(SESSION_USER_ID);

			// 根据用户ID查询用户信息
			User user = userService.getUserById(userId);
			if (user == null) {
				return "error";
			}
			System.out.println(user);

			// 根据宿舍ID查询宿舍信息
			if (user.getDormitoryId() == null) {
				return "error";
			}
			int dormitoryId = user.getDormitoryId();
			Dormitory dormitory = new Dormitory();
			dormitory = dormitoryService.getDormitoryById(dormitoryId);
			if (dormitory == null) {
				return "error";
			}

			// 修改宿舍信息
			if (dormitory.getDormitoryStatus() == 0
					&& dormitory.getDormitoryBalance() + Double.parseDouble(total_amount) > 0) {
				dormitory.setDormitoryStatus(1);
			}
			dormitory.setDormitoryId(dormitoryId);
			dormitory.setDormitoryBalance(dormitory.getDormitoryBalance() + Double.parseDouble(total_amount));
			System.out.println(dormitory);
			int num = dormitoryService.updateDormitory(dormitory);
			System.out.println(num + "条宿舍信息修改成功！");

			// 新增充值信息
			Pay pay = new Pay();
			pay.setPayDate(new Date());
			pay.setPayAmount(Double.parseDouble(total_amount));
			pay.setPayManner(1);
			pay.setPayPid(userId);
			pay.setDormitoryId(dormitoryId);
			System.out.println(pay);
			int num2 = payService.insertPay(pay);
			System.out.println(num2 + "条充值信息新增成功！");

			System.out.println("充值成功异步处理End...");
			return "success";
		} else {
			System.out.println("验签失败！");
			return "error";
		}

		// ——请在这里编写您的程序（以上代码仅作参考）——
		// return ;
	}

	/**
	 * 管理员充值电费
	 * 
	 * @param session
	 * @param dormitoryNo
	 * @param payAmount
	 * @return
	 * @return
	 */
	@PostMapping("/admin-payElectricity")
	public Map<Object, Object> payElectricity(HttpSession session, int dormitoryNo, double payAmount) {
		System.out.println("管理员充值电费Begin...");
		System.out.println(dormitoryNo + "~~~" + payAmount);

		Map<Object, Object> map = new HashMap<Object, Object>();

		if (payAmount <= 0 || payAmount > 1000) {
			System.out.println("充值金额无效！");
			map.put(REQUEST_ERROR, "充值金额无效！");
			return map;
		}
		Dormitory dormitory = new Dormitory();
		dormitory = dormitoryService.getDormitoryByNo(dormitoryNo);
		if (dormitory == null) {
			map.put(REQUEST_ERROR, "未找到宿舍信息！");
			return map;
		}

		// 修改宿舍信息
		if (dormitory.getDormitoryStatus() == 0 && dormitory.getDormitoryBalance() + payAmount > 0) {
			dormitory.setDormitoryStatus(1);
		}
		dormitory.setDormitoryId(dormitory.getDormitoryId());
		dormitory.setDormitoryBalance(dormitory.getDormitoryBalance() + payAmount);
		System.out.println(dormitory);

		// 新增充值信息
		Pay pay = new Pay();
		pay.setPayDate(new Date());
		pay.setPayAmount(payAmount);
		pay.setPayManner(0);
		pay.setPayPid((Integer) session.getAttribute(SESSION_ADMIN_ID));
		pay.setDormitoryId(dormitory.getDormitoryId());
		System.out.println(pay);

		int num = dormitoryService.updateDormitory(dormitory) + payService.insertPay(pay);
		if (num != 2) {
			map.put(REQUEST_ERROR, "数据库信息写入失败！");
			return map;
		}
		map.put("num", num);
		System.out.println("1条宿舍信息修改成功！");
		System.out.println("1条充值信息新增成功！");

		System.out.println("管理员充值电费End...");
		return map;
	}

	/**
	 * 管理员根据日期范围和宿舍号分页查询充值记录
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dormitoryNo
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin-findPaysPage")
	public Map<Object, Object> findPaysPage(String startTime, String endTime, String dormitoryNo, long nowPage,
			long pageSize) throws ParseException {
		System.out.println("管理员根据日期范围和宿舍号分页查询充值记录Begin...");
		System.out.println(startTime + "~" + endTime + "~" + dormitoryNo + "~" + nowPage + "~" + pageSize);

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

		// 根据宿舍号查询宿舍ID
		Integer dormitoryId = null;
		if (dormitoryNo != null && !dormitoryNo.equals("")) {
			if (dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)) == null) {
				map.put(REQUEST_ERROR, "请输入准确的宿舍号！");
				return map;
			}
			dormitoryId = dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)).getDormitoryId();
		}

		// 格式化日期
		Date sTime = null;// 开始日期
		Date eTime = null;// 结束日期
		if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			sTime = df.parse(startTime);
			eTime = df.parse(endTime);
			System.out.println("date：" + sTime + "~~~" + eTime);
		}

		// 根据日期范围和宿舍ID分页查询充值记录
		IPage<Pay> iPage = payService.listPaysPage(new Page<Pay>(nowPage, pageSize), sTime, eTime, dormitoryId);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		map.put("nowPage", nowPage);
		map.put("pageSize", pageSize);
		map.put("totalPage", totalPage);
		map.put("total", total);
		List<Pay> payList = iPage.getRecords();
		if (payList == null || payList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到充值记录！");
			return map;
		}
		System.out.println(payList);
		map.put("payList", payList);

		// 根据充值方式查询充值人名称
		List<String> payNameList = new ArrayList<String>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getPayManner() == 0) {
				Admin admin = adminService.getAdminById(payList.get(i).getPayPid());
				if (admin == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, admin.getAdminName());
			} else if (payList.get(i).getPayManner() == 1) {
				User user2 = userService.getUserById(payList.get(i).getPayPid());
				if (user2 == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, user2.getUserName());
			}
		}
		System.out.println("payNameList：" + payNameList);
		map.put("payNameList", payNameList);

		// 根据宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(payList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);
		map.put("dormitoryNoList", dormitoryNoList);

		System.out.println("管理员根据日期范围和宿舍号分页查询充值记录End...");
		return map;
	}

	/**
	 * 管理员导出宿舍的充值记录
	 * 
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param dormitoryNo
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/admin-exportPaysToExcel")
	public Map<Object, Object> exportPaysToExcel(HttpServletResponse response, String startTime, String endTime,
			String dormitoryNo, long nowPage, long pageSize) throws Exception {
		System.out.println("管理员导出宿舍的充值记录Begin...");
		System.out.println(startTime + "~" + endTime + "~" + dormitoryNo + "~" + nowPage + "~" + pageSize);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 根据宿舍号查询宿舍ID
		Integer dormitoryId = null;
		if (dormitoryNo != null && !dormitoryNo.equals("")) {
			if (dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)) == null) {
				map.put(REQUEST_ERROR, "请输入准确的宿舍号！");
				return map;
			}
			dormitoryId = dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)).getDormitoryId();
		}

		// 格式化日期
		Date sTime = null;// 开始日期
		Date eTime = null;// 结束日期
		if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			sTime = df.parse(startTime);
			eTime = df.parse(endTime);
			System.out.println("date：" + sTime + "~~~" + eTime);
		}

		// 根据日期范围和宿舍ID分页查询充值记录
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		IPage<Pay> iPage = payService.listPaysPage(new Page<Pay>(nowPage, pageSize), sTime, eTime, dormitoryId);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		List<Pay> payList = iPage.getRecords();
		if (payList == null || payList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到充值记录！");
			return map;
		}
		System.out.println(payList);

		// 根据充值方式查询充值人名称
		List<String> payNameList = new ArrayList<String>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getPayManner() == 0) {
				Admin admin = adminService.getAdminById(payList.get(i).getPayPid());
				if (admin == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, admin.getAdminName());
			} else if (payList.get(i).getPayManner() == 1) {
				User user2 = userService.getUserById(payList.get(i).getPayPid());
				if (user2 == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, user2.getUserName());
			}
		}
		System.out.println("payNameList：" + payNameList);

		// 根据宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(payList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);

		// 遍历充值记录并写入表格
		String title = "充值记录";
		String[] rowsName = new String[] { "序号", "ID", "宿舍号", "日期时间", "金额", "充值方式", "充值人" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (int i = 0; i < payList.size(); i++) {
			Pay pay = payList.get(i);
			Object[] objs = new Object[rowsName.length];
			objs[0] = i;
			objs[1] = pay.getPayId();
			objs[2] = dormitoryNoList.get(i);
			objs[3] = df.format(pay.getPayDate());
			objs[4] = "￥" + pay.getPayAmount();
			if (pay.getPayManner() == 0) {
				objs[5] = "管理员充值";
			} else if (pay.getPayManner() == 1) {
				objs[5] = "用户充值";
			}
			objs[6] = payNameList.get(i);
			System.out.println(objs[0] + "~" + objs[1] + "~" + objs[2] + "~" + objs[3] + "~" + objs[4] + "~" + objs[5]
					+ "~" + objs[6]);
			dataList.add(objs);
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

		System.out.println("管理员导出宿舍的充值记录End...");
		return null;
	}

	/**
	 * 管理员根据日期范围和管理员名称分页查询充值记录
	 * 
	 * @param startTime
	 * @param endTime
	 * @param adminName
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin-findAdminPaysPage")
	public Map<Object, Object> findAdminPaysPage(String startTime, String endTime, String adminName, long nowPage,
			long pageSize) throws ParseException {
		System.out.println("管理员根据日期范围和管理员名称分页查询充值记录Begin...");
		System.out.println(startTime + "~" + endTime + "~" + adminName + "~" + nowPage + "~" + pageSize);

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

		// 根据管理员名称查询管理员ID
		Integer adminId = null;
		if (adminName != null && !adminName.equals("")) {
			if (adminService.getAdminByName(adminName) == null) {
				map.put(REQUEST_ERROR, "请输入准确的管理员名称！");
				return map;
			}
			adminId = adminService.getAdminByName(adminName).getAdminId();
		}

		// 格式化日期
		Date sTime = null;// 开始日期
		Date eTime = null;// 结束日期
		if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			sTime = df.parse(startTime);
			eTime = df.parse(endTime);
			System.out.println("date：" + sTime + "~~~" + eTime);
		}

		// 根据日期范围和管理员名称分页查询充值记录
		IPage<Pay> iPage = payService.listAdminPaysPage(new Page<Pay>(nowPage, pageSize), sTime, eTime, adminId);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		map.put("nowPage", nowPage);
		map.put("pageSize", pageSize);
		map.put("totalPage", totalPage);
		map.put("total", total);
		List<Pay> payList = iPage.getRecords();
		if (payList == null || payList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到充值记录！");
			return map;
		}
		System.out.println(payList);
		map.put("payList", payList);

		// 查询充值人名称
		List<String> payNameList = new ArrayList<String>();
		for (int i = 0; i < payList.size(); i++) {
			if (adminName == null || adminName.equals("")) {
				Admin admin = adminService.getAdminById(payList.get(i).getPayPid());
				if (admin == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, admin.getAdminName());
			} else {
				payNameList.add(i, adminName);
			}
		}
		System.out.println("payNameList：" + payNameList);
		map.put("payNameList", payNameList);

		// 根据宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(payList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);
		map.put("dormitoryNoList", dormitoryNoList);

		// 当选择了日期范围时才计算总金额
		double totalAmount = 0;
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			totalAmount = payService.getAdminPaysSum(sTime, eTime, adminId);
		}
		System.out.println(totalAmount);
		map.put("totalAmount", totalAmount);

		System.out.println("管理员根据日期范围和管理员名称分页查询充值记录End...");
		return map;
	}

	/**
	 * 管理员导出管理员的充值记录
	 * 
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param adminName
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/admin-exportAdminPaysToExcel")
	public Map<Object, Object> exportAdminPaysToExcel(HttpServletResponse response, String startTime, String endTime,
			String adminName, long nowPage, long pageSize) throws Exception {
		System.out.println("管理员导出管理员的充值记录Begin...");
		System.out.println(startTime + "~" + endTime + "~" + adminName + "~" + nowPage + "~" + pageSize);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 根据管理员名称查询管理员ID
		Integer adminId = null;
		if (adminName != null && !adminName.equals("")) {
			if (adminService.getAdminByName(adminName) == null) {
				map.put(REQUEST_ERROR, "请输入准确的管理员名称！");
				return map;
			}
			adminId = adminService.getAdminByName(adminName).getAdminId();
		}

		// 格式化日期
		Date sTime = null;// 开始日期
		Date eTime = null;// 结束日期
		if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			sTime = df.parse(startTime);
			eTime = df.parse(endTime);
			System.out.println("date：" + sTime + "~~~" + eTime);
		}

		// 根据日期范围和管理员名称分页查询充值记录
		IPage<Pay> iPage = payService.listAdminPaysPage(new Page<Pay>(nowPage, pageSize), sTime, eTime, adminId);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		List<Pay> payList = iPage.getRecords();
		if (payList == null || payList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到充值记录！");
			return map;
		}
		System.out.println(payList);

		// 查询充值人名称
		List<String> payNameList = new ArrayList<String>();
		for (int i = 0; i < payList.size(); i++) {
			if (adminName == null || adminName.equals("")) {
				Admin admin = adminService.getAdminById(payList.get(i).getPayPid());
				if (admin == null) {
					map.put(REQUEST_ERROR, "未找到充值人名称！");
					return map;
				}
				payNameList.add(i, admin.getAdminName());
			} else {
				payNameList.add(i, adminName);
			}
		}
		System.out.println("payNameList：" + payNameList);

		// 根据宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < payList.size(); i++) {
			if (payList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(payList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);

		// 当选择了日期范围时才计算总金额
		double totalAmount = 0;
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			totalAmount = payService.getAdminPaysSum(sTime, eTime, adminId);
		}
		System.out.println(totalAmount);

		// 遍历充值记录并写入表格
		String title = null;// 表格标题
		if (totalAmount == 0) {
			title = "管理员充值记录";
		} else {
			title = "管理员" + adminName + "在" + startTime + "——" + endTime + "的充值记录";
		}
		String[] rowsName = new String[] { "序号", "ID", "宿舍号", "日期时间", "金额", "充值方式", "充值人" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (int i = 0; i < payList.size(); i++) {
			Pay pay = payList.get(i);
			Object[] objs = new Object[rowsName.length];
			objs[0] = i;
			objs[1] = pay.getPayId();
			objs[2] = dormitoryNoList.get(i);
			objs[3] = df.format(pay.getPayDate());
			objs[4] = "￥" + pay.getPayAmount();
			if (pay.getPayManner() == 0) {
				objs[5] = "管理员充值";
			} else if (pay.getPayManner() == 1) {
				objs[5] = "用户充值";
			}
			objs[6] = payNameList.get(i);
			System.out.println(objs[0] + "~" + objs[1] + "~" + objs[2] + "~" + objs[3] + "~" + objs[4] + "~" + objs[5]
					+ "~" + objs[6]);
			dataList.add(objs);
		}
		if (totalAmount > 0) {
			Object[] obj = new Object[7];
			obj[0] = " ";
			obj[1] = " ";
			obj[2] = " ";
			obj[3] = "总计：";
			obj[4] = "￥" + totalAmount;
			obj[5] = " ";
			obj[6] = " ";
			System.out.println(obj);
			dataList.add(obj);
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

		System.out.println("管理员导出管理员的充值记录End...");
		return null;
	}
}
