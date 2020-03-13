/**
 * 
 */
package com.jsu.campusElectricity.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Consume;

/**
 * @ClassName: ConsumeMapper.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 下午9:26:05
 * @Description: 消费记录数据库映射
 */
public interface ConsumeMapper extends BaseMapper<Consume> {
	/**
	 * 根据宿舍ID分页查询消费记录
	 * 
	 * @param page
	 * @param dormitoryId
	 * @return
	 */
	IPage<Consume> listConsumesPageByDormitoryId(Page<Consume> page, @Param("dormitoryId") int dormitoryId);

	/**
	 * 根据日期范围和宿舍号分页查询消费记录
	 * 
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @param dormitoryId
	 * @return
	 */
	IPage<Consume> listConsumesPage(Page<Consume> page, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime, @Param("dormitoryId") Integer dormitoryId);
}
