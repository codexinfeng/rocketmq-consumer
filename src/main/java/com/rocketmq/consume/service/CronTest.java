package com.rocketmq.consume.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author zhangxianbin
 *
 *  2020年6月23日
 */
@Service
public class CronTest {

	@Scheduled(cron="1-10 * * * *")
	public void cron(){
		System.out.println("*************");
	}
}


