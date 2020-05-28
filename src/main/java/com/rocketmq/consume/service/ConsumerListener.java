package com.rocketmq.consume.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author zhangxianbin
 *
 *         2020年5月27日
 */
@Slf4j
public class ConsumerListener implements MessageListenerConcurrently {

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		log.info("Topic Create_Order consume Message:{}", msgs);
		for (MessageExt messageExt : msgs) {
			log.info("message:{}", messageExt);
			try {
				String consumeBody = new String(messageExt.getBody(), "UTF-8");
				log.info("thread:{},queueId:{},body:{}", Thread.currentThread().getName(), messageExt.getQueueId(),
						consumeBody);
			} catch (UnsupportedEncodingException e) {
				log.error("decode message failed id:{}", messageExt.getMsgId());
			}
		}

		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

	}
}
