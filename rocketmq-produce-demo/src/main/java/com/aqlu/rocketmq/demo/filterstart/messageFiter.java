package com.aqlu.rocketmq.demo.filterstart;

import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

public class messageFiter implements MessageFilter{

	@Override
	public boolean match(MessageExt msg, FilterContext context) {
		String orderId = msg.getProperty("orderId");
		if(!"".equals(orderId)){
			Integer id = Integer.parseInt(orderId);
			if((id %2 == 0)){
				return true;
			}
		}
		return false;
	}

}
