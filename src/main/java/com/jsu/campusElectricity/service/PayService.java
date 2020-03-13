/**
 * 
 */
package com.jsu.campusElectricity.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Pay;

/**
 * @ClassName: PayService.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:52:13
 * @Description: 充值记录接口
 */
public interface PayService {
	/**
	 * 查询宿舍最新的电费充值记录
	 * 
	 * @param dormitoryId
	 * @return
	 */
	Pay getLatestPay(int dormitoryId);

	/**
	 * 新增充值信息
	 * 
	 * @param pay
	 * @return
	 */
	int insertPay(Pay pay);

	/**
	 * 查询宿舍所有充值记录
	 * 
	 * @param dormitoryId
	 * @return
	 */
	List<Pay> listPaysByDormitoryId(int dormitoryId);

	/**
	 * 根据宿舍ID分页查询充值记录
	 * 
	 * @param page
	 * @param dormitoryId
	 * @return
	 */
	IPage<Pay> listPaysPageByDormitoryId(Page<Pay> page, int dormitoryId);

	/**
	 * 根据日期范围和宿舍号分页查询充值记录
	 * 
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param dormitoryId
	 * @return
	 */
	IPage<Pay> listPaysPage(Page<Pay> page, Date startTime, Date endTime, Integer dormitoryId);

}
