package com.aqlu.rocketmq.demo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OrderPaidEvent Created by aqlu on 2017/11/16.
 */
@Data
@AllArgsConstructor
public class OrderPaidEvent implements Serializable {
    private String orderId;

    private BigDecimal paidMoney;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getPaidMoney() {
		return paidMoney;
	}

	public void setPaidMoney(BigDecimal paidMoney) {
		this.paidMoney = paidMoney;
	}

	public OrderPaidEvent(String orderId, BigDecimal paidMoney) {
		super();
		this.orderId = orderId;
		this.paidMoney = paidMoney;
	}
    
    
}
