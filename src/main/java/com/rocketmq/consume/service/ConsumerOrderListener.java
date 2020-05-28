package com.rocketmq.consume.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author zhangxianbin
 *
 *         2020年5月28日
 */
@Slf4j
public class ConsumerOrderListener implements MessageListenerOrderly {

	@Override
	public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
		context.setAutoCommit(true);
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
		try {
			TimeUnit.SECONDS.sleep(new Random().nextInt(10));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ConsumeOrderlyStatus.SUCCESS;
	}

}
