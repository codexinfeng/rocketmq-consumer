package com.rocketmq.consume.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangxianbin
 *
 *         2020年6月18日
 */
@Configuration
@EnableAutoConfiguration
public class RedisFactoryConfig {

	@Value("${spring.redis.sentinel.master}")
	private String sentinalMaster;
	@Value("${spring.redis.password}")
	private String password;
	@Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
	private List<String> sentinalNodes;

	@Bean
	@ConfigurationProperties(prefix="spring.redis")
	public JedisPoolConfig JedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		return poolConfig;
	}

	@Bean
	public RedisSentinelConfiguration sentinelConfiguration() {
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
		redisSentinelConfiguration.setMaster(sentinalMaster);
		Set<RedisNode> nodes = new HashSet<>();
		sentinalNodes.forEach(serverAddr -> {
			nodes.add(new RedisNode(serverAddr.split(":")[0], Integer.valueOf(serverAddr.split(":")[1])));
		});
		redisSentinelConfiguration.setSentinels(nodes);
		redisSentinelConfiguration.setPassword(password);
		return redisSentinelConfiguration;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisSentinelConfiguration sentinelConfig,
			JedisPoolConfig poolConfig) {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(sentinelConfig, poolConfig);
		return connectionFactory;
	}

}
