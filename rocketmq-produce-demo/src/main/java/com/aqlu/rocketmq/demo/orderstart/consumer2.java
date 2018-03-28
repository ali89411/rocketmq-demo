package com.aqlu.rocketmq.demo.orderstart;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class consumer2 {
	public static void main(String[] args) {
		
		 DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_Consumer");  
	        consumer.setNamesrvAddr("192.168.89.99:9876;192.168.89.109:9876");
	        consumer.setVipChannelEnabled(false);
	        /** 
	         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br> 
	         * 如果非第一次启动，那么按照上次消费的位置继续消费 
	         */  
	        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);  
	        try {
				consumer.subscribe("TopicOrderTest", "*");
				consumer.registerMessageListener(new MessageListenerOrderly() {  
		            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {  
		                for (MessageExt msg : msgs) {  
		                	try {
								System.out.println("topic:" + msg.getTopic() + "; tags:"+msg.getTags() + " body:"+ new String(msg.getBody(), "utf-8"));
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
		                }  
		                return ConsumeOrderlyStatus.SUCCESS;
		            }

		        });  
		        consumer.start();  
		        System.out.println("Consumer2 Started.");  
			} catch (MQClientException e) {
				e.printStackTrace();
			} 
	}
}
