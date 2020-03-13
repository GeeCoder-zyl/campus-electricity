/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Consume;
import com.jsu.campusElectricity.pojo.User;
import com.jsu.campusElectricity.service.ConsumeService;
import com.jsu.campusElectricity.service.DormitoryService;
import com.jsu.campusElectricity.service.UserService;
import com.jsu.campusElectricity.utils.ExportExcel;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: ConsumeController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 下午9:35:18
 * @Description: 消费控制器
 */
@RestController
public class ConsumeController implements FinalConstant {
	@Autowired
	ConsumeService consumeService;
	@Autowired
	UserService userService;
	@Autowired
	DormitoryService dormitoryService;

	long nowPage = 1;// 当前页
	long pageSize = 5;// 每页条数
	long totalPage = 0;// 总页数
	long total = 0;// 总条数

	/**
	 * 用户根据宿舍ID分页查询消费记录
	 * 
	 * @param session
	 * @param nowPage
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/user-findConsumesPageByDormitoryId")
	public Map<Object, Object> findConsumesPageByDormitoryId(HttpSession session, long nowPage, long pageSize) {
		System.out.println("用户根据宿舍ID分页查询消费记录Begin...");
		System.out.println(nowPage + "~" + pageSize);

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

		// 根据宿舍ID分页查询消费记录
		int dormitoryId = user.getDormitoryId();
		IPage<Consume> iPage = consumeService.listConsumesPageByDormitoryId(new Page<Consume>(nowPage, pageSize),
				dormitoryId);
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
		List<Consume> consumeList = iPage.getRecords();
		if (consumeList == null || consumeList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到消费记录！");
			return map;
		}
		System.out.println(consumeList);
		map.put("consumeList", consumeList);

		System.out.println("用户根据宿舍ID分页查询消费记录End...");
		return map;
	}

	/**
	 * 管理员根据日期范围和宿舍号分页查询消费记录
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dormitoryNo
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin-findConsumesPage")
	public Map<Object, Object> findConsumesPage(String startTime, String endTime, String dormitoryNo, long nowPage,
			long pageSize) throws ParseException {
		System.out.println("管理员根据日期范围和宿舍号分页查询消费记录Begin...");
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

		// 根据日期范围和宿舍号分页查询消费记录
		IPage<Consume> iPage = consumeService.listConsumesPage(new Page<Consume>(nowPage, pageSize), sTime, eTime,
				dormitoryId);
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
		List<Consume> consumeList = iPage.getRecords();
		if (consumeList == null || consumeList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到消费记录！");
			return map;
		}
		System.out.println(consumeList);
		map.put("consumeList", consumeList);

		// 根据用户绑定的宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < consumeList.size(); i++) {
			if (consumeList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(consumeList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);
		map.put("dormitoryNoList", dormitoryNoList);

		System.out.println("管理员根据日期范围和宿舍号分页查询消费记录End...");
		return map;
	}

	/**
	 * 管理员导出消费记录
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
	@GetMapping("/admin-exportConsumesToExcel")
	public Map<Object, Object> exportConsumesToExcel(HttpServletResponse response, String startTime, String endTime,
			String dormitoryNo, long nowPage, long pageSize) throws Exception {
		System.out.println("管理员导出消费记录Begin...");
		System.out.println(startTime + "~" + endTime + "~" + dormitoryNo + "~" + nowPage + "~" + pageSize);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 根据宿舍号查询宿舍ID
		Integer dormitoryId = null;
		if (dormitoryNo != null && !dormitoryNo.equals("")) {
			if (dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)) == null) {
				System.out.println("请输入准确的宿舍号！");
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

		// 根据日期范围和宿舍号分页查询消费记录
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		IPage<Consume> iPage = consumeService.listConsumesPage(new Page<Consume>(nowPage, pageSize), sTime, eTime,
				dormitoryId);
		nowPage = iPage.getCurrent();// 当前页
		totalPage = iPage.getPages();// 总页数
		long total = iPage.getTotal();// 总条数
		System.out.println("当前页：" + nowPage);
		System.out.println("总页数：" + totalPage);
		System.out.println("每页条数：" + iPage.getSize());
		System.out.println("总条数：" + total);
		List<Consume> consumeList = iPage.getRecords();
		if (consumeList == null || consumeList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到消费记录！");
			return map;
		}
		System.out.println(consumeList);

		// 根据用户绑定的宿舍ID查询宿舍号
		List<Integer> dormitoryNoList = new ArrayList<Integer>();
		for (int i = 0; i < consumeList.size(); i++) {
			if (consumeList.get(i).getDormitoryId() != null) {
				dormitoryNoList
						.add(dormitoryService.getDormitoryById(consumeList.get(i).getDormitoryId()).getDormitoryNo());
			} else {
				dormitoryNoList.add(null);
			}
		}
		System.out.println(dormitoryNoList);

		// 遍历消费记录并写入表格
		String title = "消费记录";
		String[] rowsName = new String[] { "序号", "ID", "宿舍号", "日期", "用电量", "消费金额", "余额" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < consumeList.size(); i++) {
			Consume consume = consumeList.get(i);
			Object[] objs = new Object[rowsName.length];
			objs[0] = i;
			objs[1] = consume.getConsumeId();
			objs[2] = dormitoryNoList.get(i);
			objs[3] = df.format(consume.getConsumeDate());
			objs[4] = consume.getConsumeKwh() + "kW.h";
			objs[5] = "￥" + consume.getConsumeAmount();
			objs[6] = "￥" + consume.getConsumeBalance();
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

		System.out.println("管理员导出消费记录End...");
		return null;
	}
}
