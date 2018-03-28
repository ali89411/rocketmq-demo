package com.aqlu.rocketmq.demo.transactionstart;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

/**
 * 实现LocalTransactionExecuter接口
 * 根据业务逻辑返回 事务具体标示
 */
public class LocalTransactionExecuterImpl implements LocalTransactionExecuter{

	/***
	 * 回调成功的话,证明第一条消息发往MQ成功!
	 */
	@Override
	public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
		System.out.println("msg = "+ new String(msg.getBody()));
		String tags = msg.getTags();
		if(tags.equals("tran2")){
			System.out.println("msg = "+ new String(msg.getBody()) + "账户余额不足-------事务回滚---------");
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
		if(tags.equals("tran3")){
			System.out.println("msg = "+ new String(msg.getBody()) + "LocalTransactionState.UNKNOW");
			return LocalTransactionState.UNKNOW;
		}
		return LocalTransactionState.COMMIT_MESSAGE;
	}

}
