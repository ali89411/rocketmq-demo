package com.aqlu.rocketmq.demo.filterstart;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class producer {

	public static void main(String[] args) {
		
		 DefaultMQProducer producer = new DefaultMQProducer("filter_MQ");
		 producer.setNamesrvAddr("192.168.88.51:9876;192.168.88.61:9876");
		 producer.setVipChannelEnabled(false);
		 try {
			producer.start();
			for (int i = 1; i <= 50; i++) {  
               Message msg = new Message("filterTest", "order_2", "KEY" + i, ("order_1 " + i).getBytes());  
               SendResult sendResult = producer.send(msg);
               msg.putUserProperty("orderId", String.valueOf(i));
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
