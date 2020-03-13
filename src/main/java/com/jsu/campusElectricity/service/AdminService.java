/**
 * 
 */
package com.jsu.campusElectricity.service;

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

}
