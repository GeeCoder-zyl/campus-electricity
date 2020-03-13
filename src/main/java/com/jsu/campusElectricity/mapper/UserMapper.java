package com.jsu.campusElectricity.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.pojo.User;

/**
 * @ClassName: UserDao.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 上午11:53:18
 * @Description: 用户数据库映射
 */
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 模糊查询用户信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<User> listUsersPage(Page<Pay> page, @Param("user") User user);
}
