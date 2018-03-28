package com.aqlu.rocketmq.demo.orderstart;

import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
/**
 * 
 * @author lic
 * 顺序消费---提供着
 */
public class producer {

	public static void main(String[] args) {
		
		 DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
		 producer.setNamesrvAddr("192.168.89.99:9876;192.168.89.109:9876");
		 producer.setVipChannelEnabled(false);
		 try {
			producer.start();
			for (int i = 1; i <= 10; i++) {  
			   String body = "order1 " + i;
               Message msg = new Message("TopicOrderTest", "order1", "KEY" + i, body.getBytes());  
               SendResult sendResult = producer.send(msg,new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					 Integer id = (Integer) arg;  
//                     int index = id % mqs.size();
					 System.out.println(id);
                     return mqs.get(id);  
					}
               }, 1);// 1 队列的下标
               System.out.println(sendResult + "body :"+body);
           }  
			for (int i = 1; i <= 5; i++) { 
				   String body = "order2 " + i;
	               Message msg = new Message("TopicOrderTest", "order2", "KEY" + i, body.getBytes());  
	               SendResult sendResult = producer.send(msg,new MessageQueueSelector() {
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						 Integer id = (Integer) arg;  
						 System.out.println(id);
//	                     int index = id % mqs.size();  
	                     return mqs.get(id);  
						}
	               }, 2);
	               System.out.println(sendResult + "body :"+body);
	           } 
			for (int i = 1; i <= 3; i++) { 
				   String body = "order3 " + i;
	               Message msg = new Message("TopicOrderTest", "order3", "KEY" + i, body.getBytes());  
	               SendResult sendResult = producer.send(msg,new MessageQueueSelector() {
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						 Integer id = (Integer) arg;  
						 System.out.println(id);
//	                     int index = id % mqs.size();  
	                     return mqs.get(id);  
						}
	               }, 3);
	               System.out.println(sendResult + "body :"+body);
	           }  
           producer.shutdown();
		} catch (MQClientException e) {
			e.printStackTrace();
		}catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
