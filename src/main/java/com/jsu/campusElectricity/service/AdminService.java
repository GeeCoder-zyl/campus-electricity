/**
 * 
 */
package com.jsu.campusElectricity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Admin;

/**
 * @ClassName: AdminService.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 上午10:16:23
 * @Description: 管理员接口
 */
public interface AdminService {

	/**
	 * 管理员登录
	 * 
	 * @param admin
	 * @return
	 */
	Admin login(Admin admin);

	/**
	 * 根据管理员ID查询管理员
	 * 
	 * @param adminId
	 * @return
	 */
	Admin getAdminById(int adminId);

	/**
	 * 根据管理员名查询管理员
	 * 
	 * @param adminName
	 * @return
	 */
	Admin getAdminByName(String adminName);

	/**
	 * 分页模糊查询管理员信息
	 * 
	 * @param page
	 * @param admin
	 * @return
	 */
	IPage<Admin> listAdminsPage(Page<Admin> page, Admin admin);

	/**
	 * 修改管理员信息
	 * 
	 * @param admin
	 * @return
	 */
	int updateAdmin(Admin admin);

	/**
	 * 新增管理员
	 * 
	 * @param admin
	 * @return
	 */
	int insertAdmin(Admin admin);

	/**
	 * 根据ID删除管理员
	 * 
	 * @param adminId
	 * @return
	 */
	int deleteAdminById(int adminId);

}
