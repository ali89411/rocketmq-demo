package com.aqlu.rocketmq.demo.transactionstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
/**
 * 
 * @author lic
 * 事务消费---提供着LocalTransactionExecuterImpl
 */
public class producer {

	public static void main(String[] args) {
		
		 TransactionMQProducer producer = new TransactionMQProducer("tran_Producer");
		 producer.setNamesrvAddr("192.168.88.51:9876;192.168.88.61:9876");
		 producer.setVipChannelEnabled(false);
		 // 消息大于10m 自动压缩。
		 producer.setCompressMsgBodyOverHowmuch(1024 * 1024 * 10);
		 // 向broker 发送心跳。默认15s
		 producer.setHeartbeatBrokerInterval(1000*15);
		 // 消息最大size
		 producer.setMaxMessageSize(1024 * 1024 * 20);
		 // UNKNOW(如果executer回调函数通知状态失败,MQserver会定时监听CheckListener去检查executer回调状态)。消息处于PREPARED_TYPE状态
		 producer.setTransactionCheckListener(new TransactionCheckListener() {
			// 源码屏蔽此回调：1,ckeakflag未更改。--成功还是4标示(PREPARED_TYPE)状态
			//            2,无法使用redis保存中介数据，原因无法保证DB和redis数据的一致性。
			//            3,处理思路必须保证数据一致性。(可以根据consumer段以时间节点抽取无重复,最新的数据,返回producer进行确认超时数据是否消费,若无消费,可以进行发送下MQ操作)
			@Override
			public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
				// msg.getKeys(); 业务处理判断COMMIT,ROLLBACK.
				System.out.println("COMMIT_MESSAGE----");
				return LocalTransactionState.COMMIT_MESSAGE;
			}
		});
		 // 自己本地事务
		 LocalTransactionExecuterImpl executer = new LocalTransactionExecuterImpl();
		 try {
			 producer.start();
			 for (int i = 1; i <= 3; i++) {
			   String body = "转账到A账号:1000 -----"+i;
               Message msg = new Message("TransactionTest", "tran"+i, "KEY", body.getBytes());
               // 1,发送msg到MQ服务上 ; 
               // 2,根据executer回调函数通知状态(COMMIT_MESSAGE,ROLLBACK_MESSAGE),进行具体事务操作
               // 3,UNKNOW状态属于(第一点成功,第二点未收到通知,)
               // 因此事务消费发送2次
               SendResult sendResult = producer.sendMessageInTransaction(msg, executer, "tq");
               System.out.println(sendResult + "body :"+body);
			 }
           producer.shutdown();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

}
