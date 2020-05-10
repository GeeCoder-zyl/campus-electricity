/**
 * 
 */
package com.jsu.campusElectricity.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.pojo.Dormitory;

/**
 * @ClassName: Dormitory.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:38:31
 * @Description: 宿舍接口
 */
public interface DormitoryService {
	/**
	 * 根据宿舍ID查询宿舍信息
	 * 
	 * @param dormitoryId
	 * @return
	 */
	Dormitory getDormitoryById(int dormitoryId);

	/**
	 * 根据宿舍号查询宿舍信息
	 * 
	 * @param dormitoryNo
	 * @return
	 */
	Dormitory getDormitoryByNo(int dormitoryNo);

	/**
	 * 修改宿舍信息
	 * 
	 * @param dormitory
	 * @return
	 */
	int updateDormitory(Dormitory dormitory);

	/**
	 * 根据宿舍号和送电状态分页模糊查询宿舍信息
	 * 
	 * @param dormitory
	 * @return
	 */
	IPage<Dormitory> listDormitorysPage(Page<Dormitory> page, Dormitory dormitory);

	/**
	 * 查询所有宿舍
	 * 
	 * @return
	 */
	List<Dormitory> listDormitorys();

	/**
	 * 查询所有宿舍的数量
	 * 
	 * @return
	 */
	int getDormitoryCount();

	/**
	 * 根据宿舍ID删除宿舍信息
	 * 
	 * @param dormitoryId
	 * @return
	 */
	int deleteDormitoryById(int dormitoryId);

	/**
	 * 新增宿舍
	 * 
	 * @param dormitory
	 * @return
	 */
	int insertDormitory(Dormitory dormitory);

}
