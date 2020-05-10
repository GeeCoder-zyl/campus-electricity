/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsu.campusElectricity.pojo.Consume;
import com.jsu.campusElectricity.pojo.Dormitory;
import com.jsu.campusElectricity.pojo.User;
import com.jsu.campusElectricity.service.ConsumeService;
import com.jsu.campusElectricity.service.DormitoryService;
import com.jsu.campusElectricity.service.UserService;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: StatisticsController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2020年3月14日 下午10:57:25
 * @Description: 用电量统计控制器
 */
@RestController
public class StatisticsController implements FinalConstant {
	@Autowired
	ConsumeService consumeService;
	@Autowired
	DormitoryService dormitoryService;
	@Autowired
	UserService userService;

	/**
	 * 所有宿舍某日用电量统计
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin/allDormitoryOneDayStatistics")
	public Map<Object, Object> allDormitoryOneDayStatistics(String date) throws ParseException {
		System.out.println("所有宿舍某日用电量统计Begin...");
		System.out.println(date);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 查询所有宿舍的宿舍号
		List<Dormitory> dormitoryList = dormitoryService.listDormitorys();
		List<Integer> dormitoryIdList = new ArrayList<>();
		List<Integer> dormitoryNoList = new ArrayList<>();
		if (dormitoryList == null || dormitoryList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到宿舍！");
			return map;
		}
		for (int i = 0; i < dormitoryList.size(); i++) {
			dormitoryNoList.add(dormitoryList.get(i).getDormitoryNo());
			dormitoryIdList.add(dormitoryList.get(i).getDormitoryId());
		}
		System.out.println(dormitoryNoList);
		map.put("dormitoryNoList", dormitoryNoList);

		// 格式化日期
		Date consumeDate = null;// 消费日期
		if (date != null && !date.equals("")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			consumeDate = df.parse(date);
		}

		// 根据宿舍ID和日期查询用电量
		List<Double> consumeKwhList = new ArrayList<>();
		for (int i = 0; i < dormitoryIdList.size(); i++) {
			consumeKwhList.add(consumeService.getKwhByDormitoryIdAndDate(dormitoryIdList.get(i), consumeDate));
		}
		System.out.println(consumeKwhList);
		map.put("consumeKwhList", consumeKwhList);

		System.out.println("所有宿舍某日用电量统计End...");
		return map;
	}

	/**
	 * 所有宿舍某月用电量统计
	 * 
	 * @param consumeYear
	 * @param consumeMonth
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin/allDormitoryOneMonthStatistics")
	public Map<Object, Object> allDormitoryOneMonthStatistics(String consumeYear, String consumeMonth)
			throws ParseException {
		System.out.println("所有宿舍某月用电量统计Begin...");
		System.out.println(consumeYear + "~" + consumeMonth);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 查询所有宿舍的宿舍号
		List<Dormitory> dormitoryList = dormitoryService.listDormitorys();
		List<Integer> dormitoryIdList = new ArrayList<>();
		List<Integer> dormitoryNoList = new ArrayList<>();
		if (dormitoryList == null || dormitoryList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到宿舍！");
			return map;
		}
		for (int i = 0; i < dormitoryList.size(); i++) {
			dormitoryNoList.add(dormitoryList.get(i).getDormitoryNo());
			dormitoryIdList.add(dormitoryList.get(i).getDormitoryId());
		}
		System.out.println(dormitoryIdList);
		System.out.println(dormitoryNoList);
		map.put("dormitoryNoList", dormitoryNoList);

		// 根据宿舍ID和年月查询用电量
		List<Double> consumeKwhList = new ArrayList<>();
		for (int i = 0; i < dormitoryIdList.size(); i++) {
			consumeKwhList.add(consumeService.getKwhsByDormitoryIdAndMonth(dormitoryIdList.get(i),
					Integer.parseInt(consumeYear), Integer.parseInt(consumeMonth)));
		}
		if (consumeKwhList == null || consumeKwhList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到用电量记录！");
			return map;
		}
		System.out.println(consumeKwhList);
		map.put("consumeKwhList", consumeKwhList);

		System.out.println("所有宿舍某月用电量统计End...");
		return map;
	}

	/**
	 * 某宿舍某月内用电量统计
	 * 
	 * @param dormitoryNo
	 * @param consumeYear
	 * @param consumeMonth
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin/aDormitoryInMonthStatistics")
	public Map<Object, Object> aDormitoryInMonthStatistics(String dormitoryNo, String consumeYear, String consumeMonth)
			throws ParseException {
		System.out.println("某宿舍某月内用电量统计Begin...");
		System.out.println(dormitoryNo + "~" + consumeYear + "~" + consumeMonth);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 查询宿舍号
		int dormitoryId = dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)).getDormitoryId();

		// 根据宿舍ID和年月查询消费记录
		List<Consume> consumeList = new ArrayList<>();
		consumeList = consumeService.listConsumesByDormitoryIdAndMonth(dormitoryId, Integer.parseInt(consumeYear),
				Integer.parseInt(consumeMonth));
		if (consumeList == null || consumeList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到消费记录！");
			return map;
		}
		System.out.println(consumeList);

		// 获取用电量和日期
		List<String> consumeDateList = new ArrayList<>();
		List<Double> consumeKwhList = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日");
		for (int i = 0; i < consumeList.size(); i++) {
			consumeDateList.add(df.format(consumeList.get(i).getConsumeDate()));
			consumeKwhList.add(consumeList.get(i).getConsumeKwh());
		}
		System.out.println(consumeDateList);
		System.out.println(consumeKwhList);
		map.put("consumeDateList", consumeDateList);
		map.put("consumeKwhList", consumeKwhList);

		System.out.println("某宿舍某月内用电量统计End...");
		return map;
	}

	/**
	 * 某宿舍某年内用电量统计
	 * 
	 * @param dormitoryNo
	 * @param consumeYear
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/admin/aDormitoryInYearStatistics")
	public Map<Object, Object> aDormitoryInYearStatistics(String dormitoryNo, String consumeYear)
			throws ParseException {
		System.out.println("某宿舍某年内用电量统计Begin...");
		System.out.println(dormitoryNo + "~" + consumeYear);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 查询宿舍号
		int dormitoryId = dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo)).getDormitoryId();

		// 根据宿舍ID和年份查询每月用电量总和
		List<Double> consumeKwhList = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			consumeKwhList
					.add(consumeService.getKwhsByDormitoryIdAndMonth(dormitoryId, Integer.parseInt(consumeYear), i));
		}
		if (consumeKwhList == null || consumeKwhList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到用电量记录！");
			return map;
		}
		System.out.println(consumeKwhList);
		map.put("consumeKwhList", consumeKwhList);

		System.out.println("某宿舍某年内用电量统计End...");
		return map;
	}

	/**
	 * 用户绑定宿舍近30天用电量统计
	 * 
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/user/dormitoryLatest30DaysStatistics")
	public Map<Object, Object> dormitoryLatest30DaysStatistics(HttpSession session) throws ParseException {
		System.out.println("用户绑定宿舍近30天用电量统计Begin...");

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 从session中获取已登录用户的ID
		int userId = (int) session.getAttribute(SESSION_USER_ID);

		// 根据用户ID查询用户信息
		User user = userService.getUserById(userId);
		if (user.getDormitoryId() == null) {
			map.put(REQUEST_ERROR, "未绑定宿舍！");
			return map;
		}

		// 根据宿舍ID查询宿舍号
		int dormitoryId = user.getDormitoryId();
		int dormitoryNo = dormitoryService.getDormitoryById(dormitoryId).getDormitoryNo();

		// 根据宿舍ID查询近30天消费记录
		List<Consume> consumeList = new ArrayList<>();
		consumeList = consumeService.listConsumesByDormitoryIdLatest30Days(dormitoryId);
		if (consumeList == null || consumeList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到消费记录！");
			return map;
		}
		Collections.reverse(consumeList);// 反转List
		System.out.println(consumeList);

		// 获取用电量和日期
		List<String> consumeDateList = new ArrayList<>();
		List<Double> consumeKwhList = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日");
		for (int i = 0; i < consumeList.size(); i++) {
			consumeDateList.add(df.format(consumeList.get(i).getConsumeDate()));
			consumeKwhList.add(consumeList.get(i).getConsumeKwh());
		}
		System.out.println(consumeDateList);
		System.out.println(consumeKwhList);
		map.put("consumeDateList", consumeDateList);
		map.put("consumeKwhList", consumeKwhList);
		map.put("dormitoryNo", dormitoryNo);

		System.out.println("用户绑定宿舍近30天用电量统计End...");
		return map;
	}
}
