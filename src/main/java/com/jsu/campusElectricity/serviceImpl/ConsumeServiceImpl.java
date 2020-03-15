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

	/**
	 * 根据宿舍ID和日期查询用电量
	 */
	@Override
	public double getKwhByDormitoryIdAndDate(int dormitoryId, Date consumeDate) {
		QueryWrapper<Consume> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dormitory_id", dormitoryId);
		queryWrapper.eq("consume_date", consumeDate);
		Consume consume = consumeMapper.selectOne(queryWrapper);
		if (consume == null) {
			return 0.0;
		} else {
			return consume.getConsumeKwh();
		}
	}

	/**
	 * 根据宿舍ID和月份查询某月用电量总和
	 */
	@Override
	public double getKwhsByDormitoryIdAndMonth(int dormitoryId, int consumeYear, int consumeMonth) {
		Double consumeKwh = consumeMapper.getKwhsByDormitoryIdAndMonth(dormitoryId, consumeYear, consumeMonth);
		if (consumeKwh == null) {
			return 0.0;
		}
		return consumeKwh;
	}

	/**
	 * 根据宿舍ID和月份查询消费记录
	 */
	@Override
	public List<Consume> listConsumesByDormitoryIdAndMonth(int dormitoryId, int consumeYear, int consumeMonth) {
		List<Consume> consumeList = consumeMapper.listConsumesByDormitoryIdAndMonth(dormitoryId, consumeYear,
				consumeMonth);
		return consumeList;
	}

	/**
	 * 根据宿舍ID和年份查询每月用电量总和
	 */
	@Override
	public double getKwhsByDormitoryIdAndYear(int dormitoryId, int consumeYear, int consumeMonth) {
		Double consumeKwh = consumeMapper.getKwhsByDormitoryIdAndYear(dormitoryId, consumeYear, consumeMonth);
		if (consumeKwh == null) {
			return 0.0;
		}
		return consumeKwh;
	}

	/**
	 * 新增消费记录
	 */
	@Override
	public int insertConsume(Consume consume) {
		return consumeMapper.insert(consume);
	}

	/**
	 * 根据宿舍ID和日期查询消费记录
	 */
	@Override
	public Consume getConsumeByDormitoryIdAndDate(int dormitoryId, Date consumeDate) {
		QueryWrapper<Consume> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dormitory_id", dormitoryId);
		queryWrapper.eq("consume_date", consumeDate);
		Consume consume = consumeMapper.selectOne(queryWrapper);
		return consume;
	}

}
