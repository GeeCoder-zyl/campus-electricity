/**
 * 
 */
package com.jsu.campusElectricity.utils;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @ClassName: MybatisPlusConfig.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年11月26日 上午11:01:37
 * @Description: MyBatis-plus分页插件配置
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.jsu.campusElectricity.mapper.*.mapper*")
public class MybatisPlusConfig {
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求 默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		// paginationInterceptor.setLimit(500);
		return paginationInterceptor;
	}
}
