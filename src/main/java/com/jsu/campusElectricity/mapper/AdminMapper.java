/**
 * 
 */
package com.jsu.campusElectricity.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Admin;

/**
 * @ClassName: AdminMapper.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 上午10:15:27
 * @Description: 管理员数据库映射
 */
public interface AdminMapper extends BaseMapper<Admin> {

	/**
	 * 分页模糊查询管理员信息
	 * 
	 * @param page
	 * @param admin
	 * @return
	 */
	IPage<Admin> listAdminsPage(Page<Admin> page, @Param("admin") Admin admin);
}
