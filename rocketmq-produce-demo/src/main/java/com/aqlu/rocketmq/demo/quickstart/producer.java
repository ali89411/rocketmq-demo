package com.aqlu.rocketmq.demo.quickstart;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class producer {

	public static void main(String[] args) {
		
		 DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
		 producer.setNamesrvAddr("192.168.89.99:9876;192.168.89.109:9876");
		 producer.setVipChannelEnabled(false);
		 try {
			producer.start();
			for (int i = 1; i <= 50; i++) {  
               Message msg = new Message("TopicOrderTest", "order_2", "KEY" + i, ("order_1 " + i).getBytes());  
               SendResult sendResult = producer.send(msg);
//               producer.sendt
               System.out.println(sendResult);
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
