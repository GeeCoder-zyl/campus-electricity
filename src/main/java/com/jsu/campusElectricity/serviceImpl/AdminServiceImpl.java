/**
 * 
 */
package com.jsu.campusElectricity.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsu.campusElectricity.mapper.AdminMapper;
import com.jsu.campusElectricity.pojo.Admin;
import com.jsu.campusElectricity.service.AdminService;

/**
 * @ClassName: AdminServiceImp.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 上午10:17:44
 * @Description: 管理员接口实现类
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminMapper adminMapper;

	/**
	 * 管理员登录
	 */
	@Override
	public Admin login(Admin admin) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("admin_name", admin.getAdminName());
		queryWrapper.eq("admin_password", admin.getAdminPassword());
		Admin admin1 = adminMapper.selectOne(queryWrapper);
		return admin1;
	}

	/**
	 * 根据管理员ID查询管理员
	 */
	@Override
	public Admin getAdminById(int adminId) {
		Admin admin = adminMapper.selectById(adminId);
		return admin;
	}

	/**
	 * 根据管理员名查询管理员
	 */
	@Override
	public Admin getAdminByName(String adminName) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("admin_name", adminName);
		Admin admin = adminMapper.selectOne(queryWrapper);
		return admin;
	}

}
