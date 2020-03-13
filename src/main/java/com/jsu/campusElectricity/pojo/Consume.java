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
 * @ClassName: Consume.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午9:48:02
 * @Description: 消费记录实体类
 */
@Data
@Accessors(chain = true)
@TableName("tb_consume")
public class Consume {
	@TableId(value = "pk_consume_id", type = IdType.AUTO)
	private Integer consumeId;// 消费记录ID

	@TableField(value = "consume_date")
	private Date consumeDate;// 消费日期

	@TableField(value = "consume_kwh")
	private Double consumeKwh;// 用电量（度）

	@TableField(value = "consume_amount")
	private Double consumeAmount;// 消费金额

	@TableField(value = "consume_balance")
	private Double consumeBalance;// 电费余额

	@TableField(value = "dormitory_id")
	private Integer dormitoryId;// 寝室号

}
