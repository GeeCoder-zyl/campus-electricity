package com.jsu.campusElectricity.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.mapper.UserMapper;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.pojo.User;
import com.jsu.campusElectricity.service.UserService;

/**
 * @ClassName: UserServiceImp.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 上午11:54:20
 * @Description: 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;

	/**
	 * 分页模糊查询用户信息
	 */
	@Override
	public IPage<User> listUsersPage(Page<Pay> page, User user) {
		IPage<User> userList = userMapper.listUsersPage(page, user);
		return userList;
	}

	/**
	 * 新增用户
	 */
	@Override
	public int insertUser(User user) {
		int num = userMapper.insert(user);
		return num;
	}

	/**
	 * 根据用户名和密码查询用户信息
	 */
	@Override
	public User getUserLogin(String userName, String userPassword) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", userName);
		queryWrapper.eq("user_password", userPassword);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}

	/**
	 * 根据用户名查询用户信息
	 */
	@Override
	public User getUserByUserName(String userName) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", userName);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}

	/**
	 * 根据用户ID查询用户信息
	 */
	@Override
	public User getUserById(int userId) {
		User user = userMapper.selectById(userId);
		return user;
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public int updateUser(User user) {
		int num = userMapper.updateById(user);
		return num;
	}

	/**
	 * 根据用户ID删除用户
	 */
	@Override
	public int deleteUserById(int userId) {
		int num = userMapper.deleteById(userId);
		return num;
	}

}
