/**
 * 
 */
package com.jsu.campusElectricity.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Pay;

/**
 * @ClassName: PayMapper.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:51:26
 * @Description: 充值记录数据库映射
 */
public interface PayMapper extends BaseMapper<Pay> {
	/**
	 * 根据宿舍ID分页查询充值记录
	 * 
	 * @param page
	 * @param dormitoryId
	 * @return
	 */
	IPage<Pay> listPaysPageByDormitoryId(Page<Pay> page, @Param("dormitoryId") int dormitoryId);

	/**
	 * 根据日期范围和宿舍ID分页查询充值记录
	 * 
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param dormitoryId
	 * @return
	 */
	IPage<Pay> listPaysPage(Page<Pay> page, @Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("dormitoryId") Integer dormitoryId);

	/**
	 * 根据日期范围和管理员ID分页查询充值记录
	 * 
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param adminId
	 * @return
	 */
	IPage<Pay> listAdminPaysPage(Page<Pay> page, @Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("adminId") Integer adminId);

	/**
	 * 根据日期范围和管理员ID计算充值金额总和
	 * 
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param adminId
	 * @return
	 */
	Double getAdminPaysSum(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("adminId") Integer adminId);
}
