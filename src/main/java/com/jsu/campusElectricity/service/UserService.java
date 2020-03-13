package com.jsu.campusElectricity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.pojo.User;

/**
 * @ClassName: UserService.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 上午11:54:08
 * @Description: 用户接口
 */
public interface UserService {
	/**
	 * 分页模糊查询用户信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<User> listUsersPage(Page<Pay> page, User user);

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	int insertUser(User user);

	/**
	 * 根据用户名和密码查询用户信息
	 * 
	 * @param user
	 * @return
	 */
	User getUserLogin(String userName, String userPassword);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param UserName
	 * @return
	 */
	User getUserByUserName(String userName);

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	User getUserById(int userId);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	int updateUser(User user);

	/**
	 * 根据用户ID删除用户
	 * 
	 * @param userId
	 * @return
	 */
	int deleteUserById(int userId);

}
