/**
 * 
 */
package com.jsu.campusElectricity.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: IntercetporConfig.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月18日 下午4:53:14
 * @Description: 拦截器配置类
 */
@Configuration
public class IntercetporConfig implements WebMvcConfigurer {

	@Autowired
	LoginInterceptor loginInterceptor;

	/**
	 * 配置拦截器URL路径
	 * 
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user-login",
				"/user-register", "/getValidateCode", "/admin-login");
	}

}
