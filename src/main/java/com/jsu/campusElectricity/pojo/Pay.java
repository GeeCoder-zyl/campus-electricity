/**
 * 
 */
package com.jsu.campusElectricity.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: Pay.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午10:00:19
 * @Description: 充值记录实体类
 */
@Data
@Accessors(chain = true)
@TableName("tb_pay")
public class Pay {
	@TableId(value = "pk_pay_id", type = IdType.AUTO)
	private Integer payId;// 充值记录ID

	@TableField(value = "pay_date")
	private Date payDate;// 充值时间

	@TableField(value = "pay_amount")
	private Double payAmount;// 充值金额

	@TableField(value = "pay_manner")
	private Integer payManner;// 充值方式

	@TableField(value = "pay_pid")
	private Integer payPid;// 充值人ID

	@TableField(value = "dormitory_id")
	private Integer dormitoryId;// 宿舍ID

}
