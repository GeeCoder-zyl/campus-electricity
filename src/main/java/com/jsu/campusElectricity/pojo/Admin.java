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
 * @ClassName: Admin.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 下午9:44:18
 * @Description: 管理员实体类
 */
@Data
@Accessors(chain = true)
@TableName("tb_admin")
public class Admin {
	@TableId(value = "pk_admin_id", type = IdType.AUTO)
	private Integer adminId;// 管理员ID

	@TableField(value = "admin_name")
	private String adminName;// 管理员名

	@TableField(value = "admin_password")
	private String adminPassword;// 管理员密码

	@TableField(value = "admin_status")
	private Integer adminStatus;// 管理员状态

	@TableField(value = "admin_level")
	private Integer adminLevel;// 管理员等级

	@TableField(value = "admin_real_name")
	private String adminRealName;// 管理员真实姓名

	@TableField(value = "admin_tel")
	private String adminTel;// 管理员电话
}
