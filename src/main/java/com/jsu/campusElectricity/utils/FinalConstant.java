package com.jsu.campusElectricity.utils;

/**
 * @ClassName: FinalConstant.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年12月3日 下午6:57:18
 * @Description: 常量控制器
 */
public interface FinalConstant {
	String SESSION_USER_ID = "userId";// 存储在服务器端session中的用户ID

	String COOKIE_USER_ID = "userId";// 存储在客户端cookie中的用户ID

	String COOKIE_USER_NAME = "userName";// 存储在客户端cookie中的用户名

	String SESSION_ADMIN_ID = "adminId";// 存储在服务器端session中的管理员ID

	String COOKIE_ADMIN_NAME = "adminName";// 存储在客户端cookie中的管理员名

	String SESSION_VALIDATECODE = "imgValidate";// 存储在服务器端session中的图片验证码

	String REQUEST_VALIDATECODE = "validateCode";// 客户端提交到服务器端的验证码

	String REQUEST_ERROR = "error";// 服务器端返回到客户端的错误信息
}
