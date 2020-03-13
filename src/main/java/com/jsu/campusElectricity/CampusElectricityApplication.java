package com.jsu.campusElectricity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: CampusElectricityApplication.java
 * @version: v1.0.0
 * @author: ZYL
 * @date: 2019年10月16日 上午11:54:34
 * @Description: 主启动类
 */
@SpringBootApplication
@MapperScan("com.jsu.campusElectricity.mapper") // 接口包扫描
public class CampusElectricityApplication {
	public static void main(String[] args) {
		SpringApplication.run(CampusElectricityApplication.class, args);
	}
}
