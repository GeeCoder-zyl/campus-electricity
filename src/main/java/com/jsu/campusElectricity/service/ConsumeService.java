/**
 * 
 */
package com.jsu.campusElectricity.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Consume;

/**
 * @ClassName: ComsumeService.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 下午9:27:03
 * @Description: 消费记录接口
 */
public interface ConsumeService {
	/**
	 * 根据宿舍ID查询消费记录
	 * 
	 * @return
	 */
	List<Consume> listConsumesByDormitoryId(int dormitoryId);

	/**
	 * 根据宿舍ID分页查询消费记录
	 * 
	 * @param page
	 * @param dormitoryId
	 * @return
	 */
	IPage<Consume> listConsumesPageByDormitoryId(Page<Consume> page, int dormitoryId);

	/**
	 * 根据日期范围和宿舍号分页查询消费记录
	 * 
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param dormitoryId
	 * @return
	 */
	IPage<Consume> listConsumesPage(Page<Consume> page, Date startTime, Date endTime, Integer dormitoryId);

	/**
	 * 根据宿舍ID和日期查询用电量
	 * 
	 * @param dormitoryNo
	 * @param date
	 * @return
	 */
	double getKwhByDormitoryIdAndDate(int dormitoryId, Date consumeDate);

	/**
	 * 根据宿舍ID和月份查询某月用电量总和
	 * 
	 * @param dormitoryId
	 * @param consumeDate
	 * @return
	 */
	double getKwhsByDormitoryIdAndMonth(int dormitoryId, int consumeYear, int consumeMonth);

	/**
	 * 根据宿舍ID和月份查询消费记录
	 * 
	 * @param dormitoryId
	 * @param consumeYear
	 * @param consumeMonth
	 * @return
	 */
	List<Consume> listConsumesByDormitoryIdAndMonth(int dormitoryId, int consumeYear, int consumeMonth);

	/**
	 * 根据宿舍ID和年份查询每月用电量总和
	 * 
	 * @param dormitoryId
	 * @param consumeYear
	 * @return
	 */
	double getKwhsByDormitoryIdAndYear(int dormitoryId, int consumeYear, int consumeMonth);

	/**
	 * 新增消费记录
	 * 
	 * @param consume
	 * @return
	 */
	int insertConsume(Consume consume);

	/**
	 * 根据宿舍ID和日期查询消费记录
	 * 
	 * @param dormitoryId
	 * @param date
	 * @return
	 */
	Consume getConsumeByDormitoryIdAndDate(int dormitoryId, Date consumeDate);
}
