package com.rocketmq.consume.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 *         2020年5月27日
 */
@Service
@Slf4j
public class RocketConsumeClient {

	@Value("${rocketmq.consume.name}")
	private String consumeGroup;
	@Value("${rocketmq.namesrv.addr}")
	private String nameAddr;

	private DefaultMQPushConsumer pushConsumer;

	@PostConstruct
	public void init() {
		pushConsumer = new DefaultMQPushConsumer();
		pushConsumer.setConsumerGroup(consumeGroup);
		pushConsumer.setNamesrvAddr(nameAddr);
		pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		pushConsumer.registerMessageListener(new ConsumerListener());
		try {
			pushConsumer.subscribe("CREATE_ORDER", "");
			pushConsumer.start();
		} catch (MQClientException e) {
			log.error("consme client failed consume group {}", consumeGroup, e);
		}
	}

	@PreDestroy
	public void destroy() {
		if (pushConsumer != null) {
			pushConsumer.shutdown();
		}
	}
}
