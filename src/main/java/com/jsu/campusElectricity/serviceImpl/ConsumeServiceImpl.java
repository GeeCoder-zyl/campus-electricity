/**
 * 
 */
package com.jsu.campusElectricity.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsu.campusElectricity.mapper.ConsumeMapper;
import com.jsu.campusElectricity.pojo.Consume;
import com.jsu.campusElectricity.service.ConsumeService;

/**
 * @ClassName: ConsumeServiceImp.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月17日 下午9:27:52
 * @Description: 消费记录接口实现类
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {
	@Autowired
	ConsumeMapper consumeMapper;

	/**
	 * 根据宿舍ID查询消费记录
	 */
	@Override
	public List<Consume> listConsumesByDormitoryId(int dormitoryId) {
		QueryWrapper<Consume> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dorMitory_id", dormitoryId);
		List<Consume> consumeList = consumeMapper.selectList(queryWrapper.orderByDesc("consume_date"));
		return consumeList;
	}

	/**
	 * 根据宿舍ID分页查询消费记录
	 */
	@Override
	public IPage<Consume> listConsumesPageByDormitoryId(Page<Consume> page, int dormitoryId) {
		IPage<Consume> consumeList = consumeMapper.listConsumesPageByDormitoryId(page, dormitoryId);
		return consumeList;
	}

	/**
	 * 根据日期范围和宿舍号分页查询消费记录
	 */
	@Override
	public IPage<Consume> listConsumesPage(Page<Consume> page, Date startTime, Date endTime, Integer dormitoryId) {
		IPage<Consume> consumeList = consumeMapper.listConsumesPage(page, startTime, endTime, dormitoryId);
		return consumeList;
	}

}
