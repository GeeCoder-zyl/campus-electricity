/**
 * 
 */
package com.jsu.campusElectricity.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.mapper.DormitoryMapper;
import com.jsu.campusElectricity.pojo.Dormitory;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.service.DormitoryService;

/**
 * @ClassName: DormitoryServiceImp.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:39:57
 * @Description: 宿舍接口实现类
 */
@Service
public class DormitoryServiceImpl implements DormitoryService {
	@Autowired
	DormitoryMapper dormitoryMapper;

	/**
	 * 根据宿舍ID查询宿舍信息
	 */
	@Override
	public Dormitory getDormitoryById(int dormitoryId) {
		Dormitory dormitory = dormitoryMapper.selectById(dormitoryId);
		return dormitory;
	}

	/**
	 * 根据宿舍号查询宿舍信息
	 */
	@Override
	public Dormitory getDormitoryByNo(int dormitoryNo) {
		QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dormitory_no", dormitoryNo);
		Dormitory dormitory = dormitoryMapper.selectOne(queryWrapper);
		return dormitory;
	}

	/**
	 * 修改宿舍信息
	 */
	@Override
	public int updateDormitory(Dormitory dormitory) {
		int num = dormitoryMapper.updateById(dormitory);
		return num;
	}

	/**
	 * 根据宿舍号和送电状态分页模糊查询宿舍信息
	 */
	@Override
	public IPage<Dormitory> listDormitorysPage(Page<Pay> page, Dormitory dormitory) {
		IPage<Dormitory> dormitoryList = dormitoryMapper.listDormitorysPage(page, dormitory);
		return dormitoryList;
	}

	/**
	 * 查询所有宿舍
	 */
	@Override
	public List<Dormitory> listDormitorys() {
		List<Dormitory> dormitoryList = dormitoryMapper.selectList(null);
		return dormitoryList;
	}

	/**
	 * 查询所有宿舍的数量
	 */
	@Override
	public int getDormitoryCount() {
		int num = dormitoryMapper.selectCount(null);
		return num;
	}

	/**
	 * 根据宿舍ID删除宿舍信息
	 */
	@Override
	public int deleteDormitoryById(int dormitoryId) {
		int num = dormitoryMapper.deleteById(dormitoryId);
		return num;
	}

	/**
	 * 新增宿舍
	 */
	@Override
	public int insertDormitory(Dormitory dormitory) {
		int num = dormitoryMapper.insert(dormitory);
		return num;
	}

}
