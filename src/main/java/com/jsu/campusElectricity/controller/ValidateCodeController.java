package com.jsu.campusElectricity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsu.campusElectricity.utils.FinalConstant;
import com.jsu.campusElectricity.utils.RandomValidateCode;

/**
 * @ClassName: ValidateCodeController.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年12月3日 下午6:38:07
 * @Description: 验证码生成和验证控制器
 */
@RestController
public class ValidateCodeController implements FinalConstant {
	/**
	 * 验证码生成
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@GetMapping("/getValidateCode")
	public String getSysManageLoginCode(HttpServletResponse response, HttpServletRequest request) {
		System.out.println("验证码生成Begin...");

		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Set-Cookie", "name=value; HttpOnly");// 设置HttpOnly属性,防止Xss攻击
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response, SESSION_VALIDATECODE);// 输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("验证码生成End...\n");
		return "success";
	}

	/**
	 * 验证码验证
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@GetMapping("/checkImageCode")
	public String checkCode(HttpServletRequest request, HttpSession session) {
		System.out.println("验证码验证Begin...");

		String validateCode = request.getParameter(REQUEST_VALIDATECODE);
		String code = (String) request.getSession().getAttribute(session.getId() + SESSION_VALIDATECODE);
		if (StringUtils.isEmpty(validateCode) && !validateCode.toLowerCase().equals(code.toLowerCase())) {
			return "error";
		}

		System.out.println("验证码验证End...\n");
		return "success";
	}
}
