package com.xinwei.fiber.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class OpenAccountApplication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1901208801828353111L;
	//申请人信息
	private BasicInformation basicInformation;
	//开户客户信息
	private CustomerInfo customerInfo;
	
	
	public BasicInformation getBasicInformation() {
		return basicInformation;
	}
	public void setBasicInformation(BasicInformation basicInformation) {
		this.basicInformation = basicInformation;
	}
	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
