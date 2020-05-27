package com.rocketmq.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zhangxianbin
 *
 *         2020年5月27日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ConsumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumeApplication.class, args);
	}
}
