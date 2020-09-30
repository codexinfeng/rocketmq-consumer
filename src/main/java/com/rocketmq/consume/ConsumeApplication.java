package com.rocketmq.consume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * 2020年5月27日
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ConsumeApplication implements ApplicationRunner {

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
		// redisTemplate.opsForValue().set("redis", "redis");
		//
		// System.out.println(redisTemplate.opsForValue().get("redis"));
		System.out.println(stringRedisTemplate.opsForValue().get("daily"));
		stringRedisTemplate.opsForValue().set("daily", "fruit");
		System.out.println(stringRedisTemplate.opsForValue().get("dailt"));
		stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

				Boolean returnFlag = connection.setNX("a".getBytes(), "b".getBytes());
				if (returnFlag) {
					connection.expire("a".getBytes(), 10);
				}
				return returnFlag;
			}
		});
		System.out.println(stringRedisTemplate.opsForValue().get("daily"));

	}
}
