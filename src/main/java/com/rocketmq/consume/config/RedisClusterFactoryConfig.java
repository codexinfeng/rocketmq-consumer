package com.rocketmq.consume.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangxianbin
 *
 *         2020年9月30日
 */
@Configuration
@EnableAutoConfiguration
public class RedisClusterFactoryConfig {
	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;
	@Value("${spring.redis.password}")
	private String password;

	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public JedisPoolConfig JedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		return poolConfig;
	}

	@Bean
	public RedisClusterConfiguration getRedisClusterConfiguration() {
		RedisClusterConfiguration cluster = new RedisClusterConfiguration();
		List<RedisNode> nodeList = new ArrayList<>();
		String[] nodes = clusterNodes.split(",");
		for (String node : nodes) {
			String[] ns = node.split(":");
			RedisNode redisNode = new RedisNode(ns[0], Integer.valueOf(ns[1]));
			nodeList.add(redisNode);
		}
		cluster.setClusterNodes(nodeList);
		cluster.setPassword(password);
		return cluster;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration clusterConfiguration,
			JedisPoolConfig poolConfig) {
		JedisConnectionFactory factory = new JedisConnectionFactory(clusterConfiguration, poolConfig);
		return factory;

	}
}
