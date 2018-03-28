package com.aqlu.rocketmq.demo.consumer;

import com.aqlu.rocketmq.demo.domain.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.impl.consumer.PullRequest;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.springframework.stereotype.Service;
/**
 * OrderPaidEventConsumer Created by aqlu on 2017/11/16.
 */
@Slf4j
//@Service
@RocketMQMessageListener(topic = "order-paid-topic", consumerGroup = "order-paid-consumer")
public class OrderPaidEventConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
//        log.info("------- OrderPaidEventConsumer received: {}", orderPaidEvent);
//    	DefaultMQPullConsumer 
        System.out.println("------- OrderPaidEventConsumer received: {}"+ orderPaidEvent);
    }
    
}
