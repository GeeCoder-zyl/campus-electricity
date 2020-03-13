/**
 * 
 */
package com.jsu.campusElectricity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Dormitory;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.service.DormitoryService;
import com.jsu.campusElectricity.utils.ExportExcel;
import com.jsu.campusElectricity.utils.FinalConstant;

/**
 * @ClassName: DormitoryContrller.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 下午5:44:08
 * @Description: 宿舍控制器
 */
@RestController
public class DormitoryContrller implements FinalConstant {
	@Autowired
	DormitoryService dormitoryService;

	long nowPage = 1;// 当前页
	long pageSize = 5;// 每页条数
	long totalPage = 0;// 总页数
	long total = 0;// 总条数

	/**
	 * 根据宿舍号查询宿舍ID
	 * 
	 * @param dormitoryNo
	 * @return
	 */
	@GetMapping("/findDormitoryByNo")
	public int findDormitoryByNo(int dormitoryNo) {
		System.out.println("根据宿舍号查询宿舍IDBegin...");
		System.out.println(dormitoryNo);

		// 根据宿舍号查询宿舍信息
		Dormitory dormitory = dormitoryService.getDormitoryByNo(dormitoryNo);
		if (dormitory == null) {
			return 0;
		}
		System.out.println(dormitory);

		System.out.println("根据宿舍号查询宿舍End...");
		return dormitory.getDormitoryId();
	}

	/**
	 * 根据宿舍号和送电状态分页模糊查询宿舍信息
	 * 
	 * @param dormitory
	 * @return
	 */
	@GetMapping("/admin-findDormitorysPage")
	public Map<Object, Object> findDormitorysPage(Dormitory dormitory, long nowPage, long pageSize) {
		System.out.println("分页模糊查询宿舍信息Begin...");
		System.out.println(nowPage + "~" + pageSize + "~" + dormitory);

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

		// 根据宿舍号和送电状态分页模糊查询宿舍信息
		IPage<Dormitory> iPage = dormitoryService.listDormitorysPage(new Page<Pay>(nowPage, pageSize), dormitory);
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
		List<Dormitory> dormitoryList = iPage.getRecords();
		if (dormitoryList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到宿舍信息！");
			return map;
		}
		System.out.println(dormitoryList);
		map.put("dormitoryList", dormitoryList);

		System.out.println("分页模糊查询宿舍信息End...");
		return map;
	}

	/**
	 * 导出宿舍信息
	 * 
	 * @param dormitory
	 * @param nowPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@GetMapping("admin-exportDormitorysToExcel")
	public Map<Object, Object> exportDormitorysToExcel(HttpServletResponse response, Dormitory dormitory, long nowPage,
			long pageSize) throws Exception {
		System.out.println("导出宿舍信息Begin...");
		System.out.println(nowPage + "~" + pageSize + "~" + dormitory);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 根据宿舍号和送电状态分页模糊查询宿舍信息
		if (nowPage < 1) {
			nowPage = 1;
		} else if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		IPage<Dormitory> iPage = dormitoryService.listDormitorysPage(new Page<Pay>(nowPage, pageSize), dormitory);
		List<Dormitory> dormitoryList = iPage.getRecords();
		if (dormitoryList.size() == 0) {
			map.put(REQUEST_ERROR, "未找到宿舍信息！");
			return map;
		}
		System.out.println(dormitoryList);

		// 遍历宿舍信息并写入表格
		String title = "宿舍列表";
		String[] rowsName = new String[] { "序号", "宿舍ID", "宿舍号", "电费余额", "送电状态" };
		List<Object[]> dataList = new ArrayList<Object[]>();
		for (int i = 0; i < dormitoryList.size(); i++) {
			Dormitory dormitory2 = dormitoryList.get(i);
			Object[] objs = new Object[rowsName.length];
			objs[0] = i;
			objs[1] = dormitory2.getDormitoryId();
			objs[2] = dormitory2.getDormitoryNo();
			objs[3] = "￥" + dormitory2.getDormitoryBalance();
			if (dormitory2.getDormitoryStatus() == 1) {
				objs[4] = "送电";
			} else if (dormitory2.getDormitoryStatus() == 0) {
				objs[4] = "断电";
			}
			for (int j = 0; j < objs.length; j++) {
				if (objs[j] == null) {
					objs[j] = " ";
				}
			}
			System.out.println(objs[0] + "~" + objs[1] + "~" + objs[2] + "~" + objs[3] + "~" + objs[4]);
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
		System.out.println("导出宿舍信息End...");
		return null;
	}

	/**
	 * 根据宿舍ID删除宿舍
	 * 
	 * @param dormitoryId
	 * @return
	 */
	@DeleteMapping("/admin-delDormitoryById")
	public int delDormitoryById(Integer dormitoryId) {
		System.out.println("根据宿舍ID删除宿舍Begin...");
		System.out.println(dormitoryId);

		int num = dormitoryService.deleteDormitoryById(dormitoryId);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条宿舍信息删除成功！");

		System.out.println("根据宿舍ID删除宿舍End...");
		return num;
	}

	/**
	 * 修改宿舍信息
	 * 
	 * @param dormitory
	 * @return
	 */
	@PutMapping("/admin-updateDormitory")
	public int updateDormitory(Dormitory dormitory) {
		System.out.println("修改宿舍信息Begin...");
		System.out.println(dormitory);

		int num = dormitoryService.updateDormitory(dormitory);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条宿舍信息修改成功！");

		System.out.println("修改宿舍信息End...");
		return num;
	}

	/**
	 * 添加宿舍
	 * 
	 * @param dormitory
	 * @return
	 */
	@PostMapping("/admin-addDormitory")
	public int addDormitory(Dormitory dormitory) {
		System.out.println("添加宿舍Begin...");
		System.out.println(dormitory);

		dormitory.setDormitoryStatus(0);
		int num = dormitoryService.insertDormitory(dormitory);
		if (num == 0) {
			return 0;
		}
		System.out.println(num + "条宿舍信息添加成功！");

		System.out.println("添加宿舍End...");
		return num;
	}
}
