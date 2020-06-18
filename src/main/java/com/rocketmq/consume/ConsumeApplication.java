package com.rocketmq.consume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 *         2020年5月27日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ConsumeApplication implements ApplicationRunner{
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate; 
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate; 

	public static void main(String[] args) {
		
		SpringApplication.run(ConsumeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		
//		redisTemplate.opsForValue().set("redis", "redis");
//		
//		System.out.println(redisTemplate.opsForValue().get("redis"));
		stringRedisTemplate.opsForValue().set("daily", "fruit");
		System.out.println(stringRedisTemplate.opsForValue().get("daily"));
		
	}
}
