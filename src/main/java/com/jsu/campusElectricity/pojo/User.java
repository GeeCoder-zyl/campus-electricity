package com.jsu.campusElectricity.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: User.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 上午11:53:54
 * @Description: 用户实体类
 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User {
	@TableId(value = "pk_user_id", type = IdType.AUTO)
	private Integer userId;// 用户ID

	@TableField(value = "user_name")
	private String userName;// 用户名

	@TableField(value = "user_password")
	private String userPassword;// 用户密码

	@TableField(value = "user_real_name", strategy = FieldStrategy.IGNORED)
	private String userRealName;// 真实姓名

	@TableField(value = "user_tel", strategy = FieldStrategy.IGNORED)
	private String userTel;// 联系电话

	@TableField(value = "user_status")
	private Integer userStatus;// 用户状态（0：禁用 1：启用）

	@TableField(value = "dormitory_id", strategy = FieldStrategy.IGNORED)
	private Integer dormitoryId;// 宿舍ID

}
