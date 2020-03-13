/**
 * 
 */
package com.jsu.campusElectricity.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Dormitory;
import com.jsu.campusElectricity.pojo.Pay;

/**
 * @ClassName: DormitoryMapper.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:36:01
 * @Description: 宿舍数据库映射
 */
public interface DormitoryMapper extends BaseMapper<Dormitory> {
	/**
	 * 根据宿舍号和送电状态分页模糊查询宿舍信息
	 * 
	 * @param dormitory
	 * @return
	 */
	IPage<Dormitory> listDormitorysPage(Page<Pay> page, @Param("dormitory") Dormitory dormitory);
}
