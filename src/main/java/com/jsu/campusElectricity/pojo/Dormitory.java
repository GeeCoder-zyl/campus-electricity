/**
 * 
 */
package com.jsu.campusElectricity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: Dormitory.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午9:55:26
 * @Description: 宿舍实体类
 */
@Data
@Accessors(chain = true)
@TableName("tb_dormitory")
public class Dormitory {
	@TableId(value = "pk_dormitory_id", type = IdType.AUTO)
	private Integer dormitoryId;// 宿舍ID

	@TableField(value = "dormitory_no")
	private Integer dormitoryNo;// 宿舍号

	@TableField(value = "dormitory_balance")
	private Double dormitoryBalance;// 电费余额

	@TableField(value = "dormitory_status")
	private Integer dormitoryStatus;// 送电状态（0：断电 1：送电）

}
