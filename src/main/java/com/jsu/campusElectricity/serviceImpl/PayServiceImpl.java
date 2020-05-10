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
import com.jsu.campusElectricity.mapper.PayMapper;
import com.jsu.campusElectricity.pojo.Pay;
import com.jsu.campusElectricity.service.PayService;

/**
 * @ClassName: PayServiceImp.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:52:58
 * @Description: 充值记录接口实现类
 */
@Service
public class PayServiceImpl implements PayService {
	@Autowired
	PayMapper payMapper;

	/**
	 * 查询宿舍最新的电费充值记录
	 */
	@Override
	public Pay getLatestPay(int dormitoryId) {
		QueryWrapper<Pay> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dorMitory_id", dormitoryId);
		Pay pay = payMapper.selectOne(queryWrapper.orderByDesc("pay_date").last("limit 1"));
		return pay;
	}

	/**
	 * 新增充值信息
	 */
	@Override
	public int insertPay(Pay pay) {
		int num = payMapper.insert(pay);
		return num;
	}

	/**
	 * 查询宿舍所有充值记录
	 */
	@Override
	public List<Pay> listPaysByDormitoryId(int dormitoryId) {
		QueryWrapper<Pay> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dorMitory_id", dormitoryId);
		List<Pay> payList = payMapper.selectList(queryWrapper.orderByDesc("pay_date"));
		return payList;
	}

	/**
	 * 根据宿舍ID分页查询充值记录
	 */
	@Override
	public IPage<Pay> listPaysPageByDormitoryId(Page<Pay> page, int dormitoryId) {
		IPage<Pay> payList = payMapper.listPaysPageByDormitoryId(page, dormitoryId);
		return payList;
	}

	/**
	 * 根据日期范围和宿舍ID分页查询充值记录
	 */
	@Override
	public IPage<Pay> listPaysPage(Page<Pay> page, Date startTime, Date endTime, Integer dormitoryId) {
		IPage<Pay> payList = payMapper.listPaysPage(page, startTime, endTime, dormitoryId);
		return payList;
	}

	/**
	 * 根据日期范围和管理员ID分页查询充值记录
	 */
	@Override
	public IPage<Pay> listAdminPaysPage(Page<Pay> page, Date startTime, Date endTime, Integer adminId) {
		IPage<Pay> payList = payMapper.listAdminPaysPage(page, startTime, endTime, adminId);
		return payList;
	}

	/**
	 * 根据日期范围和管理员ID计算充值金额总和
	 */
	@Override
	public double getAdminPaysSum(Date startTime, Date endTime, Integer adminId) {
		double paysSum = payMapper.getAdminPaysSum(startTime, endTime, adminId);
		return paysSum;
	}

}
