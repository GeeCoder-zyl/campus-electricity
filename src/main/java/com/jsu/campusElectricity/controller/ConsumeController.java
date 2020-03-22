/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Consume;
import com.jsu.campusElectricity.pojo.Dormitory;
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
 * @Description: 消费记录控制器
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

	/**
	 * 消费记录生成
	 * 
	 * @param date
	 * @param dormitoryNo
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("admin-consumeRecordCreate")
	public int consumeRecordCreate(String date, String dormitoryNo) throws ParseException {
		System.out.println("消费记录生成Begin...");
		System.out.println(date + "~" + dormitoryNo);

		// 获取宿舍ID
		Dormitory dormitory = dormitoryService.getDormitoryByNo(Integer.parseInt(dormitoryNo));
		int dormitoryId = dormitory.getDormitoryId();

		// 获取年和月
		int year = Integer.parseInt(date.substring(0, date.indexOf("-")));
		int month = Integer.parseInt(date.substring(date.indexOf("-") + 1));
		System.out.println(year + "~" + month);

		// 获取当前日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df2 = new DecimalFormat("0.00");
		Date date2 = new Date();
		String nowDate = df.format(date2);
		int nowMonth = Integer.parseInt(nowDate.substring(nowDate.indexOf("-") + 1, nowDate.lastIndexOf("-")));
		int nowDay = Integer.parseInt(nowDate.substring(nowDate.lastIndexOf("-") + 1));

		// 新增消费记录内部类
		class CreateConsumeRecord {
			Consume consume = new Consume();
			Random random = new Random();
			Dormitory dormitory2 = new Dormitory();
			int cNum = 0;// 新增消费记录数量
			int dNum = 0;// 修改宿舍数量
			int[] number = new int[2];
			double dormitoryBalance = dormitory.getDormitoryBalance();// 电费余额

			int[] createConsumeRecord(int day) throws ParseException {// 新增消费记录方法
				double consumeKwh = Double.parseDouble(df2.format(random.nextDouble() * 10));// 随机产生用电量
				double consumeAmount = Double.parseDouble(df2.format(consumeKwh * 0.5224));// 消费金额
				String date2 = year + "-" + month + "-" + day;// 消费日期
				if (consumeService.getConsumeByDormitoryIdAndDate(dormitoryId, df.parse(date2)) == null) {// 当数据库中不存在当前日期的消费记录时
					if (dormitoryBalance > 0) {
						dormitoryBalance = Double.parseDouble(df2.format(dormitoryBalance - consumeAmount));
						if (dormitoryBalance <= 0) {
							dormitory.setDormitoryStatus(0);
						}
						dormitory2.setDormitoryId(dormitoryId);
						dormitory2.setDormitoryBalance(dormitoryBalance);
						int num2 = dormitoryService.updateDormitory(dormitory2);
						dNum += num2;
						consume.setConsumeDate(df.parse(date2));
						consume.setConsumeKwh(consumeKwh);
						consume.setConsumeAmount(consumeAmount);
						consume.setConsumeBalance(dormitoryBalance);
						consume.setDormitoryId(dormitoryId);
						System.out.println(consume);
						int num = consumeService.insertConsume(consume);
						cNum += num;
						number[0] = cNum;
						number[1] = dNum;
						return number;
					}
				}
				return number;
			}
		}

		CreateConsumeRecord ccr = new CreateConsumeRecord();

		int[] number = new int[2];
		if (month == nowMonth) {// 当月份是当前月时
			for (int i = 1; i <= nowDay; i++) {
				number = ccr.createConsumeRecord(i);
			}
		} else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {// 当月份是1、3、5、7、8、10、12时
			for (int i = 1; i <= 31; i++) {
				number = ccr.createConsumeRecord(i);
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {// 当月份是4、6、9、11时
			for (int i = 1; i <= 30; i++) {
				number = ccr.createConsumeRecord(i);
			}
		} else if (month == 2) {// 当月份是2时
			GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
			if (cal.isLeapYear(year)) {
				for (int i = 1; i <= 29; i++) {
					number = ccr.createConsumeRecord(i);
				}
			} else {
				for (int i = 1; i <= 28; i++) {
					number = ccr.createConsumeRecord(i);
				}
			}
		}
		System.out.println(number[0] + "条消费记录新增成功！");
		System.out.println(number[1] + "条宿舍信息修改成功！");

		System.out.println("消费记录生成End...");
		return number[0] + number[1];
	}
}
